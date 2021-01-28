package gl2.useful;

import java.io.Serializable;
import java.util.HashMap;

/**
 * A wrapper for two HashMaps to allow for two keys to one value.
 * @author Heiko Ribberink
 *
 * @param <K1>
 * @param <K2>
 * @param <V>
 * 
 * @see java.util.HashMap
 */

public class DoublyLinkedHashMap<K1, K2, V> implements Cloneable, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4977327947163548656L;
	private HashMap<K1, V> hash1; 
	private HashMap<K2, V> hash2;
	
	//Constructors

	public DoublyLinkedHashMap() {
		hash1 = new HashMap<K1, V>();
		hash2 = new HashMap<K2, V>();
	}
	
	public DoublyLinkedHashMap(int initialCapacity) {
		hash1 = new HashMap<K1, V>(initialCapacity);
		hash2 = new HashMap<K2, V>(initialCapacity);
	}
	
	public DoublyLinkedHashMap(int initialCapacity, float loadFactor) {
		hash1 = new HashMap<K1, V>(initialCapacity, loadFactor);
		hash2 = new HashMap<K2, V>(initialCapacity, loadFactor);
	}
	
	private DoublyLinkedHashMap(HashMap<K1, V> hash1, HashMap<K2, V> hash2) {
		this.hash1 = hash1;
		this.hash2 = hash2;
	}
	
	//Functions from HashMap
	
	public void put(K1 key1, K2 key2, V value) {
		hash1.put(key1, value);
		hash2.put(key2, value);
	}
	
	public V getByK1(K1 key1) {
		return hash1.get(key1);
	}
	
	public V getByK2(K2 key2) {
		return hash2.get(key2);
	}
	
	public void clear() {
		hash1.clear();
		hash2.clear();
	}
	
	public V remove(K1 key1, K2 key2) {
		V v1 = hash1.remove(key1);
		V v2 = hash2.remove(key2);
		if(v1 != v2) throw new Error("Hash maps are out of sync");
		return v1;
	}
	
	public int size() {
		int l1 = hash1.size();
		int l2 = hash2.size();
		if(l1 != l2) throw new Error("Hash maps are out of sync");
		return l1;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DoublyLinkedHashMap<K1, K2, V> clone() {
		return new DoublyLinkedHashMap<K1, K2, V> ((HashMap<K1, V>) hash1.clone(), (HashMap<K2, V>) hash2.clone());
	}
	

}
