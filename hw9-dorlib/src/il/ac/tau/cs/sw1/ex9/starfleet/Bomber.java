package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Bomber extends myBattleShip {

	private int numOfTechnicians;
	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons, int numberOfTechnicians){
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.numOfTechnicians = numberOfTechnicians;
	}

	public int getNumberOfTechnicians() {
		return this.numOfTechnicians;
	}

	public int getAnnualMaintenanceCost() {
		return (int) (5000 + Math.floor(super.getAnnualMaintenanceCost() * (1 - (0.1 * numOfTechnicians))));
	}

	public String toString() {
		String res = super.toString();
		res +=  System.lineSeparator();
		res += "	";
		res += super.getWeaponsListAsString() + System.lineSeparator();
		res += "	";
		res = res + "NumberOfTechnicians=" + this.getNumberOfTechnicians();

		return res;
	}
}
