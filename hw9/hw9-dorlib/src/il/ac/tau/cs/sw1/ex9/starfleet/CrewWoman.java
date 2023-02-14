package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public class CrewWoman extends myAbstractWorker {

	public CrewWoman(int age, int yearsInService, String name) {
		super(age, yearsInService, name);
	}

	public String toString() {
		String res = "CrewWoman" + System.lineSeparator();
		res += super.toString();

		return res;
	}
}
