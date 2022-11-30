package il.ac.tau.cs.sw1.ex5;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		String[] vocabulary = new String[MAX_VOCABULARY_SIZE];
		int vocabularyIndex = 0;

		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;

		while ((line = bufferedReader.readLine()) != null && vocabularyIndex < MAX_VOCABULARY_SIZE) {
			String[] wordsInLine = line.split(" ");

			for (String word : wordsInLine) {
				word = word.toLowerCase();
				if (isLegalWord(word)) {
					if (!wordInVocabulary(Arrays.copyOfRange(vocabulary, 0, vocabularyIndex), word)) {
						vocabulary[vocabularyIndex] = word;
						vocabularyIndex++;
					}
				} else if (isLegalHoleNumber(word)) {
					if (!wordInVocabulary(Arrays.copyOfRange(vocabulary, 0, vocabularyIndex), word)) {
						vocabulary[vocabularyIndex] = SOME_NUM;
						vocabularyIndex++;
					}
				}
			}
		}

		bufferedReader.close();

		return Arrays.copyOfRange(vocabulary, 0, vocabularyIndex);
	}

	private static boolean isLegalWord(String word) {
		char[] charsInWord = word.toCharArray();
		int numOfEnglishChars = 0;

		for (int i = 0; i < charsInWord.length; i++) {
			char chr = word.charAt(i);

			if (isEnglishChar(chr)) {
				numOfEnglishChars++;
			}
		}

		return numOfEnglishChars >= 1;
	}

	private static boolean isEnglishChar(char chr) {
		return switch (chr) {
			case 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' ->
					true;
			default -> false;
		};
	}

	private static boolean isLegalHoleNumber(String StrNumber) {
		char[] charsInWord = StrNumber.toCharArray();

		for (char c : charsInWord) {
			if (!Character.isDigit(c)) {
				return false;
			}
		}

		return true;
	}
	
	private static boolean wordInVocabulary(String[] vocabulary, String word) {
		for (String s : vocabulary) {
			if (Objects.equals(s, word)) {
				return true;
			}
		}

		return false;
	}
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		int[][] countArray = new int[MAX_VOCABULARY_SIZE][MAX_VOCABULARY_SIZE];

		File file = new File(fileName);
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		String line;

		while ((line = bufferedReader.readLine()) != null) {
			String[] wordsInLine = line.split(" ");

			for (int i = 0; i < wordsInLine.length - 1; i++) {
				String firstWord = wordsInLine[i].toLowerCase();
				String secondWord = wordsInLine[i + 1].toLowerCase();

				if (isLegalHoleNumber(firstWord)) {
					firstWord = SOME_NUM;
				}

				if (isLegalHoleNumber(secondWord)) {
					secondWord = SOME_NUM;
				}

				int indexOfFirstWord = IndexOfWord(vocabulary, firstWord);
				int indexOfSecondWord = IndexOfWord(vocabulary, secondWord);

				if (indexOfFirstWord > -1 && indexOfSecondWord > -1) {
					countArray[indexOfFirstWord][indexOfSecondWord]++;
				}
			}
		}

		bufferedReader.close();

		return countArray;
	}

	private static int IndexOfWord(String[] vocabulary, String word) {
		for (int i = 0; i < vocabulary.length; i++) {
			if (Objects.equals(vocabulary[i], word)) {
				return i;
			}
		}

		return ELEMENT_NOT_FOUND;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		File countFile = new File(fileName + COUNTS_FILE_SUFFIX);

		BufferedWriter vocFileWriter = new BufferedWriter(new FileWriter(vocFile));
		BufferedWriter countFileWriter = new BufferedWriter(new FileWriter(countFile));

		// write to vocFile
		int vocLength = this.mVocabulary.length;
		vocFileWriter.write(vocLength + " words" + "\r\n");

		int i = 0;

		while (i < vocLength) {
			vocFileWriter.write(i+ "," + this.mVocabulary[i] + "\r\n");
			i++;
		}

		vocFileWriter.close();

		// write to counterFile
		int[][] counter = this.mBigramCounts;
		for (int j = 0; j < counter.length; j++) {
			for (int k = 0; k < counter[i].length; k++) {
				int res = counter[j][k];
				if (res > 0) {
					countFileWriter.write(j + "," + k + ":" + res + "\r\n");
				}
			}
		}

		countFileWriter.close();
	}

	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException { // Q - 4
		File vocFile = new File(fileName + VOC_FILE_SUFFIX);
		File countFile = new File(fileName + COUNTS_FILE_SUFFIX);

		BufferedReader vocFileReader = new BufferedReader(new FileReader(vocFile));
		BufferedReader countFileReader = new BufferedReader(new FileReader(countFile));

		// read from vocFile and initialize this.mVocabulary.
		String line = vocFileReader.readLine();

		int maxIndex = Integer.parseInt(Character.toString(line.charAt(0)));
		String[] vocabulary = new String[maxIndex];
		int vocabularyIndex = 0;

		while ((line = vocFileReader.readLine()) != null) {
			String[] lineAsArr = line.split(",");
			vocabulary[vocabularyIndex] = lineAsArr[1];
			vocabularyIndex++;
		}

		this.mVocabulary = vocabulary;

		// read from counterFile and initialize this.mBigramCounts.
		int[][] counter = new int[maxIndex][maxIndex];

		while ((line = countFileReader.readLine()) != null) {
			int firstIndex = Integer.parseInt(Character.toString(line.charAt(0)));
			int secondIndex = Integer.parseInt(Character.toString(line.charAt(2)));
			int value = Integer.parseInt(Character.toString(line.charAt(4)));
			counter[firstIndex][secondIndex] = value;
		}

		this.mBigramCounts = counter;
	}



	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		for (int i = 0; i < this.mVocabulary.length; i++) {
			if (Objects.equals(this.mVocabulary[i], word)) {
				return i;
			}
		}

		return ELEMENT_NOT_FOUND;
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int indexOfFirstWord = getWordIndex(word1);
		int indexOfSecondWord = getWordIndex(word2);

		if (indexOfFirstWord < 0 || indexOfSecondWord < 0) {
			return 0;
		}

		return this.mBigramCounts[indexOfFirstWord][indexOfSecondWord];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int maxIndex = this.mVocabulary.length;
		int maxNumOfAppearances = 0;
		int indexOfMaxNumOfAppearances = 0;
		int indexOfWord = getWordIndex(word);

		for (int i = 0; i < maxIndex; i++) {
			int res = this.mBigramCounts[indexOfWord][i];
			if (res > maxNumOfAppearances) {
				maxNumOfAppearances = res;
				indexOfMaxNumOfAppearances = i;
			}
		}

		if (maxNumOfAppearances > 0) {
			String[] voc = this.mVocabulary;
			return voc[indexOfMaxNumOfAppearances];
		}

		return null;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		String[] sentenceAsArr = sentence.split("\\W+");

		if (sentenceAsArr.length == 0) {
			return true;
		}

		if (sentenceAsArr.length == 1) {
			return getWordIndex(sentenceAsArr[0]) >= 0;
		}

		for (String s : sentenceAsArr) {
			int res = getWordIndex(s);
			if (res < 0) {
				return false;
			}
		}

		for (int i = 0; i < sentenceAsArr.length - 1; i++ ) {
			int res = getBigramCount(sentenceAsArr[i], sentenceAsArr[i + 1]);
			if (res == 0) {
				return false;
			}
		}

		return true;
	}
	
	
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		if (isArrOfZeroes(arr1) || isArrOfZeroes(arr2)) {
			return -1;
		}

		double up = sqrtOfSumOfAMultiplyB(arr1, arr2, 1);
		double down = sqrtOfSumOfAMultiplyB(arr1, arr1, 0.5) * sqrtOfSumOfAMultiplyB(arr2, arr2, 0.5);

		return up / down;
	}

	private static boolean isArrOfZeroes(int[] arr) {
		for (int j : arr) {
			if (j != 0) {
				return false;
			}
		}

		return true;
	}

	private static double sqrtOfSumOfAMultiplyB(int[] arr1, int[] arr2, double power) {
		int sum = 0;

		for (int i = 0; i < arr1.length; i++) {
			int res = arr1[i] * arr2[i];
			sum += res;
		}

		return Math.pow(sum, power);
	}


	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		if (this.mVocabulary.length < 2) {
			return this.mVocabulary[0];
		}

		int indexOfWord = getWordIndex(word);
		int[] wordVector = new int[this.mVocabulary.length];
		int[] secondVector = new int[this.mVocabulary.length];

		System.arraycopy(this.mBigramCounts[indexOfWord], 0, wordVector, 0, this.mVocabulary.length);

		double cosineSim = 0;
		String closestWord = "";

		for (String w : this.mVocabulary) {
			int IndexOfSecondWord = getWordIndex(w);

			if (IndexOfSecondWord != indexOfWord) {
				System.arraycopy(this.mBigramCounts[IndexOfSecondWord], 0, secondVector, 0, this.mVocabulary.length);
				double res = calcCosineSim(wordVector, secondVector);
				if (res > cosineSim) {
					cosineSim = res;
					closestWord = w;
				}
			}
		}

		return closestWord;
	}
	
}
