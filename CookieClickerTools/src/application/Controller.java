package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Controller {
	
	
	
	
	//clicker
		//FXML
			@FXML
			private TextField clickerToggleKeyDisplay;
			@FXML
			private TextField xText;
			@FXML
			private TextField yText;
			@FXML
			private Circle clickerEnabledIndicator;
			@FXML
			private Button clickerSetPos;
			@FXML
			private CheckBox clickerUsePosition;
			@FXML
			private TextField clickerClickCount;
			@FXML
			private CheckBox clickerUseClickCount;
			@FXML
			private TextField clickerDelay;
			@FXML

			
			
			
			
			
		//other
			private Clicker clicker = new Clicker();
		
		
		
		
		
	//Settings
	//Rebirth
	//Auto Toggles
	//Farm
	//Golden Cookies
	
	
		
	//Clicker tab actions
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
		
			
		public void clickerSetPosition() {
			//TODO build the set pos button
		}
		
		public void clickerUseClickCount() {
			try {
				clicker.toggleUseClickCount( Integer.parseInt( clickerClickCount.getText() ) );
			} catch ( NumberFormatException e) {
				Logger.log("Error. Click count must be an integer. Click count was not changed.");
			}
			
		}
		
		
		
	
	//getters
		public Clicker getClicker() {
			return clicker;
		}
		
	
}
