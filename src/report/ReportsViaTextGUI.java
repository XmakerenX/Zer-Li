package report;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import networkGUI.StoreManagerGUI;
import prototype.FormController;
import serverAPI.Response;

/**
 * GUI allows to view income, order or survey report from specific quarterly and year
 */
public class ReportsViaTextGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;

	//To indicate which of the reports is required
	private String requiredReportType;
	
	// Store manager's store ID
	private long managersStoreID;

	@FXML
	private Label quarterlyLbl;

	@FXML
	private ComboBox<String> yearComboBox;

	@FXML
	private ComboBox<String> quarterlyComboBox;

	@FXML
	private Button viewReportBtn;

	@FXML
	private Label windowTitleLbl;

	@FXML
	private TextArea reportViaTextArea;

	@FXML
	private Label instructionLbl;

	@FXML
	private Label yearLbl;

	@FXML
	private Button backBtn;

	/**
	 * Displays report in GUI's text area
	 * @param event - "View report" button is pressed
	 */

	@FXML
	void onViewReport(ActionEvent event) {

	}

	@FXML
	void onBack(ActionEvent event) {
		
		ReportsMenuGUI reportsMenuGUI = (ReportsMenuGUI) parent;
		client.setUI(reportsMenuGUI);
		FormController.primaryStage.setScene(parent.getScene());

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

	public void setRequiredReportType(String requiredReportType) {
		this.requiredReportType = requiredReportType;
	}
	
	public void setManagersStoreID(long managersStoreID) {
		this.managersStoreID = managersStoreID;
	}

}
