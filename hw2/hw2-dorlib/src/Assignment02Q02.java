

public class Assignment02Q02 {
	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		
		// *** your code goes here below ***
		double sum = 0.0;
		int numOfReps = Integer.parseInt(args[0]);

		for (int i = 0; i < numOfReps; i++) {
			if (i % 2 == 0) {
				sum += 1.0 / ((2 * (i + 1)) - 1);
			} else {
				sum -= 1.0 / ((2 * (i + 1)) - 1);
			}
		}

		piEstimation = 4 * sum;
		
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}
}
