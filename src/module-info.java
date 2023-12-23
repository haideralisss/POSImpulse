module POSImpulse {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.media;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports application.screens.sidebar;
	exports application.screens.dashboard;
	exports application.screens.reports;
}
