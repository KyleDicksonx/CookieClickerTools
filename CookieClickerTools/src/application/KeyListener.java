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
	 * 
	 * 
	 * 
	 * 
	 * @param n An object of type NativeKeyEvent. It contains the key that was clicked.
	 */
	@Override
	public void nativeKeyPressed( NativeKeyEvent n ){
		
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
			controller.getWrinkler().toggleClicking();
		}

		
	}
	
	@Override
	public void nativeKeyReleased( NativeKeyEvent n ) {
		//Logger.log("KeyUp : " + NativeKeyEvent.getKeyText(n.getKeyCode()));
	}
	
	/**
	 * 
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
	
	
}
