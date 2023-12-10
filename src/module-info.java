module POSImpulse {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application.screens.sidebar;
	exports application.screens.dashboard;
}
