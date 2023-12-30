module POSImpulse {
	requires javafx.controls;
	requires java.base;
	requires javafx.graphics;
	requires javafx.fxml;
	requires java.desktop;
	requires javafx.base;
	requires com.jfoenix;
	requires javafx.media;
	requires java.sql;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.components.inputform to javafx.fxml;
	opens application.components.datagrid to javafx.fxml;
	opens application.screens.profile to javafx.fxml;
	opens application.screens.billing to javafx.fxml;
	opens application.screens.purchases to javafx.fxml;
	opens application.models.entities to javafx.base;
	
	exports application;
	exports application.screens.login;
	exports application.screens.signup;
	exports application.screens.sidebar;
	exports application.screens.dashboard;
	exports application.components.datagrid;
	exports application.components.inputform;
	exports application.screens.profile;
	exports application.screens.purchases;
	exports application.screens.billing;
	exports application.screens.reports;
}
