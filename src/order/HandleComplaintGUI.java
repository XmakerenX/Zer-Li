package order;

import client.Client;
import client.ClientInterface;
import prototype.FormController;
import serverAPI.Response;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HandleComplaintGUI extends FormController implements ClientInterface{

	Response response=null;
	
	@FXML
    private TextField phoneNumberTextField;
    @FXML
    private TextField customerNameTextFiled;
    @FXML
    private TextField refundTextField;
    @FXML
    private Button handleButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea complainTextField;
  //===============================================================================================================
    @FXML
    void onHandleButton(ActionEvent event) {

    }
  //===============================================================================================================
    @FXML
    void onCancelButton(ActionEvent event) {

    }
  //===============================================================================================================
    public void doInit() 
    {
		
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
