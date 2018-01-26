package survey;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import networkGUI.CustomerServiceExpertGUI;
import prototype.FormController;
import serverAPI.Response;
/**
 * A class that lets the customer service expert browse through the survey's results which haven't been assigned an analysis yet
 * @author dk198
 *
 */
public class SurveyAnalysisGUI extends FormController implements ClientInterface{
	
	//class variables
	// holds the last replay we got from server
	private Response response = null;
	private ArrayList<CustomerSatisfactionSurveyResults> resultList = null;
	private int currentResult = 0; //the id of the result we are currently looking at
	//GUI:
	    @FXML
	    private TextField resultField3;
	    @FXML
	    private TextField resultField4;
	    @FXML
	    private TextField resultField1;
	    @FXML
	    private TextField resultField2;
	    @FXML
	    private TextField questionField5;
	    @FXML
	    private TextField questionField4;
	    @FXML
	    private TextField resultField5;
	    @FXML
	    private TextField resultField6;
	    @FXML
	    private TextField questionField6;
	    @FXML
	    private Button nextButton;
	    @FXML
	    private TextField questionField1;
	    @FXML
	    private Button cancelButton;
	    @FXML
	    private TextField questionField3;
	    @FXML
	    private TextField questionField2;
	    @FXML
	    private Button prevButton;
	    @FXML
	    private TextField resultIDField;
	    @FXML
	    //========================================================================
	    void onPrevButton(ActionEvent event) {
	    	if(currentResult!=0)
	    	{
	    		currentResult--;
	    		setFields(resultList.get(currentResult));
	    	}
	    }
	  //========================================================================
	    @FXML
	    void onCancelButton(ActionEvent event) {
	    	clearFields();
	    	currentResult=0;
	    	CustomerServiceExpertGUI customerServiceExpertGUI = (CustomerServiceExpertGUI)parent;
	    	client.setUI(customerServiceExpertGUI);
	    	FormController.primaryStage.setScene(parent.getScene());
	    }
	  //========================================================================
	    @FXML
	    void onNextButton(ActionEvent event) {
	    	if(resultList!=null) 
	    	{
	    		if(currentResult<resultList.size()-1)
	    		{
	    			currentResult++;
	    			setFields(resultList.get(currentResult));
	    		}
	    	}
	    }
	  //========================================================================
	    /**
	     * setting all the fields in the form to reflect a specific survey result
	     * @param result	the one we use to fill up our fields
	     */
    private void setFields(CustomerSatisfactionSurveyResults result)
    {
    	resultIDField.setText(Integer.toString(result.getID()));
    	resultField1.setText(Integer.toString(result.getAnswers()[0]));
    	resultField2.setText(Integer.toString(result.getAnswers()[1]));
    	resultField3.setText(Integer.toString(result.getAnswers()[2]));
    	resultField4.setText(Integer.toString(result.getAnswers()[3]));
    	resultField5.setText(Integer.toString(result.getAnswers()[4]));
    	resultField6.setText(Integer.toString(result.getAnswers()[5]));
    }
  //========================================================================
    /**
     * clearing all the fields in the form
     */
    private void clearFields()
    {
    	resultIDField.clear();
    	resultField1.clear();
    	resultField2.clear();
    	resultField3.clear();
    	resultField4.clear();
    	resultField5.clear();
    	resultField6.clear();
    }
  //========================================================================
    /**
     * setting the fields to show  the questions for the survey
     */
    public void initResults()
    {
    	questionField1.setText(CustomerSatisfactionSurvey.question1);
    	questionField2.setText(CustomerSatisfactionSurvey.question2);
    	questionField3.setText(CustomerSatisfactionSurvey.question3);
    	questionField4.setText(CustomerSatisfactionSurvey.question4);
    	questionField5.setText(CustomerSatisfactionSurvey.question5);
    	questionField6.setText(CustomerSatisfactionSurvey.question6);
    	CustomerSatisfactionSurveyResultsController.getResultsWithoutAnalysis(client);
    	waitForServerResponse();
    	// show success 
    	if (response.getType() == Response.Type.SUCCESS)
    	{
    		resultList = (ArrayList<CustomerSatisfactionSurveyResults>)response.getMessage();
    		setFields(resultList.get(0));
    		//clear response
    		response = null;
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR, "No results without analysis were found.", ButtonType.OK);
    		alert.showAndWait();
    		// clear response
    		response = null;
    	}
    	
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
	//waiting for the server to respond
    public void waitForServerResponse()
    {
    	synchronized(this) 
    	{
    		try 
    		{
    			this.wait();
    		}
    		
    		catch (InterruptedException e) 
    		{
    			e.printStackTrace();
    		}
    	} 
    }
}
