package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.*;


public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships, sorted in descending order by
	 * fire power, and then in descending order by commission year, and finally in ascending order by
	 * name
	 */
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear (Collection<Spaceship> fleet) {
		List<String> result = new ArrayList<>();
		List<myAbstractShip> shipList = new ArrayList<>();

		for (Spaceship spaceship : fleet) {
			shipList.add((myAbstractShip) spaceship);
		}

		myShipsComparator comp = new myShipsComparator();

		shipList.sort(comp);

		for (myAbstractShip ship: shipList) {
			result.add(ship.toString());
		}

		return result;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		HashMap<String, Integer> result = new HashMap<>();

		for (Spaceship ship: fleet) {
			String className = ship.getClass().getSimpleName();

			if (result.containsKey(className)) {
				Integer current = result.get(className);
				result.put(className, current + 1);
			} else {
				result.put(className, 1);
			}
		}

		return result;
	}

	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost (Collection<Spaceship> fleet) {
		int result = 0;

		for (Spaceship ship : fleet) {
			result += ship.getAnnualMaintenanceCost();
		}

		return result;
	}


	/**
	 * Returns a set containing the names of all the fleet's weapons installed on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> res = new HashSet<>();

		for (Spaceship ship: fleet){
			List<Weapon> result = switch (ship.getClass().getSimpleName()) {
				case "Fighter" -> ((Fighter) ship).getWeapons();
				case "Bomber" -> ((Bomber) ship).getWeapons();
				case "StealthCruiser" -> ((StealthCruiser) ship).getWeapon();
				default -> new LinkedList<>();
			};

			for (Weapon weapon: result){
				res.add(weapon.getName());
			}
		}

		return res;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int result = 0;
		for (Spaceship spaceship : fleet) {
			myAbstractShip ship = (myAbstractShip) spaceship;
			result += ship.getCrewMembers().size();
		}

		return result;
	}

	/*
	 * Returns the average age of all officers serving on board of the given fleet's ships. 
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		int sum = 0;
		int counter = 0;

		for (Spaceship spaceship : fleet) {
			myAbstractShip ship = (myAbstractShip) spaceship;
			Set<? extends CrewMember> members = ship.getCrewMembers();

			for (CrewMember member : members) {
				if (member.getClass().getSimpleName().equals("Officer")) {
					sum += member.getAge();
					counter++;
				}
			}
		}

		return (float) sum / counter;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys), to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {
		HashMap<Officer, Spaceship> result = new HashMap<>();

		for (Spaceship spaceship : fleet) {
			Set<? extends CrewMember> members = spaceship.getCrewMembers();
			Officer highestOfficer = null;
			OfficerRank maxRank = OfficerRank.Ensign;

			for (CrewMember member : members) {
				if (member.getClass().getSimpleName().equals("Officer")) {
					Officer officer = (Officer) member;

					if (maxRank.compareTo(officer.getRank()) <= 0) {
						highestOfficer = officer;
						maxRank = officer.getRank();
					}
				}
			}
			if (highestOfficer != null) {
				result.put(highestOfficer, spaceship);
			}
		}

		return result;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences.
	 * Each entry represents a pair composed of an officer rank, and the number of its occurrences among starfleet personnel.
	 * The returned list is sorted ascendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {
		HashMap<OfficerRank, Integer> map = new HashMap<>();
		List<Map.Entry<OfficerRank, Integer>> lst = new ArrayList<>();

		for (Spaceship spaceship : fleet) {
			myAbstractShip ship = (myAbstractShip) spaceship;
			Set<? extends CrewMember> members = ship.getCrewMembers();

			for (CrewMember member : members) {
				if (member.getClass().getSimpleName().equals("Officer")) {
					Officer officer = (Officer) member;

					if (map.containsKey(officer.getRank())) {
						map.put(officer.getRank(), map.get(officer.getRank()) + 1);
					} else {
						map.put(officer.getRank(), 1);
					}
				}
			}

			lst = new ArrayList<>(map.entrySet());
			lst.sort(new myRankComparator());
		}

		return lst;
	}
}
