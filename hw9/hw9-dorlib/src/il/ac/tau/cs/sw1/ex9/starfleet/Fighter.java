package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class Fighter extends myBattleShip {
	
	public Fighter(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons){
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
	}

	public int getFirePower() {
		return super.getFirePower();
	}

	public int getAnnualMaintenanceCost() {
		return super.getAnnualMaintenanceCost() + Math.round(1000 * this.getMaximalSpeed()) + 2500;
	}

	public String toString() {
		String res = super.toString();
		res +=  System.lineSeparator();
		res += "	";
		res += super.getWeaponsListAsString();

		return res;
	}

}
