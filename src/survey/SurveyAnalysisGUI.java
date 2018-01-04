package survey;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import networkGUI.CustomerServiceExpertGUI;
import prototype.FormController;
import serverAPI.Response;

public class SurveyAnalysisGUI extends FormController implements ClientInterface{

	// holds the last replay we got from server
	private Response response = null;
	
	@FXML
	private ComboBox<String> chooseSurveyCbb;
	 
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
    void onChooseSurveyCbb(ActionEvent event) {
    	String surveyName = chooseSurveyCbb.getValue();
    }
    //===============================================================================================================
    @FXML
    void onSendBtn(ActionEvent event) {

    }
    //===============================================================================================================
	@Override
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
	
	//===============================================================================================================

		public void initComboBox()
		{
	    	ArrayList<String> surveyNames = new ArrayList<String>();
	    	CustomerSatisfactionSurveyController.requestSurveys(Client.client);
	    	try
	    	{
	    		synchronized(this)
	    		{
	    			// wait for server response
	    			this.wait();
	    		}
	    	
	    		if (response == null)
	    			return;
	        		
	        	// show success 
	        	if (response.getType() == Response.Type.SUCCESS)
	        	{
	        		ArrayList<CustomerSatisfactionSurvey> results = (ArrayList<CustomerSatisfactionSurvey>)response.getMessage();
	        		for(int i=0; i<results.size(); i++)
	        			surveyNames.add(results.get(i).getSurveyName());
	 
	        		//ObservableList<String> surveyNamesObservable = FXCollections.observableArrayList(surveyNames);
	        		chooseSurveyCbb.getItems().setAll(surveyNames);
	        		// clear response
	        		response = null;
	        	}
	        	else
	        	{
	        		Alert alert = new Alert(AlertType.ERROR, "Could not load surveys", ButtonType.OK);
	        		alert.showAndWait();
	        		// clear response
	        		response = null;
	        	}
	    	}
	        catch(InterruptedException e) {}
		}
		
	 //===============================================================================================================
}

