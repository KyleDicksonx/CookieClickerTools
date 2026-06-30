package application;
	


import com.github.kwhat.jnativehook.GlobalScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	//stage setup
		private static final Color BG_COLOR = Color.BURLYWOOD; //BLANCHEDALMOND, 
		//title bar
			private static final String SCENE_TITLE = "Cookie Clicker Tools v0.0";
			private static final String ICON_PATH = "cookie-cookie-from-cookie-clicker-11563236737bj2c4xyraj-3743965712.png";
		//stage size
			private static final int STAGE_WIDTH = 620;
			private static final int STAGE_HEIGHT = 420;
		//stage start pos
			private static final int STAGE_X = 200;
			private static final int STAGE_Y = 200;
		//fullscreen configs
			private static final boolean IS_FULLSCREEN = false;
			private static final String FS_EXIT_HINT = "Press esc to exit.";
			private static final KeyCombination FS_EXIT_KEY = KeyCombination.valueOf("esc");
	//default text configs
		//private static final int FONT_SIZE = 25;
		//private static final Font DEFAULT_FONT = Font.font("Arial", FONT_SIZE);

		
	
	@Override
	@OnStartup
	/**
	 * 
	 * @param stage An object of type Stage from JavaFX
	 */
	public void start(Stage stage) {
		
		try {
			//BorderPane root = new BorderPane();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("something.fxml"));
			Parent root = loader.load();
			Controller controller = loader.getController();
			
			//global input handlers 
				final KeyListener KEY_LISTENER = new KeyListener(controller);
				final MouseListener MOUSE_LISTENER = new MouseListener(controller);
			
			Scene scene = new Scene(root,BG_COLOR);
			
			//Mouse and key Listeners
				
				nativeHookLogFixer();
				GlobalScreen.registerNativeHook();
				GlobalScreen.addNativeKeyListener(KEY_LISTENER);
				GlobalScreen.addNativeMouseListener(MOUSE_LISTENER);
			
			stage.setTitle(SCENE_TITLE);
			
			//sets the image icon in the top left corner for the stage
			Image icon = new Image(ICON_PATH);
			stage.getIcons().add(icon);
			
			//width and height
			stage.setWidth(STAGE_WIDTH);
			stage.setHeight(STAGE_HEIGHT);
			stage.setResizable(false);
			
			//set window start location
			//without this it defaults to the middle
			stage.setX(STAGE_X);
			stage.setY(STAGE_Y);
			
			stage.setFullScreen(IS_FULLSCREEN);
			stage.setFullScreenExitHint(FS_EXIT_HINT);
			stage.setFullScreenExitKeyCombination(FS_EXIT_KEY);
			
			
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			//add scene to the stage
			stage.setScene(scene);
			
			//display the window
			stage.show();
		} catch ( LoadException e ) {
			System.out.println(e);
			Logger.log(e.getStackTrace().toString());
		} catch ( Exception e ) {
			Logger.log(e.getStackTrace().toString());
		} 
	}
	
	/**
	 * This is called when the stage is closed. 
	 * This does not get marked with @OnShutdown, everything it call does.
	 */
	public void stop() {
		if ( Logger.getFileWriter() != null ) 
			Logger.closeFile();
	}
	
	public static void main(String[] args) {
		//opens the file for the logger and builds the header
		Logger.buildLogger();
		//launch calls start()
		launch(args);
	}
	
	/**
	 * Fixes the JNativeHook Logger without importing java.util.logging.Logger.
	 * I already made my own Logger class this will conflict with.
	 * Stops JNativeHook from filling the console with "WARNING: process_button_pressed [332]: Click count overflow detected!"
	 * after a small click limit is reached. 
	 */
	private static void nativeHookLogFixer() {
		java.util.logging.Logger nativeHookLogFixer = 
				java.util.logging.Logger.getLogger( GlobalScreen.class.getPackage().getName() );
		nativeHookLogFixer.setLevel(java.util.logging.Level.SEVERE);
	}
}

