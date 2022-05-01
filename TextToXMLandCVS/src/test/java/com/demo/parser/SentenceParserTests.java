package com.demo.parser;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.io.FileNotFoundException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.data.Sentence;
	
/**
 * Unit test class containing test for the class com.demo.parser.SentenceParser
 * @author birkaluk
 *
 */
@SpringBootTest
class SentenceParserTests {
	
	SentenceParser sentenceParser;
		
	/**
	 * Unit test for a simple test on a row in the text file
	 * @throws FileNotFoundException
	 */
	@Test
	void parseSimpleSentence() throws FileNotFoundException {
		sentenceParser = new SentenceParser("test/test1.in");
		Sentence sentence  = sentenceParser.nextSentence();		
		List<String> words = sentence.getWords();
		
		String[] expectedWords = new String[] {"anger", "at", "directing", "he", "his", "me", "was", "Why"};
		
		assertArrayEquals(expectedWords, words.toArray());
	}
	
	/**
	 * Unit test for more sentences on a row in the text file
	 * @throws FileNotFoundException
	 */
	@Test
	void parseMoreSentencesOnRow() throws FileNotFoundException {
		sentenceParser = new SentenceParser("test/test2.in");
		Sentence sentence  = sentenceParser.nextSentence();		
		List<String> words = sentence.getWords();		
		String[] expectedWords1 = new String[] {"anger", "at", "directing", "he", "his", "me", "was", "Why"};
		assertArrayEquals(expectedWords1, words.toArray());		
		
		sentence  = sentenceParser.nextSentence();		
		words = sentence.getWords();		
		String[] expectedWords2 = new String[] {"about", "did", "I", "know", "Little", "that"};
		assertArrayEquals(expectedWords2, words.toArray());
	}
	
	/**
	 * Unit test for a sentence divided to more rows in the text file
	 * @throws FileNotFoundException
	 */
	@Test
	void parseSentenceOnMoreRows() throws FileNotFoundException {
		sentenceParser = new SentenceParser("test/test3.in");
		Sentence sentence  = sentenceParser.nextSentence();		
		List<String> words = sentence.getWords();
		
		String[] expectedWords = new String[] {"access", "all", "an", "best", "But", "capital", "dedicated", "experts", "facets", "in", "in", "manner", "markets", "more", "of", "of", "offer", "possible", "serving", "significantly", "team", "the", "to", "to", "unequalled", "we", "you", "you"};
		
		assertArrayEquals(expectedWords, words.toArray());
	}
	
	/**
	 * Unit test for a sentence divided to more rows in the text file containing . as abreviation
	 * @throws FileNotFoundException
	 */
	@Test
	void parseSentenceOnMoreRowsWithAbr() throws FileNotFoundException {
		sentenceParser = new SentenceParser("test/test4.in");
		Sentence sentence  = sentenceParser.nextSentence();		
		List<String> words = sentence.getWords();
				
		String[] expectedWords = new String[] {"around", "furious", "he", "I", "just", "marching", "Mr.", "standing", "there", "was", "was", "watching", "Young"};
		
		assertArrayEquals(expectedWords, words.toArray());
	}
}
