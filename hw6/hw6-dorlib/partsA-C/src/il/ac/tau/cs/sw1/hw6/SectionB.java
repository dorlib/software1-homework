package il.ac.tau.cs.sw1.hw6;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) {
		if (array != null) {
			for (int val : array) {
				if (val == value) {
					return true;
				}
			}
		}

		return false;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) { 
		return 0;
	}

	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] <= $ret
	*/
	public static int max(int[] array) { 
		return array[array.length - 1];
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) { 
		int minVal = array[0];
		for (int j : array) {
			if (j < minVal) {
				minVal = j;
			}
		}

		return minVal;
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(word.length() - i - 1)

	*/
	public static String reverse(String word) {
		char[] charArray = new char[word.length()];
		for (int i = word.length() - 1, j = 0; i >= 0; i--, j++) {
			charArray[j] = word.charAt(i);
		}

		return String.valueOf(charArray);
	}
	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret))
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true
	*/
	public static int[] guess(int[] array) { 
		int smallest = array[0];
		int bigger = 0;
		int indexOfBigger = 0;

		for (int i = 1; i < array.length; i++) {
			if (array[i] > smallest) {
				bigger = array[i];
				indexOfBigger = i;
				break;
			}
		}

		array[0] = bigger;
		array[indexOfBigger] = smallest;

		return array;
	}
}
