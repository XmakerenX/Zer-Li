package survey;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import customer.CustomerGUI;
import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'SurveyCreationGUI.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import networkGUI.CustomerServiceGUI;
import networkGUI.NetworkWorkerGUI;
import networkGUI.SystemManagerGUI;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class SurveyCreationGUI extends FormController implements ClientInterface{
	
	// holds the last replay we got from server
	private Response response = null;
		
    @FXML // fx:id="surveyNameTxtFld"
    private TextField surveyNameTxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question3TxtFld"
    private TextArea  question3TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question6TxtFld"
    private TextArea  question6TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question4TxtFld"DANI
    private TextArea  question4TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question1TxtFld"
    private TextArea  question1TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question5TxtFld"
    private TextArea  question5TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="createBtn"
    private Button createBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question2TxtFld"
    private TextArea  question2TxtFld; // Value injected by FXMLLoader

  //==============================================================================================INITIALIZE FOR COMFORT=================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	surveyNameTxtFld.setText("123");
    	question1TxtFld.setText("1");
    	question2TxtFld.setText("1");//================REMOVE LATER ON============
    	question3TxtFld.setText("1");
    	question4TxtFld.setText("1");
    	question5TxtFld.setText("1");
    	question6TxtFld.setText("1");
    }
    
  //===============================================================================================================
    @FXML
    void closeWindow(ActionEvent event) {
    	CustomerServiceGUI customerServiceGUI = (CustomerServiceGUI)parent;
    	client.setUI(customerServiceGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
  //===============================================================================================================
    /**
     * a method that creates a new survey
     * @param event
     */
    @FXML
    void createSurvey(ActionEvent event) {
    	String[] questions = new String[6];
    	String surveyName = surveyNameTxtFld.getText();
    	int questionsFault = 0;							//FLAG - 1 = all 6 questions haven't been filled in
    	int nameFault = 0;								//FLAG - 1 = the name of the survey hasn't been filled in
    	//get all the questions from the GUI
    	questions[0]=question1TxtFld.getText();
    	questions[1]=question2TxtFld.getText();
    	questions[2]=question3TxtFld.getText();
    	questions[3]=question4TxtFld.getText();
    	questions[4]=question5TxtFld.getText();
    	questions[5]=question6TxtFld.getText();
    	
    	//Check whether the name or the questions haven't been fully filled in the form
    	if(surveyName.equals("")) nameFault = 1;
    	for(int i=0; i<6; i++)
    	{
    		if(questions[i].equals(""))
    		{
    			questionsFault = 1;
    			break;
    		}		
    	}
    	if(nameFault == 0 && questionsFault == 0)	//both survey name and 6 questions have been filled:
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
	        		// clear response
	        		response = null;
	        	}
	        	//if(response.getType() == Response.Type.ERROR)
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
    		if(nameFault == 1 || questionsFault == 1) 
    		{
    			// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "A name and 6 questions are needed to create a survey.", ButtonType.OK);
	    		alert.showAndWait();
    		}
    	}
    }
  //===============================================================================================================
    /**
     * changes the default display method
     * @param message from the server
     */
    //@Override
    public void display(Object message)
{
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
		
		Response replay = (Response)message;
		this.response = replay;
		
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
