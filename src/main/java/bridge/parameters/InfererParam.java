package bridge.parameters;

/**
 * 
 * Sets of parameters to data inferencing. This parameters are based on Mallet infer data methods.
 * <br><br>
 * 
 * <pre> inferencerFilename
 * A serialized topic inferencer from a previous trained topic model.</pre>
 * 
 * <pre> inputFile
 * The filename from which to read the list of instances for which topics should be inferred.  Use - for stdin.
 * The instances must be FeatureSequence or FeatureSequenceWithBigrams, not FeatureVector
 * </pre>
 * 
 * <pre> docTopicsFile
 * The filename in which to write the inferred topic proportions per document.
 * </pre>
 * 
 * <pre> docTopicsThreshold
 * When writing topic proportions per document with --output-doc-topics do not print topics with proportions less than this threshold value.
 * </pre>
 * 
 * <pre> docTopicsMax
 * When writing topic proportions per document with --output-doc-topics do not print more than INTEGER number of topics.
 * A negative value indicates that all topics should be printed.
 * </pre>
 * 
 * <pre> numIterations
 * The number of iterations of Gibbs sampling.
 * </pre>
 * 
 * <pre> sampleInterval
 * The number of iterations between saved samples
 * </pre>
 * 
 * <pre> burnInIterations
 * The number of iterations before the first sample is saved.
 * </pre>
 * 
 * <pre> randomSeed
 * The random seed for the Gibbs sampler.  Default is 0, which will use the clock.
 * </pre>
 * 
 * @author Pedro Celard
 *
 */
public class InfererParam {
	// A serialized topic inferencer from a trained topic model.
	private String inferencerFilename = null;
	
	//The filename from which to read the list of instances for which topics should be inferred.
	//The instances must be FeatureSequence or FeatureSequenceWithBigrams, not FeatureVector
	private String inputFile = null;
	
	//The filename in which to write the inferred topic proportions per document.
	private String docTopicsFile = null;
	
	//When writing topic proportions per document with --output-doc-topics do not print topics with proportions less than this threshold value.
	private double docTopicsThreshold = 0.0;
	
	//When writing topic proportions per document with --output-doc-topics do not print more than INTEGER number of topics.
    //A negative value indicates that all topics should be printed.
	private int docTopicsMax = -1;
	
	//The number of iterations of Gibbs sampling.
	private int numIterations = 100;
	
	//The number of iterations between saved samples
	private int sampleInterval = 10;
	
	//The number of iterations before the first sample is saved.
	private int burnInIterations = 10;
	
	//The random seed for the Gibbs sampler.  Default is 0, which will use the clock.
	private int randomSeed = 0;

	/**
	 * Default constructor
	 */
	public InfererParam() {
		
	}
	
	/**
	 * Constructor setting the file to write the distributions obtained
	 * @param docTopicsFile
	 */
	public InfererParam(String docTopicsFile) {
		this.docTopicsFile = docTopicsFile;
	}
	
	/**
	 * Gets a serialized topic inferencer from a previously trained topic model.
	 * 
	 * @return File where a serialized topic inferencer from a trained topic model is located.
	 */
	public String getInferencerFilename() {
		return inferencerFilename;
	}

	/**
	 * Sets a serialized topic inferencer from a trained topic model file.
	 * 
	 * @param inferencerFilename file where serialized topic inferencer from a trained topic model is located.
	 */
	public void setInferencerFilename(String inferencerFilename) {
		this.inferencerFilename = inferencerFilename;
	}

	/**
	 * Gets the filename from which to read the list of instances for which topics should be inferred.
	 * 
	 * @return The filename from which to read the list of instances for which topics should be inferred. 
	 */
	public String getInputFile() {
		return inputFile;
	}

	/**
	 * Sets the filename from which to read the list of instances for which topics should be inferred.
	 * 
	 * @param inputFile the filename from which to read the list of instances for which topics should be inferred.
	 */
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	 * Gets the filename in which to write the inferred topic proportions per document.
	 * 
	 * @return the filename in which to write the inferred topic proportions per document.
	 */
	public String getDocTopicsFile() {
		return docTopicsFile;
	}

	/**
	 * Sets the filename in which to write the inferred topic proportions per document.
	 * 
	 * @param docTopicsFile the filename in which to write the inferred topic proportions per document.
	 */
	public void setDocTopicsFile(String docTopicsFile) {
		this.docTopicsFile = docTopicsFile;
	}

	/**
	 * Do not print topics with proportions less than this threshold value.
	 * 
	 * @return do not print topics with proportions less than this threshold value.
	 */
	public double getDocTopicsThreshold() {
		return docTopicsThreshold;
	}

	/**
	 * Do not print topics with proportions less than this threshold value.
	 * 
	 * @param docTopicsThreshold Threshold value
	 */
	public void setDocTopicsThreshold(double docTopicsThreshold) {
		this.docTopicsThreshold = docTopicsThreshold;
	}

	/**
	 * Gets max number of topics to be printed
	 * 
	 * @return max number of topics to be printed
	 */
	public int getDocTopicsMax() {
		return docTopicsMax;
	}

	/**
	 * Sets max number of topics to be printed.
	 * 
	 * @param docTopicsMax max number of topics to be printed
	 */
	public void setDocTopicsMax(int docTopicsMax) {
		this.docTopicsMax = docTopicsMax;
	}

	/**
	 * Gets The number of iterations of Gibbs sampling.
	 *  
	 * @return number of iterations of Gibbs sampling.
	 */
	public int getNumIterations() {
		return numIterations;
	}

	/**
	 * Sets the number of iterations of Gibbs sampling.
	 * 
	 * @param numIterations the number of iterations of Gibbs sampling.
	 */
	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	/**
	 * Gets the number of iterations between saved samples.
	 * 
	 * @return the number of iterations between saved samples
	 */
	public int getSampleInterval() {
		return sampleInterval;
	}

	/**
	 * Sets the number of iterations between saved samples
	 * 
	 * @param sampleInterval the number of iterations between saved samples
	 */
	public void setSampleInterval(int sampleInterval) {
		this.sampleInterval = sampleInterval;
	}

	/**
	 * Gets the number of iterations before the first sample is saved.
	 * 
	 * @return the number of iterations before the first sample is saved.
	 */
	public int getBurnInIterations() {
		return burnInIterations;
	}

	/**
	 * Sets the number of iterations before the first sample is saved. 
	 * 
	 * @param burnInIterations the number of iterations before the first sample is saved.
	 */
	public void setBurnInIterations(int burnInIterations) {
		this.burnInIterations = burnInIterations;
	}

	/**
	 * Gets the random seed for the Gibbs sampler. Default is 0, which will use the clock.
	 * 
	 * @return The random seed for the Gibbs sampler.
	 */
	public int getRandomSeed() {
		return randomSeed;
	}

	/**
	 * Sets the random seed for the Gibbs sampler.
	 * 
	 * @param randomSeed the random seed for the Gibbs sampler.
	 */
	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}
	
}
