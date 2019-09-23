package server_side;

public class StringReverser implements Solver<String, String> {
	@Override
	public String solve(String p) {
		return (new StringBuilder(p).reverse().toString());
	}
}