package weka.filters.supervised.instance;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;
import java.util.Vector;

import bridge.LdaBridge;
import bridge.parameters.ImporterParam;
import bridge.parameters.TrainerParam;
import bridge.utils.DataImport;
import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.types.Alphabet;
import cc.mallet.types.IDSorter;
import weka.core.Capabilities;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.Option;
import weka.core.OptionHandler;
import weka.core.RevisionUtils;
import weka.core.Utils;
import weka.core.Capabilities.Capability;
import weka.core.stemmers.NullStemmer;
import weka.core.stemmers.Stemmer;
import weka.core.DenseInstance;
import weka.filters.Filter;
import weka.filters.UnsupervisedFilter;


/** 
 <!-- globalinfo-start -->
 * Simple random oversampling method
 * <br><br>
 * 
 <!-- globalinfo-end -->
 * 
 <!-- options-end -->
 * 
 * @author Pedro Celard (pedrocelard@gmail.com)
 * @version $Revision$
 *
 */
public class SimpleRandomOversample extends Filter 
	implements UnsupervisedFilter, OptionHandler{

	/** Autogenerated for serialization. */
	private static final long serialVersionUID = -7829987874112661835L;
	
	/** the random seed to use. */
	protected int randomSeed = 1;
	
	
	/**
	 * Default constructor
	 */
	public SimpleRandomOversample() {
		
	}

	/**
	 * Main method for running this filter.
	 *
	 * @param args 	should contain arguments to the filter: 
	 * 			use -h for help
	 */
	public static void main(String[] args) {
	  runFilter(new SimpleRandomOversample(), args);
	}
	
	/**
	 * Returns a string describing this filter.
	 *
	 * @return a description of the filter suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String globalInfo() {
		//TODO: Incluir una descripción más completa
		return "SimpleRandomOversample";
	}
	
	/**
	 * Returns the revision string.
	 *
	 * @return the revision
	 */
	@Override
	public String getRevision() {
		return RevisionUtils.extract("$Revision$");
	}
	

	/**
	 * Returns the Capabilities of this filter.
	 *
	 * @return the capabilities of this object
	 * @see Capabilities
	 */
	@Override
	public Capabilities getCapabilities(){
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
	 * Returns an enumeration describing the available options.
	 * 
	 * @return an enumeration of all the available options
	 */
	@Override
	public Enumeration<Option> listOptions(){
		Vector<Option> result = new Vector<Option>();
		
		return result.elements();
	}
	
	/**
	 * <!-- options-start -->
	 * <pre> -randomSeed
	 * Specifies the random number seed (default: 1)
	 * </pre>
	 * 
	 * <pre>-num-iterations
	 * The number of iterations of Gibbs sampling. (default: 1000)
	 * </pre>
	 * 
	 * <pre>-alpha
	 * SumAlpha parameter: sum over topics of smoothing over doc-topic distributions. 
	 * alpha_k = [this value] / [num topics] (default: 0.5)
	 * </pre>
	 * 
	 * <pre>-beta
	 * Beta parameter: smoothing parameter for each topic-word. beta_w = [this value](default: 0.01)
	 * </pre>
	 * 
	 * <pre>-num-topics
	 * Specifies number of Topics (default: 10) 
	 * </pre>
	 * 
	 * <pre> -stemmer &lt;spec&gt;
	 * The stemming algorithm (classname plus parameters) to use.</pre>
	 * 
	 * <!-- options-end -->
	 * 
	 * @param options the list of options as an array of strings
	 * @throws Exception if an option is not supported
	 */
	public void setOptions(String[] options) throws Exception {
		
		String value = Utils.getOption("randomSeed", options);
		if (value.length() != 0) {
			setRandomSeed(Integer.valueOf(value).intValue());
		} else {
			setRandomSeed(1);
		}
	}
	
	/**
	 * 
	 */
	public String[] getOptions() {
		Vector<String> result = new Vector<String>();
		
		result.add("-randomSeed");
		result.add(String.valueOf(getRandomSeed()));

		
		return result.toArray(new String[result.size()]);
	}
	
	
	/**
	 * Sets the format of the input instances.
	 *
	 * @param instanceInfo an Instances object containing the input instance
	 *          structure (any instances contained in the object are ignored -
	 *          only the structure is required).
	 * @return true if the outputFormat may be collected immediately
	 * @throws Exception if the input format can't be set successfully
	 */
	@Override
	public boolean setInputFormat(Instances instanceInfo) throws Exception {
		super.setInputFormat(instanceInfo);
		
		return false;
	}
	 
	/**
	 * Returns the tip text for this property.
	 *
	 * @return tip text for this property suitable for displaying in the
	 *         explorer/experimenter gui
	 */
	public String randomSeedTipText() {
		return "The random seed to use.";
	}
	 
	/**
	 * Gets the random seed to use.
	 * 
	 * @return the random seed to use.
	 */
	public int getRandomSeed() {
		return randomSeed;
	}

	/**
	 * Sets the random seed to use.
	 * 
	 * @param randomSeed the random seed to use.
	 */
	public void setRandomSeed(int randomSeed) {
		this.randomSeed = randomSeed;
	}

	
	/**
	 * Input an instance for filtering. Filter requires all training instances be
	 * read before producing output.
	 *
	 * @param instance the input instance.
	 * @return true if the filtered instance may now be collected with output().
	 * @throws IllegalStateException if no input structure has been defined.
	 */
	@Override
	public boolean input(Instance instance) throws Exception {
		if(getInputFormat() == null) {
		 throw new IllegalStateException("No input stance format defined");
		}
		
		if(m_NewBatch) {
		 resetQueue();
		 m_NewBatch = false;
		}
		
		if(isFirstBatchDone()) {
		 return true;
		} else {
		 bufferInput(instance);
		 return false;
		}
	}
	 
	 
	/**
	 * Signify that this batch of input to the filter is finished. 
	 * If the filter requires all instances prior to filtering,
	 * output() may now be called to retrieve the filtered instances.
	 *
	 * @return 		true if there are instances pending output
	 * @throws IllegalStateException if no input structure has been defined
	 * @throws Exception 	if provided options cannot be executed 
	 * 			on input instances
	 */
	public boolean batchFinished() throws Exception {
		// Checks if format input exists
		if(getInputFormat() == null) {
			throw new IllegalStateException("No input instance format defined");
		}
		
		// We only need to do something in this method
		// if the first batch hasn't been processed. Otherwise
		// input() has already done all the work.
		if(!isFirstBatchDone()) {
			doRandomOversample();
		}
		
		flushInput();
		m_NewBatch = true;
		m_FirstBatchDone = true;
		return (numPendingOutput() != 0);
	}
	 
	
	protected void doRandomOversample() {

		Random randomInstance = new Random(getRandomSeed());
		
		//Gets original instances
		Instances totalInstances = getInputFormat();
		setOutputFormat(totalInstances);
		
		/* ******************************************************************************
		 * Gets all instances and creates a set of groups of instances based on class.
		 * ******************************************************************************/
		
		Map<String, ArrayList<Instance>> instancesMap = new HashMap<String, ArrayList<Instance>>();
		Iterator<Instance> totalInstIter = totalInstances.iterator();
		
		while(totalInstIter.hasNext()) {
			
			ArrayList<Instance> tempInstancesList = null;
			
			Instance instTemp = (Instance)totalInstIter.next();
			if(instancesMap.containsKey(instTemp.stringValue(instTemp.classIndex()))) {
				tempInstancesList = (ArrayList<Instance>) instancesMap.get(instTemp.stringValue(instTemp.classIndex()));
			} else {
				tempInstancesList = new ArrayList<>();
			}
			
			tempInstancesList.add(instTemp);
			instancesMap.put(instTemp.stringValue(instTemp.classIndex()), tempInstancesList);
	    }
		
		
		/* ******************************************************************************
		 * Gets the largest group size to calculate percentage of instances to generate.
		 * ******************************************************************************/
		
		Iterator<String> iter = instancesMap.keySet().iterator();
		int largestSet = 0;
		
		while(iter.hasNext()) {
			String mapClass = iter.next().toString();
			if(instancesMap.get(mapClass).size() > largestSet) {
				largestSet = instancesMap.get(mapClass).size();
			}
		}
		
		
		/* ******************************************************************************
		 * Creates new instances and output them.
		 * ******************************************************************************/
		
		iter = instancesMap.keySet().iterator();
		String mapClass = null;
		
		while(iter.hasNext()) {
			
			mapClass = iter.next().toString();
			ArrayList<Instance> instList = instancesMap.get(mapClass);
			
			//output original instances
			for(int i=0; i < instList.size(); i++) {
	    	 	push(instList.get(i),false);
	    	}
			
			if(instList.size() < largestSet) {
				
				//calculate the number of instances for each class to generate
				double actualInstanceNum = instList.size();
				int newInstanceNum = (int) Math.round(actualInstanceNum*((((largestSet/actualInstanceNum)*100)-100)/100));

				//generate new instances and output them 
				for(int i=0; i<newInstanceNum ; i++) {
					ArrayList<Instance> originalList = instList;
					Instance newInst = originalList.get(randomInstance.nextInt(originalList.size()));
					instList.add(newInst);
					push(newInst,false);
				}
			}
		}
	}	 
}