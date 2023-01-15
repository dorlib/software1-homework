package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class ColonialViper extends Fighter {

	private Set<CrewWoman> crewWomenSet;
	private List<Weapon> weapons;

	public ColonialViper(String name, int commissionYear, float maximalSpeed, Set<CrewWoman> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.weapons = weapons;
		this.crewWomenSet = crewMembers;
	}

	public List<Weapon> getWeapons() {
		return this.weapons;
	}

	public int getFirePower() {
		return super.getFirePower();
	}

	public int getAnnualMaintenanceCost() {
		int res = 4000;

		for (Weapon weapon: this.weapons) {
			res += weapon.getAnnualMaintenanceCost();
		}

		return res + (int) Math.floor(this.maximalSpeed * 500) + this.crewWomenSet.size() * 500;
	}

	public String toString() {
		return super.toString();
	}
}
