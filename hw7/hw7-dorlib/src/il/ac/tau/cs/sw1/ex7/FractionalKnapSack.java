package il.ac.tau.cs.sw1.ex7;
import java.util.*;

public class FractionalKnapSack implements Greedy<FractionalKnapSack.Item>{
    int capacity;
    List<Item> lst;

    FractionalKnapSack(int c, List<Item> lst1){
        capacity = c;
        lst = lst1;
    }

    public static class Item {
        double weight, value;
        Item(double w, double v) {
            weight = w;
            value = v;
        }

        @Override
        public String toString() {
            return "{" + "weight=" + weight + ", value=" + value + '}';
        }
    }

    @Override
    public Iterator<Item> selection() {
        List<Item> candidates = new ArrayList<>();
        Iterator<Item> iterator = lst.iterator();

        if (iterator.hasNext()) {
            candidates.add(iterator.next());
        } else {
            return candidates.iterator();
        }

        while (iterator.hasNext()) {
            Item item = iterator.next();
            double value = item.value / item.weight;
            boolean entered = false;

            for (int i = 0; i < lst.size(); i++) {
                if (value > candidates.get(i).value / candidates.get(i).weight) {
                    candidates.add(i, item);
                    entered = true;
                    break;
                } else if (i == candidates.size() - 1) {
                    break;
                }
            }

            if (!entered) {
                candidates.add(item);
            }
        }

        return candidates.iterator();
    }

    @Override
    public boolean feasibility(List<Item> candidates_lst, Item element) {
        return capacity > 0;
    }

    @Override
    public void assign(List<Item> candidates_lst, Item element) {
        if (capacity >= element.weight) {
            candidates_lst.add(new Item(element.weight, element.value));
            capacity -= element.weight;
            element.weight  = 0;
        } else {
            candidates_lst.add(new Item(capacity, element.value * (capacity / element.weight)));
            element.weight -= capacity;
            capacity = 0;
            element.weight -= capacity;
        }
    }

    @Override
    public boolean solution(List<Item> candidates_lst) {
        boolean areItemsLeft = false;

        for (Item item : lst) {
            if (item.weight > 0) {
                areItemsLeft = true;
                break;
            }
        }

        return !areItemsLeft || capacity == 0;
    }
}
