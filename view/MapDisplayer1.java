package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import server_side.MyClientHandler;
import server_side.MySerialServer;
import test.ConnectCommand;
import test.DataReaderServer;


public class MapDisplayer1 extends Canvas {
	double[][] mapHights;
	//int airPlaneCol, airPlaneRow;
	double cellDistance;
	double datumPointX,datumPointY;
	int mapRows=0;
	int mapCols=0;
	double maxHight;
	double mapWidth;
	double mapHeight;
	double cellWidth, cellHeight;
	double planePosX, planePosY; 
	double destPointX, destPointY;
	String lst;
	static int numOfTimesEnterd = 0;
	
	
	public void intiallizesetMapDisplayer1() {
		mapHights=null;
		cellDistance=0;
		datumPointX=0;
		datumPointY=0;
		mapRows=0;
		mapCols=0;
		maxHight=0;
		mapWidth=0;
		mapHeight=0;
		cellWidth=0;
		cellHeight=0;
		planePosX=0;
		planePosY=0;
		destPointX=0;
		destPointY=0;
		lst=null;
	}
	
	public void drawPathOnMap(){
		GraphicsContext gc = getGraphicsContext2D();
		String[] moves = lst.split(",");
		double currPositionX = planePosX;
		double currPositionY = planePosY;
		for(int i=0;i<moves.length-1;i++) {
			try {
				Image pathPhoto = new Image(new FileInputStream("./resources/circle.jpg"));
				
				if(moves[i].equals("Up")) {
					currPositionY = currPositionY-1;
					gc.drawImage(pathPhoto, currPositionX*cellWidth, currPositionY*cellHeight, cellWidth, cellHeight);
				}
				if(moves[i].equals("Down")) {
					currPositionY = currPositionY+1;
					gc.drawImage(pathPhoto, currPositionX*cellWidth, currPositionY*cellHeight, cellWidth, cellHeight);
				}
				if(moves[i].equals("Left")) {
					currPositionX=currPositionX-1;
					gc.drawImage(pathPhoto, currPositionX*cellWidth, currPositionY*cellHeight, cellWidth, cellHeight);
				}
				if(moves[i].equals("Right")) {
					currPositionX=currPositionX+1;
					gc.drawImage(pathPhoto, currPositionX*cellWidth, currPositionY*cellHeight, cellWidth, cellHeight);
				}
			} catch (FileNotFoundException e) {e.printStackTrace(); }
		}
	}

	public void setMapDisplayer(File file) {
		try {
			ArrayList<String[]> mapLst = new ArrayList<String[]>();
			@SuppressWarnings("resource")
			Scanner CSVreader = new Scanner(new BufferedReader(new FileReader(file)));
			String[] datumPoints = CSVreader.nextLine().split(",");
			datumPointX = Double.parseDouble(datumPoints[0]);
			datumPointY = Double.parseDouble(datumPoints[1]);
//			System.out.println("datumPointX: "+datumPointX);
//			System.out.println("datumPointY: "+datumPointY);
			String[] cellDistanceStr = CSVreader.nextLine().split(",");
			cellDistance = Double.parseDouble(cellDistanceStr[0]);
			maxHight=0;
			
			while(CSVreader.hasNextLine()) {
				String[] line = CSVreader.nextLine().split(",");
				mapLst.add(line);
				mapCols = line.length;
				mapRows++;
			}
			int count = 0;
				
			mapHights = new double[mapRows][mapCols];
			for(int i=0; i<mapRows; i++) {
				String[] line = mapLst.get(i);
				for(int j=0; j<mapCols;j++) {
					
					double value =  Double.parseDouble(line[j]);
					mapHights[i][j] = value;
					count++;
					if(value>maxHight) {
						maxHight = value;
					}
				}
			}
			reDraw();

		} catch (IOException e) { e.printStackTrace(); } 
	}

	public void drawPlane(double posX, double posY) {
		int corX = (int)(posX / cellWidth);
		int corY = (int)(posY / cellHeight);
		planePosX = corX;
		planePosY = corY;
		try {
			Image img = new Image(new FileInputStream("./resources/airplane.jpg"));
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(img, corX*cellWidth, corY*cellHeight,cellWidth,cellHeight); // draw plane
			
		} catch (FileNotFoundException e) {	e.printStackTrace(); }
	}
	
	public void drawAirplaneMoves() {
		
	}

	public void markDestByMouse(double posX, double posY) {
		numOfTimesEnterd++;
		int corX = (int)(posX / cellWidth);
		int corY = (int)(posY / cellHeight);
		destPointX = corX;
		destPointY =corY;
		if(numOfTimesEnterd==1) {
		try {
			Image img = new Image(new FileInputStream("./resources/destination.jpg"));
			GraphicsContext gc = getGraphicsContext2D();
			gc.drawImage(img, corX*cellWidth, corY*cellHeight,cellWidth,cellHeight); // draw the dest		
		} catch (FileNotFoundException e) {e.printStackTrace();}
	}
		else {
			try {
				Image img = new Image(new FileInputStream("./resources/destination.jpg"));
				GraphicsContext gc = getGraphicsContext2D();
				reDraw();
				gc.drawImage(img, corX*cellWidth, corY*cellHeight,cellWidth,cellHeight); // draw the dest
				 MySerialServer mss = new MySerialServer(5402);
				 mss.start(5402, new MyClientHandler(this));
				try {
					Socket theServer = new Socket("127.0.0.1", 5402);
					} catch (IOException e) { e.printStackTrace(); }
		  
				Thread t = new Thread();
				try {
					t.sleep(100);
				} catch (InterruptedException e) { e.printStackTrace(); }
				drawPathOnMap();
			} catch (FileNotFoundException e) {e.printStackTrace();}
		}
}
	
	public void reDraw() {
		if(mapHights != null) {
			double red = 0;
			double green = 0;
			mapWidth = getWidth();
			mapHeight = getHeight();
			cellWidth = mapWidth / mapCols;
			cellHeight = mapHeight / mapRows;

	
			GraphicsContext gc = getGraphicsContext2D();	
			
			for(int i = 0; i < mapRows;i++) {
				for(int j = 0; j<mapCols; j++) {
					if(mapHights[i][j] <= maxHight * 0.5){
						red = 255;
						green = mapHights[i][j] * (255 / maxHight) * 2;
						if(green>1.0) {
							green=1.0;
						}
					}
					else{
						red = Math.abs(255 - ((mapHights[i][j] - (maxHight/2)) * (255 / maxHight) * 2));
						green = 255;
					}
					gc.setFill(new Color(red/255, green/255, 0, 1));
					gc.fillRect(j*cellWidth, i*cellHeight, cellWidth, cellHeight);					
				}
			}
			drawPlane(this.datumPointX, this.datumPointY);
		}
	}
	
	public double getPlanePosX() {
		return planePosX;
	}

	public void setPlanePosX(int planePosX) {
		this.planePosX = planePosX;
	}

	public double getPlanePosY() {
		return planePosY;
	}

	public void setPlanePosY(int planePosY) {
		this.planePosY = planePosY;
	}
	
	public double[][] getMapHights() {
		return mapHights;
	}

	public void setMapHights(double[][] mapHights) {
		this.mapHights = mapHights;
	}

	public double getDatumPointX() {
		return datumPointX;
	}

	public void setDatumPointX(double datumPointX) {
		this.datumPointX = datumPointX;
	}

	public double getDatumPointY() {
		return datumPointY;
	}

	public void setDatumPointY(double datumPointY) {
		this.datumPointY = datumPointY;
	}

	public double getDestPointX() {
		return destPointX;
	}

	public void setDestPointX(double destPointX) {
		this.destPointX = destPointX;
	}

	public double getDestPointY() {
		return destPointY;
	}

	public void setDestPointY(double destPointY) {
		this.destPointY = destPointY;
	}
	
	public String getLst() {
		return lst;
	}

	public void setLst(String ls) {
		this.lst = ls;
	}
	
	public int getMapRows() {
		return mapRows;
	}

	public void setMapRows(int mapRows) {
		this.mapRows = mapRows;
	}

	public int getMapCols() {
		return mapCols;
	}

	public void setMapCols(int mapCols) {
		this.mapCols = mapCols;
	}

}