package application;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;


public class MouseListener implements NativeMouseListener{
	
	private Controller controller;
	private static Coordinate lastClickPosition = null;
	
	/**
	 * Passes the controller so that the methods can access 
	 * the toggle functions from specific tabs
	 * @param controller The controller used by the JavaFX window
	 */
	public MouseListener ( Controller controller ) {
		this.controller = controller;
	}
	
	/**
	 * WARNING.
	 * This will pick up Java's Robot clicking.
	 * This clicker uses Robot.
	 * 
	 * Any mouse click will disable the auto clicker
	 */
	@Override
	public void nativeMouseClicked( NativeMouseEvent n ) {
		
		//all mouse clicks disable all clickers as a safeguard
		//disableAllClickers();
		
		int mouseButton = n.getButton();
		
		int x = n.getPoint().x;
		int y = n.getPoint().y;
		setLastClickPosition( x, y );
		
		if ( mouseButton == 2 ) { 
			
			//set the clicker tab's position to wherever the last right click was
			Logger.log("Clicker position updated to (" + x + ", " + y + ")");
			controller.clickerSetPosition( new Coordinate (x, y) );
		}
		if ( Rebirth.getBuilding() ) {
			Rebirth.setContinueThread(true);
		} 
		controller.getWrinkler().setContinueThread( true );

	}
	
	/**
	 * Unused method
	 * @param 
	 */
	@Override
	public void nativeMousePressed( NativeMouseEvent n ) {
		
	}
	
	/**
	 * Unused method
	 * @param 
	 */
	@Override
	public void nativeMouseReleased( NativeMouseEvent n ) {
		
	}
	
	//setters
	public void setLastClickPosition( int x, int y) {
		MouseListener.lastClickPosition =  new Coordinate( x, y );
	}
	
	//getters
	public static Coordinate getLastClickPosition() {
		return MouseListener.lastClickPosition;
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
