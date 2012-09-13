package core;

import java.io.File;
import java.io.IOException;

public class Main {
	public static final String INDEX_PATH = "/var/tmp/indices/";
	public static final String MEDIUM_INDEX_NAME = INDEX_PATH + "medium";
	public static final String SMALL_INDICES_PATH = INDEX_PATH + "small/";
	public static final String BIG_INDEX_NAME = INDEX_PATH + "words";
	public static final String KORPUS_NAME = "/info/adk12/labb1/korpus";
	public static final Integer SURROUNDING_TEXT_SIZE = 30;
	
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			String word = args[0];
			
			System.out.println("Word to analyze: " + word);
			WordPair result = WordFinder.find(word);
			if(result == null){
				System.out.println("Word not found!");
				return;
			}
			System.out.println("Det finns " + result.index + " förekomster av ordet.");
			System.out.println(result.text);	
		}
		else if (args.length == 2) {
			String bigIndicePath = args[0];
			writeIndices(bigIndicePath);
			
		}
		else {
			System.out.println("Specify arguments.");
		}
	}
	
	private static void writeIndices(String bigIndexPath) throws IOException{
		long bigIndexPos = 0;
		long mediumIndexPos = 0;
		String lastMediumWord = "";
		
		File f = new File(INDEX_PATH);
		f.mkdirs();
		
		// Delete old files and create new medium index file and also create new directory for small indices
		FileBuffered bigIndexFile = new FileBuffered(bigIndexPath, "r");
		f = new File(MEDIUM_INDEX_NAME);
		delete(f);
		f.createNewFile();
		delete(SMALL_INDICES_PATH.substring(0, SMALL_INDICES_PATH.length() - 1 ));
		new File(SMALL_INDICES_PATH.substring(0, SMALL_INDICES_PATH.length() - 1 )).mkdir();
		
		// Open the writer to newly created medium index file
		FileBuffered mediumIndexFile = new FileBuffered(MEDIUM_INDEX_NAME, "w");
		// Get the first word and index pair in the big index file
		WordPair bigIndexPair = bigIndexFile.readWordPair();
		
		// Loop through the big index file until we have no more pairs/lines
		while(bigIndexPair != null){
			String currentBigWord = bigIndexPair.text;
			if(!lastMediumWord.equals(bigIndexPair.text)){
				// We have a new word that have not been added to the medium index file yet
				// Add the new word to the medium index file together with the byte-position to the big index file
				String appendMediumText = currentBigWord + " " + bigIndexPos + " ";
				mediumIndexFile.append(appendMediumText);
				lastMediumWord = currentBigWord;
				String smallIndexFileName = SMALL_INDICES_PATH + WordFinder.hashSmallIndex(lastMediumWord);
				File newSmallFile = new File(smallIndexFileName);
				if(!newSmallFile.isFile()){
					// We have a new "three-word-combination" that needs to added to the small indices directory
					newSmallFile.createNewFile();
					FileBuffered smallIndexFile = new FileBuffered(smallIndexFileName, "w");
					smallIndexFile.write(mediumIndexPos + "");
					smallIndexFile.close();
				}
				mediumIndexPos += appendMediumText.length();
			}
			int largeIndexPlusser = currentBigWord.length() + (bigIndexPair.index + "").length() + 2;
			bigIndexPos += largeIndexPlusser;
			bigIndexPair = bigIndexFile.readWordPair();
		}
		
		mediumIndexFile.close();
		bigIndexFile.close();
		System.out.println("Done createing indices!");
		
	}
	
	public static void delete(String filePath) throws IOException{
		File file = new File(filePath);
		delete(file);
	}
	
	public static void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	 
	    		   file.delete();
	    		   System.out.println("Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	 
	    		}else{
	 
	    		   //list all the directory contents
	        	   String files[] = file.list();
	 
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	 
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     System.out.println("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	 
	    	}else if(file.isFile()){
	    		//if file, then delete it
	    		file.delete();
	    	}
	    }
}
