package il.ac.tau.cs.sw1.ex4;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class WordPuzzleTester {
	public static void main(String[] args) {
		// puzzle1 = {w,h,_,i,_}
		char[] puzzle1 = WordPuzzle.createPuzzleFromTemplate("while",
				new boolean[] { false, false, true, false, true });
		if (!Arrays.equals(puzzle1, new char[] { 'w', 'h', WordPuzzle.HIDDEN_CHAR, 'l', WordPuzzle.HIDDEN_CHAR })) {
			System.err.println("Error 1.1");
		}

		boolean[] template1 = { true, true, true, false, true, true };
		boolean[] template2 = { true, true, false, false, true, true };

		// puzzle = {_,_,_,e,_,_}
		if (WordPuzzle.checkLegalTemplate("wheeps", template1)) {
			System.err.println("Error 2.1");
		}

		// puzzle = {_,_,e,e,_,_}
		if (!WordPuzzle.checkLegalTemplate("wheeps", template2)) {
			System.err.println("Error 2.2");
		}

		boolean[][] templates = WordPuzzle.getAllLegalTemplates("look", 1);
		if (templates.length != 2) {
			System.err.println("Error 3.1");
		}
		if (!Arrays.equals(templates[0],(new boolean[] { false, false, false, true }))) {
			System.err.println("Error 3.2");
		}

		// puzzle = {w,_,_,_,_s}
		char[] puzzle = WordPuzzle.createPuzzleFromTemplate("wheeps",
				new boolean[] { false, true, true, true, true, false });

		int numOfChangedLetters = WordPuzzle.applyGuess('h', "wheeps", puzzle);
		if (numOfChangedLetters != 1) {
			System.err.println("Error 4.1");
		}
		numOfChangedLetters = WordPuzzle.applyGuess('e', "wheeps", puzzle);
		if (numOfChangedLetters != 2) {
			System.err.println("Error 4.2");
		}
		boolean[] already_guessed = new boolean[26];
		
		// already guessed [a,b,c,d]
		for (int i = 0; i <4; i++){
			already_guessed[i] = true;
		}
		
		// puzzle = {w,_,_,_,_s}
		puzzle = WordPuzzle.createPuzzleFromTemplate("wheeps",
						new boolean[] { false, true, true, true, true, false });
		for (int j = 0; j < 100; j++){
			char[] hint = WordPuzzle.getHint("wheeps", puzzle, already_guessed);
			/*
			 * correctGuesses - all those letters are a "correct" hint.
			 * incorrectGuesses - none of those letters should be the "misleading" hint. Some
			 * of them have been guessed already (a,b,c,d), and the rest appear in the word
			 */
			String correctGuesses = "hep"; 
			String incorrectGuesses = "whepsabcd";
			boolean case1 = (correctGuesses.contains("" + hint[0]) && 
					!incorrectGuesses.contains("" + hint[1]));  //first char is correct
			boolean case2 = (correctGuesses.contains("" + hint[1]) && 
					!incorrectGuesses.contains("" + hint[0]));  //first char is incorrect
			if ( !case1 && !case2){
				System.err.println("Error 5.1");
			}
		}
		
		
		
		/*
		 * In order for these tests to run properly, your scanner should only use: nextInt(), nextChar() and next() 
		 * to read integerts, characters and strings. Alternatively, you should only use nextLine() to read each line.
		 * mixing nextLine() with the rest of the functions will fail this test (and is more problematic in general).
		 */


		testMainTemplate();
		testMainGame();
		System.out.println("Done!");	
	}
	
	public static void testMainTemplate(){
		PrintStream sysOut = System.out; //backup System.in
		try{
			redirectOutput(); // no need to save output

			Scanner inputScanner = getInputScanner("2", "_,X,X,_,_", "someOtherString");

			char[] puzzle = WordPuzzle.mainTemplateSettings("print", inputScanner);
			System.setOut(sysOut);
			if (!Arrays.equals(puzzle, new char[] {'_', 'r', 'i', '_', '_'})){
				System.err.println("Error 6.1");
			}
			checkScannerIsNotClosed(inputScanner, "6");

		}
		finally{
			System.setOut(sysOut); //restore System.out
		}
	}
	
	public static void testMainGame(){
		PrintStream sysOut = System.out; //backup System.in
		try{
			ByteArrayOutputStream baos = redirectOutput();
			
			Scanner inputScanner = getInputScanner("p", "n", "t", "someOtherString");
			
			WordPuzzle.mainGame("print", new char[] {'_', 'r', 'i', '_', '_'}, inputScanner);
			System.out.flush();
			System.setOut(sysOut);
			if (! baos.toString().contains("Congratulations")){
				System.err.println("Error 7.1");
			}
			checkScannerIsNotClosed(inputScanner, "7");
		}
		finally{
			System.setOut(sysOut); //restore System.out

		}
	}
	
	private static void checkScannerIsNotClosed(Scanner s, String errorPrefix){
		try{
			if (!s.hasNext()){
				System.err.println(String.format("Error %s.2", errorPrefix));
			}
		}
		catch (IllegalStateException exp){
			// scanner should not be closed
			System.err.println(String.format("Error %s.2", errorPrefix));
		}
	}
	private static ByteArrayOutputStream redirectOutput(){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
		return baos;
	}
	
	private static Scanner getInputScanner(String... arr){
		String inputString = String.join(System.lineSeparator(), arr);
		InputStream inStream = new ByteArrayInputStream(inputString.getBytes());
		Scanner inputScanner = new Scanner(inputString);
		return inputScanner;
	}

}
