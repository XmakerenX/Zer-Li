package survey;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceGUI;
import networkGUI.StoreWorkerGUI;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import serverAPI.Response;
import user.User;
import utils.FormController;
/**
 * this class holds the GUI functionality that lets the customer service add an analysis to a specific survey result
 * @author dk198
 *
 */
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
    @FXML
    private Label lengthField;
	    //===============================================================================================================
	 public void initialize(){
		 // a listener that checks the length of the input analysis.
		 analysisField.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) 
		    {
		    int inputLength = newValue.length();
		    if(inputLength<=500 && !newValue.contains("'")) 
		    	{
		    		if(inputLength<=9)
		    			lengthField.setText("  " + inputLength+"/500");
		    		else if(inputLength<=99)
		    			lengthField.setText(" " + inputLength+"/500");
		    		else 
		    			lengthField.setText("" + inputLength+"/500");
		    	}
		    	else
		    		analysisField.setText(oldValue);
		    }
		});
	 }
	  //===============================================================================================================
	    @FXML
	    /**
	     * looking for a specific result
	     * @param event
	     */
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
	    	else
	    	{
	    		Alert alert = new Alert(AlertType.ERROR, "Please enter a result number to search for.", ButtonType.OK);
        		alert.showAndWait();
	    	}
	    }
	    //===============================================================================================================
	    @FXML
	    void onCancelButton(ActionEvent event) {
	    	clearForm();
	    	CustomerServiceGUI customerServiceGUI = (CustomerServiceGUI)parent;
	    	client.setUI(customerServiceGUI);
	    	FormController.primaryStage.setScene(parent.getScene());
	    }
	    //===============================================================================================================
	    @FXML
	    /**
	     * adding a new analysis to a specific result
	     * @param event
	     */
	    void onAddButton(ActionEvent event) {
	    	if(result!=null)
	    	{
	    		if(!analysisField.getText().contains("No analysis have been given to "))
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
	    			Alert alert = new Alert(AlertType.ERROR, "Please add an analysis.", ButtonType.OK);
	        		alert.showAndWait();
	    		}
	    		
	    	}
	    	else
	    	{
        		Alert alert = new Alert(AlertType.ERROR, "A result must be chosen to add an analysis to.", ButtonType.OK);
        		alert.showAndWait();
	    	}

	    }
		//===============================================================================================================
	    /**
	     * clearing all the fields when needed
	     */
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
		/**
		 * setting the current user for the given one
		 * @param user the user to set
		 */
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
