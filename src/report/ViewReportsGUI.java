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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import networkGUI.StoreManagerGUI;
import prototype.FormController;
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
	ObservableList<String> reportTypesList = FXCollections.observableArrayList("Income", "Order", "Complaint", "Survey");
	
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
	private BarChart<Long, Long> complaintReportBarChart;

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
	 * Displays report in GUI's text area
	 * @param event - "View report" button is pressed
	 */

	@FXML
	void onViewReport(ActionEvent event) {
		
		switch (reportTypeComboBox.getValue())
		{
			case "Income":
			{
				String quarterly = quarterlyComboBox.getValue();
				String year = yearComboBox.getValue();

				ReportController.getReport("IncomeReport", IncomeReport.Quarterly.valueOf(quarterly.toUpperCase()), year, managersStoreID, client);
				
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
				
			}
			break;
			
			case "Complaint":
			{
				
			}
			break;
			
			case "Survey":
			{
				
			}
			break;
		}
	}

	@FXML
	void onBack(ActionEvent event) {

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
	 * Receives message that will be splitted by " " symbol and transformed to data base view.
	 * For example: "Credit card" is transformed to "CREDIT_CARD"
	 * @param stringToSplit - message to be splitted by specific symbol
	 * @return
	 */
	
	public String handleSplittedStringFromGUI(String stringToSplit)
	{
		String [] splittedString;
		splittedString = stringToSplit.split(" ");
		
		String tempString = "";
		
		for(String splitted : splittedString )
			{
		   		if( !tempString.equals(""))
		   			tempString = tempString + "_";
		   		tempString = tempString + splitted.toUpperCase();
		   	}
		
		return tempString;
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
    	

    	for(IncomeReport.Quarterly quarterly : IncomeReport.Quarterly.values())
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
		String messageToDisplay = "          *****INCOME REPORT*****\n\n\n"
				+ "This is the income report from " + quarterly.toLowerCase() + " quarterly of a year " + year + ":\n\n"
						+ "The income amount is: " + incomeAmount + ".";
		
		reportViaTextArea.setText(messageToDisplay);
	}

}
