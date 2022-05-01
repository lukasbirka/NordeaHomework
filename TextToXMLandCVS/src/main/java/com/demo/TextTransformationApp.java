package com.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.demo.data.Sentence;
import com.demo.out.SentenceCSVFileWriter;
import com.demo.out.SentenceWriter;
import com.demo.out.SentenceXMLFileWriter;
import com.demo.parser.SentenceParser;
import com.demo.out.SentenceCSVFileWriter;

@SpringBootApplication
public class TextTransformationApp implements CommandLineRunner {

	public static void main(String[] args) 
    {
      SpringApplication app = new SpringApplication(TextTransformationApp.class);
      app.run(args);
    }
	
	public void run(String... args) throws Exception {
		
		// Set input for the program
		String inputPath = "in/small.in";
		String outputPath = "out/small.cvs";
		
		transformInputFile(inputPath, outputPath, "CSV");
		
	}
	
	
	/**
	 * Method initiates transformation of the input file
	 * to the requested output file in CSV / XML format 
	 * @param inputPath
	 * @param outputPath
	 * @param outputType
	 * @throws Exception
	 */
	public void transformInputFile(String inputPath, String outputPath, String outputType) throws Exception {
		
		boolean isXML, isCSV = false;
		
		// Check and set the requested format of the output
		if("XML".equals(outputType)) {
			isXML = true;
		}
		else if("CSV".equals(outputType)) {
			isCSV = true;
		}
		else {
			throw new Exception("Unsupported type of output:" + outputType);
		}
		
		// Create a new instance of the sentence parser
		SentenceParser sp = new SentenceParser(inputPath);
		
		// Create a new instance of the output file writter
		SentenceWriter scv = null;
		if(isCSV) {
			scv = new SentenceCSVFileWriter(outputPath);
		}
		else {
			scv = new SentenceXMLFileWriter(outputPath);
		}
						
		Sentence sentence;
		int index = 1;
		int maxNumOfWords = 0;
		
		// Parse the input file in the loop sentence by sentence
		while (sp.hasNextSentence()) {
			
			// get the next sentence from the input
			sentence = sp.nextSentence();			
			
			// rememebr maximal number of words in all parsed sentences
			if(sentence.getWords().size() > maxNumOfWords) {
				maxNumOfWords = sentence.getWords().size();
			}
			
			// add the sentence to the output
			scv.writeSentence(sentence, index);
			
			// increase sentence index
			index++;
		}		
		
		// create the final output file
		scv.createFile(maxNumOfWords);
	}

}
