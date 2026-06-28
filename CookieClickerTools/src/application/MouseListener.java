package application;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;


public class MouseListener implements NativeMouseListener{
	private Controller controller;
	private static Coordinate lastClickPosition = null;
	
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
		disableAllClickers();
		
		int mouseButton = n.getButton();
		
		int x = n.getPoint().x;
		int y = n.getPoint().y;
		setLastClickPosition( x, y );
		
		if ( mouseButton == 2 ) { 
			
			//set the clicker tab's position to wherever the last right click was
			Logger.log("Clicker position updated to (" + x + ", " + y + ")");
			controller.clickerSetPosition(x, y);
		}
		if ( Rebirth.getBuilding() ) {
			Rebirth.setContinueThread(true);
		}
		

	}
	
	@Override
	public void nativeMousePressed( NativeMouseEvent n ) {
		
	}
	
	@Override
	public void nativeMouseReleased( NativeMouseEvent n ) {
		
	}
	
	public void setLastClickPosition( int x, int y) {
		MouseListener.lastClickPosition =  new Coordinate( x, y );
	}
	
	public static Coordinate getLastClickPosition() {
		return MouseListener.lastClickPosition;
	}
	
	
	public void disableAllClickers() {
		//TODO ensure all clickers are disabled here
		controller.getClicker().setClicking(false);
		Rebirth.setContinueThread(false);
	}
 }
