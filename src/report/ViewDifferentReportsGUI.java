package report;

import java.util.ArrayList;
import java.util.HashMap;

import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import networkGUI.NetworkManagerGUI;
import prototype.FormController;
import report.Report.Quarterly;
import serverAPI.Response;

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
	private HashMap<String, Long> stores = new HashMap<String, Long>();;

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
    	
    	firstReportViaTextArea.setEditable(false);
    	secondReportViaTextArea.setEditable(false);
    	firstComplaintReportBarChart.setAnimated(false);
    	secondComplaintReportBarChart.setAnimated(false);
    }
    
	/**
	 * Displays income, order or survey report in text area or complaint report in graph
	 * @param event - "View report" button is pressed
	 */

	@SuppressWarnings("unchecked")
	@FXML
	void onViewReport(ActionEvent event) {

		if(firstStoreComboBox.getValue() != null && secondStoreComboBox.getValue() != null && firstQuarterlyComboBox.getValue() != null
				&& secondQuarterlyComboBox.getValue() != null && firstYearComboBox.getValue() != null
				&& secondYearComboBox.getValue() != null && reportTypeComboBox.getValue() != null)
		{
			//If same reposts were chosen, a message will be displayed to warn the user
			if(firstStoreComboBox.getValue().equals(secondStoreComboBox.getValue()) &&
					firstQuarterlyComboBox.getValue().equals(secondQuarterlyComboBox.getValue()) &&
					firstYearComboBox.getValue().equals(secondYearComboBox.getValue()))
				showWarningMessage("Pay Attention!\nSame reports were selected!");
			
			long firstStore = stores.get(firstStoreComboBox.getValue());
			String firstStoreName = firstStoreComboBox.getValue();
			long secondStore = stores.get(secondStoreComboBox.getValue());
			String secondStoreName = secondStoreComboBox.getValue();
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
	
					ReportController.getReport("incomereport", Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
									incomeReport = (IncomeReport)((ArrayList<?>) replay.getMessage()).get(0);
									
									reportMessage = buildIncomeReportsTextAreaMessage(""+incomeReport.getQuarterly(),""+incomeReport.getYear(),firstStoreName,
											incomeReport.getIncomeAmount());
									firstReportViaTextArea.setText(reportMessage);
				  	}
				  	else
									showWarningMessage("In "+firstStore+ " store such income's report doesn't exists!");
					
					replay = null;
					
					ReportController.getReport("incomereport", Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
									incomeReport = (IncomeReport)((ArrayList<?>) replay.getMessage()).get(0);
									
									reportMessage = buildIncomeReportsTextAreaMessage(""+incomeReport.getQuarterly(),""+incomeReport.getYear(), secondStoreName,
											incomeReport.getIncomeAmount());
									secondReportViaTextArea.setText(reportMessage);
				  	}
				  	else
									showWarningMessage("In "+secondStore+ " store such income's report doesn't exists!");
					
					replay = null;
				}
				break;
				
				case "Order":
				{
					ReportController.getReport("orderreport", IncomeReport.Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
					
					waitForResponse();
									
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
				
									reportMessage = buildOrderReportsTextAreaMessage(""+orderReport.getQuarterly(), ""+orderReport.getYear(),firstStoreName,
											totalOrdersAmount, bouquetAmount, brideBouquetAmount, flowerPotAmount, flowerAmount, plantAmount);
									firstReportViaTextArea.setText(reportMessage);
				  	}
				  	else
									showWarningMessage("In "+firstStore+ " store such orders' report doesn't exists!");
					
					replay = null;
					
					ReportController.getReport("orderreport", IncomeReport.Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
					
					waitForResponse();
									
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
				
									reportMessage = buildOrderReportsTextAreaMessage(""+orderReport.getQuarterly(), ""+orderReport.getYear(),secondStoreName,
											totalOrdersAmount, bouquetAmount, brideBouquetAmount, flowerPotAmount, flowerAmount, plantAmount);
									secondReportViaTextArea.setText(reportMessage);
				  	}
				  	else
									showWarningMessage("In "+secondStore+ " store such orders' report doesn't exists!");
				
					replay = null;
				}
				break;
				
				case "Survey":
				{
					ReportController.getReport("surveyreport", Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
									surveyReport = (SurveyReport)((ArrayList<?>) replay.getMessage()).get(0);
									
									firstComplaintReportBarChart.getData().addAll(buildSurveyReportsHystogramGraph(surveyReport));
				  	}
				  	else
									showWarningMessage("In "+firstStore+ " store such surveys' report doesn't exists!");
					
					replay = null;
					
					ReportController.getReport("surveyreport", Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
									surveyReport = (SurveyReport)((ArrayList<?>) replay.getMessage()).get(0);
									
									secondComplaintReportBarChart.getData().addAll(buildSurveyReportsHystogramGraph(surveyReport));
				  	}
				  	else
									showWarningMessage("In "+secondStore+ " store such surveys' report doesn't exists!");

					
					replay = null;
				}
				break;
				
				case "Complaint":
				{
					ReportController.getReport("ComplaintReport", Quarterly.valueOf(firstQuarterly.toUpperCase()), firstYear, firstStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
				
									complaintReport = (ComplaintReport)((ArrayList<?>) replay.getMessage()).get(0);
									ArrayList<Series<String, Number>> series = buildComplaintReportsHystogramGraph(complaintReport);
									
									firstComplaintReportBarChart.getData().addAll(series.get(0), series.get(1));
				  	}
				  	else
									showWarningMessage("In "+firstStore+ " store such complaints' report doesn't exists!");
					
					replay = null;
					
					ReportController.getReport("ComplaintReport", Quarterly.valueOf(secondQuarterly.toUpperCase()), secondYear, secondStore, client);
					
					waitForResponse();
									
				  	// show success 
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
				
									complaintReport = (ComplaintReport)((ArrayList<?>) replay.getMessage()).get(0);
									ArrayList<Series<String, Number>> series = buildComplaintReportsHystogramGraph(complaintReport);
									
									secondComplaintReportBarChart.getData().addAll(series.get(0), series.get(1));
				  	}
				  	else
									showWarningMessage("In "+secondStore+ " store such complaints' report doesn't exists!");

					
					replay = null;
				}
				break;
			}
		}
		else
			showWarningMessage("Please select all attributes (store, quarterly and year) for both reports.");
	}

	/**
	 * Clears all the fields and returns to previous GUI window
	 * @param event - "Back" button is pressed
	 */
	@FXML
	void onBack(ActionEvent event) {
		
		reportTypeComboBox.setValue(null);
		clearFieldsMethod();
		
		NetworkManagerGUI networkManagerGUI = (NetworkManagerGUI) parent;
		client.setUI(networkManagerGUI);
		FormController.primaryStage.setScene(parent.getScene());

	}
	
	/**
	 * Displays text are or bar chart according to report's type.
	 * @param event - report type is selected from combo box
	 */
	
    @FXML
    void onReportTypeSelection(ActionEvent event) {
    	
       if(reportTypeComboBox.getValue() != null)
       {
    	if(reportTypeComboBox.getValue().equals("Complaint") || reportTypeComboBox.getValue().equals("Survey"))
    	{
    		if(reportTypeComboBox.getValue().equals("Complaint"))
    		{
    			firstComplaintReportBarChart.setTitle("Complaint Report");
        		firstComplaintReportBarChart.getXAxis().setLabel("Months");
        		firstComplaintReportBarChart.getYAxis().setLabel("# of complaints");
        		secondComplaintReportBarChart.setTitle("Complaint Report");
        		secondComplaintReportBarChart.getXAxis().setLabel("Months");
        		secondComplaintReportBarChart.getYAxis().setLabel("# of complaints");
    		}
    		else
    		{
    			firstComplaintReportBarChart.setTitle("Survey Report");
        		firstComplaintReportBarChart.getXAxis().setLabel("Questions");
        		firstComplaintReportBarChart.getYAxis().setLabel("Average results");
        		secondComplaintReportBarChart.setTitle("Survey Report");
        		secondComplaintReportBarChart.getXAxis().setLabel("Questions");
        		secondComplaintReportBarChart.getYAxis().setLabel("Average results");
    		}
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
    	
    	clearFieldsMethod();
       }
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
	 * @param stringToSplit  message to be splitted by specific symbol
	 * @return the stringToSplit with spaces instead of '_'
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
	
	public void setComboBoxes()
	{
		String temporaryString = "";

    	ArrayList<String> quarterlies = new ArrayList<String>();
    	ArrayList<String> storeNameList = new ArrayList<String>();
    	
    	for(String storeName : stores.keySet())
    		storeNameList.add(storeName);


    	for(Quarterly quarterly : Quarterly.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+quarterly);
    		quarterlies.add(""+temporaryString);
    	}
    	
    	storesNamesList.addAll(stores.keySet());
    	quarterliesList.addAll(quarterlies);
    	
    	firstQuarterlyComboBox.setItems(quarterliesList);
    	secondQuarterlyComboBox.setItems(quarterliesList);
    	
    	firstYearComboBox.setItems(yearsList);
    	secondYearComboBox.setItems(yearsList);
    	
    	firstStoreComboBox.setItems(storesNamesList);
    	secondStoreComboBox.setItems(storesNamesList);
    	
    	reportTypeComboBox.setItems(reportTypesList);
	}
	
	private String buildIncomeReportsTextAreaMessage(String quarterly, String year, String storeName, float incomeAmount)
	{
		String messageToDisplay = "          *****INCOME REPORT*****\n\n"
				+ "This is "+storeName+" store's income report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n"
						+ "- The income amount is: " + incomeAmount + ".";
		
		return messageToDisplay;
	}
	
	private String buildOrderReportsTextAreaMessage(String quarterly, String year, String storeName, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long customAmount, long flowerClusterAmount)
	{
		String messageToDisplay = "          *****ORDER REPORT*****\n\n"
				+ "This is "+storeName+" store's order report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n"
						+ "- Total amount of ordered products: " + totalOrdersAmount + ".\n"
								+ "In more details:\n"
								+ "- The amount of ordered bouquets: " + bouquetAmount + ".\n"
								+ "- The amount of ordered bride bouquets: " + brideBouquetAmount + ".\n"
								+ "- The amount of ordered flower pots: " + flowerPotAmount + ".\n"
								+ "- The amount of ordered flower clusters: " + flowerClusterAmount + ".\n"
								+ "- The amount of ordered customed items: " + customAmount + ".";
		
		return messageToDisplay;
	}
	
	private Series<String, Number> buildSurveyReportsHystogramGraph(SurveyReport surveyReport)
	{
		final String first = "1st";
		final String second = "2nd";
		final String third = "3rd";
		final String fourth = "4th";
		final String fifth = "5th";
		final String sixth = "6th";
		
	    long firstSurveyAverageResult = surveyReport.getFirstSurveyAverageResult();
	    long secondSurveyAverageResult = surveyReport.getSecondSurveyAverageResult();
	    long thirdSurveyAverageResult = surveyReport.getThirdSurveyAverageResult();
		long fourthSurveyAverageResult = surveyReport.getFourthSurveyAverageResult();
		long fifthSurveyAverageResult = surveyReport.getFifthSurveyAverageResult();
		long sixthSurveyAverageResult = surveyReport.getSixthSurveyAverageResult();


		XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
		series.setName("Results");       
		series.getData().add(new XYChart.Data<String, Number>(first, (Number)firstSurveyAverageResult));
		series.getData().add(new XYChart.Data<String, Number>(second, (Number)secondSurveyAverageResult));
		series.getData().add(new XYChart.Data<String, Number>(third, (Number)thirdSurveyAverageResult));
		series.getData().add(new XYChart.Data<String, Number>(fourth, (Number)fourthSurveyAverageResult));
		series.getData().add(new XYChart.Data<String, Number>(fifth, (Number)fifthSurveyAverageResult));
		series.getData().add(new XYChart.Data<String, Number>(sixth, (Number)sixthSurveyAverageResult));
		
		return series;
	}
	
	private ArrayList<Series<String, Number>> buildComplaintReportsHystogramGraph(ComplaintReport complaintReport)
	{
		final String first = "1st";
		final String second = "2nd";
		final String third = "3rd";
		
		ArrayList<Series<String, Number>> series = new ArrayList<Series<String, Number>>();
		
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
		series.add(series1);
		  
		XYChart.Series<String, Number> series2 = new XYChart.Series<String, Number>();
		series2.setName("Pending");
		series2.getData().add(new XYChart.Data<String, Number>(first, (Number)firstMonthPendingComplaintsAmount));
		series2.getData().add(new XYChart.Data<String, Number>(second, (Number)secondMonthPendingComplaintsAmount));
		series2.getData().add(new XYChart.Data<String, Number>(third, (Number)thirdMonthPendingComplaintsAmount));
		series.add(series2);
		
		return series;
	}
	
	private void clearFieldsMethod()
	{
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
}
