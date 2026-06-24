package application;

public class Coordinate {
	
	public int x, y;
	
	public Coordinate() {
		this.x = 0;
		this.y = 0;
	}
	
	public Coordinate( int x, int y ) {
		this.x = x;
		this.y = y;
	}
	
	public void setX ( int x ) {
		this.x = x;
	}
	
	public void setY ( int y ) {
		this.y = y;
	}
	
	public String toString () {
		return "(" + x + ", " + y + ")";
		
	}
	
	
	public String toString ( Coordinate[] c ) {
		
		StringBuilder sb = new StringBuilder();
		
		for ( Coordinate i : c ) {
			sb.append( "(" + i.x + ", " + i.y + "), " );
		}
		
		return sb.toString();
		
	}
	
	
}
