package bridge;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import bridge.parameters.ImporterParam;
import bridge.parameters.InfererParam;
import bridge.parameters.TrainerParam;
import bridge.utils.DataImport;
import bridge.utils.DataInfer;
import bridge.utils.DataTrain;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.IDSorter;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;
import weka.core.Instances;
import weka.core.Stopwords;
import weka.core.stemmers.LovinsStemmer;
import weka.core.stemmers.NullStemmer;
import weka.core.stemmers.Stemmer;
import weka.core.stopwords.Rainbow;

/**
 * Data import and model creation class
 * 
 * @author Pedro Celard
 *
 */
public class LdaBridge implements Serializable{
	
	/** For serialization */
	private static final long serialVersionUID = -318015018244478507L;
	/*
	   * Action mode:
	   * 		0 = Same data for training and inferencing
	   * 		1 = Train and save model
	   * 		2 = Read model and inference 
	   */
	public static int ALL_MODE = 0;
	public static int SAVE_MODE = 1;
	public static int READ_MODE = 2;
	
	private static InstanceList instanceList = null;
	
  	/** If true, remove a default list of common English "stop words" from the text. */
	protected boolean removeStopWords = false;
	
	/** The number of topics to fit */
	protected int numTopics = 10;
	
	/** The number of most probable words to print for each topic after model estimation. */
	protected int numTopWords = 20; 
	
	/** The number of threads for parallel training. */
	protected int numThreads = 1;
	
	/** The number of iterations of Gibbs sampling. */
	protected int numIterations = 1000;
	
	/** SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics] */
	protected double alpha = 0.5;
	
	/** Beta parameter: smoothing parameter for each topic-word. beta_w = [this value] */
	protected double beta = 0.01;
	
	/** The number of iterations to run before first estimating dirichlet hyperparameters. */
	protected int burnIn = 200;
	
	/** the stemming algorithm. */
	protected Stemmer stemmer = new NullStemmer();
	  
	static protected int docLength = 0;
  	
  	public static TopicInferencer inferencer = null;
  	
	public static void main (String[] args) {
		
	}
	
	/**
	 * Default constructor
	 */
	public LdaBridge() {
		
	}
	
	
	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String numTopicsTipText() {
		return "The number of topics to fit";
	}
	
	/**
	 * Gets the number of topics to fit.
	 * 
	 * @return the number of topics to fit
	 */
	public int getNumTopics() {
		return numTopics;
	}

	/**
	 * 
	 * Sets the number of topics to fit.
	 * 
	 * @param numTopics the number of topics to fit
	 */
	public void setNumTopics(int numTopics) {
		this.numTopics = numTopics;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String numTopWordsTipText() {
		return "The number of most probable words to print for each topic after model estimation.";
	}
	
	/**
	 * Returns the number of most probable words to print for each topic after model estimation.
	 * 
	 * @return The number of most probable words to print for each topic after model estimation.
	 */
	public int getNumTopWords() {
		return numTopWords;
	}

	/**
	 * Sets the number of most probable words to print for each topic after model estimation.
	 * 
	 * @param numTopWords the number of most probable words to print for each topic after model estimation.
	 */
	public void setNumTopWords(int numTopWords) {
		this.numTopWords = numTopWords;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String numThreadsTipText() {
		return "The number of threads for parallel training.";
	}
	
	/**
	 * Returns the number of threads for parallel training.
	 * 
	 * @return the number of threads for parallel training.
	 */
	public int getNumThreads() {
		return numThreads;
	}

	/**
	 * Sets the number of threads for parallel training.
	 * @param numThreads the number of threads for parallel training.
	 */
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String numIterationsTipText() {
		return "The number of iterations of Gibbs sampling.";
	}
	
	/**
	 * Returns the number of iterations of Gibbs sampling.
	 * 
	 * @return the number of iterations of Gibbs sampling.
	 */
	public int getNumIterations() {
		return numIterations;
	}

	/**
	 * Sets the number of iterations of Gibbs sampling.
	 * @param numIterations the number of iterations of Gibbs sampling.
	 */
	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String alphaTipText() {
		return "SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. "
				+ "alpha_k = [this value] / [num topics] ";
	}
	
	/**
	 * Returns SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics] 
	 *
	 * @return SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics] 
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * Sets SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics] 
	 * 
	 * @param alpha sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics] 
	 */
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String betaTipText() {
		return "Beta parameter: smoothing parameter for each topic-word. "
				+ "beta_w = [this value]";
	}
	
	/**
	 * Returns Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
	 * 
	 * @return Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * Sets Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
	 * @param beta smoothing parameter for each topic-word. beta_w = [this value]
	 */
	public void setBeta(double beta) {
		this.beta = beta;
	}

	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String burnInTipText() {
		return "The number of iterations to run before first estimating dirichlet hyperparameters.";
	}
	
	/**
	 * Returns the number of iterations to run before first estimating dirichlet hyperparameters.
	 * 
	 * @return the number of iterations to run before first estimating dirichlet hyperparameters.
	 */
	public int getBurnIn() {
		return burnIn;
	}

	/**
	 * Sets the number of iterations to run before first estimating dirichlet hyperparameters.
	 * @param burnIn the number of iterations to run before first estimating dirichlet hyperparameters.
	 */
	public void setBurnIn(int burnIn) {
		this.burnIn = burnIn;
	}

	/**
	 * Returns the current stemming algorithm, null if none is used.
	 *
	 * @return the current stemming algorithm, null if none set
	 */
	public Stemmer getStemmer() {
	  return stemmer;
	}
	
	/**
	 * the stemming algorithm to use, null means no stemming at all (i.e., the
	 * NullStemmer is used).
	 *
	 * @param value the configured stemming algorithm, or null
	 * @see NullStemmer
	 */
	public void setStemmer(Stemmer value) {
	  if (value != null) {
	    stemmer = value;
	  } else {
	    stemmer = new NullStemmer();
	  }
	}
	
	/**
	 * Returns the tip text for this property.
	 * 
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String stemmerTipText() {
	  return "The stemming algorithm to use on the words.";
	}
	
	
	public static int getDocLength() {
		return docLength;
	}

	public static void setDocLength(int docLength) {
		LdaBridge.docLength = docLength;
	}

	/**
	 * Process individual instances to get LDA distributions. This method must be 
	 * called after model training and first batch of data
	 * has been already processed.
	 * 
	 * @param data Original text to process
	 * @param importParam Import parameters
	 * @param trainParam Training parameters
	 * @param inferParam Inferencing parameters
	 * 
	 * @return Set of topic distributions
	 */
	public ArrayList<double[]> processIntance(String[] data, ImporterParam importParam, TrainerParam trainParam, InfererParam inferParam) {
		
		 //Delete stopwords and get word roots
		 String[] stemmedData = preprocessText(data, stemmer);
		
		 //Obtains data from weka
		 DataImport dataImp = new DataImport(importParam);
		 instanceList = dataImp.importDataFromWeka(stemmedData, instanceList);
		 		 
		 //Process data and obtains topic distributions
		 DataInfer dataInfer = new DataInfer(inferParam);
		 return dataInfer.infer(inferencer, instanceList);
	}

	/**
	 * Process a group of instances. This method creates the original model and trains it.
	 * 
	 * @param data Original data obtained from corpus
	 * @param importParam import parameters
	 * @param trainParam training parameters
	 * @param inferParam inferencer parameters
	 * 
	 * @return List of sets of topic distributions
	 */
	public ArrayList<double[]> processBatch(String[] data, ImporterParam importParam, TrainerParam trainParam, InfererParam inferParam) {
		
		/* ***************************************
		 * IMPORT DATA
		 * **************************************/
		
		DataImport dataImp = new DataImport(importParam);
		String[] stemmedData = preprocessText(data, stemmer);
		instanceList = dataImp.importDataFromWeka(stemmedData, null);
		
		
		/* ***************************************
		 * TRAIN MODEL
		 * **************************************/
		
		DataTrain dataTrain = new DataTrain(trainParam);
		
		//Obtains a trained model
		inferencer = dataTrain.train(instanceList).getInferencer();
		
		 if (inferParam.getRandomSeed() != 0) {
			inferencer.setRandomSeed(inferParam.getRandomSeed());
		}
			
		
		/* ***************************************
		 * INFER DOCUMENTS
		 * **************************************/
		
		DataInfer dataInfer = new DataInfer(inferParam);
		return dataInfer.infer(inferencer, instanceList);
		
		
	}
	
	/**
	 * Pre-process original text deleting stopwords and obtaining each word root.
	 * 
	 * @param data
	 * @return
	 */
	public static String[] preprocessText(String[] data, Stemmer stemmer) {
		
		//LovinsStemmer ls = new LovinsStemmer();
		String[] stemmedData = new String[data.length];
		int docLengthCount = 0;
		for(int i=0; i<data.length; i++) {
				
			String[] wordsData = data[i].split("['\\s+]");
			
			for(int k=0; k<wordsData.length; k++) {
				 if(k==0) {
					 stemmedData[i] = "";
				 }
				 if(!Stopwords.isStopword(wordsData[k])) {
						 stemmedData[i] += stemmer.stem(wordsData[k])+" ";
					
				 }
				 
				 if(k==wordsData.length-1) {
					 docLengthCount += new StringTokenizer(stemmedData[i]).countTokens();
				 }
			 }
			
			
		}
		
		docLength = docLengthCount/data.length;
		return stemmedData;
	}
	
	
	public ParallelTopicModel generateModel(String[] data, ImporterParam importParam, TrainerParam trainParam) {
		
		/* ***************************************
		 * IMPORT DATA
		 * **************************************/
		DataImport dataImp = new DataImport(importParam);
		String[] stemmedData = preprocessText(data, stemmer);
		instanceList = dataImp.importDataFromWeka(stemmedData, null);
		

		/* ***************************************
		 * TRAIN MODEL
		 * **************************************/
		DataTrain dataTrain = new DataTrain(trainParam);
		return dataTrain.train(instanceList);
		
	}
}
