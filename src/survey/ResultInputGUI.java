package survey;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import networkGUI.CustomerServiceGUI;
import prototype.FormController;
import serverAPI.Response;

public class ResultInputGUI extends FormController implements ClientInterface {
	private Response response = null;

    @FXML // fx:id="answerTxtFld1"
    private TextField answerTxtFld1; // Value injected by FXMLLoader

    @FXML // fx:id="surveyComboBox"
    private ComboBox<?> surveyComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld3"
    private TextField answerTxtFld3; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld2"
    private TextField answerTxtFld2; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="sendBtn"
    private Button sendBtn; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld8"
    private TextField answerTxtFld8; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld5"
    private TextField answerTxtFld5; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld4"
    private TextField answerTxtFld4; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld7"
    private TextField answerTxtFld7; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld6"
    private TextField answerTxtFld6; // Value injected by FXMLLoader
  //===============================================================================================================
    @FXML
    void onShowSurveys(ActionEvent event) {

    }
  //===============================================================================================================
    @FXML
    void onSendBtn(ActionEvent event) {

    }
  //===============================================================================================================
    @FXML
    void onCancel(ActionEvent event) {
    	CustomerServiceGUI customerServiceGUI = (CustomerServiceGUI)parent;
    	client.setUI(customerServiceGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
  //===============================================================================================================
	public void display(Object message) {
		
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
		
		Response response = (Response)message;
		this.response = response;
		
		synchronized(this)
		{
			this.notify();
		}
	}
	//===============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}

}
