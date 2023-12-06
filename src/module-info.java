module POSImpulse {
	requires javafx.controls;
	requires java.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application;
	exports application.screens.login;
	exports application.screens.signup;
	exports application.screens.sidebar;
}
