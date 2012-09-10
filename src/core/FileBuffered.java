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
	
	public String getFilename() {
		return filename;
	}
}
