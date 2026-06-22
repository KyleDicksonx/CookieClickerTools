package application;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.time.Duration;


public class Clicker {
	
	private Thread clickingThread;
	private volatile boolean clicking = false; 
	private boolean usePosition = false, useClickCount = false;
	private int delay = 100; //the setter sets this to input - 1 so that the next click happens in the ms requested
	private int clickCount = 100;
	private Robot robot;
	
	private String toggleKey = "F1"; //must be upper case, enforced by the setter
	
	/**
	 * Default constructor
	 * Use setters for setting delay, no other constructors should be needed
	 */
	public Clicker() {
		
		//creates the robot for executing mouse output
		try {
			robot = new Robot();
		} catch ( AWTException e ) {
			Logger.log("AWTException caught in Clicker constructor, Mouse and keyboard output may not function.S");
		}
	}
	
	/**
	 * Toggles the clicking state from true to false or false to true.
	 * Creates and starts the dedicated clicking thread
	 */
	public void toggleClicking() {
		if ( !clicking ) {
			
			clicking = true;
			Logger.log("Clicker.toggleClicking -> true");
			//set the clicking thread
			clickingThread = new Thread(() ->  {
				Logger.log("Clicking Thread Created");
				clickerChooser();
			});
			clickingThread.setDaemon(true);//allows the program to be killed with this thread still active
			clickingThread.start();

			
		} else {
			clicking = false;
			Logger.log("Clicker.toggleClicking -> false");

		}
		
	}
	
	/**
	 * Toggles the clicking state from true to false or false to true
	 * for position based mouse clicking. 
	 * Also starts the dedicated clicking thread
	 * @param x The x position the mouse will click at
	 * @param y The y position the mouse will click at
	 */
	public void toggleClicking(int x, int y) {
		if ( !clicking ) {
			
			clicking = true;
			Logger.log("Clicker.toggleClicking(x,y) -> true");
			
			//set the clicking thread
			clickingThread = new Thread(() ->  {
				Logger.log("Position Clicking Thread Created");
				clickerChooser(x, y);	
				
			});
			clickingThread.setDaemon(true);//allows the program to be killed with this thread still active
			clickingThread.start();

			
		} else {
			clicking = false;
			Logger.log("Clicker.toggleClicking(x,y) -> false");

		}
		
	}
	
	//just clicks the mouse
		/**
		 * Clicks the mouse at the mouse's current position
		 */
		public void clickHere() {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			try {
				Thread.sleep(Duration.ofNanos(50000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		
		/**
		 * Clicks the mouse at the specified location
		 * @param x The x position the mouse will click at
		 * @param y The y position the mouse will click at
		 */
		public void clickPos(int x, int y) {
			robot.mouseMove(x, y);
			clickHere();
		}
	
	//functions with clicking loops
		/**
		 * Clicks at the current mouse location until stopped (when clicking is changed to false)
		 */
		public void clicking() {
			Logger.log("Clicker.clicking called");
			while ( clicking ) {
				clickHere();
			}
			this.setClicking(false);
		}
		
		/**
		 * Moves the mouse to the given location and then clicks
		 * @param x x position that the mouse will be moved to
		 * @param y y position that the mouse will be moved to
		 */
		public void clicking(int x, int y) {
			Logger.log("Clicker.clicking(x,y) called");
			
			while ( clicking ) {
				clickPos(x,y);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					//TODO error handling
					e.printStackTrace();
				}
			}
			this.setClicking(false);
		}
		
		/**
		 * Uses clicking(x,y) paired with a loop and a counter to click in
		 * a specific location a set number of times.
		 * If the mouse is moved externally it will snap back to the set location each time this loops.
		 * @param x x position that the mouse will be moved to
		 * @param y y position that the mouse will be moved to
		 */
		public void countClicking(int x, int y) {
			Logger.log("Clicker.countClicking(x,y) called");
			
			int counter = 0;
			while ( clicking && counter < clickCount) {
				clickPos(x,y);
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					//TODO error handling
					Logger.log(e.getStackTrace().toString());
				}
				counter++;
			}
			this.setClicking(false);
		}
		
		/**
		 * Clicks a set number of times wherever the mouse currently is.
		 * Mouse movement will not affect the clicking
		 */
		public void countClicking() {
			Logger.log("Clicker.countClicking called");
			
			int counter = 0;
			while ( clicking && clickCount > counter) {
				clickHere();
				counter++;
			}
			this.setClicking(false);
		}
	
	/**
	 * Toggles usePosition from true to false or false to true
	 */
	public void toggleUsePosition() {
		
		if ( !usePosition ) { //false -> true
			usePosition = true;
			Logger.log("Clicker.toggleUsePosition -> true");
		} else { // true -> false
			usePosition = false;
			Logger.log("Clicker.toggleUsePosition -> false");
		}	
	}

	
	/**
	 * Called by toggle clicking to call the correct clicker loop.
	 * Checks the value of useClickCount to determine if we should use click count method or endless method.
	 * @param x The x position the mouse will be clicked at
	 * @param y The y position the mouse will be clicked at
	 */ 
	public void clickerChooser(int x, int y) {
		if ( useClickCount ) {
			
			
			countClicking(x,y);
		} else {
			clicking(x,y);
		}
	}
	public void clickerChooser() {
		if ( useClickCount ) {
			countClicking();
		} else {
			clicking();
		}
	}
	
	/**
	 * Toggles the useClickCount boolean
	 * @param i 
	 */
	public void toggleUseClickCount(int i) {
		useClickCount = !useClickCount;
		Logger.log( "useClickCount set to : " + useClickCount );
		setClickCount(i);
		
	}
	
	
	
	//setters
		//clicking
		public void setClicking(boolean x) {
			this.clicking = x;
			//Logger.log("Clicker clicking set to : " + x + " --with setter.");
		}
		
		//delay
		public void setDelay(int delay) {
			
			//ensures no negative delays are allowed
			if ( delay < 0 ) {
				Logger.log("Delay can not be below 0. Delay set to 0.");
				this.delay = 0;
			} else {
				this.delay = delay;
			}
			
		}
		
		//toggleKey
		public void setToggleKey( String s ) {
			this.toggleKey = s.toUpperCase();
			Logger.log("Toggle key set to : " + s );
		}
		
		//clickCount
		public void setClickCount( int i ) {
			if ( i < 1 ) {
				Logger.log("Click count must be a positive integer. Automatically set click count to the default of 100 clicks.");
				this.clickCount = 100;
			} else {
				this.clickCount = i;
			}
		}
		
		
	//getters
		
		//toggleKey
		public String getToggleKey() {
			return toggleKey;
		}
		
		//clicking
		public boolean getClicking() {
			return clicking;
		}

		//usePosition
		public boolean getUsePosition() {
			return usePosition;
		}

		//useClickCount
		public boolean getUseClickCount() {
			return useClickCount;
		}

		


	
}
