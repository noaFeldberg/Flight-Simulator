package server_side;

import java.util.ArrayList;

public interface Searchable<T> {
	State<T> getInitialState();
	boolean isGoalState(State<T> state);
	ArrayList<State<T>> getAllPossibleStates(State<T> s); 
	Position getGoalState();

}
