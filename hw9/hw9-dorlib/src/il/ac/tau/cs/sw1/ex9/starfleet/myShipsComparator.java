package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class myShipsComparator implements Comparator<myAbstractShip> {
    @Override
    public int compare(myAbstractShip ship1, myAbstractShip ship2) {
        Integer firePower1 = ship1.getFirePower();
        Integer firePower2 = ship2.getFirePower();

        if (!firePower1.equals(firePower2)) {
            return firePower2.compareTo(firePower1);
        }

        Integer commissionYear1 = ship1.getCommissionYear();
        Integer commissionYear2 = ship2.getCommissionYear();

        if (!commissionYear1.equals(commissionYear2)) {
            return commissionYear2.compareTo(commissionYear1);
        }

        return ship1.getName().compareTo(ship2.getName());
    }
}
