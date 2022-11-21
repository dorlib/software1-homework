public class StringUtils {
	public static String findSortedSequence(String str) {
		if (str.equals("")) {
			return "";
		}

		String[] arrOfString = str.split(" ");

		int numOfWords = arrOfString.length;

		String result = "";

		String[] results = new String[numOfWords + 1];
		int index = 0;

		for (int i = 0; i < numOfWords - 1; i++) {
			boolean ok = checkAlphabeticOrder(arrOfString[i], arrOfString[i + 1]);

			if (ok) {
				if (numOfWords(result) > 1) {
					result = result +  " " + arrOfString[i + 1];
				} else {
					result = result + " " + arrOfString[i] + " " + arrOfString[i + 1];
				}

				if (i == numOfWords - 2) {
					results[index] = result;
					++index;
					result = "";
				}
			} else {
				results[index] = result;
				++index;
				result = "";
			}
		}

		int maximumNumberOfWords = 0;
		int indexOfMax = 0;

		for (int i = 0; i < numOfWords; i++) {
			if (results[i] == null) {
				break;
			}

			int sum = numOfWords(results[i]);

			if (sum >= maximumNumberOfWords) {
				maximumNumberOfWords = sum;
				indexOfMax = i;
			}
		}

		if (maximumNumberOfWords < 2) {
			return arrOfString[arrOfString.length - 1];
		}

		return results[indexOfMax].substring(1);
	}

	private static boolean checkAlphabeticOrder(String firstWord, String secondWord) {
		int n = firstWord.length();

		if (secondWord.length() < n) {
			n = secondWord.length();
		}

		for (int i = 0; i < n; i++) {
			if (firstWord.charAt(i) > secondWord.charAt(i)) {
				return false;
			}

			if (firstWord.charAt(i) < secondWord.charAt(i)) {
				return true;
			}
		}
		return true;
	}

	private static int numOfWords(String str) {
		int state = 0;
		int counter = 0;
		int i = 0;

		while (i < str.length()) {
			if (str.charAt(i) == ' ') {
				state = 0;
			} else if (state == 0) {
				++counter;
				state = 1;
			}

			++i;
		}

		return counter;
	}

	public static boolean isEditDistanceOne(String a, String b){
		if (a.length() == b.length()) {
			return sameLengthEditDistanceOne(a, b);
		}

		if (Math.abs(a.length() - b.length()) == 1) {
			return differentLengthEditDistanceOne(a, b);
		}

		return false;
	}

	private static boolean sameLengthEditDistanceOne(String a, String b) {
		int numOfDiffChars = 0;

		for (int i = 0; i < a.length(); i++) {
			if (a.charAt(i) != b.charAt(i)) {
				++numOfDiffChars;
			}
		}

		return numOfDiffChars < 2;
	}

	private static boolean differentLengthEditDistanceOne(String a, String b) {
		int numOfDiffChars = 0;
		char shorter = 'a';
		int n = a.length();


		if (b.length() < n) {
			n = b.length();
			shorter = 'b';
		}

		for (int i = 0, j = 0; i < n; i++, j++) {
			if (a.charAt(i) != b.charAt(j)) {
				if (shorter == 'b') {
					--j;
				} else {
					--i;
				}
				++numOfDiffChars;
			}
			if (j == Math.min(a.length(), b.length())) {
				break;
			}
		}

		return numOfDiffChars < 2;
	}
}
