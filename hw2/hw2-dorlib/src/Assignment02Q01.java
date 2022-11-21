
public class Assignment02Q01 {

	public static void main(String[] args) {
		// *** your code goes here below ***
		for (String arg : args) {
			char chr = arg.charAt(0);

			if ((int) chr % 5 == 0) {
				System.out.println(chr);
			}
		}
	}
}
