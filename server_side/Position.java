package server_side;

public class Position {
	private double x,y;
	
	public Position() {
	}
	
	public Position(Position p) {
		this.x=p.x;
		this.y=p.y;
	}
	
	public Position(double x, double y) {
		this.x=x;
		this.y=y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	@Override
	public boolean equals(Object position) {
		Position p = (Position) position;
		return this.x == p.x && this.y == p.y;
	}
	  
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
	
	
}
