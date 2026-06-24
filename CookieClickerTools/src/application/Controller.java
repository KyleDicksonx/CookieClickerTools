package application;

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
		private Clicker clicker = new Clicker();
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
		
		
	//Settings
	//Rebirth
		@FXML
		private TextField rebirthToggleKey;
		@FXML
		private TextArea rebirthLocationDisplay;
		@FXML
		private Label rebirthCurrentItemToClickText;
	//Auto Toggles
	//Farm
	//Golden Cookies
	
	
		
	//Clicker tab actions -----------------------------------------------------------------
		public void clickerChangeKey(ActionEvent e) {
			Logger.log("Clicker.clickerChangeKey called");
			clicker.setToggleKey( clickerToggleKeyDisplay.getText() );
		}
		
		/**
		 * Sends the clicker to its own thread to prevent UI freezing
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
				clicker.toggleClicking(
						Integer.parseInt(xText.getText()),
						Integer.parseInt(yText.getText())
				);
			} else {
				clicker.toggleClicking();
			}	
			clickerSwapCircleColor();
			
			
		}
		
		private void clickerSwapCircleColor() {
			if ( clicker.getClicking() ) {
				clickerEnabledIndicator.setFill(Color.GREEN);
			} else {
				clickerEnabledIndicator.setFill(Color.RED);
			}
		}
		
		public void clickerChangePos(ActionEvent e) {
			System.out.println("clickerChangePos pressed");
		}
		
		public void clickerUsePosition(ActionEvent e) {
			clicker.toggleUsePosition();
			
		}
		
		/**
		 * Changes the displayed values for x and y position of the mouse.
		 * When a position based clicker is called, it fetches the values that are currently
		 * in the text fields, therefore no other changes are needed to store the values. 
		 * @param x The x position to be displayed in the UI
		 * @param y The y position to be displayed in the UI
		 */
		public void clickerSetPosition(int x, int y) {
			xText.setText( Integer.toString(x) );
			yText.setText( Integer.toString(y) );
		}
		
		public void clickerUseClickCount() {
			try {
				clicker.toggleUseClickCount( Integer.parseInt( clickerClickCount.getText() ) );
			} catch ( NumberFormatException e) {
				Logger.log("Error. Click count must be an integer. Click count was not changed.");
			}
			
		}
	
		
	//Rebirth Tab Actions -----------------------------------------------------------------------------
		
		public void rebirthChangeKeyPressed() {
			//TODO implement changing the toggle key
			//reuse ideas from the clicker tab toggle key
		}
		
		public void rebirthStartRecording() {
			
			//TODO add the option to save and load from file for easy access
			Logger.log("Rebirth location logging started.");
			rebirthThread = new Thread(() ->  {
				
				Logger.log("Rebirth Thread Created");
				
				Coordinate[] c = Rebirth.locationBuilderController( rebirthCurrentItemToClickText );
				this.rebirth = new Rebirth(	c[
				                           	  0].x, c[0].y,
											c[1].x, c[1].y,
											c[2].x, c[2].y,
											c[3].x, c[3].y,
											c[4].x, c[4].y,
											c[5].x, c[5].y );
				
				Logger.log("Full coordinates for rebirth built.");
				
				//Build the coordinates to be displayed in the text area
				StringBuilder display = new StringBuilder();
				for ( Coordinate i : c ) {
					display.append( i.toString() + "\n");
				}
				
				//update the text area in the JavaFX thread
				Platform.runLater( () -> { rebirthLocationDisplay.setText( display.toString() ); } );
			});
			rebirthThread.start();
		}
		
		public void rebirthRunRebirth() {
			Logger.log("Running reirth cycle from Controller.rebirthRunRebirth");
			rebirthThread = new Thread( () -> { 
				rebirth.runRebirth(); 
			});
			rebirthThread.start();
		}
		
		
	//getters
		public Clicker getClicker() {
			return clicker;
		}
		public Rebirth getRebirth() {
			return rebirth;
		}
		
	
}
