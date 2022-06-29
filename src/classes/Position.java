package classes;

public class Position {
	public int x, y;
	
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	static double between(Position x, Position y) {
		return Math.sqrt( Math.pow(x.x - y.x,2) + Math.pow(x.y-y.y,2) );
	}
}
