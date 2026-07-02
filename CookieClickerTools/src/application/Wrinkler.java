package application;

import java.util.Arrays;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class Wrinkler extends Clicker{
	
	private Thread wrinklerThread = null;
	private static boolean continueThread = false;
	private Coordinate up;
	private Coordinate down;
	private Coordinate[] circlePoints;
	
	
	public Wrinkler() {
		
		//sets the toggle key to f3
		super( "F3" );
	}
	
	@Override
	public void toggleClicking() {
		
		//Logger.log("Wrinkler.toggleClickng called");
		
		if ( !clicking ) {
			if ( circlePoints == null ) {
				Logger.log( "Can not enable the circle clicker. Circle has not been created.");
			} else {
				clicking = true;
				Logger.log("Wrinkler.toggleClicking -> true");
				//set the wrinkler thread
				wrinklerThread = new Thread(() ->  {
					Logger.log("Wrinikler Thread Created");
					circleClicker();
				});
				wrinklerThread.setDaemon(true);//allows the program to be killed with this thread still active
				wrinklerThread.start();
			}
		} else {
			clicking = false;
			Logger.log("Wrinkler.toggleClicking -> false");

		}
		
	}
	
	/**
	 * Uses the circle points to click in a circle.
	 * Continues clicking until clicking is disabled through a hotkey
	 */
	private void circleClicker() {
		//Logger.log("Wrinkler.circleClicker called");
		
		int currentPos = 0;
		
		while ( clicking ) {
			//System.out.println(currentPos);
			//resets the circle
			if ( currentPos >= 360) {
				currentPos = 0;
			}
			
			clickPos( circlePoints[currentPos] );	
			currentPos++;
		}		
	}
	
	
	
	
	
	
	/**
	 * 
	 * @param t
	 */
	public void circleMaker( Label t, TextArea ta ) {
		
		Logger.log("Wrinkler.circleMaker called");
		
		//set the wrinkler thread
		wrinklerThread = new Thread(() ->  {
			//get up and down Coordinates for the circle
			up = waitFetchClick( t, "Click the hightest point of the circle.");
			down = waitFetchClick( t, "Click the lowest point of the circle. " );
			
			//make an array of positions around the circle
			circlePoints = makeCirclePoints();
			
			//run on the JavaFX thread
				Platform.runLater( () -> { ta.setText( up.toString() + "\n" + down.toString() ); } );
				Platform.runLater( () -> { t.setText("All Positions Recorded."); } );
			
			Logger.log(Arrays.toString(circlePoints));
		});
		wrinklerThread.setDaemon(true);//allows the program to be killed with this thread still active
		wrinklerThread.start();
		
	}
	
	/**
	 * Waits for the MouseListener to allow the process to continue
	 * then fetches the position of the right click.
	 * @param t An object of type Label from JavaFX
	 * @param s A string to be displayed on the label t
	 * @return An object of type Coordinate. The location of the last right click by the user.
	 */
	private Coordinate waitFetchClick( Label t, String s ) {
		
		continueThread = false;
		try {
			Platform.runLater( () -> { t.setText( s ); } );
			//loops while waiting for the MouseListener to allow this to continue
			while ( !continueThread ) {
				//any more than 100 and the use may notice delay
				Thread.sleep(100);
			}			
		} catch (InterruptedException e) {
			Logger.log( "Wrinkler.circleMaker sleep interupted." );
		}
		
		return MouseListener.getLastClickPosition();
	}
	
	
	/**
	 * 
	 * @return
	 */
	private Coordinate[] makeCirclePoints() {

		int r = getDistance(up, down) / 2;
		
		Coordinate midpoint = getMidpoint( up, down );
		
		Coordinate[] cords = new Coordinate[360];
		
		int x, y;
		//one point for each degree around the circle
		for ( int i = 0; i < 360; i++ ) {
			
			//loss of precision is not an issue as sub-pixel clicking is not possible
			x = (int) ( midpoint.x + r * Math.cos(i) );
			y = (int) ( midpoint.y + r * Math.sin(i) );

			cords[i] = new Coordinate( x, y );
		}
		
		
		return cords;
	}
	
	
	/**
	 * Calculates the distance between a and b.
	 * @param a An object of type Coordinate
	 * @param b An object of type Coordinate
	 * @return The distance between a and b
	 */
	private int getDistance( Coordinate a, Coordinate b) {
		
		int x = ( a.x - b.x ) * ( a.x - b.x );
		int y = ( a.y - b.y ) * ( a.y - b.y );
		
		//Loss of accuracy is not an issue because you can't click between pixels
		return (int) Math.sqrt( x + y );
	
	}
	
	/**
	 * Calculates the midpoint of a and b
	 * @param a An object of type Coordinate
	 * @param b An object of type Coordinate
	 * @return An object of type Coordinate containing the midpoint of a and b
	 */
	private Coordinate getMidpoint(Coordinate a, Coordinate b) {
		int xMid = ( a.x + b.x ) / 2;
		int yMid = ( a.y + b.y ) / 2;
		return new Coordinate( xMid, yMid );
	}

	
	
	//setters
		public void setContinueThread( boolean b ) {
			Wrinkler.continueThread = b;
			
		}
		//toggleKey
		public void setToggleKey( String s ) {
			this.toggleKey = s.toUpperCase();
			Logger.log("Wrnkler toggle key set to : " + s );
		}
	
	
	
	
	
	
	
}
