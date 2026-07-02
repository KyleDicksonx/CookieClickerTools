package application;

/**
 * Used anywhere a (x, y) is needed
 */
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
	
	/**
	 * Takes the current Coordinate and outputs a string in
	 * the following format: "(x, y)".
	 * @return A String 
	 */
	public String toString () {
		return "(" + x + ", " + y + ")";
	}
	
	/**
	 * Takes an array of Coordinate objects and creates a single
	 * string containing all of the given coordinates listed first to last. 
	 * @param c An array of Coordinate objects
	 * @return A String containing all of the coordinates in the given array
	 */
	public String toString ( Coordinate[] c ) {
		
		StringBuilder sb = new StringBuilder();
		
		for ( Coordinate i : c ) {
			sb.append( "(" + i.x + ", " + i.y + "), " );
		}
		
		return sb.toString();		
	}
	
	
}
