
package survey;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import networkGUI.CustomerServiceGUI;
import prototype.FormController;
import serverAPI.Response;

public class SurveyExplorerGUI extends FormController implements ClientInterface{
	// holds the last replay we got from server
	private Response response = null;

    @FXML // fx:id="surveyComboBox"
    private ComboBox<?> surveyComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld1"
    private TextField questionTxtFld1; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld2"
    private TextField questionTxtFld2; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld3"
    private TextField questionTxtFld3; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld4"
    private TextField questionTxtFld4; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld5"
    private TextField questionTxtFld5; // Value injected by FXMLLoader

    @FXML // fx:id="questionTxtFld6"
    private TextField questionTxtFld6; // Value injected by FXMLLoader

    @FXML // fx:id="analysisTextArea"
    private TextArea analysisTextArea; // Value injected by FXMLLoader

    //===============================================================================================================
    @FXML
    public void initialize() 
 {
    	
 }
    
  //===============================================================================================================
    @FXML
    void onSurveyComboBox(ActionEvent event) {

    }
  //===============================================================================================================
    @FXML
    void onBackBtn(ActionEvent event) {
    	CustomerServiceGUI customerServiceGUI = (CustomerServiceGUI)parent;
    	client.setUI(customerServiceGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }

    //===============================================================================================================
    /**
     * changes the default display method
     * @param message from the server
     */
    public void display(Object message)
	{
		
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
	 //===============================================================================================================
    @Override
	public void setClinet(Client client)
	{
    	super.setClinet(client);
    	//CustomerSatisfactionSurveyController.requestSurveys(client);
	}

}