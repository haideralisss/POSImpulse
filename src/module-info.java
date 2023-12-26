module POSImpulse {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application.screens.sidebar;
	exports application.screens.dashboard;
	exports application.screens.reports;
}
