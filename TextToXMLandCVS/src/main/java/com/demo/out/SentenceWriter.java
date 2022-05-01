package com.demo.out;

import java.io.IOException;

import com.demo.data.Sentence;

public abstract class SentenceWriter {
	
	private String path;
	
	public SentenceWriter(String path) {
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public abstract void writeSentence(Sentence sentence, int index) throws IOException;
	
	public abstract void createFile(int numOfColumns) throws IOException;
}
