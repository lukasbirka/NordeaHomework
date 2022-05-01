package com.demo.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.Locale;
import java.util.Scanner;

import com.demo.data.Sentence;

/**
 * Class SentenceParser parses a given text file line by line
 * and transform particular lines into instance sentences (instances of class Sentence).
 *  *    
 * @author birkaluk
 *
 */
public class SentenceParser {
	
	private Scanner fileScanner;	
	private StringBuilder textLine;
	private StringBuilder partialSentence = new StringBuilder();
	private BreakIterator iterator = BreakIterator.getSentenceInstance(Locale.US);
	private int startIndex;
	private int endIndex;
	
	public SentenceParser(String path) throws FileNotFoundException {
		this.fileScanner = new Scanner(new File(path));
		this.textLine = new StringBuilder();
	}
	
	public SentenceParser() {		
	}
	
	/**
	 * Method hasNextSentence returns information whether there are unread sentences left in the input text file. 
	 * @return
	 */
	public boolean hasNextSentence() {
		
		return this.fileScanner.hasNextLine()
				|| (startIndex < endIndex && endIndex < textLine.length());
	}
	
	/**
	 * Method parses and returns next available sentence from the input text file.
	 * @return
	 */
	public Sentence nextSentence() {
				
		// if a sentence is found on a position on the text line
		if(startIndex < endIndex) {
								
			// find position (index) of the next sentence on the text line 
			startIndex = endIndex;
			endIndex = iterator.next();
			
			/* If there are no more sentences on the text line
			 * then remember the partial / not complete sentence
			 * and reset the start / end sentence indexes to 0 
			 */
			if(endIndex == BreakIterator.DONE) {
				textLine = new StringBuilder(partialSentence);
				textLine.append(" ");
				startIndex = 0;
				endIndex = 0;
			}
			else {				
								
				/* return the parsed sentence (if it is a complete sentence)
				 * or continue with parsing of the sentence on the next line in the text file.
				 */
				return returnSentence();
				
			}			
		}
				
		// If there are more text lines in the input text file
		if(this.fileScanner.hasNext()) {
			
			// parse the next text line
			String line =  this.fileScanner.nextLine().trim();
			line = transformAbreviations(line);
			
			// if line is empty then continue to next line 
			if(line.isEmpty()) {
				return nextSentence();
			}
			
			// Remember the parsed text line 
			textLine.append(line);
			
			// Initiate iterator to parse the text line split to sentences 
			iterator.setText(textLine.toString());
			
			// Remember start and end index of the first sentence 
			startIndex = iterator.first();
			endIndex = iterator.next();
			
			/* return the parsed sentence (if it is a complete sentence)
			 * or continue with parsing of the sentence on the next line in the text file.
			 */
			return returnSentence();
						    
		}		
		
		return null;
	}
	
	
	/**
	 * Method parses the sentence on the given position.
	 * 
	 * If the parsed sentence is complete (ended by . or ? or !) 
	 * then the parsing is stopped and the parsed sentence is returned.  
	 * 
	 * If the parsed sentence is not complete 
	 * then parsing of the sentence continues on the next line in the text file.
	 * @return
	 */
	private Sentence returnSentence() {
		
		// Read sentence on the given position
		String sentenceTxt = textLine.substring(startIndex,endIndex).trim();
		
		// If sentence is complete then return the parsed sentence
		if((sentenceTxt.endsWith(".") || sentenceTxt.endsWith("?") || sentenceTxt.endsWith("!")))				
		{
			// cleanup partialSentence variable one the sentence is complete.
			partialSentence = new StringBuilder();
			
			// return parsed sentence
			return new Sentence(sentenceTxt);				
		}
		/*If sentence is not complete then remember the partial sentence
		  and continue with parsing of the sentence on the next line in the text file*/
		else {
			// remember the partial sentence						
			partialSentence.append(sentenceTxt);			
			
			// continue to parsing of the next line in the text file
			return nextSentence();
		}
	}
	
	/**
	 * Method transforms abbreviations 
	 * from xyz_. format to xyz_dot format
	 */
	private String transformAbreviations(String input) {
		
		if(input.contains("Mr.")) {
			input = input.replace("Mr.", "Mr_dot");
		}
		
		if(input.contains("Ms.")) {
			input = input.replace("Ms.", "Ms_dot");
		}
		
		if(input.contains("Mrs.")) {
			input = input.replace("Mrs.", "Mrs_dot");
		}
		return input;
	}
}
