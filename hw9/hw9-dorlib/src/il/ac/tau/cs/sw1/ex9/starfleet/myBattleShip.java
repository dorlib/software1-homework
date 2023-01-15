package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class myBattleShip extends myAbstractShip{
    private List<Weapon> weapons;

    public myBattleShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers, List<Weapon> weapons) {
        super(name, commissionYear, maximalSpeed, crewMembers);
        this.weapons = weapons;
    }

    public List<Weapon> getWeapons() {
        return weapons;
    }

    @Override
    public int getFirePower() {
        int res = super.getFirePower();

        for (Weapon weapon: this.weapons) {
            res += weapon.getFirePower();
        }

        return res;
    }

    public String getWeaponsListAsString(){
        StringBuilder result = new StringBuilder("WeaponArray=[");

        for (Weapon weapon: this.getWeapons()){
            result.append(weapon.toString());
            result.append(",");
        }

        return result.substring(0, result.length() - 2) + "]";
    }

    public int getAnnualMaintenanceCost() {
        int result = 0;

        for (Weapon weapon : this.weapons){
            result += weapon.getAnnualMaintenanceCost();
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        myBattleShip that = (myBattleShip) o;
        return Objects.equals(weapons, that.weapons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weapons);
    }
}
