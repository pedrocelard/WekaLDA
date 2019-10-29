package bridge.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.logging.Logger;

import bridge.parameters.TrainerParam;
import cc.mallet.pipe.iterator.DBInstanceIterator;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.topics.TopicModelDiagnostics;
import cc.mallet.topics.tui.TopicTrainer;
import cc.mallet.types.FeatureSequence;
import cc.mallet.types.InstanceList;
import cc.mallet.util.MalletLogger;

/**
 * Train a topic model
 * 
 * @author Pedro Celard
 *
 */
public class DataTrain {

	private static Logger logger = MalletLogger.getLogger(DataTrain.class.getName());

	
	private static TrainerParam trainParam = null;
	
	/**
	 * Default constructor
	 */
	public DataTrain() {
		
	}
	
	/**
	 * Main constructor
	 * 
	 * @param trainParam training parameters
	 */
	public DataTrain(TrainerParam trainParam) {
		
		this.trainParam = trainParam;
	}
	
	
	/**
	 * Trains a topic model.
	 * 
	 * @param training Set of instances to train.
	 * 
	 * @return trained topic inferencer.
	 */
	public ParallelTopicModel train(InstanceList training) {
		
				
			ParallelTopicModel topicModel = new ParallelTopicModel(trainParam.getNumTopics(), trainParam.getAlpha(), trainParam.getBeta());

			try {
			if (trainParam.getRandomSeed() != 0) {
				topicModel.setRandomSeed(trainParam.getRandomSeed());
			}
				
			if (training.size() > 0 && training.get(0) != null) {
				Object data = training.get(0).getData();
				if (!(data instanceof FeatureSequence)) {
					logger.warning(
							"Topic modeling currently only supports feature sequences: use --keep-sequence option when importing data.");
					System.exit(1);
				}
			}

			topicModel.addInstances(training);
				
				
			if (trainParam.getInputStateFilename() != null) {
				logger.info("Initializing from saved state.");
				topicModel.initializeFromState(new File(trainParam.getInputStateFilename()));
			}
	
			topicModel.setTopicDisplay(trainParam.getShowTopicsInterval(), trainParam.getTopWords());
	
			topicModel.setNumIterations(trainParam.getNumIterations());
			topicModel.setOptimizeInterval(trainParam.getOptimizeInterval());
			topicModel.setBurninPeriod(trainParam.getOptimizeBurnIn());
			topicModel.setSymmetricAlpha(trainParam.isUseSymetricAlpha());
	
			if (trainParam.getOutputStateInterval() != 0) {
				topicModel.setSaveState(trainParam.getOutputStateInterval(), trainParam.getStateFile());
			}
	
			if (trainParam.getOutputModelinterval() != 0) {
				topicModel.setSaveSerializedModel(trainParam.getOutputModelinterval(), trainParam.getOutputModelFilename());
			}
	
			topicModel.setNumThreads(trainParam.getNumThreads());
	
			if (!trainParam.isNoInference()) {
				topicModel.estimate();
			}
	
			if (trainParam.getNumMaximizationIterations() > 0) {
				topicModel.maximize(trainParam.getNumMaximizationIterations());
			}
	
			if (trainParam.getTopicKeysFile() != null) {
				topicModel.printTopWords(new File(trainParam.getTopicKeysFile()), trainParam.getTopWords(), false);
			}
	
			if (trainParam.getDiagnosticFile() != null) {
				PrintWriter out = new PrintWriter(trainParam.getDiagnosticFile());
				TopicModelDiagnostics diagnostics = new TopicModelDiagnostics(topicModel, trainParam.getTopWords());
				out.println(diagnostics.toXML());
				out.close();
			}
	
			if (trainParam.getTopicReportXMLFile() != null) {
				PrintWriter out = new PrintWriter(trainParam.getTopicReportXMLFile());
				topicModel.topicXMLReport(out, trainParam.getTopWords());
				out.close();
			}
	
			if (trainParam.getTopicPhraseReportXMLFile() != null) {
				PrintWriter out = new PrintWriter(trainParam.getTopicPhraseReportXMLFile());
				topicModel.topicPhraseXMLReport(out, trainParam.getTopWords());
				out.close();
			}
	
			if (trainParam.getStateFile() != null && trainParam.getOutputStateInterval() == 0) {
				topicModel.printState(new File(trainParam.getStateFile()));
			}
	
			if (trainParam.getTopicDocsFile() != null) {
				PrintWriter out = new PrintWriter(new FileWriter((new File(trainParam.getTopicDocsFile()))));
				topicModel.printTopicDocuments(out, trainParam.getNumTopDocs());
				out.close();
			}
	
			if (trainParam.getDocTopicsFile() != null) {
				PrintWriter out = new PrintWriter(new FileWriter((new File(trainParam.getDocTopicsFile()))));
				if (trainParam.getDocTopicsThreshold() == 0.0) {
					topicModel.printDenseDocumentTopics(out);
				} else {
					topicModel.printDocumentTopics(out, trainParam.getDocTopicsThreshold(), trainParam.getDocTopicsMax());
				}
				out.close();
			}
			
			if (trainParam.getTopicWordWeightsFile() != null) {
				topicModel.printTopicWordWeights(new File(trainParam.getTopicWordWeightsFile()));
			}
	
			if (trainParam.getWordTopicCountsFile() != null) {
				
					topicModel.printTypeTopicCounts(new File(trainParam.getWordTopicCountsFile()));
				
			}
	
			if (trainParam.getOutputModelFilename() != null) {
				assert (topicModel != null);
				try {
	
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trainParam.getOutputModelFilename()));
					oos.writeObject(topicModel);
					oos.close();
	
				} catch (Exception e) {
					logger.warning("Couldn't write topic model to filename " + trainParam.getOutputModelFilename());
				}
			}
	
			if (trainParam.getInferencerFilename() != null) {
				try {
	
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trainParam.getInferencerFilename()));
					oos.writeObject(topicModel.getInferencer());
					oos.close();
	
				} catch (Exception e) {
					logger.warning("Couldn't create inferencer: " + e.getMessage());
				}
	
			}
	
			if (trainParam.getEvaluatorFilename() != null) {
				try {
	
					ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trainParam.getEvaluatorFilename()));
					oos.writeObject(topicModel.getProbEstimator());
					oos.close();
	
				} catch (Exception e) {
					logger.warning("Couldn't create evaluator: " + e.getMessage());
				}
	
			}
		
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return topicModel;
	}
	
	

}
