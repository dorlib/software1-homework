package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import il.ac.tau.cs.sw1.ex8.TesterUtils;
import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;
import il.ac.tau.cs.sw1.ex8.histogram.IHistogram;


public class HashMapHistogramTester  {
	public static void main(String[] args) {
		List<Integer> intLst = Arrays.asList(1, 2, 1, 2, 3, 4, 3, 1);
		IHistogram<Integer> intHist = new HashMapHistogram<>();
		for (int i : intLst) {
			intHist.addItem(i);
		}
		if (intHist.getCountForItem(1) != 3) {
			TesterUtils.printErrorNum(1);
		}
		if (intHist.getCountForItem(5) != 0) {
			TesterUtils.printErrorNum(2);
		}
		if (intHist.getCountsSum() != 8){
			TesterUtils.printErrorNum(3);
		}
		Iterator <Map.Entry<Integer,Integer>> intHistIt = intHist.iterator();
		List<Map.Entry<Integer, Integer>> tmpList = new ArrayList<>();
		while (intHistIt.hasNext()) {
			tmpList.add(intHistIt.next());
		}
		if (tmpList.get(0).getKey() != 1) {
			TesterUtils.printErrorNum(4);
		}
		if (tmpList.get(0).getValue() != 3) {
			TesterUtils.printErrorNum(5);
		}

		if (tmpList.get(3).getKey() != 4) {
			TesterUtils.printErrorNum(6);
		}

		if (tmpList.size() != 4) {
			TesterUtils.printErrorNum(7);
		}

		IHistogram<String> stringHist = new HashMapHistogram<>();
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("abc");
		stringHist.addItem("de");
		stringHist.addItem("de");
		if (stringHist.getCountForItem("abc") != 3) {
			TesterUtils.printErrorNum(8);
		}
		
		try{
			boolean val = stringHist.removeItem("abba"); // this should throw an exception
			if (val){
				TesterUtils.printErrorNum(9);
			}
		}
		catch (Exception exp){
			TesterUtils.printErrorNum(10);
		}
		
		if (stringHist.getCountForItem("abba") != 0) {
			TesterUtils.printErrorNum(11);
		}

		System.out.println("done!");
	}

}
