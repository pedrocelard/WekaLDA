package bridge.utils;

import java.io.File;
import java.util.ArrayList;

import bridge.parameters.InfererParam;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

/**
 * Obtain topic distribution from text 
 * 
 * @author Pedro Celard
 *
 */
public class DataInfer {

	private static InfererParam inferParam = null;
	
	/**
	 * Default constructor
	 */
	public DataInfer() {
		
	}
	
	/**
	 * Main constructor 
	 * @param inferParam inferencer parameters
	 */
	public DataInfer(InfererParam inferParam) {
		this.inferParam = inferParam;
	}
	
	/**
	 * Obtains topic distribution of given data
	 * 
	 * @param inferencer Trained inferencer.
	 * @param instances instances of original data to process.
	 * 
	 * @return Set of topic distributions.
	 */
	public ArrayList<double[]> infer(TopicInferencer inferencer, InstanceList instances) {
		try {
			
			int numIterations = inferParam.getNumIterations(); //100
		    int thinning = inferParam.getSampleInterval(); //10
		    int burnIn = inferParam.getBurnInIterations(); //10
		    
			ArrayList<double[]> result = new ArrayList<>();
			for (Instance instance: instances) {
				
				double[] topicDistribution = inferencer.getSampledDistribution(instance, numIterations, thinning, burnIn);
				result.add(topicDistribution);
			}
			    
			if(inferParam.getDocTopicsFile()!=null && !inferParam.getDocTopicsFile().equals("")) {
				inferencer.writeInferredDistributions(instances, new File(
						inferParam.getDocTopicsFile()),
						inferParam.getNumIterations(), 
						inferParam.getSampleInterval(),
						inferParam.getBurnInIterations(),
						inferParam.getDocTopicsThreshold(), 
						inferParam.getDocTopicsMax());
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		return null;
	}
}
