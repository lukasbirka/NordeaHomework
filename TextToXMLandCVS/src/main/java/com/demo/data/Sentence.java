package com.demo.data;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Class Sentence represents the sentence data entity
 * @author birkaluk
 *
 */
public class Sentence {
	
	// a native text representation of the sentence
	private String textSentence;
	
	// list of words holding the words that the sentence consist of	
	private List<String> words;
	
		
	/**
	 * Sets the text representation of the sentence
	 * and splits the sentence into a sorted and normalized 
	 * list of words. 
	 * @param textSentence
	 */
	public Sentence(String textSentence) {
		// set the text representation of sentence						
		this.textSentence = textSentence;
		// split the sentence to a text word array list split on space or tab or comma
		words = Arrays.asList(textSentence.split(" |	|,"));
		
		// change to word list to stream 
		words = words.stream()
				// trim each word in the stream
				.map(s -> s = s.trim())
				// remove special characters from words in the stream
				.map(s -> s = s.replace(".", ""))
				.map(s -> s = s.replace("?", ""))
				.map(s -> s = s.replace("!", ""))
				.map(s -> s = s.replace(":", ""))
				.map(s -> s = s.replace("(", ""))
				.map(s -> s = s.replace(")", ""))
				.map(s -> s = s.replace("-", ""))
				// filter white spaces in the stream
				.filter(s -> s != null && !s.trim().isEmpty())
				// convert the stream back to a list 
				.collect(Collectors.toList());
		// sort all words in the list 
		words.sort(String.CASE_INSENSITIVE_ORDER);
		
		// transform abbreviations back to original format
		this.transformAbreviations();
				
	}
	
	/**
	 * Method transforms abbreviations 
	 * from xyz_dot format to xyz. format
	 */
	private void transformAbreviations() {
	
		for (int i =0; i < words.size(); i++) {
			
			String word = words.get(i);
			
			if(word.contains("Mr_dot")) {
				word = word.replace("Mr_dot", "Mr.");
			}
			
			if(word.contains("Ms.")) {
				word = word.replace("Ms_dot", "Ms.");
			}
			
			if(word.contains("Mrs.")) {
				word = word.replace("Mrs_dot", "Mrs.");
			}
			
			words.set(i, word);
		}
	}

	public String getTextSentence() {
		return textSentence;
	}

	public void setTextSentence(String textSentence) {
		this.textSentence = textSentence;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(List<String> words) {
		this.words = words;
	}

	@Override
	public int hashCode() {
		return Objects.hash(textSentence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sentence other = (Sentence) obj;
		return Objects.equals(textSentence, other.textSentence);
	}

	@Override
	public String toString() {
		return "Sentence [textSentence=" + textSentence + "]";
	}
	
	
	
	
}
