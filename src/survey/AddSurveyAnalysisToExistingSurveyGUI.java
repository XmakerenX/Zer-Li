package survey;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.StoreWorkerGUI;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class AddSurveyAnalysisToExistingSurveyGUI  extends FormController implements ClientInterface{

	Response response = null;
	User user;
	CustomerSatisfactionSurveyResults result = null;
	
	
	@FXML
	private TextField resultNumberField;
	@FXML
	private Button searchButton;
	@FXML
	private Button cancelButton;
	@FXML
	private Button addButton;
	@FXML
	private TextArea analysisField;
	    //===============================================================================================================
	    @FXML
	    void onSeachButton(ActionEvent event) {
	    	if(!resultNumberField.getText().isEmpty())
	    	{
	    		CustomerSatisfactionSurveyResultsController.getSpecificResults(resultNumberField.getText(), client);
	    		waitForServerResponse();
	    		// show success 
	        	if (response.getType() == Response.Type.SUCCESS)
	        	{
	        		result = ((ArrayList<CustomerSatisfactionSurveyResults>)response.getMessage()).get(0);
	        		if(result.getAnalysis().isEmpty())
	        		{
	        			String message = "No analysis have been given to \nresult number "+resultNumberField.getText()+" yet.\nEnter the analysis here.";
	        			analysisField.setText(message);
	        		}
	        		else
	        		{
	        			analysisField.setText("Current given analysis is:\n"+result.getAnalysis()+"\nTo change it, simply \nchange this message to the desired analysis.");
	        		}
	        		//clear response
	        		response = null;
	        	}
	        	else
	        	{
	        		analysisField.clear();
	        		Alert alert = new Alert(AlertType.ERROR, "No result found for the given result number.", ButtonType.OK);
	        		alert.showAndWait();
	        		// clear response
	        		response = null;
	        	}
	    		
	    	}
	    }
	    //===============================================================================================================
	    @FXML
	    void onCancelButton(ActionEvent event) {
	    	clearForm();
	        StoreWorkerGUI storeWorkerGUI = (StoreWorkerGUI)parent;
	        client.setUI(storeWorkerGUI);
	        FormController.primaryStage.setScene(parent.getScene());
	    }
	    //===============================================================================================================
	    @FXML
	    void onAddButton(ActionEvent event) {
	    	if(result!=null)
	    	{
	    		result.setAnalysis(analysisField.getText());
	    		CustomerSatisfactionSurveyResultsController.addResultAnalysis(Integer.toString(result.getID()), result, client);
	    		waitForServerResponse();
	    		// show success 
	        	if (response.getType() == Response.Type.SUCCESS)
	        	{
	        		Alert alert = new Alert(AlertType.CONFIRMATION, "Analysis added successfully.", ButtonType.OK);
	        		alert.showAndWait();
	        		//clear response
	        		response = null;
	        		clearForm();
	        	}
	        	else
	        	{
	        		Alert alert = new Alert(AlertType.ERROR, "Could not add analysis.", ButtonType.OK);
	        		alert.showAndWait();
	        		// clear response
	        		response = null;
	        	}
	    		
	    	}
	    	else
	    	{
        		Alert alert = new Alert(AlertType.ERROR, "A result must be chosen to add an analysis to.", ButtonType.OK);
        		alert.showAndWait();
	    	}

	    }
		//===============================================================================================================
	    private void clearForm()
	    {
	    	resultNumberField.clear();
	    	analysisField.clear();
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
		//===============================================================================================================
		public void setUser(User user)
		{
			this.user = user;
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
