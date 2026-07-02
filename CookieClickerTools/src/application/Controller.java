package application;
//TODO update all comments to reflect the change to Coordinate objects over x, y
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
	
	//tabs
		private Clicker clicker = new Clicker( );
		private Wrinkler wrinkler = new Wrinkler();
		private Rebirth rebirth;
	
	//threads
		private Thread rebirthThread;
		
	
	//clicker
		@FXML
		private TextField clickerToggleKeyDisplay;
		@FXML
		private TextField xText;
		@FXML
		private TextField yText;
		@FXML
		private Circle clickerEnabledIndicator;
		@FXML
		private TextField clickerClickCount;
		@FXML
		private TextField clickerDelay;
	//Wrinkler
		@FXML
		private TextField wrinklerToggleKey;
		@FXML 
		private TextArea wrinklerLocationDisplay;
		@FXML
		private Label wrinklerWhereClickText;
		@FXML
		private Circle wrinklerClickingIndicator;
	//Settings
	//Rebirth
		@FXML
		private TextField rebirthToggleKey;
		@FXML
		private TextArea rebirthLocationDisplay;
		@FXML
		private Label rebirthCurrentItemToClickText;
		@FXML
		private Circle rebirthClickingIndicator;
	//Auto Toggles
	//Farm
	//Golden Cookies
	
	
		
	//Clicker tab actions -----------------------------------------------------------------
		
		/**
		 * Sets the clicker toggleKey to the string in the toggle key text field
		 * when the "Change Key" button is pressed in the Clicker tab of the UI.
		 * @param e
		 */
		public void clickerChangeKey(ActionEvent e) {
			Logger.log("Clicker.clickerChangeKey called");
			clicker.setToggleKey( clickerToggleKeyDisplay.getText() );
		}
		
		/**
		 * Sends the clicker to its own thread to prevent UI freezing.
		 * Called when the user clicks the clicker toggle key.
		 * @param e
		 */
		public void clickerClicking(ActionEvent e) {
			
			//Parse the number and set the delay
			try {
				clicker.setDelay( Integer.parseInt( clickerDelay.getText() ) );
			} catch ( NumberFormatException ex ) {
				Logger.log("Text input into clicker delay is invalid. Delay set to 100ms.");
				clicker.setDelay(100);
			}
			
			//parse the number and set the click count
			//causes the click count to be updated
			if ( clicker.getUseClickCount() ) {
				try {
					clicker.setClickCount( Integer.parseInt( clickerClickCount.getText() ) );
				} catch ( NumberFormatException ex ) {
					Logger.log("Text input into click count field is invalid. Count set to 100.");
				}
			}
			
			
			//call the correct clicker
			//Logger.log("Use pos " + clicker.getUsePosition());
			if ( clicker.getUsePosition() ) {
				clicker.toggleClicking( new Coordinate ( 	Integer.parseInt( xText.getText() ), 
															Integer.parseInt( yText.getText() ) ) );
			} else {
				clicker.toggleClicking();
			}	
			clickerSwapCircleColor();
			
			
		}
		
		/**
		 * Sets the color of the clicker indicator circle to 
		 * red or green based on the status of clicker.clicking.
		 */
		private void clickerSwapCircleColor() {
			if ( clicker.getClicking() ) {
				clickerEnabledIndicator.setFill(Color.GREEN);
			} else {
				clickerEnabledIndicator.setFill(Color.RED);
			}
		}

		/**
		 * Calls clicker.toggleUsePosition() when the "Use Position?" checkbox
		 * is clicked in the Clicker tab of the UI.
		 * @param e An ActionEvent
		 */
		public void clickerUsePosition(ActionEvent e) {
			clicker.toggleUsePosition();	
		}
		
		/**
		 * Changes the displayed values for x and y position of the mouse.
		 * When a position based clicker is called, it fetches the values that are currently
		 * in the text fields, therefore no other changes are needed to store the values.
		 * Called when the mouse is right clicked by MouseListener. 
		 * @param c A Coordinate containing the position to be clicked
		 */
		public void clickerSetPosition( Coordinate c ) {
			xText.setText( Integer.toString(c.x) );
			yText.setText( Integer.toString(c.y) );
		}
		
		/**
		 * Toggles the status of clicker.useClickCount
		 * when the "UseClickCount?" checkbox is clicked in the Clicker tab of the UI. 
		 */
		public void clickerUseClickCount() {
			try {
				clicker.toggleUseClickCount( Integer.parseInt( clickerClickCount.getText() ) );
			} catch ( NumberFormatException e) {
				Logger.log("Error. Click count must be an integer. Click count was not changed.");
			}
			
		}
	
		
	//Rebirth Tab Actions -----------------------------------------------------------------------------
		
		/**
		 * Changes the toggle key for the Rebirth clicker
		 * when the "Change Key" button on the Rebirth tab of the UI is clicked.
		 */
		public void rebirthChangeKeyPressed() {
			rebirth.setToggleKey( rebirthToggleKey.getText() );
		}
		
		/**
		 * Creates a thread for recording the Coordinates for the rebirth cycle
		 * when the "Start Recording" button is pressed in the Rebirth section of the UI.
		 */
		public void rebirthStartRecording() {
			
			//TODO add the option to save and load from file for easy access
			Logger.log("Rebirth location logging started.");
			rebirthThread = new Thread(() ->  {
				
				Logger.log("Rebirth Thread Created");
				
				Coordinate[] c = Rebirth.locationBuilderController( rebirthCurrentItemToClickText );
				this.rebirth = new Rebirth(	c[0].x, c[0].y, 	//buy100
											c[1].x, c[1].y, 	//buy all upgrades
											c[2].x, c[2].y, 	//cursor
											c[3].x, c[3].y, 	//grandma
											c[4].x, c[4].y,	 	//legacy
											c[5].x, c[5].y ); 	//reincarnate
				
				Logger.log("Full coordinates for rebirth built.");
				
				//Build the coordinates to be displayed in the text area
				StringBuilder display = new StringBuilder();
				for ( Coordinate i : c ) {
					display.append( i.toString() + "\n");
				}
				
				//update the text area in the JavaFX thread
				Platform.runLater( () -> { rebirthLocationDisplay.setText( display.toString() ); } );
			});
			rebirthThread.setDaemon(true);//allows the program to be killed with this thread still active
			rebirthThread.start();
		}
		
		/**
		 * Runs the full cycle for rebirths
		 * when the rebirth toggle key is pressed. 
		 */
		public void rebirthRunRebirth() {
			
			Logger.log("Running reirth cycle from Controller.rebirthRunRebirth");
			
			rebirthThread = new Thread( () -> { 
				
				rebirthSwapCircleColor(); //red to green
				rebirth.runRebirth(); //the clicking and loop	
				rebirthSwapCircleColor(); //green to red
				
			});
			rebirthThread.setDaemon(true);//allows the program to be killed with this thread still active
			rebirthThread.start();
		}
		
		/**
		 * Changes the rebirth indicator circle's color based on the status
		 * of the continueRebirth field.
		 */
		private void rebirthSwapCircleColor() {
			if ( rebirth.getContnueRebirth() ) {
				rebirthClickingIndicator.setFill(Color.GREEN);
			} else {
				rebirthClickingIndicator.setFill(Color.RED);
			}
		}
		
		
		
	//Wrinkler Tab actions ---------------------------------------------------------------------------------------
		
		/**
		 * Calls the circle maker for the Wrinkler tab 
		 * when the "Build Circle" UI button is clicked in the Wrinkler tab.
		 */
		public void wrinklerBuildCircle() {
			wrinkler.circleMaker( wrinklerWhereClickText, wrinklerLocationDisplay );
		}
		
		/**
		 * Sets the toggle key for the Wrinkler tab
		 * when the "Build Circle" button is clicked in the Wrinkler tab of the UI.
		 */
		public void wrinklerChangeKey() {
			wrinkler.setToggleKey( wrinklerToggleKey.getText() );
		}
		
		/**
		 * Toggles the clicking state off the Wrinkler tab clicker and swaps the color
		 * of the clicking indicator circle when the wrinkler toggle key is pressed.
		 */
		public void wrinklerToggleClicking() {
			
			wrinkler.toggleClicking();
			wrinklerSwapCircleColor();
		}
		
		/**
		 * Changes the color of the Wrinkler clicking indicator based on the status
		 * of wrinkler.clicking
		 */
		private void wrinklerSwapCircleColor() {
			if ( wrinkler.getClicking() ) {
				wrinklerClickingIndicator.setFill(Color.GREEN);
			} else {
				wrinklerClickingIndicator.setFill(Color.RED);
			}
		}
		
	//Settings tab actions ------------------------------------------------------------------
		/**
		 * Calls the static method Settings.toggleAllowMouseClicks() 
		 * when the "Allow Mouse Clicks" checkbox is clicked in the Settings tab.
		 */
		public void settingsToggleAllowMouseClicks() {
			Settings.toggleAllowMouseClicks();
		}
		
		
		
	
		
	//getters
		public Clicker getClicker() {
			return clicker;
		}
		public Rebirth getRebirth() {
			return rebirth;
		}
		public Wrinkler getWrinkler() {
			return wrinkler;
		}
		
	
}
