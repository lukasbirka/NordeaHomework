package com.demo.out;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.demo.data.Sentence;

/**
 * Class provides functionality to write 
 * a Sentence into a XML file
 * @author birkaluk
 *
 */
public class SentenceXMLFileWriter extends SentenceWriter {

	private static String TMPFILEPATH = "tmp.xml";
	private PrintWriter out;
	
	public SentenceXMLFileWriter (String path) throws IOException {		
		super(path);		
		out = new PrintWriter(TMPFILEPATH);
	}
	
	/**
	 * Write sentence as a new row into the xml file
	 * @param sentence
	 * @param index
	 * @throws IOException
	 */
	@Override
	public void writeSentence(Sentence sentence, int index) throws IOException {
		       
        // write root XML element
		if(index == 1) {
			out.println(createStartRootXMLElement());
		}
        
        // write words on XML format
		out.println(createWordXMLElements(sentence));       
	}
	
	/**
	 * Method creates final output xml file containing
	 * header row and all added sentences
	 * @param numOfColumns
	 * @throws IOException
	 */
	@Override
	public void createFile(int numOfColumns) throws IOException {
		
		// write ending root element
		out.println(createEndRootXMLElement());
		out.close();
		
		Path fileToMovePath = Paths.get(TMPFILEPATH);
	    Path targetPath = Paths.get(getPath());
	    
	    Files.delete(targetPath);
	    Files.move(fileToMovePath, targetPath);
		
	}
	
	/**
	 * Method creates a starting root XML element
	 * @return
	 */
	private String createStartRootXMLElement() {
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
		sb.append("<text>");
		return sb.toString();
	}
	
	/**
	 * Method creates an ending root XML element
	 * @return
	 */
	private String createEndRootXMLElement() {
		return "</text>";
	}
	
	/**
	 * Method converts list of words into XML format 
	 * @param sentence
	 * @return
	 */
	private String createWordXMLElements (Sentence sentence) {
		StringBuilder sb = new StringBuilder();
						
		sb.append("<sentence>");
		for(String word : sentence.getWords()) {
			sb.append("<word>");
			//TODO: escape special characters in XML 
			sb.append(word);			
			sb.append("</word>");
		}
		sb.append("</sentence>");
		return sb.toString();
	}

}
