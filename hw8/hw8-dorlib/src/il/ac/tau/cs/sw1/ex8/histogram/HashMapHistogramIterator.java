package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.*;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<Map.Entry<T, Integer>>{

	private HashMap<T, Integer> map;
	private List<T> keys;
	private int currentIndex;
	private int mapSize;
	private List<Map.Entry<T, Integer>> entries;

	//add constructor here, if needed
	HashMapHistogramIterator(HashMap<T, Integer> hashMap) {
		this.map = hashMap;
		this.currentIndex = 0;
		this.mapSize = map.size();
		this.keys = new ArrayList<>(map.keySet());
		Collections.sort(keys, new HashMapHistogramComparator(map));
		this.entries = setToList(this.map.entrySet());
	}

	public class HashMapHistogramComparator implements Comparator<T>{
		private HashMap<T, Integer> map;

		public HashMapHistogramComparator(HashMap<T, Integer> hashMap) {
			this.map = hashMap;
		}

		public int compare(T o1, T o2) {
			if(Objects.equals(this.map.get(o1), this.map.get(o2))) {
				return o1.compareTo(o2);
			}

			if(this.map.get(o1) < this.map.get(o2)) {
				return 1;
			}

			return -1;
		}
	}
	
	@Override
	public boolean hasNext() {
		return this.currentIndex < this.mapSize;
	}

	@Override
	public Map.Entry<T, Integer> next() {
		Map.Entry<T, Integer> entry =  this.entries.get(currentIndex);
		currentIndex++;

		return entry;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	//add private methods here, if needed
	private List<Map.Entry<T, Integer>> setToList(Set<Map.Entry<T,Integer>> set) {
		return new ArrayList<>(set);
	}
}
