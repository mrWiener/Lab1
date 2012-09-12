package core;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class FileRandom extends RandomAccessFile{
	String filename;
	
	public FileRandom(String filename, String mode) throws FileNotFoundException {
		super(filename, mode);
	
		this.filename = filename;
	}
	
	public WordPair readWordPairLinewise() throws IOException {
		String line[] = new String[2];
		try {
			String lineString = readLine();
			if (lineString == null || lineString.equals("")){
				return null;
			}
			line = lineString.split(" ");
		} catch (IOException e) {
			System.out.println("Failed to read line in file: " + getFilename());
			e.printStackTrace();
		}
		
		return new WordPair(line[0], Long.parseLong(line[1]));
	}
	
	public String getFilename() {
		return filename;
	}

	/**
	 * Assuming that we are in the beginning of a word!
	 * @param mediumIndexStart
	 * @return
	 * @throws IOException
	 */
	public String readWordStandStill(long seekValue) throws IOException {
		seek(seekValue);
		StringBuilder word = new StringBuilder();
		String letter = convertByteToString((byte)read());
		while(!letter.equals(" ")){
			word.append(letter);
			letter = convertByteToString((byte)read());
		}
		return word.toString();
	}
	
	public WordPair readWordWalkLeft(long seekValue) throws IOException {
		seek(seekValue);
		String currentLetter = convertByteToString((byte)read());
		String lastLetter = "";
		boolean found = false;
		while(!found){
			if(currentLetter.equals(" ") && !isInteger(lastLetter)){
				found = true;
			}else{
				lastLetter = currentLetter;
				if(getFilePointer() - 2 < 2){
					seek(0);
					found = true;
				} else{
					seek(getFilePointer() - 2);
					currentLetter = convertByteToString((byte)read());
				}
			}
		}
		long wordPointer = getFilePointer();
		return new WordPair(readWordStandStill(getFilePointer()),wordPointer);
	}
	
	public WordPair readWordWalkRight(long seekValue) throws IOException {
		if(seekValue == 0){
			return new WordPair(readWordStandStill(0),0);
		}
		seek(seekValue-1);
		String lastLetter = convertByteToString((byte)read());;
		String currentLetter = convertByteToString((byte)read());
		
		boolean found = false;
		while(!found){
			if(lastLetter.equals(" ") && !isInteger(currentLetter)){
				found = true;
				seek(getFilePointer()-1);
			}else{
				lastLetter = currentLetter;
				currentLetter = convertByteToString((byte)read());
			}
		}
		long wordPointer = getFilePointer();
		return new WordPair(readWordStandStill(getFilePointer()),wordPointer);
	}
	
	private String convertByteToString(byte b) throws UnsupportedEncodingException{
		byte[] by= new byte[1];
		by[0] = b;
		return new String(by, "ISO-8859-1");
	}
	
    private boolean isInteger(String input )  
    {  
       try  
       {  
          Integer.parseInt( input );  
          return true;  
       }  
       catch( Exception e)  
       {  
          return false;  
       }  
       catch(StackOverflowError e){
    	   return false;
       }
    }

	public long readWordsIndex(long seekValue) throws IOException {
		seek(seekValue);
		String currentLetter = "";
		while(!currentLetter.equals(" ")){
			currentLetter = convertByteToString((byte)read());
		}
		return Long.parseLong(readWordStandStill(getFilePointer()));
	}

	public String getSurroundingText(long wordStart, int wordSize) throws UnsupportedEncodingException, IOException {
		long startPoint = wordStart-Main.SURROUNDING_TEXT_SIZE;
		if(startPoint < 0){
			seek(0);
		} else{
			seek(startPoint);
		}
		StringBuilder word = new StringBuilder();
		for(int i = 0; i < wordSize + Main.SURROUNDING_TEXT_SIZE*2; i++){
			if(!(startPoint + i < 0 || startPoint + i > length() -1)){
				word.append(convertByteToString((byte)read()));
			}
		}
		
		return word.toString();
	}
}
