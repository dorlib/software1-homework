package il.ac.tau.cs.sw1.ex9.starfleet;

public class Cylon extends CrewWoman{
	private int modelNumber;

	public Cylon(String name, int age, int yearsInService, int modelNumber) {
		super (age, yearsInService, name);
		this.modelNumber = modelNumber;
	}

	public int getModelNumber() {
		return this.modelNumber;
	}

	public String toString() {
		String res = "Cylon" + System.lineSeparator();
		res += super.toString();
		res += "	";
		res = res + "YearsInService=" + this.getYearsInService() + System.lineSeparator();
		res += "	";
		res = res + "ModelNumber=" + this.getModelNumber() + System.lineSeparator();

		return res;
	}
}
