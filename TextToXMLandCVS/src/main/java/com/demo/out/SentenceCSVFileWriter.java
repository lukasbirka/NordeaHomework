package com.demo.out;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.demo.data.Sentence;
import com.opencsv.CSVWriter;
import com.opencsv.ICSVWriter;


/**
 * Class provides functionality to write 
 * a Sentence into a CSV file
 * @author birkaluk
 *
 */
public class SentenceCSVFileWriter extends SentenceWriter {	
	
	private ICSVWriter writer;	
	private static String TMPFILEPATH = "tmp.csv";
	
	public SentenceCSVFileWriter (String path) throws IOException {		
		super(path);
		this.writer = new CSVWriter(new FileWriter(TMPFILEPATH));		
	}
	
	/**
	 * Write sentence as a new row into the csv file
	 * @param sentence
	 * @param index
	 * @throws IOException
	 */
	public void writeSentence(Sentence sentence, int index) throws IOException {
		
		// Add sentence index column
		sentence.getWords().add(0, "Sentence " + index);
		
		String[] wordArray = new String[sentence.getWords().size()];
		wordArray = sentence.getWords().toArray(wordArray);
		
		// Write row to csv file
		writer.writeNext(wordArray, false);
		writer.flush();
				
	}
	
	/**
	 * Method creates final output csv file containing
	 * header row and all added sentences
	 * @param numOfColumns
	 * @throws IOException
	 */
	public void createFile(int numOfColumns) throws IOException {
		
		// close the tmp file
		writer.close();
		
		// PrintWriter object for the output csv file
        PrintWriter pw = new PrintWriter(super.getPath());
       
        // write header row to the csv file
        pw.println(createCSVHeader(numOfColumns));
          
        // BufferedReader object for tmp csv file
        BufferedReader br = new BufferedReader(new FileReader(TMPFILEPATH));          
        String line;
        
        // loop to copy each line of 
        // tmp csv file the the final output csv file
        while ((line = br.readLine()) != null)
        {
        	pw.println(line);
        }     
          
        pw.flush();
          
        // closing resources
        br.close();
        pw.close();
        
        // delete the tmp file
        Files.delete(Paths.get("tmp.csv"));
	}
	
	/**
	 * Method creates a header CSV row
	 * @param numOfColumns
	 * @return
	 */
	public String createCSVHeader(int numOfColumns) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < numOfColumns; i++) {
			sb.append(",");
			sb.append("Word ");
			sb.append(i + 1);
		}
			
		return sb.toString();		
	}
	
	
}
