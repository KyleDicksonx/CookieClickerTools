package application;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.time.Duration;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Rebirth {
	
	private Coordinate buy100;
	private Coordinate buyAllUpgrades;
	private Coordinate buyCursor;
	private Coordinate buyGrandma;
	private Coordinate legacy;
	private Coordinate reincarnate;
	private Coordinate lower;
	
	private static boolean continueThread = false;
	private volatile static boolean building = false;
	private volatile boolean continueRebirth = false;
	
	private final int LOWER_OFFSET = 400;
	
	private Robot r = null;
	
	private String toggleKey = "F2";
	
	//constructors 
		/**
		 * Builds Coordinate objects for each of the required positions for
		 * the rebirth actions. This includes a position below the lowest building
		 * so that the mouse can move away from the upgrade menu and thereby
		 * close the expanded upgrade menu.
		 * @param buy100X The x position of the "100" button
		 * @param buy100Y The y position of the "100" button
		 * @param buyAllUpgradesX The x position of the "Buy All Upgrades" button
		 * @param buyAllUpgradesY The y position of the "Buy All Upgrades" button
		 * @param buyCursorX The x position of the "Cursor" building
		 * @param buyCursorY The y position of the "Cursor" building
		 * @param buyGrandmaX The x position of the "Grandma" building
		 * @param buyGrandmaY The y position of the "Grandma" building
		 * @param legacyX The x position of the "Legacy" button
		 * @param legacyY The y position of the "Legacy" button
		 * @param reincarnateX The x position of the "Reincarnate" button
		 * @param reincarnateY The y position of the "Reincarnate" button
		 */
		public Rebirth (int buy100X, int buy100Y,
						int buyAllUpgradesX, int buyAllUpgradesY,
						int buyCursorX, int buyCursorY,
						int buyGrandmaX, int buyGrandmaY,
						int legacyX, int legacyY,
						int reincarnateX, int reincarnateY) {
			
			this.buy100 = new Coordinate( buy100X, buy100Y );
			this.buyAllUpgrades = new Coordinate( buyAllUpgradesX, buyAllUpgradesY );
			this.buyCursor = new Coordinate( buyCursorX, buyCursorY );
			this.buyGrandma = new Coordinate( buyGrandmaX, buyGrandmaY );
			this.legacy = new Coordinate( legacyX, legacyY );
			this.reincarnate = new Coordinate( reincarnateX, reincarnateY );
			this.lower = new Coordinate( buyGrandmaX, buyGrandmaY - LOWER_OFFSET );
			
			try {
				this.r = new Robot();
			} catch ( AWTException e ) {
				Logger.log("Unable to create Robot for mouse and keyboard outputs. Rebirth cycle will not work.");
			}
		}
			
	//getters
		public Coordinate getBuy100() {
			return buy100;
		}
		public Coordinate getBuyAllUpgrades() {
			return buyAllUpgrades;
		}
		public Coordinate getBuyCursor() {
			return buyCursor;
		}
		public Coordinate getBuyGrandma() {
			return buyGrandma;
		}
		public Coordinate getLegacy() {
			return legacy;
		}
		public Coordinate getReincarnate() {
			return reincarnate;
		}
		public static boolean getBuilding() {
			return building;
		}
		public String getToggleKey() {
			return toggleKey;
		}
		public boolean getContnueRebirth() {
			return continueRebirth;
		}
		
	
	//building locations
		public static Coordinate[] locationBuilderController( Label t ) {
			
			Coordinate[] allCords = new Coordinate[6];
			building = true;
			
			//this thread waits for a mouse click for each coordinate
			//TODO add a ding nose after each fetch so the user knows the click went through without looking
			allCords[0] = fetchCords( t, "Left Click on the \"100\" under buy multipliers." );
			allCords[1] = fetchCords( t, "Left Click on the \"Buy All Upgrades\" button." );
			allCords[2] = fetchCords( t, "Left Click on the \"Cursor\" building." );
			allCords[3] = fetchCords( t, "Left Click on the \"Grandma\" building." );
			allCords[4] = fetchCords( t, "Left Click on the \"Legacy\" button." );
			allCords[5] = fetchCords( t, "Left Click on the \"Reincarnate\" button.");
			
			//Change the text display on the JavaFX thread
			Platform.runLater( () ->  { t.setText("All positions recorded"); } );
			
			building = false;
			return allCords;
		}
		private static Coordinate fetchCords( Label t, String s ) {
			
			//Logger.log(s);
			Platform.runLater(() -> { t.setText(s); } );
			
			try {
				
				//when the mouse is clicked this is switched to true
				while ( !continueThread ) {
					//sleep any higher than 100 then the user notices some delay
					Thread.sleep(100);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			continueThread = false;
			return MouseListener.getLastClickPosition();
			
		}
	
	//setters

		public static void setContinueThread( boolean b ) {
			Rebirth.continueThread = b;
			
		}
		
	//Auto rebirth
		/**
		 * Continues doing rebirth cycles until the KeyListener toggles continueRebirth to false
		 */
		public void runRebirth() {
			
			continueRebirth = true;
			while ( continueRebirth ) {
				rebirthCycle();
				
			}
	
		}
		
		private void rebirthCycle() {
			try {
				
				click( buyAllUpgrades );
				
				r.mouseMove( lower.x, lower.y);
				Thread.sleep( 100 );
				click( buy100 );
				
				//move the mouse low enough to close the expanded upgrade menu

				
				//buildings and upgrades 1
					r.mouseMove( lower.x, lower.y);
					//buy 2 sets
						Thread.sleep(100);	
						buyCycle(2);
					click( buyAllUpgrades );
					Thread.sleep( 100 );
				//buildings and upgrades 2
					r.mouseMove( lower.x, lower.y);
					//buy 2 sets
						Thread.sleep(100);	
						buyCycle(2);
					click( buyAllUpgrades );
					Thread.sleep( 100 );
				//buildings and upgrades 3
					r.mouseMove( lower.x, lower.y);
					Thread.sleep(100);
					buyCycle(1);
					click( buyAllUpgrades );
					Thread.sleep(100);
				
				
				Thread.sleep( 1000 );
				//buy
					r.mouseMove( lower.x, lower.y);
					Thread.sleep(100);
					buyCycle(1);
				Thread.sleep(900);
				click ( buyAllUpgrades );
				Thread.sleep( 1000 );
				
				
				//start over
					click( legacy ); 
					//enter (confirm)
					click( KeyEvent.VK_ENTER );
					click( KeyEvent.VK_ESCAPE );
						
					Thread.sleep(100);
					click ( reincarnate );
					click ( KeyEvent.VK_ENTER );
					Thread.sleep( 1500 );
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * Left clicks at the given coordinate
		 * @param c An object of type Coordinate
		 * @throws InterruptedException
		 */
		private void click( Coordinate c ) throws InterruptedException {
			r.mouseMove(c.x, c.y);
			r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			Thread.sleep(Duration.ofNanos(50000));
			r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		
		/**
		 * Clicks the given key
		 * @param c An object of type Coordinate
		 * @param k A KeyEvent 
		 * @throws InterruptedException
		 */
		private void click( int k ) throws InterruptedException {
			r.keyPress(k);
			Thread.sleep(Duration.ofNanos(50000));
			r.keyRelease(k);
		}

		public void toggleContinueRebirth() {
			this.continueRebirth = !continueRebirth;
		}
		
		private void buyCycle( int count ) throws InterruptedException {
			for ( int i = 0; i < count; i++) {
				click( buyCursor );
				Thread.sleep(100);
				click( buyGrandma );
				Thread.sleep(100);
			}
			
		}

		

		
		

		

		
		
}
