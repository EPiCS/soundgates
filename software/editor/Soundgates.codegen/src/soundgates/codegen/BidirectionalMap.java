package soundgates.codegen;

import java.util.HashMap;

public class BidirectionalMap<K,V> {
	HashMap<K, V> map1;
	HashMap<V, K> map2;
	
	public BidirectionalMap() {
		map1 = new HashMap<>();
		map2 = new HashMap<>();
	}
	
	public void put(K key, V value){
		map1.put(key, value);
		map2.put(value, key);
	}
	
	public V getValue(K key){
		return map1.get(key);
	}
	
	public K getKey(V value){
		return map2.get(value);
	}
	
	public int size(){
		return map1.size();
	}
	
	public boolean containsKey(K key){
		return map1.containsKey(key);
	}
}
