package application;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
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
	 */
	@Override
	public void nativeMouseClicked( NativeMouseEvent n ) {
		int mouseButton = n.getButton();
		if ( mouseButton != 1 ) {
			Logger.log("Mouse : " + mouseButton + " @" + n.getPoint().toString()); 
			
		}

	}
	
	@Override
	public void nativeMousePressed( NativeMouseEvent n ) {
		
	}
	
	@Override
	public void nativeMouseReleased( NativeMouseEvent n ) {
		
	}
}
