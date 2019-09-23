package server_side;


public interface CacheManager<Problem, Soloution> {
	public boolean solvedAlready(Problem p);
	public Soloution GetSoloutin(Problem p);
	public void SaveSoloution(Problem p,Soloution s);
}
