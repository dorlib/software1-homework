
public class Assignment02Q03 {
	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = -1;

		// *** your code goes here below ***
		n = Integer.parseInt(args[0]);
		String fibNumbers = "";

		Integer[] fibArr = fibonacci(n);

		for (int i = 0; i < n; i++) {
			int num = fibArr[i];
			fibNumbers += num + " ";

			if (num % 2 != 0) {
				numOfOdd++;
			}
		}

		System.out.println("The first "+ n +" Fibonacci numbers are:");

		// *** your code goes here below ***
		System.out.println(fibNumbers.substring(0, fibNumbers.length() - 1));
		System.out.println("The number of odd numbers is: "+numOfOdd);
	}

	private static Integer[] fibonacci(int amount) {
		Integer[] fibArr = new Integer[amount];

		fibArr[0] = 1;
		fibArr[1] = 1;

		for (int i = 0; i < amount - 2; i++) {
			fibArr[i + 2] = fibArr[i + 1] + fibArr[i];
		}

		return fibArr;
	}
}
