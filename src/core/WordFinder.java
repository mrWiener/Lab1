package core;

public final class WordFinder {

	public static FoundResults find(String word) {
		int smallIndex = getSmallIndex(word);
		return null;
		
	}
	public static int getSmallIndex(String word){
		int returnValue = 0;
		for(int i = 0; i < 3 || i < word.length(); i++){
			returnValue += convertCharToInt(word.charAt(i))*28^i;
		}
		
		return returnValue;
		
	}
	
	public static int convertCharToInt(char c){
		int ascii = (int) c;
		if(ascii > 64 && ascii < 91){
			return ascii - 64;
		}else if(ascii > 96 && ascii < 123){
			return ascii - 96;
		}else if(ascii == 134 || ascii == 143){
			return 27;
		}else if(ascii == 132 || ascii == 142){
			return 28;
		}else if(ascii == 148 || ascii == 153){
			return 29;
		} else{
			throw new RuntimeException("Character is not a letter: " + c);
		}
	}

}
