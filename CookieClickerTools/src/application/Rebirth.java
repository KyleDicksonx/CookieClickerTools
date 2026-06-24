package application;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Rebirth {
	
	private Coordinate buy100;
	private Coordinate buyAllUpgrades;
	private Coordinate buyCursor;
	private Coordinate buyGrandma;
	private Coordinate legacy;
	private Coordinate reincarnate;
	
	private static Coordinate lastClickPosition = null;
	private static boolean continueThread = false;
	private volatile static boolean building = false;
	
	//constructors 
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
				
		}
		
		public Rebirth () {
			
			//default value that will not click anything on the Cookie Clicker screen
			this.buy100 = new Coordinate(50,50);
			this.buyAllUpgrades = new Coordinate(50,50);
			this.buyCursor = new Coordinate(50,50);
			this.buyGrandma = new Coordinate(50,50);
			this.legacy = new Coordinate(50,50);
			this.reincarnate = new Coordinate(50,50);
			
		}
		
		public Rebirth ( int x, int y ) {
			
			//default value that will not click anything on the Cookie Clicker screen
			this.buy100 = new Coordinate(x,y);
			this.buyAllUpgrades = new Coordinate(x,y);
			this.buyCursor = new Coordinate(x,y);
			this.buyGrandma = new Coordinate(x,y);
			this.legacy = new Coordinate(x,y);
			this.reincarnate = new Coordinate(x,y);
			
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
		
	
	//building locations
		public static Coordinate[] locationBuilderController( Label t ) {
			
			Coordinate[] allCords = new Coordinate[6];
			building = true;
			
			//this thread waits for a mouse click for each coordinate
			allCords[0] = fetchCords( t, "Left Click on the \"100\" under buy multipliers." );
			allCords[1] = fetchCords( t, "Left Click on the \"Buy All Upgrades\" button." );
			allCords[2] = fetchCords( t, "Left Click on the \"Cursor\" building." );
			allCords[3] = fetchCords( t, "Left Click on the \"Grandma\" building." );
			allCords[4] = fetchCords( t, "Left Click on the \"Legacy\" button." );
			allCords[5] = fetchCords( t, "Left Click on the \"Reincarnate\" button.");
			Platform.runLater( () ->  { t.setText("All positions recorded"); } );
			
			building = false;
			return allCords;
		}
		private static Coordinate fetchCords( Label t, String s ) {
			
			Logger.log(s);
			Platform.runLater(() -> { t.setText(s); } );
			
			try {
				
				//when the mouse is clicked this is switched to true
				while ( !continueThread ) {
					Thread.sleep(1);
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			continueThread = false;
			return new Coordinate( lastClickPosition.x, lastClickPosition.y );
			
		}
	
	//setters
		public static void setLastClickPosition ( Coordinate c ) {
			Rebirth.lastClickPosition = c;
		}

		public static void setContinueThread( boolean b ) {
			Rebirth.continueThread = b;
			
		}

		
		
}
