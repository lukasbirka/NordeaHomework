package com.demo.out;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.out.SentenceCSVFileWriter;


/**
 * Unit test class containing test for the class com.demo.out.SentenceCSVFileWriter
 * @author birkaluk
 *
 */
@SpringBootTest
public class SentenceCSVFileWriterTest {
	
	@Test
	void testGeadingRow() throws IOException {
		SentenceCSVFileWriter scv = new SentenceCSVFileWriter("tmp.csv");
		String header = scv.createCSVHeader(5);
		
		System.out.println(header);
		String expectedgeader = ",Word 1,Word 2,Word 3,Word 4,Word 5";
		assertEquals(header, expectedgeader);
	}
}
