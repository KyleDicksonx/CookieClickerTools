package application;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;


public class MouseListener implements NativeMouseListener{
	private Controller controller;
	
	
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
		
		//all mouse clicks disable the clicker as a safeguard
		controller.getClicker().setClicking(false);
		
		int mouseButton = n.getButton();
		if ( mouseButton != 1 ) {
			Logger.log("Mouse : " + mouseButton + " @" + n.getPoint().toString()); 
			
		}
		
		if ( mouseButton == 2 ) { 
			
			int x = n.getPoint().x;
			int y = n.getPoint().y;
			Logger.log("Clicker position updated to (" + x + ", " + y + ")");
			controller.clickerSetPosition(x, y);
		}

	}
	
	@Override
	public void nativeMousePressed( NativeMouseEvent n ) {
		
	}
	
	@Override
	public void nativeMouseReleased( NativeMouseEvent n ) {
		
	}
}
