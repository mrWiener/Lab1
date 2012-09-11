package core;

import java.io.File;
import java.io.IOException;

public class Main {
	
	public static final String MEDIUM_INDEX_PATH = "medium_index.txt";
	public static final String SMALL_INDEX_PATH = "resources/";
	
	public static void main(String[] args) throws IOException {
		if (args.length == 1) {
			String word = args[0];
			
			System.out.println("Word to analyze: " + word);
			FoundResults fr = WordFinder.find(word);
			
		}
		else if (args.length == 2) {
			String bigIndexPath = args[1];
			writeIndexes(bigIndexPath);
			
		}
		else {
			System.out.println("Specify arguments.");
		}
	}
	
	private static void writeIndexes(String bigIndexPath) throws IOException{
		long largeIndexPos = 0;
		long mediumIndexPos = 0;
		String lastMediumWord = "";
		
		FileBuffered largeIndexFile = new FileBuffered(bigIndexPath, "r");
		File f = new File(MEDIUM_INDEX_PATH);
		delete(f);
		f.createNewFile();
		delete(SMALL_INDEX_PATH.replace("/", ""));
		
		
		FileBuffered mediumIndexFile = new FileBuffered(MEDIUM_INDEX_PATH, "w");
		
		WordPair bigIndexPair = largeIndexFile.readWordPair();
		while(bigIndexPair != null){
			String currentBigWord = bigIndexPair.text;
			if(!lastMediumWord.equals(bigIndexPair.text)){
				String appendMediumText = currentBigWord + " " + largeIndexPos + " ";
				mediumIndexFile.append(appendMediumText);
				lastMediumWord = currentBigWord;
				String smallIndexFileName = SMALL_INDEX_PATH + WordFinder.getSmallIndex(lastMediumWord) + ".txt";
				File newSmallFile = new File(smallIndexFileName);
				if(!newSmallFile.isFile()){
					newSmallFile.createNewFile();
					FileBuffered smallIndexFile = new FileBuffered(smallIndexFileName, "w");
					smallIndexFile.write(mediumIndexPos + "");
				}

				mediumIndexPos += appendMediumText.length();
			}
			int largeIndexPlusser = currentBigWord.length() + (bigIndexPair.index + "").length() + 2;
			largeIndexPos += largeIndexPlusser;
		}
		//Bör kanske ta bort sista "mellanslaget" i mediumIndexFile här.
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
	    		System.out.println("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
}
