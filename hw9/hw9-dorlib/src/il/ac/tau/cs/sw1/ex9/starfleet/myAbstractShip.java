package il.ac.tau.cs.sw1.ex9.starfleet;


import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class myAbstractShip implements Spaceship {

    protected String name;
    protected int commissionYear;
    protected float maximalSpeed;
    protected Set<? extends CrewMember> crewMembers;

    public myAbstractShip(String name, int commissionYear, float maximalSpeed, Set<? extends CrewMember> crewMembers) {
        this.name = name;
        this.commissionYear = commissionYear;
        this.maximalSpeed = maximalSpeed;
        this.crewMembers = crewMembers;
    }

    public String getName() {
        return this.name;
    }

    public int getCommissionYear() {
        return this.commissionYear;
    }

    public float getMaximalSpeed() {
        return this.maximalSpeed;
    }

    public int getFirePower() {
        return 10;
    }

    public Set<? extends CrewMember> getCrewMembers() {
        return this.crewMembers;
    }

    public abstract int getAnnualMaintenanceCost();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        myAbstractShip that = (myAbstractShip) o;
        return commissionYear == that.commissionYear && Float.compare(that.maximalSpeed, maximalSpeed) == 0 && Objects.equals(name, that.name) && Objects.equals(crewMembers, that.crewMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, commissionYear, maximalSpeed, crewMembers);
    }

    public String toString() {
        String res = this.getClass().getSimpleName() + System.lineSeparator();
        res += "	";
        res += "Name=" + this.getName() + System.lineSeparator();
        res += "	";
        res = res + "CommissionYear=" + this.getCommissionYear() + System.lineSeparator();
        res += "	";
        res = res + "MaximalSpeed=" + this.getMaximalSpeed() + System.lineSeparator();
        res += "	";
        res = res + "FirePower=" + this.getFirePower() + System.lineSeparator();
        res += "	";
        res = res + "CrewMembers=" + this.getCrewMembers().size() + System.lineSeparator();
        res += "	";
        res = res + "AnnualMaintenanceCost=" + this.getAnnualMaintenanceCost();

        return res;
    }
}

