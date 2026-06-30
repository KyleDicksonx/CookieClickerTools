package application;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyListener implements NativeKeyListener{
	private Controller controller;
	

	public KeyListener ( Controller controller ) {
		this.controller = controller;
	}
	
	@Override
	public void nativeKeyTyped( NativeKeyEvent n ) {
		//System.out.println(NativeKeyEvent.getKeyText(n.getKeyCode())); 
	}
	
	/**
	 * Checks the current key and executes the related action
	 * 
	 * 
	 * Actions:
	 * 	Escape - Hard coded kill switch for the clicker.
	 * 	Toggle Clicking - Set by the user; defaults to F1.
	 *  Toggle Rebirth - Set by the user; defaults to F2.
	 *  Toggle Wrinkler - Set by the user; defaults to F3.
	 * 
	 * 
	 * 
	 * @param n An object of type NativeKeyEvent. It contains the key that was clicked.
	 */
	@Override
	public void nativeKeyPressed( NativeKeyEvent n ){
		//disableAllClickers();
		
		
		//gets the name of the pressed key as a string
		String key = NativeKeyEvent.getKeyText( n.getKeyCode() );
		//System.out.println(controller.getClicker().getToggleKey());
		//Logger.log( "KeyDown : " + key );
		

		//executes the correct action for the pressed key.
		//Note: Don't use switch, it needs constants.
		if ( key.equals("Escape") ) { //Kill Switch
			controller.getClicker().setClicking(false);
			
		} else if ( key.equals( controller.getClicker().getToggleKey() ) ) { //clicker toggle 
			controller.clickerClicking( null );
			
		} else if ( controller.getRebirth() != null ) { //rebirth toggle
			//ensures rebirth exists before attempting to toggle rebirth clicking
			toggleRebirth( key );
			
	
		} else if ( key.equals( controller.getWrinkler().getToggleKey() ) ) { // wrinkler toggle
			controller.wrinklerToggleClicking();
		}

		
	}
	
	/**
	 * Unused method
	 * @param 
	 */
	@Override
	public void nativeKeyReleased( NativeKeyEvent n ) {
		//Logger.log("KeyUp : " + NativeKeyEvent.getKeyText(n.getKeyCode()));
	}
	
	/**
	 * Calls controller.toggleRunRebirth() if the correct key is pressed
	 * and if the rebirth cycle is set to continue.
	 * @param key A String containing the name of a key
	 */
	private void toggleRebirth( String key ) {
		if ( key.equals( controller.getRebirth().getToggleKey() ) ) {
			controller.getRebirth().toggleContinueRebirth();
			if ( controller.getRebirth().getContnueRebirth() ) {
				controller.rebirthRunRebirth();
			}
		}
	}
	
	/**
	 * Disables all of the following: Clicker, Rebirth, Wrinkler, 
	 */
	public void disableAllClickers() {
		//TODO ensure all clickers are disabled here
		Logger.log("Disabled All Clickers.");
		controller.getClicker().setClicking(false);
		Rebirth.setContinueThread(false);
		controller.getWrinkler().setClicking(false);
	}
	
	
}
