package application.screens.dashboard;

import java.net.URL;
import java.util.ResourceBundle;

import application.models.entities.Bills;
import application.models.repositories.BillsRepo;
import application.models.repositories.ExpensesRepo;
import application.models.repositories.StockRepo;
import application.utils.backendUtils.NumberFormatter;
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
	public Label dataGridHeading1, dataGridHeading2, sales, expenses, 
	todaySalesLabel, monthSalesLabel, stockWorthLabel, 
	monthExpensesLabel;
	
	CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
	
	@FXML
	public BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
	
	XYChart.Series<String, Number> series = new XYChart.Series<>();
	
	BillsRepo billsRepo = new BillsRepo();
	StockRepo stockRepo = new StockRepo();
	ExpensesRepo expensesRepo = new ExpensesRepo();
	private String[] names = {"Low Stock", "Check Profit"};
	private String monthlySales = "", monthlyExpenses = "";

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		monthlyExpenses = expensesRepo.fetchMonthExpenses();
		monthlySales = billsRepo.fetchMonthSales();
		comboBox.getItems().addAll(names);
		setAnalysisData();
		setMonthlyExpensesGraph();
		setSalesAndExpensesGraph();
	}
	
	public void setAnalysisData()
	{
		todaySalesLabel.setText("Rs. " + billsRepo.fetchTodaySales());
		monthSalesLabel.setText("Rs. " + monthlySales);
		stockWorthLabel.setText("Rs. " + stockRepo.fetchStockWorth());
		monthExpensesLabel.setText("Rs. " + monthlyExpenses);
	}
	
	public void changeValueAndGetData()
	{
		if(comboBox.getValue().equals("Low Stock"))
		{
			dataGridHeading1.setText("Low");
			dataGridHeading2.setText("Stock");
			dataGridHeading2.setLayoutX(60);
		}
		else
		{
			dataGridHeading1.setText("Check");
			dataGridHeading2.setText("Profit");
			dataGridHeading2.setLayoutX(79);
		}
	}
	
	public void setMonthlyExpensesGraph()
	{
		series.setName("Monthly Sales");
		for(Bills bill : billsRepo.fetchMonthSalesReport())
		{
			series.getData().add(new XYChart.Data<>(bill.getBillsDate(), bill.getAmountPaid()));
		}
	    barChart.getData().add(series);
	}
	
	public void setSalesAndExpensesGraph()
	{
		ObservableList<PieChart.Data> piecChartData = FXCollections.observableArrayList(
				new PieChart.Data("Sales", Double.parseDouble(monthlySales)),
				new PieChart.Data("Expenses", Double.parseDouble(monthlyExpenses))
				);
		pieChart.setData(piecChartData);
	}
	
	public void showSalesAndExpensesGraphData()
	{
		ObservableList<Data> data = pieChart.getData();
		sales.setText("Rs. " + NumberFormatter.format(data.get(0).getPieValue()));
		expenses.setText("Rs. " + NumberFormatter.format(data.get(1).getPieValue()));
	}
}
