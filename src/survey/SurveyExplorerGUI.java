
package survey;

import java.util.ArrayList;

import javax.swing.event.ChangeEvent;

import client.Client;
import client.ClientInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceGUI;
import prototype.FormController;
import serverAPI.Response;

public class SurveyExplorerGUI extends FormController implements ClientInterface{
	// holds the last replay we got from server
	private Response response = null;

    @FXML // fx:id="surveyComboBox"
    private ComboBox<String> surveyComboBox; // Value injected by FXMLLoader

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
    void onSurveyComboBox(ActionEvent event) 
    {
    	if(surveyComboBox == null) return;
    	if(surveyComboBox.getValue() == null) return;
    	
    	
    	String surveyName = surveyComboBox.getValue();
    	CustomerSatisfactionSurveyController.getSurvey(surveyName, client);
    	
    	
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
        		CustomerSatisfactionSurvey result = (CustomerSatisfactionSurvey)((ArrayList<CustomerSatisfactionSurvey>)response.getMessage()).get(0);
        		questionTxtFld1.setText(result.getSurveyQuestions()[0]);
        		questionTxtFld2.setText(result.getSurveyQuestions()[1]);
        		questionTxtFld3.setText(result.getSurveyQuestions()[2]);
        		questionTxtFld4.setText(result.getSurveyQuestions()[3]);
        		questionTxtFld5.setText(result.getSurveyQuestions()[4]);
        		questionTxtFld6.setText(result.getSurveyQuestions()[5]);
        		analysisTextArea.setText(result.getSurveyAnalysis());
        		// clear response
        		response = null;
        		return;
        	}
        	else
        	{
        		Alert alert = new Alert(AlertType.ERROR, "Could not load surveys info.", ButtonType.OK);
        		alert.showAndWait();
        		// clear response
        		response = null;
        	}
    	}
        catch(InterruptedException e) {}
	}
    
  //===============================================================================================================
    @FXML
    void onBackBtn(ActionEvent event) 
    {
    	questionTxtFld1.setText("");
		questionTxtFld2.setText("");
		questionTxtFld3.setText("");
		questionTxtFld4.setText("");
		questionTxtFld5.setText("");
		questionTxtFld6.setText("");
		analysisTextArea.setText("");
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
 
        		surveyComboBox.getItems().clear();
        		surveyComboBox.getItems().addAll(surveyNames);
        		// clear response
        		response = null;
        	}
        	else if (response.getType() == Response.Type.ERROR)
        	{
        		Alert alert = new Alert(AlertType.ERROR, (String)response.getMessage(), ButtonType.OK);
        		alert.showAndWait();
        		// clear response
        		response = null;
        	}
    	}
        catch(InterruptedException e) {}
	}

}