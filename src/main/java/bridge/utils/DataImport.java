package bridge.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import bridge.parameters.ImporterParam;
import cc.mallet.classify.tui.Text2Vectors;
import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.CharSequenceLowercase;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.SaveDataInSource;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceNGrams;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.pipe.iterator.FileIterator;
import cc.mallet.pipe.iterator.StringArrayIterator;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import cc.mallet.util.MalletLogger;

/**
 * Import data from one or more directories
 * 
 * Based on Text2Vectors.java
 * 
 * @author Pedro Celard
 *
 */
public class DataImport {
	
	private static Logger logger = MalletLogger.getLogger(DataImport.class.getName());

	private static ImporterParam importParam = null;
	
	/**
	 * Default constructor
	 */
	public DataImport() {
		
	}
	
	/**
	 * Main constructor
	 * 
	 * @param impParam Import parameters
	 */
	public DataImport(ImporterParam impParam) {
		
		this.importParam = impParam;
		
	}


	/**
	 * Import data from directories
	 * 
	 * @return Set of instances inside an object InstanceList obtained from original data.
	 */
	public static InstanceList importDataFromDir() {
		
		/* *****************************************************************
		 *  Load data directories
		 * ****************************************************************/
		
		logger.info ("Labels = ");
		File[] directories = new File[importParam.getDirectories().length];
		for (int i = 0; i < importParam.getDirectories().length; i++) {
			directories[i] = new File (importParam.getDirectories()[i]);
			logger.info ("   "+importParam.getDirectories()[i]);
		}

		
		/* *****************************************************************
		 *  Build a new pipe
		 * ****************************************************************/

		// Create a list of pipes that will be added to a SerialPipes object later
		ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
		
		// Convert the "target" object into a numeric index
		//  into a LabelAlphabet.
		pipeList.add(new Target2Label());
		
		// The "data" field is currently a filename. Save it as "source".
		pipeList.add( new SaveDataInSource() );
		
		pipeList.add( new Input2CharSequence(importParam.getEncoding()) );
		
		if (!importParam.isPreserveCase()) {
			pipeList.add(new CharSequenceLowercase());
		}
		
		
		/* *****************************************************************
		 * Tokenize the input
		 * ****************************************************************/
		 
		//Compile the tokenization pattern
		Pattern tokenPattern = Pattern.compile(importParam.getTokenPattern());
		
		// Add the tokenizer
		pipeList.add(new CharSequence2TokenSequence(tokenPattern));
		
		

		/* *****************************************************************
		 * Remove stop words
		 * ****************************************************************/
		if (importParam.isRemoveStopWords()) {
			
			TokenSequenceRemoveStopwords stopwordFilter = new TokenSequenceRemoveStopwords(false, false);
			pipeList.add(stopwordFilter);
		}
		
		// gramSizes is an integer array, with default value [1].
		//  Check if we have a non-default value.
		if (! (importParam.getGramSizes().length == 1 && importParam.getGramSizes()[0] == 1)) {
			pipeList.add( new TokenSequenceNGrams(importParam.getGramSizes()) );
		}
		
		pipeList.add( new TokenSequence2FeatureSequence() );
		//pipeList.add( new FeatureSequence2AugmentableFeatureVector(false) );
		
		if (importParam.isPrintOutput()) {
			pipeList.add(new PrintInputAndTarget());
		}

		Pipe instancePipe = new SerialPipes(pipeList);
		
		InstanceList instances = new InstanceList (instancePipe);
		
		boolean removeCommonPrefix = true;
		instances.addThruPipe (new FileIterator (directories, FileIterator.STARTING_DIRECTORIES, removeCommonPrefix));
		
		
		return instances;
	}
	
	/**
	 * Import data from weka
	 * 
	 * @param data original texts from corpus loaded in weka.
	 * @param instanceList Set of instances, if not null contains the Alphabet used to train a model.
	 * 
	 * @return Set of instances inside an object InstanceList obtained from original data.
	 */
	public InstanceList importDataFromWeka(String[] data, InstanceList instanceList) {

		/* *****************************************************************
		 *  Build a new pipe
		 * ****************************************************************/

		// Create a list of pipes that will be added to a SerialPipes object later
		ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
		
		// The "data" field is currently a filename. Save it as "source".
		pipeList.add( new SaveDataInSource() );
		
		pipeList.add( new Input2CharSequence(importParam.getEncoding()));
		
		if (!importParam.isPreserveCase()) {
			pipeList.add(new CharSequenceLowercase());
		}
		
		
		/* *****************************************************************
		 * Tokenize the input
		 * ****************************************************************/
		 
		//Compile the tokenization pattern
		Pattern tokenPattern = Pattern.compile(importParam.getTokenPattern());
		
		// Add the tokenizer
		pipeList.add(new CharSequence2TokenSequence(tokenPattern));
		
		

		/* *****************************************************************
		 * Remove stop words
		 * ****************************************************************/
		if (importParam.isRemoveStopWords()) {
			
			TokenSequenceRemoveStopwords stopwordFilter = new TokenSequenceRemoveStopwords(false, false);
			pipeList.add(stopwordFilter);
		}
		
		// gramSizes is an integer array, with default value [1].
		//  Check if we have a non-default value.
		if (! (importParam.getGramSizes().length == 1 && importParam.getGramSizes()[0] == 1)) {
			pipeList.add( new TokenSequenceNGrams(importParam.getGramSizes()) );
		}
		
		pipeList.add( new TokenSequence2FeatureSequence() );
		
		if (importParam.isPrintOutput()) {
			pipeList.add(new PrintInputAndTarget());
		}
		
		Pipe instancePipe = new SerialPipes(pipeList);
		
		InstanceList instances;
		
		if(instanceList!=null) {
			instances = new InstanceList (instancePipe);
			StringArrayIterator iter =  new StringArrayIterator(data);
			
			
			while(iter.hasNext()) {
				Instance ins =iter.next();
				instances= instanceList.cloneEmpty();
				instances.addThruPipe(ins);
			}
			
		} else {
			instances = new InstanceList (instancePipe);
			instances.addThruPipe( new StringArrayIterator(data));
		}
		
		
		return instances;
	}
}














	

