package bridge.parameters;


/**
 * Sets of parameters to model training. This parameters are based on Mallet training data methods.
 * <br><br>
 * 
 * Input options <p>
 * 
 * <pre> inputFilename
 * Input data file
 * </pre>
 *       
 * <pre> inputStateFilename
 * The filename from which to read the gzipped Gibbs sampling state created by
 * </pre>
 *       
 * Model parameters <p>
 * 
 * <pre> numTopics
 * The number of topics to fit
 * </pre>
 *       
 * <pre> randomSeed
 * The random seed for the Gibbs sampler. Default is 0, which will use the clock
 * </pre>
 *       
 * <pre> numIterations
 * The number of iterations of Gibbs sampling.
 * </pre>
 *       
 * <pre> numThreads
 * The number of threads for parallel training.
 * </pre>
 *       
 * <pre> noInference
 * Do not perform inference, just load a saved model and create a report. Equivalent to numIterations=0.
 * </pre>
 *       
 * <pre> numMaximizationIterations
 * The number of iterations of iterated conditional modes (topic maximization)
 * </pre>
 * 
 * Hyperparameters and hyperparameter optimization <p>
 * 
 * <pre> alpha
 * SumAlpha parameter: sum over topics of smoothing over doc-topic
 * distributions. alpha_k = [this value] / [num topics]
 * </pre>
 *       
 * <pre> beta
 * Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
 * </pre>
 *       
 * <pre> optimizeInterval
 * The number of iterations between reestimating dirichlet hyperparameters.
 * </pre>
 *       
 * <pre> optimizeBurnIn
 * The number of iterations to run before first estimating dirichlet hyperparameters.
 * </pre>
 *       
 * <pre> useSymetricAlpha
 * Only optimize the concentration parameter of the prior over document-topic
 * distributions. This may reduce the number of very small, poorly estimated
 * topics, but may disperse common words over several topics.
 * </pre>
 *       
 * Model Ouptut Options <p>      
 * 
 * <pre> outputStateInterval
 * The number of iterations between writing the sampling state to a text file.
 * You must also set the --output-state to use this option, whose argument will be 
 * the prefix of the filenames.
 * </pre>
 *       
 * Model Ouptut Options <p>
 *       
 * <pre> outputModelinterval
 * The number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
 * You must also set the --output-model to use this option, whose argument will be the prefix of the filenames.
 * </pre>
 *       
 * <pre> outputModelFilename
 * The filename in which to write the binary topic model at the end of the iterations.
 * </pre>
 *       
 * <pre> stateFile
 * The filename in which to write the Gibbs sampling state after at the end of the iterations.
 * </pre>
 *       
 * <pre> inferencerFilename
 * A topic inferencer applies a previously trained topic model to new documents.
 * </pre>
 *       
 * <pre> evaluatorFilename
 * A held-out likelihood evaluator for new documents.
 * </pre>
 * 
 * Reports <p>
 * 
 * <pre> showTopicsInterval
 * The number of iterations between printing a brief summary of the topics so far.
 * </pre>
 *       
 * <pre> topWords
 * The number of most probable words to print for each topic after model estimation.
 * </pre>
 *       
 * <pre> topicKeysFile
 * The filename in which to write the top words for each topic and any Dirichlet parameters. 
 * </pre>
 *       
 * <pre> topicDocsFile
 * The filename in which to write the most prominent documents for each topic, at the end of the iterations. 
 * </pre>
 *       
 * <pre> docTopicsFile
 * The filename in which to write the topic proportions per document, at the end of the iterations. 
 * </pre>
 *       
 * <pre> docTopicsThreshold
 * When writing topic proportions per document with --output-doc-topics 
 * do not print topics with proportions less than this threshold value.
 * </pre>
 *       
 * <pre> docTopicsMax
 * When writing topic proportions per document with --output-doc-topics, 
 * do not print more than INTEGER number of topics.
 * A negative value indicates that all topics should be printed.
 * </pre>
 *       
 * <pre> numTopDocs
 * When writing topic documents
 * </pre>
 *       
 * <pre> diagnosticFile
 * The filename in which to write measures of topic quality, in XML format
 * </pre>
 *       
 * <pre> topicReportXMLFile
 * The filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
 * </pre>
 *       
 * <pre> topicPhraseReportXMLFile
 * The filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
 * </pre>
 *       
 * <pre> topicWordWeightsFile
 * The filename in which to write unnormalized weights for every topic and word type.
 * </pre>
 *       
 * <pre> wordTopicCountsFile
 * The filename in which to write a sparse representation of topic-word assignments.
 * </pre>
 * 
 * @author Pedro Celard
 *
 */
public class TrainerParam {

	
	/* ************************************************************ 
	 * Input options
	 * **********************************************************/
	
		// Input data file
		private String inputFilename = "dataBridge.mallet";
	
		// The filename from which to read the gzipped Gibbs sampling state created by
		// --output-state.
		private String inputStateFilename = null;
		
		

	/* ************************************************************ 
	 * Model parameters
	 * **********************************************************/
		
		// The number of topics to fit
		private int numTopics = 10;
	
		// The random seed for the Gibbs sampler. Default is 0, which will use the clock
		private int randomSeed = 0;
	
		// The number of iterations of Gibbs sampling.
		private int numIterations = 1000;
		
		//The number of threads for parallel training.
		private int numThreads = 1;
	
		//Do not perform inference, just load a saved model and create a report. Equivalent to --num-iterations 0.
		private boolean noInference = false;
		
		//The number of iterations of iterated conditional modes (topic maximization)
		private int numMaximizationIterations = 0;
		
		
		
	/* ************************************************************ 
	 * Hyperparameters and hyperparameter optimization
	 * **********************************************************/
		
		// SumAlpha parameter: sum over topics of smoothing over doc-topic
		// distributions. alpha_k = [this value] / [num topics]
		private double alpha = 5.0;
	
		// Beta parameter: smoothing parameter for each topic-word. beta_w = [this
		// value]
		private double beta = 0.01;
	
		// The number of iterations between reestimating dirichlet hyperparameters.
		private int optimizeInterval = 0;
	
		// The number of iterations to run before first estimating dirichlet
		// hyperparameters.
		private int optimizeBurnIn = 200;
	
		// Only optimize the concentration parameter of the prior over document-topic
		// distributions. This may reduce the number of very small, poorly estimated
		// topics, but may disperse common words over several topics.
		private boolean useSymetricAlpha = false;
		
		
	/* ************************************************************ 
	 * Model Ouptut Options
	 * **********************************************************/
	
		//The number of iterations between writing the sampling state to a text file.
	    //You must also set the --output-state to use this option, whose argument will be 
		//the prefix of the filenames.
		private int outputStateInterval = 0;
		
		//The number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
		//You must also set the --output-model to use this option, whose argument will be the prefix of the filenames.
		private int outputModelinterval = 0;
		
		//The filename in which to write the binary topic model at the end of the iterations.
		private String outputModelFilename = null;
		
		//The filename in which to write the Gibbs sampling state after at the end of the iterations.
		private String stateFile = null;
		
		//A topic inferencer applies a previously trained topic model to new documents.
		private String inferencerFilename = null;
		
		//A held-out likelihood evaluator for new documents.
		private String evaluatorFilename = null;
		
		
	/* ************************************************************ 
	 * Reports
	 * **********************************************************/

		// The number of iterations between printing a brief summary of the topics so
		// far.
		private int showTopicsInterval = 25;
	
		// The number of most probable words to print for each topic after model
		// estimation.
		private int topWords = 20;
		
		//The filename in which to write the top words for each topic and any Dirichlet parameters. 
		private String topicKeysFile = null;
		
		//The filename in which to write the most prominent documents for each topic, at the end of the iterations. 
		private String topicDocsFile = null;
		
		//The filename in which to write the topic proportions per document, at the end of the iterations. 
		private String docTopicsFile = null;
		
		//When writing topic proportions per document with --output-doc-topics 
		//do not print topics with proportions less than this threshold value.
		private double  docTopicsThreshold = 0.0;
		
		// When writing topic proportions per document with --output-doc-topics,
		//do not print more than INTEGER number of topics.
		//A negative value indicates that all topics should be printed.
		private int docTopicsMax = -1;
		
		//When writing topic documents with --output-topic-docs
		private int numTopDocs = 100;
		
		//The filename in which to write measures of topic quality, in XML format
		private String diagnosticFile = null;
		
		//The filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
		private String topicReportXMLFile = null;
		
		//The filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
		private String topicPhraseReportXMLFile = null;
		
		//The filename in which to write unnormalized weights for every topic and word type.
		private String topicWordWeightsFile = null;
		
		//The filename in which to write a sparse representation of topic-word assignments. 
		private String wordTopicCountsFile = null;
		
		
		/**
		 * Default constructor
		 */
		public TrainerParam() {
			
		}
		
		/**
		 * Constructor setting common output files (most used for testing and studying LDA results)
		 * 
		 * @param numTopics Number of topics to fit
		 * @param inferencerFile Previously trained model to infer new documents
		 * @param evaluatorFile Held-out likelihood evaluator for new documents.
		 * @param diagnosticFile Filename in which to write measures of topic quality, in XML format
		 * @param docTopicsFile Filename in which to write the topic proportions per document, at the end of the iterations. 
		 * @param topicKeysFile Filename in which to write the top words for each topic and any Dirichlet parameters. 
		 */
		public TrainerParam(int numTopics, String inferencerFile, String evaluatorFile, String diagnosticFile, String docTopicsFile, String topicKeysFile) {
			this.numTopics = numTopics;
			this.inferencerFilename = inferencerFile;
			this.evaluatorFilename = evaluatorFile;
			this.diagnosticFile = diagnosticFile;
			this.docTopicsFile = docTopicsFile;
			this.topicKeysFile = topicKeysFile;
		}
		
		

		/**
		 * Gets input data file.
		 * 
		 * @return Input data file.
		 */
		public String getInputFilename() {
			return inputFilename;
		}

		/**
		 * Sets input data file.
		 * 
		 * @param inputFilename input data file name.
		 */
		public void setInputFilename(String inputFilename) {
			this.inputFilename = inputFilename;
		}

		/**
		 * Gets the filename from which to read the gzipped Gibbs sampling state.
		 * 
		 * @return the filename from which to read the gzipped Gibbs sampling state.
		 */
		public String getInputStateFilename() {
			return inputStateFilename;
		}

		/**
		 * Sets the filename from which to read the gzipped Gibbs sampling state.
		 * 
		 * @param inputStateFilename the filename from which to read the gzipped Gibbs sampling state.
		 */
		public void setInputStateFilename(String inputStateFilename) {
			this.inputStateFilename = inputStateFilename;
		}

		/**
		 * Gets the number of topics to fit.
		 * 
		 * @return the number of topics to fit.
		 */
		public int getNumTopics() {
			return numTopics;
		}

		/**
		 * Sets the number of topics to fit.
		 * 
		 * @param numTopics the number of topics to fit.
		 */
		public void setNumTopics(int numTopics) {
			this.numTopics = numTopics;
		}

		/**
		 * Gets the random seed for the Gibbs sampler. Default is 0, which will use the clock.
		 * 
		 * @return the random seed for the Gibbs sampler. Default is 0, which will use the clock.
		 */
		public int getRandomSeed() {
			return randomSeed;
		}

		/**
		 * Sets the random seed for the Gibbs sampler. Default is 0, which will use the clock.
		 * 
		 * @param randomSeed the random seed for the Gibbs sampler. Default is 0, which will use the clock.
		 */
		public void setRandomSeed(int randomSeed) {
			this.randomSeed = randomSeed;
		}

		/**
		 * Gets the number of iterations of Gibbs sampling.
		 * 
		 * @return the number of iterations of Gibbs sampling.
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
		 * Gets the number of threads for parallel training.
		 * 
		 * @return the number of threads for parallel training.
		 */
		public int getNumThreads() {
			return numThreads;
		}

		/**
		 * Sets the number of threads for parallel training.
		 * 
		 * @param numThreads the number of threads for parallel training.
		 */
		public void setNumThreads(int numThreads) {
			this.numThreads = numThreads;
		}

		/**
		 * Gets whether if perform inference.
		 * 
		 * @return whether if perform inference.
		 */
		public boolean isNoInference() {
			return noInference;
		}

		/**
		 * Sets whether if perform inference.
		 * 
		 * @param noInference whether if perform inference.
		 */
		public void setNoInference(boolean noInference) {
			this.noInference = noInference;
		}

		/**
		 * Gets the number of iterations of iterated conditional modes (topic maximization).
		 * 
		 * @return the number of iterations of iterated conditional modes (topic maximization).
		 */
		public int getNumMaximizationIterations() {
			return numMaximizationIterations;
		}

		/**
		 * Sets the number of iterations of iterated conditional modes (topic maximization).
		 * 
		 * @param numMaximizationIterations the number of iterations of iterated conditional modes (topic maximization).
		 */
		public void setNumMaximizationIterations(int numMaximizationIterations) {
			this.numMaximizationIterations = numMaximizationIterations;
		}

		/**
		 * Gets SumAlpha parameter, sum over topics of smoothing over doc-topic distributions.
		 * alpha_k = [this value] / [num topics]
		 * 
		 * @return SumAlpha parameter
		 */
		public double getAlpha() {
			return alpha;
		}

		/**
		 * Sets SumAlpha parameter, sum over topics of smoothing over doc-topic distributions.
		 * alpha_k = [this value] / [num topics]
		 * 
		 * @param alpha sum over topics of smoothing over doc-topic distributions.
		 */
		public void setAlpha(double alpha) {
			this.alpha = alpha;
		}

		/**
		 * Gets Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
		 * 
		 * @return smoothing parameter for each topic-word.
		 */
		public double getBeta() {
			return beta;
		}

		/**
		 * Sets Beta parameter: smoothing parameter for each topic-word. beta_w = [this value]
		 * 
		 * @param beta smoothing parameter for each topic-word.
		 */
		public void setBeta(double beta) {
			this.beta = beta;
		}

		/**
		 * Gets the number of iterations between reestimating dirichlet hyperparameters.
		 * 
		 * @return the number of iterations between reestimating dirichlet hyperparameters.
		 */
		public int getOptimizeInterval() {
			return optimizeInterval;
		}

		/**
		 * Sets the number of iterations between reestimating dirichlet hyperparameters.
		 * 
		 * @param optimizeInterval the number of iterations between reestimating dirichlet hyperparameters.
		 */
		public void setOptimizeInterval(int optimizeInterval) {
			this.optimizeInterval = optimizeInterval;
		}

		/**
		 * Gets the number of iterations to run before first estimating dirichlet hyperparameters.
		 * 
		 * @return the number of iterations to run before first estimating dirichlet hyperparameters.
		 */
		public int getOptimizeBurnIn() {
			return optimizeBurnIn;
		}

		/**
		 * Sets the number of iterations to run before first estimating dirichlet hyperparameters.
		 * 
		 * @param optimizeBurnIn the number of iterations to run before first estimating dirichlet hyperparameters.
		 */
		public void setOptimizeBurnIn(int optimizeBurnIn) {
			this.optimizeBurnIn = optimizeBurnIn;
		}

		/**
		 * Gets whether if only optimize the concentration parameter of the prior over document-topic distributions.
		 * 
		 * @return whether if only optimize the concentration parameter of the prior over document-topic distributions.
		 */
		public boolean isUseSymetricAlpha() {
			return useSymetricAlpha;
		}

		/**
		 * Sets whether if only optimize the concentration parameter of the prior over document-topic distributions.
		 * 
		 * @param useSymetricAlpha whether if only optimize the concentration parameter of the prior over document-topic distributions.
		 */
		public void setUseSymetricAlpha(boolean useSymetricAlpha) {
			this.useSymetricAlpha = useSymetricAlpha;
		}

		/**
		 * Gets the number of iterations between writing the sampling state to a text file.
		 * 
		 * @return the number of iterations between writing the sampling state to a text file.
		 */
		public int getOutputStateInterval() {
			return outputStateInterval;
		}

		/**
		 * Sets the number of iterations between writing the sampling state to a text file.
		 * 
		 * @param outputStateInterval the number of iterations between writing the sampling state to a text file.
		 */
		public void setOutputStateInterval(int outputStateInterval) {
			this.outputStateInterval = outputStateInterval;
		}

		/**
		 * Gets the number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
		 * 
		 * @return the number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
		 */
		public int getOutputModelinterval() {
			return outputModelinterval;
		}

		/**
		 * Sets the number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
		 * 
		 * @param outputModelinterval the number of iterations between writing the model (and its Gibbs sampling state) to a binary file.
		 */
		public void setOutputModelinterval(int outputModelinterval) {
			this.outputModelinterval = outputModelinterval;
		}

		/**
		 * Gets the filename in which to write the binary topic model at the end of the iterations.
		 * 
		 * @return the filename in which to write the binary topic model at the end of the iterations.
		 */
		public String getOutputModelFilename() {
			return outputModelFilename;
		}

		/**
		 * Sets the filename in which to write the binary topic model at the end of the iterations.
		 * 
		 * @param outputModelFilename the filename in which to write the binary topic model at the end of the iterations.
		 */
		public void setOutputModelFilename(String outputModelFilename) {
			this.outputModelFilename = outputModelFilename;
		}

		/**
		 * Gets the filename in which to write the Gibbs sampling state after at the end of the iterations.
		 * 
		 * @return the filename in which to write the Gibbs sampling state after at the end of the iterations.
		 */
		public String getStateFile() {
			return stateFile;
		}

		/**
		 * Sets the filename in which to write the Gibbs sampling state after at the end of the iterations.
		 * 
		 * @param stateFile the filename in which to write the Gibbs sampling state after at the end of the iterations.
		 */
		public void setStateFile(String stateFile) {
			this.stateFile = stateFile;
		}

		/**
		 * Gets a previously trained topic model file to new documents.
		 * 
		 * @return a previously trained topic model file to new documents.
		 */
		public String getInferencerFilename() {
			return inferencerFilename;
		}

		/**
		 * Sets a previously trained topic model file to new documents.
		 * 
		 * @param inferencerFilename a previously trained topic model file to new documents.
		 */
		public void setInferencerFilename(String inferencerFilename) {
			this.inferencerFilename = inferencerFilename;
		}

		/**
		 * Gets a held-out likelihood evaluator file for new documents.
		 * 
		 * @return a held-out likelihood evaluator file for new documents.
		 */
		public String getEvaluatorFilename() {
			return evaluatorFilename;
		}

		/**
		 * Sets a held-out likelihood evaluator file for new documents.
		 * 
		 * @param evaluatorFilename a held-out likelihood evaluator file for new documents.
		 */
		public void setEvaluatorFilename(String evaluatorFilename) {
			this.evaluatorFilename = evaluatorFilename;
		}

		/**
		 * Gets the number of iterations between printing a brief summary of the topics so far.
		 * 
		 * @return the number of iterations between printing a brief summary of the topics so far.
		 */
		public int getShowTopicsInterval() {
			return showTopicsInterval;
		}

		/**
		 * Sets the number of iterations between printing a brief summary of the topics so far.
		 * 
		 * @param showTopicsInterval the number of iterations between printing a brief summary of the topics so far.
		 */
		public void setShowTopicsInterval(int showTopicsInterval) {
			this.showTopicsInterval = showTopicsInterval;
		}

		/**
		 * Gets the number of most probable words to print for each topic after model estimation.
		 * 
		 * @return the number of most probable words to print for each topic after model estimation.
		 */
		public int getTopWords() {
			return topWords;
		}

		/**
		 * Sets the number of most probable words to print for each topic after model estimation.
		 * 
		 * @param topWords the number of most probable words to print for each topic after model estimation.
		 */ 
		public void setTopWords(int topWords) {
			this.topWords = topWords;
		}

		/**
		 * Gets the filename in which to write the top words for each topic and any Dirichlet parameters. 
		 * 
		 * @return the filename in which to write the top words for each topic and any Dirichlet parameters.
		 */
		public String getTopicKeysFile() {
			return topicKeysFile;
		}

		/**
		 * Sets the filename in which to write the top words for each topic and any Dirichlet parameters.
		 * 
		 * @param topicKeysFile the filename in which to write the top words for each topic and any Dirichlet parameters.
		 */
		public void setTopicKeysFile(String topicKeysFile) {
			this.topicKeysFile = topicKeysFile;
		}

		/**
		 * Gets the filename in which to write the most prominent documents for each topic, at the end of the iterations. 
		 * 
		 * @return the filename in which to write the most prominent documents for each topic, at the end of the iterations.
		 */
		public String getTopicDocsFile() {
			return topicDocsFile;
		}

		/**
		 * Sets the filename in which to write the most prominent documents for each topic, at the end of the iterations.
		 * 
		 * @param topicDocsFile the filename in which to write the most prominent documents for each topic, at the end of the iterations.
		 */
		public void setTopicDocsFile(String topicDocsFile) {
			this.topicDocsFile = topicDocsFile;
		}

		/**
		 * Gets the filename in which to write the topic proportions per document, at the end of the iterations. 
		 * 
		 * @return the filename in which to write the topic proportions per document, at the end of the iterations.
		 */
		public String getDocTopicsFile() {
			return docTopicsFile;
		}

		/**
		 * Sets the filename in which to write the topic proportions per document, at the end of the iterations.
		 * 
		 * @param docTopicsFile the filename in which to write the topic proportions per document, at the end of the iterations.
		 */
		public void setDocTopicsFile(String docTopicsFile) {
			this.docTopicsFile = docTopicsFile;
		}

		/**
		 * Gets value to not print topics with lower proportions than it.
		 * 
		 * @return value to not print topics with lower proportions than it.
		 */
		public double getDocTopicsThreshold() {
			return docTopicsThreshold;
		}

		/**
		 * Sets value to not print topics with lower proportions than it.
		 * 
		 * @param docTopicsThreshold value to not print topics with lower proportions than it.
		 */
		public void setDocTopicsThreshold(double docTopicsThreshold) {
			this.docTopicsThreshold = docTopicsThreshold;
		}

		/**
		 * Gets value to not print a number of topics greater than it.
		 * 
		 * @return value to not print a number of topics greater than it.
		 */
		public int getDocTopicsMax() {
			return docTopicsMax;
		}

		/**
		 * Sets value to not print a number of topics greater than it.
		 * 
		 * @param docTopicsMax value to not print a number of topics greater than it.
		 */
		public void setDocTopicsMax(int docTopicsMax) {
			this.docTopicsMax = docTopicsMax;
		}

		/**
		 * Gets number of max documents to be printed.
		 * 
		 * @return number of max documents to be printed.
		 */
		public int getNumTopDocs() {
			return numTopDocs;
		}

		/**
		 * Sets number of max documents to be printed.
		 * 
		 * @param numTopDocs number of max documents to be printed.
		 */
		public void setNumTopDocs(int numTopDocs) {
			this.numTopDocs = numTopDocs;
		}

		/**
		 * Gets the filename in which to write measures of topic quality, in XML format
		 * 
		 * @return the filename in which to write measures of topic quality, in XML format
		 */
		public String getDiagnosticFile() {
			return diagnosticFile;
		}

		/**
		 * Sets the filename in which to write measures of topic quality, in XML format
		 * 
		 * @param diagnosticFile the filename in which to write measures of topic quality, in XML format
		 */
		public void setDiagnosticFile(String diagnosticFile) {
			this.diagnosticFile = diagnosticFile;
		}

		/**
		 * Gets the filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
		 * 
		 * @return the filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
		 */
		public String getTopicReportXMLFile() {
			return topicReportXMLFile;
		}

		/**
		 * Sets the filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
		 * 
		 * @param topicReportXMLFile the filename in which to write the top words for each topic and any Dirichlet parameters in XML format.
		 */
		public void setTopicReportXMLFile(String topicReportXMLFile) {
			this.topicReportXMLFile = topicReportXMLFile;
		}

		/**
		 * Gets the filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
		 * 
		 * @return the filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
		 */
		public String getTopicPhraseReportXMLFile() {
			return topicPhraseReportXMLFile;
		}

		/**
		 * Sets the filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
		 * 
		 * @param topicPhraseReportXMLFile the filename in which to write the top words and phrases for each topic and any Dirichlet parameters in XML format.
		 */
		public void setTopicPhraseReportXMLFile(String topicPhraseReportXMLFile) {
			this.topicPhraseReportXMLFile = topicPhraseReportXMLFile;
		}

		/**
		 * Gets the filename in which to write unnormalized weights for every topic and word type.
		 * 
		 * @return the filename in which to write unnormalized weights for every topic and word type.
		 */
		public String getTopicWordWeightsFile() {
			return topicWordWeightsFile;
		}

		/**
		 * Sets the filename in which to write unnormalized weights for every topic and word type.
		 * 
		 * @param topicWordWeightsFile the filename in which to write unnormalized weights for every topic and word type.
		 */
		public void setTopicWordWeightsFile(String topicWordWeightsFile) {
			this.topicWordWeightsFile = topicWordWeightsFile;
		}

		/**
		 * Gets the filename in which to write a sparse representation of topic-word assignments. 
		 * 
		 * @return the filename in which to write a sparse representation of topic-word assignments.
		 */
		public String getWordTopicCountsFile() {
			return wordTopicCountsFile;
		}

		/**
		 * Sets the filename in which to write a sparse representation of topic-word assignments.
		 * 
		 * @param wordTopicCountsFile the filename in which to write a sparse representation of topic-word assignments.
		 */
		public void setWordTopicCountsFile(String wordTopicCountsFile) {
			this.wordTopicCountsFile = wordTopicCountsFile;
		}
}
