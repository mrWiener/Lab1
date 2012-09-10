package core;

public class Main {
	public static void main(String[] args) {
		if (args.length == 1) {
			String word = args[0];
			
			System.out.println("Word to analyze: " + word);
		}
		else if (args.length == 2) {
			String filename = args[1];
			
		}
		else {
			System.out.println("Specify arguments.");
		}
	}
}
