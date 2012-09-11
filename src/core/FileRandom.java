package core;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileRandom extends RandomAccessFile{
	String filename;
	
	public FileRandom(String filename, String mode) throws FileNotFoundException {
		super(filename, mode);
	
		this.filename = filename;
	}
	
	public WordPair readWordPair() {
		String line[] = new String[2];
		try {
			line = this.readLine().split(" ");
		} catch (IOException e) {
			System.out.println("Failed to read line in file: " + getFilename());
			e.printStackTrace();
		}
		
		return new WordPair(line[0], Integer.parseInt(line[1]));
	}
	
	public String getFilename() {
		return filename;
	}
}
