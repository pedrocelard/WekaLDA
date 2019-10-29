/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 *    LDA.java
 *
 */


package weka.filters.unsupervised.attribute;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import bridge.LdaBridge;
import bridge.parameters.ImporterParam;
import bridge.parameters.InfererParam;
import bridge.parameters.TrainerParam;
import weka.core.*;
import weka.core.Capabilities.Capability;
import weka.filters.SimpleBatchFilter;
import weka.filters.UnsupervisedFilter;

/** 
 *
 * @author Pedro Celard
 * @version
 */
public class LDA_OLD extends SimpleBatchFilter
  implements UnsupervisedFilter, WeightedAttributesHandler, WeightedInstancesHandler {

  /** for serialization */
  static final long serialVersionUID = 5022109283147503266L;
  
  /*
   * Action mode:
   * 		0 = Same data for training and inferencing
   * 		1 = Train and save model
   * 		2 = Read model and inference 
   */
  private int ALL_MODE = 0;
  private int SAVE_MODE = 1;
  private int READ_MODE = 2;
  
  private String SET_ME = "-- set me --";
  
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
  
  /** File to save the model to */
  protected File save_modelFile = new File(SET_ME);
  
  /** File to read the model from */
  protected File read_modelFile = new File(SET_ME);
  
  /**
   * Gets an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  @Override
  public Enumeration<Option> listOptions() {

    Vector<Option> result = new Vector<Option>(10);

//    result.addElement(new Option(
//            "\tRemove a default list of common English \"stop words\" from the text.\n" + "\t(default: true)", "rm-stop-words", 1,
//            "-rm-stop-words <eemoveStopWords>"));
//    
    
    result.addElement(new Option(
            "\tSpecifies number of Topics\n" + "\t(default: 10)", "num-topics", 1,
            "-num-topics <numTopics>"));
    
    
    result.addElement(new Option(
            "\tThe number of most probable words to print for each topic after model estimation.\n" + "\t(default: 20)", "num-top-words", 1,
            "-num-top-words <numTopWords>"));
    
    
    result.addElement(new Option(
            "\tThe number of threads for parallel training.\n" + "\t(default: 1)", "num-threads", 1,
            "-num-threads <numThreads>"));
    
    
    result.addElement(new Option(
            "\tThe number of iterations of Gibbs sampling.\n" + "\t(default: 1000)", "num-iterations", 1,
            "-num-iterations <numIterations>"));
    
    
    result.addElement(new Option(
            "\tSumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics]\n" + "\t(default: 0.5)", "alpha", 1,
            "-alpha <alpha>"));
    
    
    result.addElement(new Option(
            "\tBeta parameter: smoothing parameter for each topic-word. beta_w = [this value]\n" + "\t(default: 0.01)", "beta", 1,
            "-beta <beta>"));
    
    
    result.addElement(new Option(
            "\tThe number of iterations to run before first estimating dirichlet hyperparameters.\n" + "\t(default: 200)", "burn-in", 1,
            "-burn-in <burnIn>"));
    
    result.addElement(new Option("\tThe file to save the model to.\n"
    	      + "\t(default is not to save the model)", "save-file", 1,
    	      "-save-file <path to save to>"));
    
    result.addElement(new Option("\tThe file to read the model from.\n"
  	      + "\t(default is not to read the model)", "read-file", 1,
  	      "-read-file <path to read from>"));
    
    return result.elements();
  }
  
  
  
  /**
   * Parses a given list of options.
   * <p/>
   *
   * <!-- options-start -->
   * Valid options are: <p>
   * 
   * <pre> -R &lt;col1,col2-col4,...&gt;
   *  Specifies list of columns to modify. First and last are valid indexes.
   *  (default: first-last)</pre>
   * 
   * <pre> -V
   *  Invert matching sense of column indexes.</pre>
   * 
   * <pre> -S &lt;num&gt;
   *  Specify the random number seed (default 1)</pre>
   * 
   * <pre> -P &lt;double&gt;
   *  Specify the probability  (default 0.1)</pre>
   * 
   * <pre> -unset-class-temporarily
   *  Unsets the class index temporarily before the filter is
   *  applied to the data.
   *  (default: no)</pre>
   * 
   * <!-- options-end -->
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  @Override
  public void setOptions(String[] options) throws Exception {

	  String value = Utils.getOption("num-topics", options);
	  if (value.length() != 0) {
		  setNumTopics(Integer.valueOf(value).intValue());
	    } else {
	    	setNumTopics(10);
	    }

	  if (getInputFormat() != null) {
		  setInputFormat(getInputFormat());
	  }

	  super.setOptions(options);

	  Utils.checkForRemainingOptions(options);
  }

  
  /** 
   * Returns the Capabilities of this filter.
   *
   * @return            the capabilities of this object
   * @see               Capabilities
   */
  public Capabilities getCapabilities() {
    Capabilities result = super.getCapabilities();
    result.disableAll();

    // attributes
    result.enableAllAttributes();
    result.enable(Capability.MISSING_VALUES);
    
    // class
    result.enableAllClasses();
    result.enable(Capability.MISSING_CLASS_VALUES);
    result.enable(Capability.NO_CLASS);
    
    return result;
  }

  
  
  /**
   * Gets the current settings of the filter.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  @Override
  public String[] getOptions() {

    Vector<String> result = new Vector<String>();

//    if (!getAttributeIndices().equals("")) {
//      result.add("-N");
//      result.add(getAttributeIndices());
//    }

    Collections.addAll(result, super.getOptions());

    return result.toArray(new String[result.size()]);
  }
  
  /**
   * Returns a string describing this filter
   *
   * @return a description of the filter suitable for
   * displaying in the explorer/experimenter gui
   */
  public String globalInfo() {
    return "LDA data filter \n "
    		+ "Change original text to LDA representation";
  }

  
  
  	/**
 	* Removes Stopwords
 	*
 	* @param 
 	*/
//	@OptionMetadata(displayName = "Remove stopwords",
//			description = "Remove a default list of common English \"stop words\" from the text.",
//			commandLineParamName = "rm-stop-words",
//			commandLineParamSynopsis = "-rm-stop-words <boolean>", displayOrder = 0)
	public void setRemoveStopWords(boolean removeStopWords) {
		this.removeStopWords = removeStopWords;
	}

	
	public boolean isRemoveStopWords() {
	  	return removeStopWords;
	}
	
	
	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Number of topics",
  			description = "Number of topics to fit.",
  			commandLineParamName = "num-topics",
  			commandLineParamSynopsis = "-num-topics <int>", displayOrder = 1)
  	public void setNumTopics(int numTopics) {
	  	this.numTopics = numTopics;
  	}
  
  	public int getNumTopics() {
	  	return numTopics;
  	}

 
  	
  	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Number of top words",
  			description = "The number of most probable words to print for each topic after model estimation.",
  			commandLineParamName = "num-top-words",
  			commandLineParamSynopsis = "-num-top-words <int>", displayOrder = 2)
	public void setNumTopWords(int numTopWords) {
		this.numTopWords = numTopWords;
	}

	public int getNumTopWords() {
		return numTopWords;
	}
	
	
	
  	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Number of threads",
  			description = "The number of threads for parallel training.",
  			commandLineParamName = "num-threads",
  			commandLineParamSynopsis = "-num-threads <int>", displayOrder = 3)
	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}
	
	public int getNumThreads() {
		return numThreads;
	}
	
	
	
	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Number of iterations",
  			description = "The number of iterations of Gibbs sampling.",
  			commandLineParamName = "num-iterations",
  			commandLineParamSynopsis = "-num-iterations <int>", displayOrder = 4)
	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}
	
  	public int getNumIterations() {
		return numIterations;
	}
	
	

  	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Alpha",
  			description = "SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. alpha_k = [this value] / [num topics].",
  			commandLineParamName = "alpha",
  			commandLineParamSynopsis = "-alpha <double>", displayOrder = 5)
	public double getAlpha() {
		return alpha;
	}
	
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	
	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Beta",
  			description = "Beta parameter: smoothing parameter for each topic-word. beta_w = [this value].",
  			commandLineParamName = "beta",
  			commandLineParamSynopsis = "-beta <double>", displayOrder = 6)
	public double getBeta() {
		return beta;
	}
	
	
	public void setBeta(double beta) {
		this.beta = beta;
	}
	
	
	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Burn In",
  			description = "The number of iterations to run before first estimating dirichlet hyperparameters.",
  			commandLineParamName = "burn-in",
  			commandLineParamSynopsis = "-burn-in <int>", displayOrder = 7)
	public int getBurnIn() {
		return burnIn;
	}
	
	
	public void setBurnIn(int burnIn) {
		this.burnIn = burnIn;
	}

	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Save model dir.",
  			description = "The file to save the model to.",
  			commandLineParamName = "save-file",
  			commandLineParamSynopsis = "-save-file <File>", displayOrder = 7)
	public File getSave_modelFile() {
		return save_modelFile;
	}

	public void setSave_modelFile(File save_modelFile) {

		this.save_modelFile = save_modelFile;
		if(!getSave_modelFile().getPath().equals(SET_ME)) {
			this.read_modelFile = new File(SET_ME);
		}

	}


	/**
   	* Set the modification text to apply
   	*
   	* @param text the text to apply
   	*/
  	@OptionMetadata(displayName = "Read model dir.",
  			description = "The file to read the model from.",
  			commandLineParamName = "read-file",
  			commandLineParamSynopsis = "-read-file <File>", displayOrder = 7)
	public File getRead_modelFile() {
		return read_modelFile;
	}

	public void setRead_modelFile(File read_modelFile) {
		
		this.save_modelFile = new File(SET_ME);
		
		this.read_modelFile = read_modelFile;
	}



	/**
   * Returns the revision string.
   * 
   * @return		the revision
   */
  public String getRevision() {
    return RevisionUtils.extract("$Revision$");
  }



	@Override
	protected Instances determineOutputFormat(Instances inputFormat) throws Exception {
		Instances instances = new Instances(inputFormat, inputFormat.numInstances());
		return instances;
	}
	
	@Override
	protected Instances process(Instances instances) throws Exception {
//		 if (isFirstBatchDone()) {
//		      return instances;
//	    }

		// Copies references to the header information from the given set of instances
	    //Instances resultData = new Instances(determineOutputFormat(instances), instances.numInstances());
		ArrayList<Attribute> attrList = new ArrayList<>();

	    for(int i=0; i<getNumTopics();i++) {
	    	String attrName = "topic_"+i;
	    	attrList.add(new Attribute(attrName));
	    }
	    
	    attrList.add(instances.attribute(1).copy("@@class@@"));
	    
		Instances resultDataInstances = new Instances("resultInstance",attrList,instances.numInstances());
		super.setOutputFormat(resultDataInstances);
		
	    String[] data = new String[instances.numInstances()];
	    String[] dataClass = new String[instances.numInstances()];
	    
	    //Recorre las instancias obtenidas
	    int k=0;
	    for (Instance inst : instances) {
	    	
	      String text = inst.stringValue(0);
	      String textClass = inst.stringValue(1);
	      data[k] = text;
	      dataClass[k]= textClass;
	      k++;
	    }
	    
	    //Importer configuration
	    ImporterParam importTrainParam = new ImporterParam(isRemoveStopWords());
	    ImporterParam importTestParam = new ImporterParam(isRemoveStopWords());
	    
	    //Trainer configuration
//	    TrainerParam trainParam = new TrainerParam(getNumTopics(),
//				null /*"C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeTopic-model"*/,
//				"C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeEvaluator",
//				"C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeDiagnostic.xml",
//				"C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeDocTopics.txt",
//				"C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeTopicKeys.txt");
	    
	    TrainerParam trainParam = new TrainerParam(getNumTopics(),null,null,null,null,null);
	    trainParam.setTopWords(getNumTopWords());
	    trainParam.setNumThreads(getNumThreads());
	    trainParam.setNumIterations(getNumIterations());
	    trainParam.setAlpha(getAlpha());
	    trainParam.setBeta(getBeta());
	    trainParam.setOptimizeBurnIn(getBurnIn());
	    
	    //Inferencer configuration
//	    InfererParam inferParam = new InfererParam("C:\\Users\\Pedro\\Desktop\\TFG\\Workspace\\mallet-2.0.8\\Workspace\\RESULTADOS\\BridgeOutputKeys");
	    InfererParam inferParam = new InfererParam(null);
	    
	    int mode = ALL_MODE;
	    
	    if(!getSave_modelFile().getPath().equals(SET_ME) && getRead_modelFile().getPath().equals(SET_ME)) {
	    	
	    	mode = SAVE_MODE;
	    	trainParam.setInferencerFilename(getSave_modelFile().getPath());
	    	
	    }else if(getSave_modelFile().getPath().equals(SET_ME) && !getRead_modelFile().getPath().equals(SET_ME)) {
	    	
	    	mode = READ_MODE;
	    	inferParam.setInferencerFilename(getRead_modelFile().getPath());
	    	
	    }else if(!getSave_modelFile().getPath().equals(SET_ME) && !getRead_modelFile().getPath().equals(SET_ME)) {
	    	
	    	throw new Exception("Please, select only a reading or a saving directory.");
	    	
	    }
	    LdaBridge ldaBridge = new LdaBridge();
	    ArrayList<double[]> distributions = ldaBridge.processBatch(data,importTrainParam,trainParam,inferParam);
	    
	    if(mode != SAVE_MODE) {
	    	for(int e=0; e<distributions.size(); e++) {
		    	DenseInstance denseInst = new DenseInstance(1,distributions.get(e));
		    	denseInst.insertAttributeAt(getNumTopics());
		    	denseInst.setDataset(resultDataInstances);
		    	if(!dataClass[e].equals("?")) {
		    		denseInst.setValue(numTopics, dataClass[e]);
		    	}
		    	
		    	resultDataInstances.add(denseInst);
		    	
		    	
	    		
		    }

	    	resultDataInstances.setClassIndex(resultDataInstances.numAttributes()-1);
	    	
	    }else {
	    	
	    	super.setOutputFormat(instances);
	    	return instances;
	    }
	    
	    
	  return resultDataInstances;
	}
	
	/**
	 * Main method for testing this class.
	 *
	 * @param argv should contain arguments to the filter: use -h for help
	 */
	public static void main(String [] argv) {
	  runFilter(new LDA_OLD(), argv);
	}
}
