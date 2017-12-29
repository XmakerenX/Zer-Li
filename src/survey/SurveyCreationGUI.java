package survey;

import java.util.ArrayList;

import client.Client;
import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'SurveyCreationGUI.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class SurveyCreationGUI extends FormController{
	
	// holds the last replay we got from server
	private Response response = null;
		
    @FXML // fx:id="surveyNameTxtFld"
    private TextField surveyNameTxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question3TxtFld"
    private TextField question3TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question6TxtFld"
    private TextField question6TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question4TxtFld"
    private TextField question4TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question1TxtFld"
    private TextField question1TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question5TxtFld"
    private TextField question5TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question8TxtFld"
    private TextField question8TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="createBtn"
    private Button createBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question2TxtFld"
    private TextField question2TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question7TxtFld"
    private TextField question7TxtFld; // Value injected by FXMLLoader

    @FXML
    void closeWindow(ActionEvent event) {

    }
  //===============================================================================================================
    @FXML
    void createSurvey(ActionEvent event) {
    	String[] questions = null;
    	String surveyName = surveyNameTxtFld.getText();
    	int questionsFault = 0;
    	int nameFault = 0;
    	questions[0]=question1TxtFld.getText();
    	questions[1]=question2TxtFld.getText();
    	questions[2]=question3TxtFld.getText();
    	questions[3]=question4TxtFld.getText();
    	questions[4]=question5TxtFld.getText();
    	questions[5]=question6TxtFld.getText();
    	questions[6]=question7TxtFld.getText();
    	questions[7]=question8TxtFld.getText();
    	if(surveyName==null) nameFault=1;
    	for(int i=0; i<8; i++)
    	{
    		if(questions[i]==null)
    		{
    			questionsFault=1;
    			break;
    		}		
    	}
    	if(nameFault!=0 && questionsFault!=0)	//both survey name and 8 questions have been filled:
    	{
    		CustomerSatisfactionSurveyController.doesSurveyExist(surveyName, client);
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
	        		// show failure  
	        		Alert alert = new Alert(AlertType.ERROR, "Survey with the same name already exists.", ButtonType.OK);
	        		alert.showAndWait();
	        		// clear replay
	        		response = null;
	        	}
	        	else
	        	{
	        		CustomerSatisfactionSurveyController.surveyCreation(surveyName, questions, client);
	        		try
	        		{
	        			synchronized(this)
	        			{
	        				//wait for server response
	        				this.wait();
	        			}
	        			if(response == null)
	        				return;
	        			
	        			//success
	        			if(response.getType() == Response.Type.SUCCESS)
	        			{
	        				Alert alert = new Alert(AlertType.CONFIRMATION, "Survey created successfully", ButtonType.OK);
	    	        		alert.showAndWait();
	    	        		// clear replay
	    	        		response = null;
	        			}
	        			else
	        			{
	        				// show failure  
	        	    		Alert alert = new Alert(AlertType.ERROR, (String)response.getMessage(), ButtonType.OK);
	        	    		alert.showAndWait();
	        	    		// clear replay
	        	    		response = null;
	        			}
	        		}
	        		catch(InterruptedException e) {}

	        	}
	        	
	        	}catch(InterruptedException e) {}
    	}
    	else
    	{
    		if(nameFault==1) {
    			// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "A name is needed to create a survey.", ButtonType.OK);
	    		alert.showAndWait();
    		}
    		else {
    			// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "You must input all eight(8) questions to create a survey.", ButtonType.OK);
	    		alert.showAndWait();
    		}
    	}
    	
    	
    	
    }
  //===============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}

}
