package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

public class FileBuffered{
	BufferedReader reader = null;
	BufferedWriter writer = null;
	String filename;
	
	
	public FileBuffered(String filename, String mode) throws UnsupportedEncodingException {
		this.filename = filename;
		
		if (mode.contains("r")) {
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "ISO-8859-1"));
			} catch (FileNotFoundException e) {
				System.out.println("Failed to open file to read: " + filename);
				e.printStackTrace();
			}
		}
		if (mode.contains("w")) {
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename), "ISO-8859-1"));
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
			String lineString = reader.readLine();
			if (lineString == null){
				return null;
			}
			
			line = new String(lineString.getBytes()).split(" ");
		} catch (IOException e) {
			System.out.println("Failed to read line in file: " + getFilename());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new WordPair(line[0], Long.parseLong(line[1]));
	}
	
	public String getFilename() {
		return filename;
	}
	
	public BufferedReader getReader() {
		return reader;
	}

	public void append(String text) throws IOException {
		writer.append(text);
	}
	public void write(String text) throws IOException {
		writer.write(text);
	}
	public void close() throws IOException{
		if(reader != null){
			reader.close();
		}
		if(writer != null){
			writer.close();
		}
	}

	public String readLine() throws IOException {
		return reader.readLine();
	}
}
