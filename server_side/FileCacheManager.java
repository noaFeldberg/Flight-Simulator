package server_side;

import java.util.Map;
import java.util.HashMap;

public class FileCacheManager<Problem,Soloution> implements CacheManager<Problem, Soloution> {
		Map<Problem,Soloution> soultions;
		
		public FileCacheManager() {
			soultions = new HashMap<Problem,Soloution>(); //creating a hashmap in order to get soloutin in o(1)
		}

		@Override
		public boolean solvedAlready(Problem p) {
			return soultions.containsKey(p);
		}

		@Override
		public Soloution GetSoloutin(Problem p) {
			return soultions.get(p);
		}

		@Override
		public void SaveSoloution(Problem p,Soloution s) {
			soultions.put(p,s);
		}	
}
