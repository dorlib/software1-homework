package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* don't change this interface */
public interface IHistogram<T> extends Iterable<Map.Entry<T, Integer>>{
	
	/* @pre: item != null 
	 * @post: $prev(getCountForItem(item)) = getCountForItem(item) -1*/
	public void addItem(T item);
	
	/* @pre: item != null 
	 * @post: $prev(getCountForItem(item)) > 0 $implies 
	 * 								$prev(getCountForItem(item)) = getCountForItem(item) +1
	 * @post: $prev(getCountForItem(item)) = 0 $implies getCountForItem(item) = 0*/
	public boolean removeItem(T item);
	
	/* @pre: for each x in items: x != null 
	 * @post: for each x in items: 
	 * 					$prev(getCountForItem(x)) <= getCountForItem(item)-1*/
	public void addAll(Collection<T> items);
	
	/* @pre: item != null 
	 * @post: $ret >= 0 */
	public int getCountForItem(T item);
	
	/* @post: for each x, getCountForItem(x) == 0 */
	public void clear();
	
	/* @post: for each x in $ret, getCountForItem(x) > 0 
	 * @post: for each x such that getCountForItem(x) > 0, x in $ret*/
	public Set<T> getItemsSet();
	
	/* @post: $ret = sum(getCountForItem(x) for each x in getItemsSet()) */
	public int getCountsSum();
}
