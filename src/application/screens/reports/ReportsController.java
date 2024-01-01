package application.screens.reports;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JFrame;

import application.utils.backendUtils.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class ReportsController implements Initializable 
{
	
	@SuppressWarnings("exports")
	@FXML
	public Label reportText;
	
	@SuppressWarnings("exports")
	@FXML
	public DatePicker fromDate, toDate;
	
	@SuppressWarnings("exports")
	@FXML
	public MediaView mediaView;
	
	@FXML
	public ComboBox<String> comboBox;
	
	@SuppressWarnings("exports")
	@FXML
	public Pane searchbarPane;
	
	@SuppressWarnings("exports")
	@FXML
	public AnchorPane reportControls;
	
	private MediaPlayer mediaPlayer;
	
	JasperReport jreport;
	JasperPrint jprint;
	JasperViewer jviewer;
	
	private ObservableList<String> names = FXCollections.observableArrayList("Sales Report", "Purcahse Report", "Expenses Report");
	
	public void changeComboBoxOption()
	{
		if(fromDate.getValue() == null || toDate.getValue() == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Dates not selected");
        	alert.setContentText("Please select the dates first to fetch the report!");
        	alert.show();
		}
		else
		{
			String fromDateValue = fromDate.getValue().toString(), toDateValue = toDate.getValue().toString();
			if(comboBox.getValue().equals("Sale History") || comboBox.getValue().equals("Purchase History"))
			{
				searchbarPane.setStyle("visibility: visible;");
				reportControls.setLayoutY(365);
			}
			else
			{
				searchbarPane.setStyle("visibility: hidden;");
				reportControls.setLayoutY(290);
				if(comboBox.getValue().equals("Sales Report"))
				{
					generateReport("salesReport", fromDateValue, toDateValue);
				}
				else if(comboBox.getValue().equals("Purchase Report"))
				{
					generateReport("purchaseReport", fromDateValue, toDateValue);
				}
				else if(comboBox.getValue().equals("Expenses Report"))
				{
					generateReport("expensesReport", fromDateValue, toDateValue);
				}
			}
		}
	}
	
	public void generateReport(String fileName, String startDate, String endDate)
	{
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("startDate", startDate);
		parameters.put("endDate", endDate);

		try {
		    JasperViewer jviewer = new JasperViewer(
		            JasperFillManager.fillReport(
		                    JasperCompileManager.compileReport("C:\\Users\\ALI\\eclipse-workspace\\POSImpulse\\src\\application\\utils\\" + fileName + ".jrxml"),
		                    parameters,
		                    DatabaseConnection.connect()),
		            false);

		    JFrame frame = new JFrame("Your Report Title");
		    frame.getContentPane().add(jviewer.getContentPane());
		    frame.setSize(800, 600);
		    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		    frame.setVisible(true);
		} 
		catch (Exception e) 
		{
		    e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		searchbarPane.setStyle("visibility: hidden;");
		reportControls.setLayoutY(290);
		
		try
		{
			mediaPlayer = new MediaPlayer(new Media(new File("C:\\Users\\ALI\\eclipse-workspace\\POSImpulse\\src\\application\\assets\\reportsBg.mp4").toURI().toString()));
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.setCycleCount(1000);
			mediaPlayer.play();
			
			reportText.setText("G e t  a  p r o p e r  i n s i g h t  o f  y o u r  o w n  c h o i c e");
			
			comboBox.setItems(names);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
