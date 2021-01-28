package gl2.useful;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MultiHashMap<K, V> implements Cloneable, Serializable, Iterable<V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6630196825874358540L;
	protected HashMap<K, ArrayList<V>> map;

	public MultiHashMap() {
		map = new HashMap<K, ArrayList<V>>();
	}

	public MultiHashMap(int initialCapacity) {
		map = new HashMap<K, ArrayList<V>>(initialCapacity);
	}

	public MultiHashMap(int initialCapacity, float loadFactor) {
		map = new HashMap<K, ArrayList<V>>(initialCapacity, loadFactor);
	}

	public void put(K key, V value) {
		ArrayList<V> arr = map.get(key);
		if (arr == null) {
			arr = new ArrayList<V>();
			map.put(key, arr);
		}
		arr.add(value);
	}

	public List<V> get(K key) {
		return map.get(key);
	}
	
	public int size() {
		int sum = 0;
		for(ArrayList<V> a : map.values()) {
			sum += a.size();
		}
		return sum;
	}

	public void clear() {
		map.clear();
	}

	@Override
	public Iterator<V> iterator() {
		return new MHMIterator<V>(this);
	}

}
//Subject for improvement
class MHMIterator<V> implements Iterator<V> {
	
	private Iterator<ArrayList<V>> iterator;

	protected MHMIterator(MultiHashMap<?, V> map) {
		iterator = map.map.values().iterator();
		if(iterator.hasNext()) {
			nextIterator = iterator.next().iterator();
			if(nextIterator.hasNext()) next = nextIterator.next();
		}
	}

	private Iterator<V> nextIterator = null;
	private V next = null;

	@Override
	public boolean hasNext() {
		return next != null;

	}

	@Override
	public V next() {
		V current = next;
		next = null;
		while(!nextIterator.hasNext() && iterator.hasNext()) {
			if(iterator.hasNext()) {
				nextIterator = iterator.next().iterator();
			}
		}
		if(nextIterator.hasNext()) next = nextIterator.next();
		return current;
		
	}

}
