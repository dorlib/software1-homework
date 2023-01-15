package il.ac.tau.cs.sw1.ex9.starfleet;

public class Officer extends CrewWoman {

	private OfficerRank rank;

	public Officer(String name, int age, int yearsInService, OfficerRank rank) {
		super(age, yearsInService, name);
	 	this.rank = rank;
	}

	public OfficerRank getRank() {
		return this.rank;
	}

	public String toString() {
		String res = "Officer" + System.lineSeparator();
		res += super.toString();
		res += "	";
		res = res + "Rank=" + this.getRank() + System.lineSeparator();

		return res;
	}
	
}
