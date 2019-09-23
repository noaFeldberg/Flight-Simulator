package server_side;


import view.MapDisplayer1;

import java.util.List;


public class MyClientHandler implements ClientHandler{
	
	Solver<Searchable<Position>, String> s; 
	CacheManager<Searchable<Position>, String> cm;
//	double[][] matrix;
//	Position startPosition;
//	Position destPosition;
	MapDisplayer1 mapD;
	
	public MyClientHandler(MapDisplayer1 mappy) {
		//double[][] mat, double startPosX, double startPosY, double destPosX,double destPosy
		cm=new FileCacheManager<>();
		s = new SolverSearcher(new BFS<Position>());
		mapD = mappy;
	}

	@Override
	public void handleClient() {
		Position startPos = new Position(mapD.getPlanePosX(),mapD.getPlanePosY());
		//System.out.println("startPos: "+mapD.getPlanePosX()+" , "+mapD.getPlanePosY());
		Position destPos = new Position(mapD.getDestPointX(),mapD.getDestPointY());	
		//System.out.println("destPos: "+mapD.getDestPointX()+" , "+ mapD.getDestPointY());
		
		MatrixSearchable matSearch= new MatrixSearchable(mapD.getMapHights(),startPos,destPos);

		
		if(cm.solvedAlready(matSearch)){
//			writer.println(cm.GetSoloutin(matSearch));
//			writer.flush();		
			return;
		}	 
		else {
			cm.SaveSoloution(matSearch,s.solve(matSearch));
			//System.out.println("AAAA: "+cm.GetSoloutin(matSearch));
			mapD.setLst(cm.GetSoloutin(matSearch));
			//this.notifyAll();
//			System.out.println("heyyyyyyyyy");
			return;
//			writer.println(cm.GetSoloutin(matSearch));
//			writer.flush();
		}						
//		writer.println(s.solve(matSearch));
//		writer.flush();
			
}
	
	//build matrix:
	public double[][] stringToMatrix(List<String> stringlist){
		String[] str= stringlist.get(0).split(",");
		int col = str.length;
		int row = stringlist.size();
		double[][] mat=new double[row][col];
		
		for(int i=0;i<row;i++)
		{
			String[]temp = stringlist.get(i).split(",");
			for(int j=0;j<col;j++)
			{
				mat[i][j]=Double.parseDouble(temp[j]);
			}
		}
		return mat;
	}
	
	//build position
	public Position stringToPosition(String line)
	{
		String[] str=line.split(",");
		int x = Integer.parseInt(str[0]);
		int y= Integer.parseInt(str[1]);
		return new Position(x,y);
	}	
}