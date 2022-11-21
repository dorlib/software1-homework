public class Assignment1 {
	public static void main(String[] args){
		char newChar = charInOffset(args[0].charAt(0), Integer.parseInt(args[1]));
		System.out.println("New char is " + newChar + ".");
	}

	private static char charInOffset(char chr, int offset) {
		String abc = "abcdefghijklmnopqrstuvwxyz";
		int index = (abc.indexOf(chr) + offset) % 26;

		if (index < 0) {
			index = index + 26;
		}

		return abc.charAt(index);
	}
}


