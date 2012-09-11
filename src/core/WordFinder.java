package core;

import java.io.FileNotFoundException;

public final class WordFinder {

	public static FoundResults find(String word) throws FileNotFoundException {
		int smallIndex = getSmallIndex(word);
		FileRandom fr = new FileRandom("","");
		return null;
		
	}
	public static int getSmallIndex(String word){
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
