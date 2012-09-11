package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileBuffered{
	BufferedReader reader = null;
	BufferedWriter writer = null;
	String filename;
	
	
	public FileBuffered(String filename, String mode) {
		this.filename = filename;
		
		if (mode.contains("r")) {
			try {
				reader = new BufferedReader(new FileReader(filename));
			} catch (FileNotFoundException e) {
				System.out.println("Failed to open file to read: " + filename);
				e.printStackTrace();
			}
		}
		if (mode.contains("w")) {
			try {
				writer = new BufferedWriter(new FileWriter(filename));
			} catch (IOException e) {
				System.out.println("Failed to open file to write: " + filename);
				e.printStackTrace();
			}
		}
		
		if (reader == null && writer == null) {
			System.out.println("File not opened with read/write: " + filename);
		}
	}
	
	public WordPair readWordPair() {
		if (reader == null) {
			System.out.println("File not opened for reading: " + getFilename());
			
			return null;
		}
		
		String line[] = new String[2];
		try {
			line = reader.readLine().split(" ");
		} catch (IOException e) {
			System.out.println("Failed to read line in file: " + getFilename());
			e.printStackTrace();
		}
		if (line == null){
			return null;
		}
		return new WordPair(line[0], Long.parseLong(line[1]));
	}
	
	public String getFilename() {
		return filename;
	}

	public void append(String text) throws IOException {
		writer.append(text);
	}
	public void write(String text) throws IOException {
		writer.write(text);
	}
}
