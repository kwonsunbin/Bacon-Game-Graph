
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.*;

public class Graph {
	HashMap<String,HashMap<String,Integer>> graph; 
	
	public Graph() {
		graph = new HashMap<String,HashMap<String,Integer>>();
	}
	
	public HashMap<String,HashMap<String,Integer>> getGraph() {
		return graph;
	}
	
	public int size() {
		return graph.size();
	}
	
	public boolean containsV(String V) {
		return graph.containsKey(V);
	}
	
	public boolean checkConnection(String a, String b) {
		return graph.get(a).containsKey(b);
	}
	
	
	public Set<String> getE(String V) {
		return graph.get(V).keySet() ;
	}
	
	public HashMap<String, Integer> getMap(String V) {
		return graph.get(V);
	}
	
	public void initV(String V) {
		if(graph.get(V)== null ) {
			HashMap<String,Integer> E = new HashMap<String,Integer>();
			graph.put(V, E);
		}
	}
	
	public void addE(String V, String[] b) {
		for (String name : b) {
			if(graph.get(V).containsKey(V)) {
				graph.get(V).put(V, graph.get(V).get(name)+1);
			}else {
				graph.get(V).put(name, 1);
			}
		}
		
	}
	

	
	
	

	public void show() {
		
		Iterator<String> keys = graph.keySet().iterator(); 
		
		while( keys.hasNext() ){
			String key = keys.next();
			System.out.println( "[key] : " + key + ", [value] : " + graph.get(key)) ; 
		}
		System.out.println("Actors # : " + graph.size());
	}
	

}
