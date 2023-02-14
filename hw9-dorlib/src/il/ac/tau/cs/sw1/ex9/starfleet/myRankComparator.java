package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;
import java.util.Map;

public class myRankComparator implements Comparator<Map.Entry<OfficerRank, Integer>> {
    public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
        if (o1.getValue() != o2.getValue()) {
            return o1.getValue().compareTo(o2.getValue());
        }

        return o1.getKey().compareTo(o2.getKey());
    }
}