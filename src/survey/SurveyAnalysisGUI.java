package survey;

import javafx.scene.control.TextField;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import networkGUI.CustomerServiceExpertGUI;
import prototype.FormController;
import serverAPI.Response;

public class SurveyAnalysisGUI extends FormController implements ClientInterface{

	// holds the last replay we got from server
	private Response response = null;
	
	@FXML
    private TextField avgResultQuestion5;

    @FXML
    private TextField avgResultQuestion6;

    @FXML
    private Button sendBtn;

    @FXML
    private Button backBtn;

    @FXML
    private TextField avgResultQuestion3;

    @FXML
    private TextField avgResultQuestion4;

    @FXML
    private TextField avgResultQuestion1;

    @FXML
    private TextArea analysisTxtField;

    @FXML
    private TextField avgResultQuestion2;
    //===============================================================================================================
    @FXML
    void onBackBtn(ActionEvent event) {
    	CustomerServiceExpertGUI customerServiceExpertGUI = (CustomerServiceExpertGUI)parent;
    	client.setUI(customerServiceExpertGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    //===============================================================================================================
    @FXML
    void onSendBtn(ActionEvent event) {

    }
    //===============================================================================================================
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}
	 //===============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	 //===============================================================================================================
}

