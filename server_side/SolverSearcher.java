package server_side;

import java.util.List;

//object adapter between solver and searcher
public class SolverSearcher implements Solver<Searchable<Position>, String> {
	
	Searcher<Position> s;
	
	public SolverSearcher(Searcher<Position> s){
		this.s=s;
	}
	
	@Override
	public String solve(Searchable<Position> p) {
		return getDirections(s.search(p));
	}
	
	
	public static String getDirections(List<State<Position>> backtrace) {
		int i=0;
		StringBuilder result = new StringBuilder();
		
		while(i< backtrace.size()-1)
		{
			if(backtrace.get(i).getState().getX() < backtrace.get(i+1).getState().getX())
			{
				result.append("Right,");
			}
			if(backtrace.get(i).getState().getX() > backtrace.get(i+1).getState().getX())
			{
				result.append("Left,");
			}
			if(backtrace.get(i).getState().getY() > backtrace.get(i+1).getState().getY())
			{
				result.append("Up,");
			}
			if(backtrace.get(i).getState().getY() < backtrace.get(i+1).getState().getY())
			{
				result.append("Down,");
			}
			i++;
		}
		System.out.println("Res is: "+ result.substring(0, result.length()-1));
		return result.substring(0, result.length()-1);
	}
	
//	public static void main(String[] args) {
//		double[][] mat = {
//				{1,1,1},
//				{50,50,1},
//				{50,50,1}
//		};
//		Position entrance = new Position(0,0);
//		Position exit = new Position(2,2);
//		MatrixSearchable ms = new MatrixSearchable(mat, entrance, exit);
//		System.out.print(new SolverSearcher(new BFS<Position>()).solve(ms));
//			//.forEach(st -> System.out.print(st+" -> "));
//		//System.out.println("done");
//	}


}
