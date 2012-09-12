package core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;

public final class WordFinder {

	public static WordPair find(String word) throws IOException {
		int smallIndex = hashSmallIndex(word);
		
		if(!new File(Main.SMALL_INDICES_PATH + smallIndex + ".txt").isFile()){
			// We don't have the "hash-file" meaning we do not have the word!
			return null;
		}
		
		// Get the fi
		FileBuffered smallIndexFileStart = new FileBuffered(Main.SMALL_INDICES_PATH + smallIndex + ".txt", "r");
		FileRandom mediumIndexFile = new FileRandom(Main.MEDIUM_INDEX_NAME, "r");
		
		// Get the starting pos and the starting word before the search starts
		
		long mediumIndexStart = Long.parseLong(smallIndexFileStart.readLine());
		String mediumIndexStartWord = mediumIndexFile.readWordStandStill(mediumIndexStart);
		smallIndexFileStart.close();
		WordPair startPair = new WordPair(mediumIndexStartWord, mediumIndexStart);
		WordPair endPair = null;

		
		boolean foundFile = false;
		for(int i = 1; smallIndex + i <= hashSmallIndex("ššš") && !foundFile; i++){
			if(new File(Main.SMALL_INDICES_PATH + (smallIndex + 1) + ".txt").isFile()){
				FileBuffered smallIndexFileEnd = new FileBuffered(Main.SMALL_INDICES_PATH + (smallIndex + 1) + ".txt", "r");
				endPair = mediumIndexFile.readWordWalkLeft(Long.parseLong(smallIndexFileEnd.readLine()) - 1);
				smallIndexFileEnd.close();
				foundFile = true;
			}
		}
		if(!foundFile){
			// We have not found a "ending file" meaning we have to set the last word in medium file as the ending-file.
			endPair = mediumIndexFile.readWordWalkLeft(mediumIndexFile.length()-1);
		}
		long bigIndex;
		
		if(startPair.text.equals(word)){
			bigIndex = mediumIndexFile.readWordsIndex(startPair.index);
		} else if(endPair.text.equals(word)){
			bigIndex = mediumIndexFile.readWordsIndex(endPair.index);
		} else{
			bigIndex = binarySearch(startPair, endPair, word, mediumIndexFile);
		}
		LinkedList<Long> bigIndicesList = getBigIndices(bigIndex, word);
		return getFinalResult(bigIndicesList, word);
		
		
	}
	
	public static WordPair getFinalResult(LinkedList<Long> bigIndicesList, String word) throws UnsupportedEncodingException, IOException{
		FileRandom korpusFile = new FileRandom(Main.KORPUS_NAME, "r");
		StringBuilder returnText = new StringBuilder();
		for(long korpusIndex : bigIndicesList){
			returnText.append(korpusFile.getSurroundingText(korpusIndex, word.length()) + "\n");
		}
		korpusFile.close();
		return new WordPair(returnText.toString(), bigIndicesList.size());
	}
	
	public static LinkedList<Long> getBigIndices(long bigIndex, String searchWord) throws IOException{
		FileRandom bigIndexFile = new FileRandom(Main.BIG_INDEX_NAME, "r");
		LinkedList<Long> returnList = new LinkedList<Long>();
		
		bigIndexFile.seek(bigIndex);
		WordPair bigPair = bigIndexFile.readWordPairLinewise();
		while(!(bigPair == null || !bigPair.text.equals(searchWord))){
			returnList.add(bigPair.index);
			bigPair = bigIndexFile.readWordPairLinewise();
		}
		bigIndexFile.close();
		return returnList;
	}
	
	public static long binarySearch(WordPair lowPair, WordPair highPair, String searchWord, FileRandom mediumIndexFile) throws IOException{
		String lowWord = lowPair.text;
		long lowIndex = lowPair.index;
		String highWord = highPair.text;
		long highIndex = highPair.index;
		
		long midIndex = lowIndex + (highIndex-lowIndex)/2;
		WordPair midPair = mediumIndexFile.readWordWalkRight(midIndex);
		if(midPair.text.equals(highWord)){
			midPair = mediumIndexFile.readWordWalkLeft(midIndex);
		}
		if(midPair.text.equals(lowWord)){
			return -1;
		}
		int compareValue = searchWord.compareTo(midPair.text);
		if(compareValue == 0){
			return mediumIndexFile.readWordsIndex(midPair.index);
		} else if(compareValue < 0 ){
			return binarySearch(lowPair, midPair, searchWord, mediumIndexFile);
		} else{
			return binarySearch(midPair, highPair, searchWord, mediumIndexFile);
		}
		
	}
	public static int hashSmallIndex(String word){
		int returnValue = 0;
		for(int i = 0; i < 3 && i < word.length(); i++){
			returnValue += Math.pow(29, i)*convertCharToInt(word.charAt(i));
		}
		
		return returnValue;
		
	}
	
	private static int convertCharToInt(char c){
		int ascii = (int) c;
		if(ascii > 64 && ascii < 91){
			return ascii - 64;
		}else if(ascii > 96 && ascii < 123){
			return ascii - 96;
		}else if(ascii == 134 || ascii == 143){
			return 27;
		}else if(ascii == 228 || ascii == 142){
			return 28;
		}else if(ascii == 246 || ascii == 153){ // ska vara 146 inte 246
			return 29;
		} else{
			throw new RuntimeException("Character is not a letter: " + c + " and gives the ascii: " + ascii);
		}
	}

}
