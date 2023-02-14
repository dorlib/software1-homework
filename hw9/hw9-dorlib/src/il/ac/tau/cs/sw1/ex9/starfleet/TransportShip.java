package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip extends myAbstractShip {

	int cargoCapacity;
	int passengerCapacity;

	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers, int cargoCapacity, int passengerCapacity){
		super(name, commissionYear, maximalSpeed, crewMembers);
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
	}

	public int getCargoCapacity() {
		return this.cargoCapacity;
	}

	public int getPassengerCapacity() {
		return this.passengerCapacity;
	}

	public int getAnnualMaintenanceCost() {
		int res = 3000;
		res += this.getCargoCapacity() * 5;
		res += this.getPassengerCapacity() * 3;

		return res;
	}

	public String toString() {
		String res = super.toString();
		res +=  System.lineSeparator();
		res += "	";
		res = res + "CargoCapacity=" + this.getCargoCapacity() + System.lineSeparator();
		res += "	";
		res = res + "PassengerCapacity=" + this.getPassengerCapacity();

		return res;
	}
}
