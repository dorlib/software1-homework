package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Set;

public class CylonRaider extends Fighter {

	private List<Weapon> weapons;
	private Set<Cylon> cylonSet;

	public CylonRaider(String name, int commissionYear, float maximalSpeed, Set<Cylon> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		this.cylonSet = crewMembers;
		this.weapons = weapons;
	}

	public List<Weapon> getWeapons() {
		return this.weapons;
	}

	public int getFirePower() {
		return super.getFirePower();
	}

	public int getAnnualMaintenanceCost() {
		int res = 3500;

		for (Weapon weapon: this.weapons) {
			res += weapon.getAnnualMaintenanceCost();
		}

		res += this.cylonSet.size() * 500;
		res += this.maximalSpeed * 1200;

		return res;
	}

	public String getWeaponsListAsString() {
		StringBuilder res = new StringBuilder("WeaponArray=[");

		for (int i=0; i< weapons.size()-1; i++) {
			Weapon item = weapons.get(i);
			res.append(item.toString()).append(", ");
		}

		Weapon lastItem = weapons.get(weapons.size()-1);
		res.append(lastItem.toString()).append("]");

		return res.toString();
	}

	public String toString() {
		return super.toString();
	}
}
