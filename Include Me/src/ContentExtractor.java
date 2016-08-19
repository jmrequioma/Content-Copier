import java.io.*;

public class ContentExtractor {
	private String OUTPUT_FILE = "outputFile2.out";
	private String uniqueFileList = "";
	
	/**
	 * Used to extract the source file given an include preprocessor directive.
	 * 
	 * @param fileName
	 * @throws IOException 
	 */
	private void extractor(String fileName) throws IOException {
		String expectedLine = "";
		String contentFileName = "";
		String line;
		String preprocessorStart = "#include " + "\"";
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		try {
			while ((line = in.readLine()) != null) {
				int ind = line.indexOf(preprocessorStart);
				if (ind >= 0) {
					expectedLine = line.substring((ind+preprocessorStart).length() - 2, line.length()).trim();
					contentFileName = expectedLine.substring(1, expectedLine.length() - 1);
					if (!isRepeated(contentFileName)) {
						extractor(contentFileName);
					}
				} else {
					writeToOutputFile(line);
				}
			}
		} catch(NullPointerException npe) {
			System.err.println("IOException: " + npe.getMessage());
		} finally {
			in.close();
		}
	}
	
	/**
	 * Used to write into an output file the content given.
	 * 
	 * @param content
	 * @throws IOException 
	 */
	private void writeToOutputFile(String content) throws IOException {
	    FileWriter fw = null;
		try {
			// Perform logging tasks here.
			fw = new FileWriter(OUTPUT_FILE,true); //the true will append the new data
		    fw.write(content + "\n"); //appends the string to the file
		    fw.close();
		} catch(NullPointerException npe) {
			System.err.println("IOException: " + npe.getMessage());
		}
	}
	
	/**
	 * Used to determine if the include preprocessor directive is repeated.
	 * 
	 * @param content
	 * @return true - if repeated; false - if not repeated.
	 * @throws IOException 
	 */
	private boolean isRepeated(String fileName) {
		boolean repeated = true;
	    if (!uniqueFileList.contains(fileName)) {
	    	uniqueFileList += "|" + fileName;
	    	repeated = false;
	    }
	    return repeated;
	}
	
	public static void main(String args[]) throws IOException {
		ContentExtractor c = new ContentExtractor();
		c.extractor("inputFile.cpp");
	}
}