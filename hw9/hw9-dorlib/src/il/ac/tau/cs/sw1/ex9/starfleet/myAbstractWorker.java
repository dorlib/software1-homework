package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Objects;

public abstract class myAbstractWorker implements CrewMember {

    protected int age;
    protected int yearsInService;
    protected String name;

    public myAbstractWorker(int age, int yearsInService, String name) {
        this.age = age;
        this.yearsInService = yearsInService;
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public int getYearsInService() {
        return this.yearsInService;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        String res = "	";
        res += "Name=" + this.getName() + System.lineSeparator();
        res += "	";
        res = res + "Age=" + this.getAge() + System.lineSeparator();
        res += "	";
        res = res + "YearsInService=" + this.getYearsInService() + System.lineSeparator();

        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrewWoman crewWoman = (CrewWoman) o;
        return age == crewWoman.age && yearsInService == crewWoman.yearsInService && Objects.equals(name, crewWoman.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age, yearsInService, name);
    }
}
