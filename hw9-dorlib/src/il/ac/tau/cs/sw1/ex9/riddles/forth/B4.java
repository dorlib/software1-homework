package il.ac.tau.cs.sw1.ex9.riddles.forth;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class B4 implements Iterator<String> {
    private final Iterator<String> iterator;
    private int counter;
    private final int k;
    private String lastVal;

    public B4(String[] strings, int k){
        ArrayList<String> lst = new ArrayList<>(List.of(strings));
        this.iterator = lst.iterator();
        this.k = k;
    }

    @Override
    public boolean hasNext() {
        if (counter == 0){
            return iterator.hasNext();
        }
        return counter < k;
    }

    @Override
    public String next() {
        if (counter == 0){
            counter++;
            lastVal = iterator.next();
            return lastVal;
        }

        if (counter < k-1){
            counter++;
            return lastVal;

        }

        if (counter == k-1){
            counter = 0;
            return lastVal;
        }

        return "";
    }
}
