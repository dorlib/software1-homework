package il.ac.tau.cs.sw1.ex9.riddles.third;

public class B3 extends A3 {

    public B3(String s) {
        super(s);
    }

    public String getMessage() {
        return this.s;
    }

    public void foo(String str) throws B3 {
        if (str.equals(this.s)) {
            throw new B3(this.s);
        }
    }
}