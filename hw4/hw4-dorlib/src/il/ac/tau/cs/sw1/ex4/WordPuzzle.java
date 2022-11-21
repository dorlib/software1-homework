package il.ac.tau.cs.sw1.ex4;

import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1
		// max size of hiddenChars will be the size of the english alphabet.
		char[] puzzle = new char[template.length];

		for(int i = 0; i < template.length; i++) {
			if (template[i]) {
				char hiddenChar = word.charAt(i);
				if (hiddenChar == HIDDEN_CHAR) {
					continue;
				}

				for (int j = i; j < template.length; j++) {
					if (word.charAt(j) == hiddenChar) {
						puzzle[j] = HIDDEN_CHAR;
					}
				}
			} else  {
				puzzle[i] = word.charAt(i);
			}
		}

		return puzzle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2
		if (word.length() != template.length) {
			return false;
		}

		if (word.length() < 2) {
			return false;
		}

		int numOfHiddenChars = 0;
		int numOfVisibleChars = 0;
		for (boolean b : template) {
			if (b) {
				numOfHiddenChars++;
			} else {
				numOfVisibleChars++;
			}
		}

		if (numOfHiddenChars < 1 || numOfVisibleChars < 1) {
			return false;
		}

		for (int i = 0; i < template.length; i++) {
			if (template[i]) {
				char hiddenChar = word.charAt(i);
				for (int j = 0; j < template.length; j++) {
					if (i != j) {
						if (!template[j] && word.charAt(j) == hiddenChar) {
							return false;
						}
					}
				}
			}
		}

		return true;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		if (k > word.length() || k < 1) {
			return new boolean[][]{};
		}

		int n = (int) ((Math.pow(2, word.length())) - (Math.pow(2, k) - 1));
		boolean[][] legalTemplates = new boolean[n][word.length()];
		int IndexOfLegalTemplates = 0;
		int templateAsInt = 0;

		for (int i = templateAsInt; i < n + 1; i ++) {
			boolean[] template = intToTemplate(i, word.length());
			int numOfTrueValues = 0;

			for (boolean b : template) {
				if (b) {
					numOfTrueValues++;
				}
			}

			if (numOfTrueValues == k) {
				if (checkLegalTemplate(word, template)) {
					legalTemplates[IndexOfLegalTemplates] = template;
					IndexOfLegalTemplates++;
				}
			}
		}

		if (IndexOfLegalTemplates == 0) {
			return new boolean[][]{};
		}

		return Arrays.copyOfRange(legalTemplates, 0, IndexOfLegalTemplates);
	}

	private static boolean[] intToTemplate(int num, int templateLength) {
		StringBuilder binaryRep = new StringBuilder(Integer.toBinaryString(num));
		boolean[] template = new boolean[templateLength];
		int n = binaryRep.length();

		for (int i = 0; i < templateLength - n; i++) {
			binaryRep.insert(0, '0');
		}

		for (int i = 0; i < binaryRep.length(); i++) {
			template[i] = binaryRep.charAt(i) == '1';
		}

		return template;
	}
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		int numOfCharsGuessed = 0;
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == guess && puzzle[i] == HIDDEN_CHAR) {
				puzzle[i] = guess;
				numOfCharsGuessed++;
			}
		}

		return numOfCharsGuessed;
	}

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		char[] correctChars = getCorrectChars(word, puzzle);
		char[] incorrectChars = getIncorrectChars(word, already_guessed);

		int randomIndexForCorrectChars = (int)(Math.random() * correctChars.length);
		int randomIndexForIncorrectChars = (int) (Math.random() * incorrectChars.length);

		char charFromCorrect = correctChars[randomIndexForCorrectChars];
		char charFromIncorrect = incorrectChars[randomIndexForIncorrectChars];

		if (charFromIncorrect > charFromCorrect) {
			return new char[]{charFromCorrect, charFromIncorrect};
		}

		return new char[]{charFromIncorrect, charFromCorrect};
	}

	private static char[] getCorrectChars(String word, char[] puzzle) {
		char[] correctChars = new char[word.length()];
		int IndexOfCorrectChars = 0;

		for (int i = 0; i < puzzle.length; i++) {
			if (puzzle[i] == HIDDEN_CHAR) {
				correctChars[IndexOfCorrectChars] = word.charAt(i);
				IndexOfCorrectChars++;
			}
		}

		return Arrays.copyOfRange(correctChars, 0, IndexOfCorrectChars);
	}

	private static char[] getIncorrectChars(String word, boolean[] already_guessed) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char[] incorrectChars = new char[26];
		int IndexOfIncorrectChars = 0;

		for (int i = 0; i < 26; i++) {
			if (!checkIfCharInWord(word, alphabet.charAt(i))) {
				if (!checkIfCharGuessed(already_guessed, alphabet.charAt(i))) {
					incorrectChars[IndexOfIncorrectChars] = alphabet.charAt(i);
					IndexOfIncorrectChars++;
				}
			}
		}

		return Arrays.copyOfRange(incorrectChars, 0, IndexOfIncorrectChars);
	}

	private static boolean checkIfCharInWord(String word, char chr) {
		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) == chr) {
				return true;
			}
		}

		return false;
	}

	private static boolean checkIfCharGuessed(boolean[] alreadyGuessed, char chr) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		for (int i = 0; i < alreadyGuessed.length; i++) {
			if (alreadyGuessed[i]) {
				char charGuessed = alphabet.charAt(i);
				if (charGuessed == chr) {
					return true;
				}
			}
		}

		return false;
	}

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6
		printSettingsMessage();

		while (true) {
			printSelectTemplate();

			String selectedTemplate = inputScanner.next();

			if (Objects.equals(selectedTemplate, "1")) {
				printSelectNumberOfHiddenChars();
				String numOfHiddenChars = inputScanner.next();
				boolean[][] legalTemplates = getAllLegalTemplates(word, Integer.parseInt(numOfHiddenChars));

				if (legalTemplates.length < 1) {
					printWrongTemplateParameters();
					continue;
				}

				boolean[] randomTemplate = legalTemplates[(int) (Math.random() * legalTemplates.length)];

				return createPuzzleFromTemplate(word, randomTemplate);
			} else if (Objects.equals(selectedTemplate, "2")) {
				printEnterPuzzleTemplate();
				String templateEntered = inputScanner.next();
				String[] temp = templateEntered.split(",");
				boolean[] template = new boolean[temp.length];

				for (int i = 0; i < temp.length; i++) {
					if (Objects.equals(temp[i], "X")) {
						template[i] = false;
					} else if (Objects.equals(temp[i], "_")) {
						template[i] = true;
					}
				}

				if (checkLegalTemplate(word, template)) {
					return createPuzzleFromTemplate(word, template);
				} else {
					printWrongTemplateParameters();
				}
			}
		}
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		printGameStageMessage();

		int numOfGuesses = 3;

		for (char c : puzzle) {
			if (c == '_') {
				numOfGuesses++;
			}
		}

		int numOfHiddenChars = numOfGuesses -3;

		char[] guessedChars = new char[numOfGuesses];
		int guessedCharsIndex = 0;

		while (numOfGuesses > 0) {
			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			String userInput = inputScanner.next();

			if (Objects.equals(userInput, "H")) {
				char[] hint = getHint(word, puzzle, charsArrToBoolArr(guessedChars));
				printHint(hint);
			} else {

				numOfGuesses--;

				if (charInArr(userInput.charAt(0), guessedChars)) {
					continue;
				}

				guessedChars[guessedCharsIndex] = userInput.toCharArray()[0];

				int numOfCharsFound = applyGuess(userInput.charAt(0), word, puzzle);

				// successful guess
				if (numOfCharsFound > 0) {
					numOfHiddenChars = numOfHiddenChars - numOfCharsFound;

					if (numOfHiddenChars < 1) {
						printWinMessage();
						break;
					} else {
						printCorrectGuess(numOfGuesses);

						if (numOfGuesses < 1) {
							printGameOver();
							break;
						}
					}
				} else {
					printWrongGuess(numOfGuesses);

					if (numOfGuesses < 1) {
						printGameOver();
						break;
					}

				}
			}
		}
	}

	private static boolean[] charsArrToBoolArr(char[] charArr) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		boolean[] result = new boolean[26];

		for (char chr : charArr) {
			int indexInResult = 0;

			for (int j = 0; j < 26; j++) {
				if (alphabet.charAt(j) == chr) {
					indexInResult = j;
				}
			}

			result[indexInResult] = true;
		}

		return result;
	}

	private static boolean charInArr(char chr, char[] arr) {
		for (char c : arr) {
			if (c == chr) {
				return true;
			}
		}

		return false;
	}


/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception {
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
