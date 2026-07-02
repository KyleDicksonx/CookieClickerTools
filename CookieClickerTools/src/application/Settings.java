package application;

public class Settings {
	
	private static boolean allowMouseClicks = true;
	
	
	
	
	/**
	 * Toggle allowMouseClicks
	 */
	public static void toggleAllowMouseClicks() {
		allowMouseClicks = !allowMouseClicks;
	}
	
	/**
	 * Resets all settings to their default value
	 */
	public static void resetToDefaults() {
		//TODO make sure all settings are reset here
		allowMouseClicks = true;
	}
	
	
	
	
	
	
	
	
	
	
	//getters
		public static boolean getAllowMouseClicks() {
			return allowMouseClicks;
		}
		
	//settings
		public static void setAllowMouseClicks( boolean b ) {
			allowMouseClicks = b;
		}	
}
