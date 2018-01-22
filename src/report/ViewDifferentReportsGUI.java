package report;

import java.util.ArrayList;
import java.util.HashMap;

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
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import networkGUI.StoreManagerGUI;
import prototype.FormController;
import report.OrderReport.Quarterly;
import serverAPI.Response;
import user.User;

/**
 * GUI allows to view income, order or survey report from specific quarterly and
 * year
 */
public class ViewDifferentReportsGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;
	
	//List of report's types for combo boxes
	ObservableList<String> reportTypesList = FXCollections.observableArrayList("Income", "Order", "Survey", "Complaint");
	
	//List of years for combo boxes
	ObservableList<String> yearsList = FXCollections.observableArrayList("2017", "2018");
	
	//List of quarterlies for combo boxes
	ObservableList<String> quarterliesList = FXCollections.observableArrayList();
	
	//List of stores' names for the combo box 
	ObservableList<String> storesNamesList = FXCollections.observableArrayList();
	
	//Hash map for stores: key - store ID, value - store name
	private HashMap<String, Long> stores;

	@FXML
    private ComboBox<String> firstQuarterlyComboBox;

    @FXML
    private Button viewReportBtn;

    @FXML
    private Label windowTitleLbl;

    @FXML
    private BarChart<String, Number> secondComplaintReportBarChart;

    @FXML
    private TextArea secondReportViaTextArea;

    @FXML
    private BarChart<String, Number> firstComplaintReportBarChart;

    @FXML
    private Label secondYearLbl;

    @FXML
    private Label firstSelectionLbl;

    @FXML
    private Separator verticalSeparator;

    @FXML
    private Label firstYearLbl;

    @FXML
    private TextArea firstReportViaTextArea;

    @FXML
    private ComboBox<String> firstYearComboBox;

    @FXML
    private Separator horizontalSeparator;

    @FXML
    private ComboBox<String> secondYearComboBox;

    @FXML
    private Button backBtn;

    @FXML
    private Label secondSelectionLbl;

    @FXML
    private Label instructionLbl;

    @FXML
    private ComboBox<String> reportTypeComboBox;

    @FXML
    private Label firstQuarterlyLbl;

    @FXML
    private Label secondQuarterlyLbl;

    @FXML
    private ComboBox<String> secondQuarterlyComboBox;
    
    @FXML
    private Label firstStoreLbl;
    
    @FXML
    private Label secondStoreLbl;
    
    @FXML
    private ComboBox<String> firstStoreComboBox;
    
    @FXML
    private ComboBox<String> secondStoreComboBox;

	
    /**
     * Initializes the combo box of report types, quarterlies and years.
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
    	stores = new HashMap<String, Long>();
    	setComboBoxes();
    	firstReportViaTextArea.setEditable(false);
    	secondReportViaTextArea.setEditable(false);

    }
    
	/**
	 * Displays income, order or survey report in text area or complaint report in graph
	 * @param event - "View report" button is pressed
	 */

	@FXML
	void onViewReport(ActionEvent event) {
		
		long firstStore = Long.parseLong(firstStoreComboBox.getValue());
		long secondStore = Long.parseLong(secondStoreComboBox.getValue());
		String firstQuarterly = firstQuarterlyComboBox.getValue();
		String secondQuarterly = secondQuarterlyComboBox.getValue();
		String firstYear = firstYearComboBox.getValue();
		String secondYear = secondYearComboBox.getValue();
		String reportMessage;
		
		firstReportViaTextArea.setText("");
		secondReportViaTextArea.setText("");
		firstComplaintReportBarChart.getData().clear();
		firstComplaintReportBarChart.layout();
		secondComplaintReportBarChart.getData().clear();
		secondComplaintReportBarChart.layout();
		
		IncomeReport incomeReport;
		OrderReport orderReport;
		SurveyReport surveyReport;
		ComplaintReport complaintReport;
		
		
		switch (reportTypeComboBox.getValue())
		{
			case "Income":
			{

				ReportController.getReport("IncomeReport", IncomeReport.Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
				
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
		    		incomeReport = (IncomeReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		reportMessage = buildIncomeReportsTextAreaMessage(""+incomeReport.getQuarterly(),""+incomeReport.getYear(),incomeReport.getIncomeAmount());
		    		firstReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+firstStore+ " store such income's report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
				
				ReportController.getReport("IncomeReport", IncomeReport.Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
				
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
		    		incomeReport = (IncomeReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		reportMessage = buildIncomeReportsTextAreaMessage(""+incomeReport.getQuarterly(),""+incomeReport.getYear(),incomeReport.getIncomeAmount());
		    		secondReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+secondStore+ " store such income's report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
			}
			break;
			
			case "Order":
			{
				ReportController.getReport("OrderReport", IncomeReport.Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
				
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
		    		orderReport = (OrderReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		long totalOrdersAmount = orderReport.getTotalOrdersAmount();
		    		long bouquetAmount = orderReport.getBouquetAmount();
		    		long brideBouquetAmount = orderReport.getBrideBouquetAmount();
		    		long flowerPotAmount = orderReport.getFlowerPotAmount();
		    		long flowerAmount = orderReport.getFlowerAmount();		//Maybe need to be removed
		    		long plantAmount = orderReport.getPlantAmount();

		    		reportMessage = buildOrderReportsTextAreaMessage(""+orderReport.getQuarterly(), ""+orderReport.getYear(), totalOrdersAmount, bouquetAmount, brideBouquetAmount,
		    				flowerPotAmount, flowerAmount, plantAmount);
		    		firstReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+firstStore+ " store such orders' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
				
				ReportController.getReport("OrderReport", IncomeReport.Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
				
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
		    		orderReport = (OrderReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		long totalOrdersAmount = orderReport.getTotalOrdersAmount();
		    		long bouquetAmount = orderReport.getBouquetAmount();
		    		long brideBouquetAmount = orderReport.getBrideBouquetAmount();
		    		long flowerPotAmount = orderReport.getFlowerPotAmount();
		    		long flowerAmount = orderReport.getFlowerAmount();		//Maybe need to be removed
		    		long plantAmount = orderReport.getPlantAmount();

		    		reportMessage = buildOrderReportsTextAreaMessage(""+orderReport.getQuarterly(), ""+orderReport.getYear(), totalOrdersAmount, bouquetAmount, brideBouquetAmount,
		    				flowerPotAmount, flowerAmount, plantAmount);
		    		secondReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+secondStore+ " store such orders' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
			}
			break;
			
			case "Survey":
			{
				ReportController.getReport("SurveyReport", IncomeReport.Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
				
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
		    		surveyReport = (SurveyReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		long firstSurveyAverageResult = surveyReport.getFirstSurveyAverageResult();
		    		long secondSurveyAverageResult = surveyReport.getSecondSurveyAverageResult();
		    		long thirdSurveyAverageResult = surveyReport.getThirdSurveyAverageResult();
		    		long fourthSurveyAverageResult = surveyReport.getFourthSurveyAverageResult();
		    		long fifthSurveyAverageResult = surveyReport.getFifthSurveyAverageResult();
		    		long sixthSurveyAverageResult = surveyReport.getSixthSurveyAverageResult();
		    		
		    		reportMessage = buildSurveyReportsTextAreaMessage(""+surveyReport.getQuarterly(), surveyReport.getYear(), firstSurveyAverageResult, secondSurveyAverageResult,
		    				thirdSurveyAverageResult, fourthSurveyAverageResult, fifthSurveyAverageResult, sixthSurveyAverageResult);
		    		firstReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+firstStore+ " store such surveys' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
				
				ReportController.getReport("SurveyReport", IncomeReport.Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
				
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
		    		surveyReport = (SurveyReport)((ArrayList<?>) replay.getMessage()).get(0);
		    		
		    		long firstSurveyAverageResult = surveyReport.getFirstSurveyAverageResult();
		    		long secondSurveyAverageResult = surveyReport.getSecondSurveyAverageResult();
		    		long thirdSurveyAverageResult = surveyReport.getThirdSurveyAverageResult();
		    		long fourthSurveyAverageResult = surveyReport.getFourthSurveyAverageResult();
		    		long fifthSurveyAverageResult = surveyReport.getFifthSurveyAverageResult();
		    		long sixthSurveyAverageResult = surveyReport.getSixthSurveyAverageResult();
		    		
		    		reportMessage = buildSurveyReportsTextAreaMessage(""+surveyReport.getQuarterly(), surveyReport.getYear(), firstSurveyAverageResult, secondSurveyAverageResult,
		    				thirdSurveyAverageResult, fourthSurveyAverageResult, fifthSurveyAverageResult, sixthSurveyAverageResult);
		    		secondReportViaTextArea.setText(reportMessage);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+secondStore+ " store such surveys' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
			}
			break;
			
			case "Complaint":
			{
				ReportController.getReport("ComplaintReport", IncomeReport.Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
				
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

		    	    complaintReport = (ComplaintReport)((ArrayList<?>) replay.getMessage()).get(0);
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
		    	    
		    	    firstComplaintReportBarChart.getData().addAll(series1, series2);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+firstStore+ " store such complaints' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
				
				ReportController.getReport("ComplaintReport", IncomeReport.Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
				
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

		    	    complaintReport = (ComplaintReport)((ArrayList<?>) replay.getMessage()).get(0);
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
		    	    
		    	    secondComplaintReportBarChart.getData().addAll(series1, series2);
		    	}
		    	else
		    	{
		    		showErrorMessage("In "+secondStore+ " store such complaints' report doesn't exists!");
		    	}
		    	
		    	}catch(InterruptedException e) {}
				
				replay = null;
			}
			break;
		}
		
	}

	/**
	 * Clears all the fields and returns to previous GUI window
	 * @param event - "Back" button is pressed
	 */
	@FXML
	void onBack(ActionEvent event) {
		
//		complaintReportBarChart.getData().clear();
//		reportViaTextArea.setText("");
//		yearComboBox.setValue(null);
//		reportTypeComboBox.setValue(null);
//		quarterlyComboBox.setValue(null);
		
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
    		firstReportViaTextArea.setVisible(false);
    		secondReportViaTextArea.setVisible(false);
    		firstComplaintReportBarChart.setVisible(true);
    		secondComplaintReportBarChart.setVisible(true);
    	}
    	else
    	{
    		firstComplaintReportBarChart.setVisible(false);
    		secondComplaintReportBarChart.setVisible(false);
    		firstReportViaTextArea.setVisible(true);
    		secondReportViaTextArea.setVisible(true);
    	}
    	
    	firstReportViaTextArea.setText("");
    	secondReportViaTextArea.setText("");
    	firstComplaintReportBarChart.getData().clear();
    	secondComplaintReportBarChart.getData().clear();

    	firstQuarterlyComboBox.setValue(null);
    	secondQuarterlyComboBox.setValue(null);
    	firstYearComboBox.setValue(null);
    	secondYearComboBox.setValue(null);
    	firstStoreComboBox.setValue(null);
    	secondStoreComboBox.setValue(null);

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
	
	public void setStores(HashMap<String, Long> stores) {
		this.stores = stores;
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

    	storesNamesList.addAll(stores.keySet());

    	for(IncomeReport.Quarterly quarterly : IncomeReport.Quarterly.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+quarterly);
    		quarterlies.add(""+temporaryString);
    	}
    	
    	
    	quarterliesList.addAll(quarterlies);
    	firstQuarterlyComboBox.setItems(quarterliesList);
    	secondQuarterlyComboBox.setItems(quarterliesList);
    	
    	firstYearComboBox.setItems(yearsList);
    	secondYearComboBox.setItems(yearsList);
    	
    	firstStoreComboBox.setItems(storesNamesList);
    	secondStoreComboBox.setItems(storesNamesList);
    	
    	reportTypeComboBox.setItems(reportTypesList);
    	
    	

	}
	
	private void showErrorMessage(String message)
	{
    	// show failure  
		Alert alert = new Alert(AlertType.ERROR, message, ButtonType.OK);
		alert.showAndWait();
	}
	
	private String buildIncomeReportsTextAreaMessage(String quarterly, String year, float incomeAmount)
	{
		String messageToDisplay = "          *****INCOME REPORT*****\n\n"
				+ "This is the income report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n"
						+ "- The income amount is: " + incomeAmount + ".";
		
		return messageToDisplay;
	}
	
	private String buildOrderReportsTextAreaMessage(String quarterly, String year, long totalOrdersAmount, long bouquetAmount,
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
		
		return messageToDisplay;
	}
	
	private String buildSurveyReportsTextAreaMessage(String quarterly, String year, long firstSurveyAverageResult,
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
		
		return messageToDisplay;
	}
}
