module POSImpulse {
	requires javafx.controls;
	requires java.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires com.jfoenix;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.components.inputform to javafx.fxml;
	opens application.screens.profile to javafx.fxml;
	
	exports application;
	exports application.screens.login;
	exports application.screens.signup;
	exports application.screens.sidebar;
	exports application.components.datagrid;
	exports application.components.inputform;
	exports application.screens.profile;
}