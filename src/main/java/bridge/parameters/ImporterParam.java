package bridge.parameters;

import java.io.File;
import java.nio.charset.Charset;

/**
 * 
 * Set of parameters to data import. This parameters are based on Mallet import data methods. 
 * <br><br>
 * 
 * <pre> preserve case
 * Specify if each letter must be transformed to lowercase or preserve original format 
 * </pre>
 * 
 * <pre> removeStopWords
 * Specify if stopwords and words which do not provide valuable data must be deleted 
 * </pre>
 * 
 * <pre> printOutput
 * Specify if output must be printed.
 * </pre>
 * 
 * <pre> tokkenPattern
 * Regular expression to recognize words.  
 * </pre>
 * 
 * <pre> encoding
 * Character encoding
 * </pre>
 * 
 * <pre> outputFile
 * File to write data</pre>
 * 
 * <pre> directories
 * Directories where original data is read from, its not necessary if original data is obtained from weka.</pre>
 * 
 * <pre> gramSizes
 * Include among the features all n-grams of sizes specified.  For example, to get all unigrams and bigrams, use --gram-sizes 1,2.
 * </pre>
 * 
 * @author Pedro Celard
 *
 */
public class ImporterParam {

	private boolean preserveCase = false;
	private boolean removeStopWords = true;
	private boolean printOutput = true;
	
	private String tokkenPattern = "\\p{L}[\\p{L}\\p{P}]+\\p{L}";
	private String encoding = Charset.defaultCharset().displayName();
	private File outputFile = new File("dataBridge.mallet");
	private String[] directories = null;
	private int[] gramSizes = {1};
	
	/**
	 * Default constructor
	 */
	public ImporterParam() {
	}
	
	/**
	 * Constructor setting directories to read
	 * 
	 * @param directories directories where original data can be found
	 */
	public ImporterParam(String[] directories) {
		this.directories = directories;
	}
	
	/**
	 * Constructor indicating if stopwords must be deleted
	 * @param rmStopWords
	 */
	public ImporterParam(boolean rmStopWords) {
		setRemoveStopWords(rmStopWords);
	}
	
	/**
	 * Gets whether if upper and lower case must be preserved
	 * 
	 * @return preserveCase value
	 */
	public boolean isPreserveCase() {
		return preserveCase;
	}
	
	/**
	 * Sets whether if upper and lower case must be preserved
	 * @param preserveCase false to transform every uppercase character to lowecase
	 */
	public void setPreserveCase(boolean preserveCase) {
		this.preserveCase = preserveCase;
	}
	
	/**
	 * Gets whether stopwords must be deleted
	 * 
	 * @return true if stopwrods must be deleted
	 */
	public boolean isRemoveStopWords() {
		return removeStopWords;
	}
	
	/**
	 * Sets whether stopwords must be deleted
	 * 
	 * @param removeStopWords true if stopwords must be deleted
	 */
	public void setRemoveStopWords(boolean removeStopWords) {
		this.removeStopWords = removeStopWords;
	}
	
	/**
	 * Gets whether output must be printed
	 * 
	 * @return true if output must be printed
	 */
	public boolean isPrintOutput() {
		return printOutput;
	}
	
	/**
	 * Sets where output must be printed
	 * 
	 * @param printOutput true if putput must be printed
	 */
	public void setPrintOutput(boolean printOutput) {
		this.printOutput = printOutput;
	}
	
	/**
	 * Gets regular expression to recognize tokens
	 * 
	 * @return regular expression used for tokenization 
	 */
	public String getTokenPattern() {
		return tokkenPattern;
	}
	
	/**
	 * Sets regular expressino for tokenization
	 * 
	 * @param tokkenPattern regular expression
	 */
	public void setTokenPattern(String tokkenPattern) {
		this.tokkenPattern = tokkenPattern;
	}
	
	/**
	 * Gets character encoding 
	 * 
	 * @return character encoding
	 */
	public String getEncoding() {
		return encoding;
	}
	
	/**
	 * Sets character encoding
	 * @param encoding character encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	/**
	 * Gets file where output must be written
	 * 
	 * @return file where output must be written
	 */
	public File getOutputFile() {
		return outputFile;
	}
	
	/**
	 * Sets file where output must be written
	 * 
	 * @param outputFile file where output must be written
	 */
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	/**
	 * Gets directories where original data is located
	 * 
	 * @return directories where original data is located
	 */
	public String[] getDirectories() {
		return directories;
	}
	
	/**
	 * Sets directories where original data is located
	 * 
	 * @param directories where original data is located
	 */
	public void setDirectories(String[] directories) {
		this.directories = directories;
	}
	
	/**
	 * Gets which n-grams must be obtained
	 * 
	 * @return size of n-grams
	 */
	public int[] getGramSizes() {
		return gramSizes;
	}
	
	/**
	 * Sets n-grams size to be obtained
	 * 
	 * @param gramSizes size of n-grams
	 */
	public void setGramSizes(int[] gramSizes) {
		this.gramSizes = gramSizes;
	}
	
	
	
	
	
}
