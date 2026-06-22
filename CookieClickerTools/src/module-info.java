module CookieClickerTools {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires com.github.kwhat.jnativehook;
	
	opens application to javafx.graphics, javafx.fxml;
}
