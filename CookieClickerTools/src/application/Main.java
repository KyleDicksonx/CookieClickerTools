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







/** Replaced with JNativeHook for global hotkey access
scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

	@Override
	public void handle(KeyEvent k){
		// TODO Auto-generated method stub
		KeyCode key = k.getCode();
		//System.out.println(key);
		switch ( key ) {
			case ESCAPE -> {
				controller.getClicker().setClicking(false);
				System.out.println("Clicking disabled.");
			}
			default -> {
				if (key == controller.getClicker().getToggleKey()) {
					controller.getClicker().toggleClicking();
					System.out.println("chicken butt");
				} else {
					System.out.println("Event Handler: No action defined for key " + key);
				}
				
			}
		}
	}
	
});
*/



//start examples ====================================
/**
//add text ----------------------------------------------------------------
Text text1 = new Text();
text1.setText("Text1");
text1.setX(50);
text1.setY(50);
text1.setFont(DEFAULT_FONT);
text1.setFill(FONT_COLOR);
//add text to root node
	root.getChildren().add(text1);

	
//add line ---------------------------------------------------------------
Line line1 = new Line();
//needs starting cords and ending cords
line1.setStartX(0);
line1.setStartY(65);
line1.setEndX(STAGE_HEIGHT);
line1.setEndY(65);

line1.setStrokeWidth(3); //default 1
line1.setStroke(Color.GREEN);
//line1.setOpacity(0.5);//scale of 0-1
//line1.setRotate(45); //allows you to take the same line and rotate it later, could be used for a loading spinner

root.getChildren().add(line1);


//add rectangle ------------------------------------------------------
Rectangle rec1 = new Rectangle();
rec1.setX(100);
rec1.setY(100);
rec1.setWidth(50);
rec1.setHeight(100);

rec1.setFill(Color.DARKBLUE);
rec1.setStrokeWidth(1); //outline width
rec1.setStroke(Color.WHITE); //outline color

root.getChildren().add(rec1);



//triangles (uses polygons) ----------------------------------------------------
Polygon trigon1 = new Polygon();
//each pair of inputs is a cord, newlines help divide the cords and make them readable
trigon1.getPoints().setAll(
		200.0,	200.0,
		300.0, 	300.0,
		200.0,	300.0 );
trigon1.setFill(Color.BLACK);

root.getChildren().add(trigon1);

//Circle ----------------------------------------------------------------------

Circle circle1 = new Circle();
circle1.setCenterX(500);
circle1.setCenterY(500);
circle1.setRadius(150);

circle1.setFill(Color.ORANGE);

root.getChildren().add(circle1);


//images ----------------------------------------------------------------------
Image image1 = new Image("yellow-smiley-face-clip-art-6.png");
ImageView imageView1 = new ImageView(image1);
imageView1.setX(0); 
//the x pos is not correct for some reason currently unknown to me
//the image is almost in the middle of the screen when x = 0
imageView1.setY(400);
imageView1.setScaleX(0.2);
imageView1.setScaleY(0.2);

root.getChildren().add(imageView1);
//-----------------------------------------------------------------------------
*/
//end examples ===============================

