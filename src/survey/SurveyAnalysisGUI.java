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
/**
 * a class that allows the customer service expert view the results of a given survey and add an analysis to them
 * @author dk198
 *
 */
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
    private TextArea oldAnalysisTxtField;

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
    	//reset all fields when returning to previous screen
    	chooseSurveyCbb.getItems().clear();
    	avgResultQuestion1.setText("");
    	avgResultQuestion2.setText("");
    	avgResultQuestion3.setText("");
    	avgResultQuestion4.setText("");
    	avgResultQuestion5.setText("");
    	avgResultQuestion6.setText("");
    	analysisTxtField.setText("");
    	CustomerServiceExpertGUI customerServiceExpertGUI = (CustomerServiceExpertGUI)parent;
    	client.setUI(customerServiceExpertGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    //===============================================================================================================
    @FXML
    void onChooseSurveyCbb(ActionEvent event) {
    	if(chooseSurveyCbb == null) return;
    	if(chooseSurveyCbb.getValue() == null) return;
    	String surveyName = chooseSurveyCbb.getValue();
    	CustomerSatisfactionSurveyResultsController.getResultsOfSurvey(surveyName, client);
    	try
    	{
    		synchronized(this)
    		{
    			// wait for server response
    			this.wait();
    		}
    	
    		if (response == null)
    			return;
        		
        	// shows success 
        	if (response.getType() == Response.Type.SUCCESS)
        	{
        		//calculate the average for each question of a given survey and set the test fields accordingly
         		ArrayList<CustomerSatisfactionSurveyResults> results = (ArrayList<CustomerSatisfactionSurveyResults>)response.getMessage();
        		//clear response
        		response = null;
        		double avg1=0, avg2=0, avg3=0, avg4=0, avg5=0, avg6=0;
        		int numOfResults = results.size();
        		for(int i=0; i<numOfResults; i++)
        		{
        			System.out.println(results.get(i).getAnswers()[3]);
        			avg1+=results.get(i).getAnswers()[0];
        			avg2+=results.get(i).getAnswers()[1];
        			avg3+=results.get(i).getAnswers()[2];
        			avg4+=results.get(i).getAnswers()[3];
        			avg5+=results.get(i).getAnswers()[4];
        			avg6+=results.get(i).getAnswers()[5];
        		}
        		avg1 = (avg1 / numOfResults);
        		avg2 = (avg2 / numOfResults);
        		avg3 = (avg3 / numOfResults);
        		avg4 = (avg4 / numOfResults);
        		avg5 = (avg5 / numOfResults);
        		avg6 = (avg6 / numOfResults);
        		//setting fields to show averages
        		avgResultQuestion1.setText(String.valueOf(avg1));
            	avgResultQuestion2.setText(String.valueOf(avg2));
            	avgResultQuestion3.setText(String.valueOf(avg3));
            	avgResultQuestion4.setText(String.valueOf(avg4));
            	avgResultQuestion5.setText(String.valueOf(avg5));
            	avgResultQuestion6.setText(String.valueOf(avg6));
            	
            	//load the old analysis
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
                		//set the text field of the old analysis
                		CustomerSatisfactionSurvey result = (CustomerSatisfactionSurvey)((ArrayList<CustomerSatisfactionSurvey>)response.getMessage()).get(0);
                		String oldAnalysis = result.getSurveyAnalysis();
                		if(oldAnalysis == null || oldAnalysis == "")
                			oldAnalysisTxtField.setText("No analysis have yet been given.");
                		else
                			oldAnalysisTxtField.setText(result.getSurveyAnalysis());
                		response = null;
                	}
                	else
                	{
                		Alert alert = new Alert(AlertType.ERROR, "Could not load surveys analysis.", ButtonType.OK);
                		alert.showAndWait();
                		// clear response
                		response = null;
                	}
            	}
                catch(InterruptedException e) {}
        		
        	}
        	else
        	{
        		Alert alert = new Alert(AlertType.ERROR, (String)response.getMessage(), ButtonType.OK);
        		alert.showAndWait();
        		// clear response
        		response = null;
        		//reset all fields in case of failure
        		avgResultQuestion1.setText("");
            	avgResultQuestion2.setText("");
            	avgResultQuestion3.setText("");
            	avgResultQuestion4.setText("");
            	avgResultQuestion5.setText("");
            	avgResultQuestion6.setText("");
            	oldAnalysisTxtField.setText("");
        	}
    	}
        catch(InterruptedException e) {}
    }
    //===============================================================================================================
    @FXML
    void onSendBtn(ActionEvent event) {
    	String surveyName = chooseSurveyCbb.getValue();
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
        		result.setSurveyAnalysis(analysisTxtField.getText());
        		response = null;
        		CustomerSatisfactionSurveyController.updateCustomerSatisfactionSurvey(surveyName, result, Client.client);
        		Alert alert = new Alert(AlertType.CONFIRMATION, "Analysis added successfully.", ButtonType.OK);
        		alert.showAndWait();
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
	/**
	 * Initializing the combo box with the names of the surveys we have in our database
	 */
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
 
        		chooseSurveyCbb.getItems().setAll(surveyNames);
        		// clear response
        		response = null;
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
}

