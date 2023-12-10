package application.screens.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DashboardController implements Initializable 
{
	
	@SuppressWarnings("exports")
	@FXML
	public PieChart pieChart;
	
	@FXML
	public ComboBox<String> comboBox;
	
	@SuppressWarnings("exports")
	@FXML
	public Label sales, expenses;
	
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	
	@FXML
	public BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
	
	XYChart.Series<String, Number> series = new XYChart.Series<>();
	
	private String[] names = {"Near Expiry", "Expired Products", "Low Stock", "Check Profit"};

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		series.setName("Monthly Sales");

	    // Add data points to the series
	    series.getData().add(new XYChart.Data<>("Category 1", 20));
	    series.getData().add(new XYChart.Data<>("Category 2", 40));
	    series.getData().add(new XYChart.Data<>("Category 3", 15));
	    series.getData().add(new XYChart.Data<>("Category 4", 30));

	    // Add the series to the chart
	    barChart.getData().add(series);
		
		comboBox.getItems().addAll(names);
		ObservableList<PieChart.Data> piecChartData = FXCollections.observableArrayList(
				new PieChart.Data("Sales", 10000),
				new PieChart.Data("Expenses", 1500)
				);
		pieChart.setData(piecChartData);
	}
	
	public void setSalesAndExpenses()
	{
		ObservableList<Data> data = pieChart.getData();
		sales.setText(Double.toString(data.get(0).getPieValue()));
		expenses.setText(Double.toString(data.get(1).getPieValue()));
	}
}
