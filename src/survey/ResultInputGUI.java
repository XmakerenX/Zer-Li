package survey;

import java.util.ArrayList;
import client.Client;
import client.ClientInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceGUI;
import networkGUI.StoreWorkerGUI;
import product.CatalogItem;
import prototype.FormController;
import serverAPI.Response;
import user.User;
import user.UserController;
/**
 * this class lets us add new customer satisfaction survey results to the data base
 * you can only add a result if all fields have been filled.
 * @author dk198
 *
 */
public class ResultInputGUI extends FormController implements ClientInterface {
	private Response response = null;

	//Current user's name
	private User user;
	
    @FXML // fx:id="answerTxtFld1"
    private TextField answerTxtFld1; // Value injected by FXMLLoader

    @FXML // fx:id="surveyComboBox"
    private ComboBox<String> surveyComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld3"
    private TextField answerTxtFld3; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld2"
    private TextField answerTxtFld2; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="sendBtn"
    private Button sendBtn; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld5"
    private TextField answerTxtFld5; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld4"
    private TextField answerTxtFld4; // Value injected by FXMLLoader

    @FXML // fx:id="answerTxtFld6"
    private TextField answerTxtFld6; // Value injected by FXMLLoader
  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	answerTxtFld1.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) 
	    {
	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
	    		answerTxtFld1.setText(newValue);
	    	else
	    		answerTxtFld1.setText(oldValue);
	    }
	});

    	answerTxtFld2.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) 
	    {
	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
	    		answerTxtFld2.setText(newValue);
	    	else
	    		answerTxtFld2.setText(oldValue);
	    }
	});
    	answerTxtFld3.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) 
    	    {
    	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
    	    		answerTxtFld3.setText(newValue);
    	    	else
    	    		answerTxtFld3.setText(oldValue);
    	    }
    	});
    	answerTxtFld4.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) 
    	    {
    	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
    	    		answerTxtFld4.setText(newValue);
    	    	else
    	    		answerTxtFld4.setText(oldValue);
    	    }
    	});
    	answerTxtFld5.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) 
    	    {
    	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
    	    		answerTxtFld5.setText(newValue);
    	    	else
    	    		answerTxtFld5.setText(oldValue);
    	    }
    	});
    	answerTxtFld6.textProperty().addListener(new ChangeListener<String>() {
    	    @Override
    	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
    	        String newValue) 
    	    {
    	    	if(newValue.equals("") || (newValue.matches("\\d*") && newValue.length() < 3 && Integer.parseInt(newValue)>=1 && Integer.parseInt(newValue)<=10))
    	    		answerTxtFld6.setText(newValue);
    	    	else
    	    		answerTxtFld6.setText(oldValue);
    	    }
    	});
    	
    }
  //===============================================================================================================
    @FXML
    void onShowSurveys(ActionEvent event) {

    }
  //===============================================================================================================
    /**
     * collecting the data and creating a new result for a specific survey
     * @param event
     */
    @FXML
    void onSendBtn(ActionEvent event) {
    	String surveyName = surveyComboBox.getValue();
    	int[] answers = new int[6];
    	Boolean fieldsMissing = false;
    	TextField[] txtFieldsAray = {answerTxtFld1,answerTxtFld2,answerTxtFld3,answerTxtFld4,answerTxtFld5,answerTxtFld6};
    	
    	for(TextField tf: txtFieldsAray)
    	{
    		if(tf.getText().equals(""))
    		{
    			fieldsMissing = true;
    			break;
    		}
    	}
    	
    	if(!fieldsMissing && surveyComboBox.getValue()!=null)
	    	{
	    	answers[0]=Integer.parseInt(answerTxtFld1.getText());
	    	answers[1]=Integer.parseInt(answerTxtFld2.getText());
	    	answers[2]=Integer.parseInt(answerTxtFld3.getText());
	    	answers[3]=Integer.parseInt(answerTxtFld4.getText());
	    	answers[4]=Integer.parseInt(answerTxtFld5.getText());
	    	answers[5]=Integer.parseInt(answerTxtFld6.getText());
	    	UserController.getStoreOfEmployee(user.getUserName(), client);
	    	//=======================================================================
	    	try
        	{
    			System.out.println("got creation store");
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
	    	    	System.out.println("got employee store");
	    	    	int storeID = 9;

	    	    		storeID = (int)(response.getMessage());
	    	    		//clear response
	    	    		response=null;
	    	    		System.out.println(storeID);
	        		CustomerSatisfactionSurveyResultsController.addResults(surveyName, answers, storeID, client);
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
	    	        		clearForm();
	        				Alert alert = new Alert(AlertType.CONFIRMATION, "Result input was successfull", ButtonType.OK);
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
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR, "All fields must be filled to add a result.", ButtonType.OK);
    		alert.showAndWait();
    	}

    }
  //===============================================================================================================
    @FXML
    void onCancel(ActionEvent event) {
    	surveyComboBox.getItems().clear();
    	clearForm();
    	StoreWorkerGUI storeWorkerGUI = (StoreWorkerGUI)parent;
    	client.setUI(storeWorkerGUI);
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
	//===============================================================================================================
	public void setUser(User user)
	{
		this.user = user;
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
 
        		surveyComboBox.getItems().setAll(surveyNames);
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
	
	public void clearForm()
	{
	  	answerTxtFld1.setText("");
    	answerTxtFld2.setText("");
    	answerTxtFld3.setText("");
    	answerTxtFld4.setText("");
    	answerTxtFld5.setText("");
    	answerTxtFld6.setText("");
	}
}
