module POSImpulse {
	requires javafx.controls;
	requires java.base;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application;
	exports application.screens.login;
}
