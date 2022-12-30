package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	// add members here
	HashMap<T, Integer> map;

	//add constructor here, if needed
	
	public HashMapHistogram(){
		this.map = new HashMap<T, Integer>();
	}
	
	@Override
	public void addItem(T item) {
		Integer value = this.map.get(item);
		if (value == null) {
			map.put(item, 1);
			return;
		}

		map.put(item, value + 1);
	}
	
	@Override
	public boolean removeItem(T item)  {
		Integer value = map.get(item);
		if (value == null) {
			return false;
		}

		if (value > 0) {
			map.put(item, value - 1);
		}

		if (value == 0) {
			map.remove(item);
			return true;
		}

		return false;
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for (T item : items) {
			this.addItem(item);
		}
	}

	@Override
	public int getCountForItem(T item) {
		Integer value = this.map.get(item);
		if (value == null) {
			return 0;
		}

		return value;
	}

	@Override
	public void clear() {
		this.map = new HashMap<T, Integer>();
	}

	@Override
	public Set<T> getItemsSet() {
		return this.map.keySet();
	}
	
	@Override
	public int getCountsSum() {
		Collection<Integer> values = map.values();
		int counter = 0;

		for(Integer value : values) {
			counter += value;
		}

		return counter;
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		return new HashMapHistogramIterator<T>(this.map);
	}

	//add private methods here, if needed
}
