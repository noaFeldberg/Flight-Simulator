package server_side;

import java.util.List;

//defines all the alogrithms we can use - BFS, A* and so..
public interface Searcher<T> {
	public List<State<T>> search (Searchable<T> s); 	//search method	
	
}
