package application;

/**
 * This exception exists to act as a kill switch.
 * Because this program is an auto clicker is is essential to 
 * have a sure fire way to kill it when need be. 
 * This exception will never be caught or handled,
 * it is designed to work as a last resort if the toggle
 * button fails and the mouse is locked in place by a position click.
 */
public class KillSwitch extends Exception{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @param s
	 */
	public KillSwitch(String s) {
		System.out.println(s);
	}
}
