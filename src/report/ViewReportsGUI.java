package report;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import networkGUI.StoreManagerGUI;
import prototype.FormController;
import report.Report.Quarterly;
import serverAPI.Response;
import user.User;

/**
 * GUI allows to view income, order or survey report from specific quarterly and
 * year
 */
public class ViewReportsGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;

	// Store manager's store ID
	private long managersStoreID;
	
	//List of report's types for combo boxes
	ObservableList<String> reportTypesList = FXCollections.observableArrayList("Income", "Order", "Survey", "Complaint");
	
	//List of years for combo boxes
	ObservableList<String> yearsList = FXCollections.observableArrayList("2017", "2018");
	
	//List of quarterlies for combo boxes
	ObservableList<String> quarterliesList = FXCollections.observableArrayList();

	@FXML
	private Label quarterlyLbl;

	@FXML
	private ComboBox<String> yearComboBox;

	@FXML
	private Button viewReportBtn;

	@FXML
	private Label windowTitleLbl;

	@FXML
	private BarChart<String, Number> complaintReportBarChart;

	@FXML
	private TextArea reportViaTextArea;

	@FXML
	private Label instructionLbl;

	@FXML
	private ComboBox<String> reportTypeComboBox;

	@FXML
	private ComboBox<String> quarterlyComboBox;

	@FXML
	private Label yearLbl;

	@FXML
	private Label reportTypeLbl;

	@FXML
	private Button backBtn;

	
    /**
     * Initializes the combo box of report types, quarterlies and years.
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
    	setComboBoxes();
    	reportViaTextArea.setEditable(false);

    }
    
	/**
	 * Displays income, order or survey report in text area or complaint report in graph
	 * @param event - "View report" button is pressed
	 */

	@SuppressWarnings("rawtypes")
	@FXML
	void onViewReport(ActionEvent event) {
		
		if(quarterlyComboBox.getValue() != null && yearComboBox.getValue() != null && reportTypeComboBox.getValue() != null)
		{
			String quarterly = quarterlyComboBox.getValue();
			String year = yearComboBox.getValue();
			reportViaTextArea.setText("");
			complaintReportBarChart.getData().clear();
			complaintReportBarChart.layout();
			
			switch (reportTypeComboBox.getValue())
			{
				case "Income":
				{
	
					ReportController.getReport("IncomeReport", Quarterly.valueOf(quarterly.toUpperCase()), year, managersStoreID, client);
					
					try
			    	{
			    		synchronized(this)
			    		{
			    			// wait for server response
			    			this.wait();
			    		}
			    	
			    		if (replay == null)
			    			return;
			    		
			    	// show success 
			    	if (replay.getType() == Response.Type.SUCCESS)
			    	{
			    		IncomeReport incomeReport = (IncomeReport)((ArrayList<?>) replay.getMessage()).get(0);
			    		
			    		fillIncomeReportsTextArea(""+incomeReport.getQuarterly(),""+incomeReport.getYear(),incomeReport.getIncomeAmount());
			    		
			    	}
			    	else
			    	{
			        	// show failure  
			    		Alert alert = new Alert(AlertType.ERROR, "Such income's report doesn't exists!", ButtonType.OK);
			    		alert.showAndWait();
			    	}
			    	
			    	}catch(InterruptedException e) {}
					
					replay = null;
				}
				break;
				
				case "Order":
				{
					ReportController.getReport("OrderReport", Quarterly.valueOf(quarterly.toUpperCase()), year, managersStoreID, client);
					
					try
			    	{
			    		synchronized(this)
			    		{
			    			// wait for server response
			    			this.wait();
			    		}
			    	
			    		if (replay == null)
			    			return;
			    		
			    	// show success 
			    	if (replay.getType() == Response.Type.SUCCESS)
			    	{
			    		OrderReport orderReport = (OrderReport)((ArrayList<?>) replay.getMessage()).get(0);
			    		
			    		long totalOrdersAmount = orderReport.getTotalOrdersAmount();
			    		long bouquetAmount = orderReport.getBouquetAmount();
			    		long brideBouquetAmount = orderReport.getBrideBouquetAmount();
			    		long flowerPotAmount = orderReport.getFlowerPotAmount();
			    		long flowerAmount = orderReport.getFlowerAmount();		//Maybe need to be removed
			    		long plantAmount = orderReport.getPlantAmount();
			    		
			    		fillOrderReportsTextArea(""+orderReport.getQuarterly(), ""+orderReport.getYear(), totalOrdersAmount, bouquetAmount, brideBouquetAmount,
			    				flowerPotAmount, flowerAmount, plantAmount);
			    		
			    	}
			    	else
			    	{
			        	// show failure  
			    		Alert alert = new Alert(AlertType.ERROR, "Such orders' report doesn't exists!", ButtonType.OK);
			    		alert.showAndWait();
			    	}
			    	
			    	}catch(InterruptedException e) {}
					
					replay = null;
				}
				break;
				
				case "Survey":
				{
					ReportController.getReport("SurveyReport", Quarterly.valueOf(quarterly.toUpperCase()), year, managersStoreID, client);
					
					try
			    	{
			    		synchronized(this)
			    		{
			    			// wait for server response
			    			this.wait();
			    		}
			    	
			    		if (replay == null)
			    			return;
			    		
			    	// show success 
			    	if (replay.getType() == Response.Type.SUCCESS)
			    	{
			    		SurveyReport surveyReport = (SurveyReport)((ArrayList<?>) replay.getMessage()).get(0);
			    		
			    		long firstSurveyAverageResult = surveyReport.getFirstSurveyAverageResult();
			    		long secondSurveyAverageResult = surveyReport.getSecondSurveyAverageResult();
			    		long thirdSurveyAverageResult = surveyReport.getThirdSurveyAverageResult();
			    		long fourthSurveyAverageResult = surveyReport.getFourthSurveyAverageResult();
			    		long fifthSurveyAverageResult = surveyReport.getFifthSurveyAverageResult();
			    		long sixthSurveyAverageResult = surveyReport.getSixthSurveyAverageResult();
			    		
			    		fillSurveyReportsTextArea(""+surveyReport.getQuarterly(), surveyReport.getYear(), firstSurveyAverageResult, secondSurveyAverageResult,
			    				thirdSurveyAverageResult, fourthSurveyAverageResult, fifthSurveyAverageResult, sixthSurveyAverageResult);
			    		
			    	}
			    	else
			    	{
			        	// show failure  
			    		Alert alert = new Alert(AlertType.ERROR, "Such surveys' report doesn't exists!", ButtonType.OK);
			    		alert.showAndWait();
			    	}
			    	
			    	}catch(InterruptedException e) {}
					
					replay = null;
				}
				break;
				
				case "Complaint":
				{
					ReportController.getReport("ComplaintReport", Quarterly.valueOf(quarterly.toUpperCase()), year, managersStoreID, client);
					
					try
			    	{
			    		synchronized(this)
			    		{
			    			// wait for server response
			    			this.wait();
			    		}
			    	
			    		if (replay == null)
			    			return;
			    		
			    	// show success 
			    	if (replay.getType() == Response.Type.SUCCESS)
			    	{
	
			    		ComplaintReport complaintReport = (ComplaintReport)((ArrayList<?>) replay.getMessage()).get(0);
			    	    final String first = "1st";
			    	    final String second = "2nd";
			    	    final String third = "3rd";
			    		
			    		long firstMonthHandledComplaintsAmount = complaintReport.getFirstMonthHandledComplaintsAmount();
			    		long firstMonthPendingComplaintsAmount = complaintReport.getFirstMonthPendingComplaintsAmount();
			    		long secondMonthHandledComplaintsAmount = complaintReport.getSecondMonthHandledComplaintsAmount();
			    		long secondMonthPendingComplaintsAmount = complaintReport.getSecondMonthPendingComplaintsAmount();
			    		long thirdMonthHandledComplaintsAmount = complaintReport.getThirdMonthHandledComplaintsAmount();
			    		long thirdMonthPendingComplaintsAmount = complaintReport.getThirdMonthPendingComplaintsAmount();
			    	 
			    	    XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
			    	    series1.setName("Handled");       
			    	    series1.getData().add(new XYChart.Data<String, Number>(first, (Number)firstMonthHandledComplaintsAmount));
			    	    series1.getData().add(new XYChart.Data<String, Number>(second, (Number)secondMonthHandledComplaintsAmount));
			    	    series1.getData().add(new XYChart.Data<String, Number>(third, (Number)thirdMonthHandledComplaintsAmount));
	  
			    	      
			    	    XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
			    	    series2.setName("Pending");
			    	    series2.getData().add(new XYChart.Data<String, Number>(first, (Number)firstMonthPendingComplaintsAmount));
			    	    series2.getData().add(new XYChart.Data<String, Number>(second, (Number)secondMonthPendingComplaintsAmount));
			    	    series2.getData().add(new XYChart.Data<String, Number>(third, (Number)thirdMonthPendingComplaintsAmount));
			    	    
			    	    complaintReportBarChart.getData().addAll(series1, series2);
			    	}
			    	else
			    	{
			        	// show failure  
			    		Alert alert = new Alert(AlertType.ERROR, "Such complaints' report doesn't exists!", ButtonType.OK);
			    		alert.showAndWait();
			    	}
			    	
			    	}catch(InterruptedException e) {}
					
					replay = null;
				}
				break;
			}
		}
		else
		{
	    	// show warning  
			Alert alert = new Alert(AlertType.WARNING, "Please select all attributes: report type, quarterly and year.", ButtonType.OK);
			alert.showAndWait();
		}
	}

	/**
	 * Clears all the fields and returns to previous GUI window
	 * @param event - "Back" button is pressed
	 */
	@FXML
	void onBack(ActionEvent event) {
		
		complaintReportBarChart.getData().clear();
		reportViaTextArea.setText("");
		yearComboBox.setValue(null);
		reportTypeComboBox.setValue(null);
		quarterlyComboBox.setValue(null);
		
		StoreManagerGUI storeManageGui = (StoreManagerGUI) parent;
		client.setUI(storeManageGui);
		FormController.primaryStage.setScene(parent.getScene());

	}
	
	/**
	 * Displays text are or bar chart according to report's type.
	 * @param event - report type is selected from combo box
	 */
	
    @FXML
    void onReportTypeSelection(ActionEvent event) {
    	
    	if(reportTypeComboBox.getValue().equals("Complaint"))
    	{
    		reportViaTextArea.setVisible(false);
    		complaintReportBarChart.setVisible(true);
    	}
    	else
    	{
    		complaintReportBarChart.setVisible(false);
    		reportViaTextArea.setVisible(true);
    	}
    	
    	reportViaTextArea.setText("");
    	quarterlyComboBox.setValue(null);
    	yearComboBox.setValue(null);
    	complaintReportBarChart.getData().clear();
    }

	@Override
	public void display(Object message) {

		System.out.println(message.toString());
		System.out.println(message.getClass().toString());

		Response replay = (Response) message;
		this.replay = replay;

		synchronized (this) {
			this.notify();
		}

	}

	@Override
	public void onSwitch(Client newClient) {

	}

	public void setManagersStoreID(long managersStoreID) {
		this.managersStoreID = managersStoreID;
	}
	
	/**
	 * Receives message that will be splitted by "_" symbol and transformed to user friendly view.
	 * For example: "CREDIT_CARD" is transformed to "Credit card"
	 * @param stringToSplit - message to be splitted by specific symbol
	 * @return
	 */
	
	public String handleSplittedStringFromDataBase(String stringToSplit)
	{
		String [] splittedString;
		splittedString = stringToSplit.split("_");
		
		String tempString = "";
		
		for(String splitted : splittedString)
		{
			splitted = splitted.toLowerCase();
			
			if(!tempString.equals(""))
				tempString = tempString + " ";
			else
				splitted = Character.toUpperCase(splitted.charAt(0)) + splitted.substring(1);
			
			tempString = tempString + splitted;
		}
		
		return tempString;
	}
	
	/**
	 * Filling combo boxes according to enum declarations
	 */
	
	private void setComboBoxes()
	{
		String temporaryString = "";
		
    	ArrayList<String> quarterlies = new ArrayList<String>();
    	

    	for(Quarterly quarterly : Quarterly.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+quarterly);
    		quarterlies.add(""+temporaryString);
    	}
    	
    	quarterliesList.addAll(quarterlies);
    	quarterlyComboBox.setItems(quarterliesList);
    	
    	yearComboBox.setItems(yearsList);
    	
    	reportTypeComboBox.setItems(reportTypesList);

	}
	
	private void fillIncomeReportsTextArea(String quarterly, String year, float incomeAmount)
	{
		String messageToDisplay = "          *****INCOME REPORT*****\n\n"
				+ "This is the income report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n"
						+ "- The income amount is: " + incomeAmount + ".";
		
		reportViaTextArea.setText(messageToDisplay);
	}
	
	private void fillOrderReportsTextArea(String quarterly, String year, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long flowerAmount, long plantAmount)
	{
		String messageToDisplay = "          *****ORDER REPORT*****\n\n"
				+ "This is the order report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n"
						+ "- Total amount of ordered products: " + totalOrdersAmount + ".\n"
								+ "In more details:\n"
								+ "- The amount of ordered bouquets: " + bouquetAmount + ".\n"
								+ "- The amount of ordered bride bouquets: " + brideBouquetAmount + ".\n"
								+ "- The amount of ordered flower pots: " + flowerPotAmount + ".\n"
								+ "- The amount of ordered plants: " + plantAmount + ".\n"
								+ "- The amount of ordered customed items: " + flowerAmount + ".";
		
		reportViaTextArea.setText(messageToDisplay);
	}

	private void fillSurveyReportsTextArea(String quarterly, String year, long firstSurveyAverageResult,
			long secondSurveyAverageResult, long thirdSurveyAverageResult, long fourthSurveyAverageResult,
			long fifthSurveyAverageResult, long sixthSurveyAverageResult)
	{
		String messageToDisplay = "          *****SURVEY REPORT*****\n\n"
				+ "This is the survey report from " + quarterly.toLowerCase() + " quarterly of a year" + year + ".\n"
						+ "This report contains average results for every question:\n"
						+ "- Average result of the first question: " + firstSurveyAverageResult + ".\n"
						+ "- Average result of the second question: " + secondSurveyAverageResult + ".\n"
						+ "- Average result of the third question: " + thirdSurveyAverageResult + ".\n"
						+ "- Average result of the fourth question: " + fourthSurveyAverageResult + ".\n"
						+ "- Average result of the fifth question: " + fifthSurveyAverageResult + ".\n"
						+ "- Average result of the sixth question: " + sixthSurveyAverageResult + ".";
		
		reportViaTextArea.setText(messageToDisplay);
	}
}
