package core;

public class Main {
	public static void main(String[] args) {
		if (args.length == 1) {
			String word = args[0];
			
			System.out.println("Word to analyze: " + word);
			FoundResults fr = WordFinder.find(word);
			
		}
		else if (args.length == 2) {
			String file = args[1];
			
			System.out.println(file);
		}
		else {
			System.out.println("Specify arguments.");
		}
	}
}
