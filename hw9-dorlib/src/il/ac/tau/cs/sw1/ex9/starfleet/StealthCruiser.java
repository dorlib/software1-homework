package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {

	private static int stealthCruisersCounter = 0;
	private static final List<Weapon> defaultWeapons = initDefaultWeapons();


	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		stealthCruisersCounter++;
	}

	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		this(name, commissionYear, maximalSpeed, crewMembers, defaultWeapons);
	}


	private static List<Weapon> initDefaultWeapons(){
		Weapon weapon = new Weapon("Laser Cannons",10,100);
		List<Weapon> weapons = new ArrayList<Weapon>();
		weapons.add(weapon);

		return weapons;
	}

	public List<Weapon> getWeapon() {
		return this.getWeapons();
	}

	public int getFirePower() {
		return super.getFirePower();
	}

	public int getAnnualMaintenanceCost() {
		int res = 2500;

		for (Weapon weapon : this.getWeapons()) {
			res += weapon.getAnnualMaintenanceCost();
		}

		return res + 50 * stealthCruisersCounter + Math.round(1000 * this.maximalSpeed);
	}

	public String toString() {
		return super.toString();
	}
}
