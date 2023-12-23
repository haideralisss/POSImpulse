package application.screens.reports;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

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
	
	private MediaPlayer mediaPlayer;
	
	private String[] names = {"Sales Report", "Sales Return Report", "Purcahse Report", "Purchase Return Report",
			"Loose Purcahse Report", "Expenses Report"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		try
		{
			mediaPlayer = new MediaPlayer(new Media(new File("C:\\Users\\ALI\\eclipse-workspace\\POSImpulse\\src\\application\\assets\\reportsBg.mp4").toURI().toString()));
			mediaView.setMediaPlayer(mediaPlayer);
			mediaPlayer.setCycleCount(1000);
			mediaPlayer.play();
			
			reportText.setText("G e t  a  p r o p e r  i n s i g h t  o f  y o u r  o w n  c h o i c e");
			
			comboBox.getItems().addAll(names);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
