package report;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import networkGUI.StoreManagerGUI;
import networkGUI.SystemManagerGUI;
import prototype.FormController;
import serverAPI.Response;


/**
 * Main menu for reports' selection
 */

public class ReportsMenuGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;
	
	// Store manager's store ID
	private long managersStoreID;
	
	//Reports GUIs
	ReportsViaTextGUI reportsViaText;
	ReportsViaGraphGUI reportsViaGraph;

	@FXML // fx:id="complaintReportBtn"
    private Button complaintReportBtn;

    @FXML // fx:id="surveyReportBtn"
    private Button surveyReportBtn;

    @FXML // fx:id="orderReportBtn"
    private Button orderReportBtn;

    @FXML // fx:id="incomeReportBtn"
    private Button incomeReportBtn; 

    @FXML // fx:id="mainReportsMenuLbl"
    private Label mainReportsMenuLbl;
    
    @FXML
    private Label windowTitleLbl;
    
    @FXML
    private Button backBtn;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
    	reportsViaText = FormController.<ReportsViaTextGUI, AnchorPane>loadFXML(getClass().getResource("/report/ReportsViaTextGUI.fxml"), this);
    	
    }
    
    /**
     * Opens income report's GUI
     * @param event - "Income report" button is pressed
     */
    
    @FXML
    void onIncomeReport(ActionEvent event) {
    	
		if(reportsViaText != null)
			switchToReportsViaTextGUI("IncomeReport");
    	
    }

    /**
     * Opens order report's GUI
     * @param event - "Order report" button is pressed
     */
    
    @FXML
    void onOrderReport(ActionEvent event) {
		if(reportsViaText != null)
			switchToReportsViaTextGUI("OrderReport");
    }

    /**
     * Opens complaint report's GUI
     * @param event - "Complaint report" button is pressed
     */
    
    @FXML
    void onComplaintReport(ActionEvent event) {

    }
    
    /**
     * Opens survey report's GUI
     * @param event - "Survey report" button is pressed
     */
    
    @FXML
    void onSurveyReport(ActionEvent event) {
		if(reportsViaText != null)
			switchToReportsViaTextGUI("SurveyReport");
    }

	@Override
	public void display(Object message) {
		
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
		
		Response replay = (Response)message;
		this.replay = replay;
		
		synchronized(this)
		{
			this.notify();
		}
		
	}

	@Override
	public void onSwitch(Client newClient) {
		
	}
	
    @FXML
    void onBack(ActionEvent event) {
    	
    	StoreManagerGUI storeManagerGUI = (StoreManagerGUI)parent;
    	client.setUI(storeManagerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }
	
    public void setManagersStoreID(long managersStoreID) {
		this.managersStoreID = managersStoreID;
	}
    
    private void switchToReportsViaTextGUI(String reportType)
    {
		reportsViaText.setRequiredReportType(reportType);
		reportsViaText.setManagersStoreID(managersStoreID);
		
    	reportsViaText.setClinet(client);
		client.setUI(reportsViaText);
		FormController.primaryStage.setScene(reportsViaText.getScene());
    }

}

