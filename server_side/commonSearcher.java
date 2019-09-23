package server_side;

import java.util.HashSet;
import java.util.Set;

public abstract class commonSearcher<T> implements Searcher<T> {
	
	//PriorityQueue<State<T>> openList; 
	Set<State<T>> closedList;
	
	public commonSearcher() {
		closedList = new HashSet<>();
	}

}
