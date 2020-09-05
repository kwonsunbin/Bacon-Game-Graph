
import java.lang.*;
import java.util.*;
import java.io.*;

public class MovieNet {

  static final String KevinBacon = "Bacon, Kevin";
  Graph graph = new Graph();
  HashMap<String,HashSet<String>> movies = new HashMap<String,HashSet<String>>();

  public MovieNet(LinkedList<String[]> movielines) {
	 
	  Iterator<String[]> itMovie = movielines.iterator();
	  while(itMovie.hasNext()) {
		  String[] movieLine = itMovie.next();
		  String[] actorNames = removeTitle(movieLine);
		  HashSet<String> movieActors = new HashSet<String>(Arrays.asList(actorNames));
		  movies.put(movieLine[0],movieActors);
		  for (int i =0; i<actorNames.length; i++) {
			  List<String> list = new ArrayList<>(Arrays.asList(actorNames)); 
			  list.remove(actorNames[i]); 
			  String[] temp = list.toArray(new String[list.size()]);
			  graph.initV(actorNames[i]);
			  graph.addE(actorNames[i],temp);
		  }  
	  }
  }  
  
  	public void show() {
	  
	  System.out.println("==================Current Graph==================================");
	  graph.show();
	  System.out.println("==================Current Graph==================================");
	  Iterator<String> keys = movies.keySet().iterator(); 
		
	  while( keys.hasNext() ){
		  String key = keys.next();
		  System.out.println( "[key] : " + key + ", [value] : " + movies.get(key)) ; 
	  }
	  System.out.println("Movies # : " + movies.size());
  }
  
  public static String[] removeTitle(String[] s)
  {
    String[] rest = new String[s.length-1];
    for(int i=0; i < s.length-1 ;i++) rest[i] = s[i+1];
    return rest;
  }
  
  
  

  // [Q1]
  public String[] moviesby(String[] actors) { 
	  ArrayList<String> result = new ArrayList<String>();
	  Iterator<String> keys = movies.keySet().iterator();
	  while(keys.hasNext()) {
		  String key = keys.next();
		  if( checkMovieContains(actors, key) == true) {
			  result.add(key);
		  }
	  }
	  if(result.size()==0) {return null;}
	  return toStringArr(result);
  }
    
  public String[] toStringArr(ArrayList<String> input) {
	  String[] array = input.toArray(new String[input.size()]);
	  return array;
  }
  
  public boolean checkMovieContains(String[] actorsToCheck, String title) { 
	  for (int i=0; i<actorsToCheck.length; i++) {
		  if(!movies.get(title).contains(actorsToCheck[i])){
			  return false;
		  }
	  }
	  return true;
  }
    
  // [Q2]
  public String[] castin(String[] titles) { 
	  HashSet<String> intersection = new HashSet<String>();
	  if(movies.get(titles[0])==null) {return null;}
	  intersection.addAll(movies.get(titles[0]));
	  if(titles.length==1) {
		  return toStringArr(intersection);
	  }
	  for (int i=1; i<titles.length; i++) {
		  HashSet<String> temp = new HashSet<String>();
		  if(movies.get(titles[i])==null) {continue;}
		  temp.addAll(movies.get(titles[i]));
		  intersection.retainAll(temp);
	  }
	  if(intersection.size() == 0) {return null;}
	  return toStringArr(intersection);
  }
  
  public String[] toStringArr(HashSet<String> input) {
	  String[] array = input.toArray(new String[input.size()]);
	  return array;
  }


  // [Q3]
  public String[] pairmost(String[] actors) { 
	  int maxValue = 0 ;
	  int[] maxIdx = new int[2]; 
	  
	  for (int i=0; i<actors.length; i++) {
		  for(int j=i; j<actors.length; j++) {
			  if( graph.getMap(actors[i]).containsKey(actors[j]) ) {
				  if(graph.getMap(actors[i]).get(actors[j]) >maxValue) {
					  maxValue = graph.getMap(actors[i]).get(actors[j]);
					  maxIdx[0]= i;
					  maxIdx[1]= j;
				  }
			  }else {
				  continue;
			  }
		  }
	  }
	  
	  if( maxValue==0) {return null;}
	  String[] answer = {actors[maxIdx[0]],actors[maxIdx[1]]};
	  return answer;
  }
  
  
  
  
  // [Q4]
  public int Bacon(String actor) { 
	  if (graph.containsV(KevinBacon)== false | graph.containsV(actor)==false ) {return -1;}
	  return pairBFSshortest(KevinBacon, actor);
	  
  }
  
  public int pairBFSshortest(String start, String end) {

	  Queue<String> que = new LinkedList<String>();
	  HashMap<String,Integer> d = new HashMap<String,Integer>();
	  Iterator<String> keys = graph.getGraph().keySet().iterator();
	  while(keys.hasNext()) {
		  d.put(keys.next(),-1);
	  }
	  d.put(start,0);
	  que.offer(start);
	  
	  while(!que.isEmpty()) {
		  String current = que.poll();
		  Set<String> connectedNodes = graph.getE(current);
		  Iterator<String> it = connectedNodes.iterator();
		  while(it.hasNext()) {
			  String crnt = it.next();
			  if (d.get(crnt)==-1) {
				  que.offer(crnt);
				  d.put(crnt,d.get(current)+1);
				  if(crnt.equals(end)) { return d.get(crnt);}
			  }
			  
		  }
	  }
	  return -1;
	  
  }

  
  // [Q5]
  public int distance(String src, String dst) {
	  if (graph.containsV(src)== false | graph.containsV(dst)==false ) {return -1;}
	  return pairBFSshortest(src, dst);
  }

  
  
  // [Q6]
  public int npath(String src, String dst) { 
	  if (graph.containsV(src)== false | graph.containsV(dst)==false ) {return 0;}
	  int N = distance(src, dst);
	  if(N==-1) {return 0;}
	  int cnt = 0;
	  
	  HashMap<String,Integer> d = BFSSingleSourceUnderN(src, N);
	  HashMap<String,Integer> onWayOfDst = new HashMap<String,Integer>();
	  
	  Iterator<String> keys = d.keySet().iterator(); 
	  
	  while( keys.hasNext() ){
		  String key = keys.next();
		  if(d.get(key)>=0){
			  onWayOfDst.put(key,d.get(key));
		  }; 
	  }
	  
	  
	  HashMap<Integer, LinkedList<String>> temp = new HashMap<Integer,LinkedList<String>>();
	  for(int i=0; i<N; i++) { LinkedList<String> hi = new LinkedList<String>(); temp.put(i,hi);}
 	  Iterator<String> it = onWayOfDst.keySet().iterator();
	  
	  while(it.hasNext()) {
		  String key = it.next();
		  temp.get(onWayOfDst.get(key)).add(key);
	  }
	  
	  
	  for(int i=2; i<N; i++) {
		  Iterator<String> itt = temp.get(i).iterator();
		  while(itt.hasNext()) {
			  String current = itt.next();
			  int num = 0;
			  Iterator<String> ittt = temp.get(i-1).iterator();
			  while(ittt.hasNext()) {
				  String crnt = ittt.next();
				  if(graph.checkConnection(current,crnt)) {num+= onWayOfDst.get(crnt);};
			  }
			  onWayOfDst.put(current, num );
		  }

	  }
	  
	  Iterator<String> itttt = temp.get(N-1).iterator();
	  while(itttt.hasNext()) {
		  String bye = itttt.next();
		  if(graph.getE(dst).contains(bye)) {
			  cnt+=onWayOfDst.get(bye);
		  }
	  }
	  if(cnt==0) {cnt++;}
	  return cnt;
	  
  }
  
  public HashMap<String,Integer> BFSSingleSourceUnderN(String src, int N) {

	  Queue<String> que = new LinkedList<String>();
	  HashMap<String,Integer> d = new HashMap<String,Integer>();
	  Iterator<String> keys = graph.getGraph().keySet().iterator();
	  while(keys.hasNext()) {
		  String key = keys.next();
		  d.put(key,-1);
	  }
	  que.offer(src);
	  d.put(src,0);
	  
	  while(!que.isEmpty()) {
		  String current = que.poll();
		  if(d.get(current)==N-1) {return d;}
		  Set<String> connectedNodes = graph.getE(current);
		  Iterator<String> it = connectedNodes.iterator();
		  while(it.hasNext()) {
			  String crnt = it.next();
			  if (d.get(crnt)==-1) {
				  que.offer(crnt);
				  d.put(crnt,d.get(current)+1);
			  }  
		  }  
		  
	  }
	  return d;
  }
  
 
  
  // [Q7]
  public String[] apath(String src, String dst) { 
	  if (graph.containsV(src)== false | graph.containsV(dst)==false | distance(src,dst)==-1 ) {return null;}
	  return pairBFSshortestPathTracking(src,dst);
  }
  
  public String[] pairBFSshortestPathTracking(String start, String end) {
	  
	  Queue<String> que = new LinkedList<String>();
	  int cnt = 0;
	  
	  HashMap<String,Integer> d = new HashMap<String,Integer>();
	  HashMap<String,String> p = new HashMap<String,String>();
	  Iterator<String> keys = graph.getGraph().keySet().iterator();
	  while(keys.hasNext()) {
		  String key = keys.next();
		  d.put(key,-1);
		  p.put(key,"N/A");
	  }
	  
	  d.put(start,0);
	  p.put(start, "start"); 
	  que.offer(start);
	  
	  
	  while(!que.isEmpty()) {
		  String current = que.poll();
		  
		  Set<String> connectedNodes = graph.getE(current);
		  Iterator<String> it = connectedNodes.iterator();
		  while(it.hasNext()) {
			  String crnt = it.next();
			  if (d.get(crnt)==-1) {
				  que.offer(crnt);
				  d.put(crnt,d.get(current)+1);
				  p.put(crnt,current);
				  if(crnt.equals(end)) { cnt= d.get(crnt); break;}
			  }
		  }  
	  }
	  
	  String[] path = new String[cnt+1];
	  String crnt = end;
	  for (int i=cnt; i>=0; i--) {
		  path[i] = crnt; 
		  crnt = p.get(crnt);
	  }
	  return path;
  }
  
  
  
  // [Q8]
  public int eccentricity(String actor) { 
	  if(graph.containsV(actor)== false) { return -1;}
	  return findMax(BFSSingleSource(actor));
  }
  
  public int[] HMtoIntArr(HashMap<String,Integer> input) {
	  
	  int[] output = new int[input.size()];

	  Iterator<String> keys = input.keySet().iterator(); 
	  int cnt =0;
	  while( keys.hasNext() ){
		  int temp = input.get(keys.next());
		  output[cnt] = temp; 
		  cnt++;
	  }
	  return output;
  }
  
  public int[] BFSSingleSource(String src) {

	  Queue<String> que = new LinkedList<String>();
	  HashMap<String,Integer> d = new HashMap<String,Integer>();
	  Iterator<String> keys = graph.getGraph().keySet().iterator();
	  while(keys.hasNext()) {
		  String key = keys.next();
		  d.put(key,-1);
	  }
	  que.offer(src);
	  d.put(src,0);
	  
	  while(!que.isEmpty()) {
		  String current = que.poll();
		  
		  Set<String> connectedNodes = graph.getE(current);
		  Iterator<String> it = connectedNodes.iterator();
		  while(it.hasNext()) {
			  String crnt = it.next();
			  if (d.get(crnt)==-1) {
				  que.offer(crnt);
				  d.put(crnt,d.get(current)+1);
			  }
		  }  
	  }
	  
	  return HMtoIntArr(d);
	  
  }
  public int findMax(int[] input) {
	  int max = input[0];
	  for (int i =1; i<input.length; i++) {
		  if( max<input[i] ) {
			  max = input[i];
		  }
	  }
	  return max;
  }
  
  
  // [Q9]
  public float closeness(String actor) { 
	  if(graph.containsV(actor)== false) { return -1;}//
	  return calCloseness(BFSSingleSource(actor));
	  
	  
  }

  public float calCloseness(int[] input) {
	  float ans =0;
	  for (int i=0; i<input.length; i++) {
		  if(input[i]==-1) { continue;}
		  ans += 1/Math.pow(2, input[i]);
	  }
	  return ans-1;  
  }

  



}

