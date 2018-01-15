package report;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import prototype.FormController;
import serverAPI.Response;


/**
 * Main menu for reports' selection
 */

public class ReportsMenuGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;

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

    /**
     * Opens income report's GUI
     * @param event - "Income report" button is pressed
     */
    
    @FXML
    void onIncomeReport(ActionEvent event) {

    }

    /**
     * Opens order report's GUI
     * @param event - "Order report" button is pressed
     */
    
    @FXML
    void onOrderReport(ActionEvent event) {

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

}

