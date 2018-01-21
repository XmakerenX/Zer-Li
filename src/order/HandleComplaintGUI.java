package order;

import java.util.ArrayList;

import Server.ProtoTypeServer;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import prototype.FormController;
import serverAPI.Response;
import user.User;
import user.UserController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class HandleComplaintGUI extends FormController implements ClientInterface{

	Response response = null;
	User user;
	OrderComplaint complaint;

	
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
    @FXML
    private TextField orderPriceTextFiled;
  //===============================================================================================================
    @FXML
    void onHandleButton(ActionEvent event) {
    	
    	System.out.println(complaint);
		Alert alert = new Alert(AlertType.WARNING, "Are you sure the complaint has been taken care of?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		{
			if(!refundTextField.getText().isEmpty())
				complaint.setComplaintCompensation(Float.valueOf(refundTextField.getText()));
			
			else
				complaint.setComplaintCompensation(0);
	    	OrderComplaintController.handleOrderComplaint(String.valueOf(complaint.getComplaintID()), complaint);
	    	waitForServerResponse();
	    	if(response.getType() == Response.Type.SUCCESS)			//updating complaint in data base success
	    	{
	    		CustomerController.getCertainCustomers("personID", complaint.getCustomerID()+"", client);
	    		waitForServerResponse();
	    		if(response.getType() == Response.Type.SUCCESS)				//we got the right customer
	    		{
	    			Customer customer = ((ArrayList<Customer>)response.getMessage()).get(0);
		    		customer.setAccountBalance(customer.getAccountBalance()+complaint.getComplaintCompensation());
		    		CustomerController.updateCustomerDetails(customer, complaint.getCustomerID()+"", client);
		    		waitForServerResponse();
		    		if(response.getType() == Response.Type.SUCCESS)				//refunding succeed
		    		{
		    			Alert alert2 = new Alert(AlertType.CONFIRMATION, "A refund has been issued successfuly!", ButtonType.OK);
			    		alert2.showAndWait();
		    			//clearing response
			    		response = null;
		    		}
		    		else if(response.getType() == Response.Type.ERROR)			//refunding failed
		    		{
		    			Alert alert2 = new Alert(AlertType.ERROR, "Could not update user balance!", ButtonType.OK);
			    		alert2.showAndWait();
		    			//clearing response
			    		response = null;
		    		}
	    		}
	    		else if(response.getType() == Response.Type.ERROR)				//we didn't get the customer
	    		{
	    			Alert alert2 = new Alert(AlertType.ERROR, "Could not find the customer!", ButtonType.OK);
		    		alert2.showAndWait();
	    			//clearing response
		    		response = null;
	    		}
		    	//clearing response
	    		response = null;
	    	}
	    	else if(response.getType() == Response.Type.ERROR)		//updating complaint in database failed
	    	{
	    		Alert alert2 = new Alert(AlertType.ERROR, "Could not update complaint!", ButtonType.OK);
	    		alert2.showAndWait();
	    		//clearing response
	    		response = null;
	    	}
		} else return;
    }
    //===============================================================================================================

  public void initialize(){
	  refundTextField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, 
	        String newValue) 
	    {
	    	if(newValue.isEmpty() || (Float.valueOf(newValue) >= 0 && Float.valueOf(newValue) <= complaint.getMaxCompensationAmount()))
	    		refundTextField.setText(newValue);
	    	else
	    		refundTextField.setText(oldValue);
	    }
	});
  }
 	//===============================================================================================================
    @FXML
    void onCancelButton(ActionEvent event) {
    	customerNameTextFiled.clear();
    	phoneNumberTextField.clear();
    	complainTextField.clear();
    	orderPriceTextFiled.clear();
    	
    	ComplaintManageGUI complaintManageGUI = (ComplaintManageGUI)parent;
    	client.setUI(complaintManageGUI);
    	complaintManageGUI.doInit();
    	FormController.primaryStage.setScene(parent.getScene());
    }
  //===============================================================================================================
    public void doInit() 
    {
    	customerNameTextFiled.setText(complaint.getCustomerName());
    	phoneNumberTextField.setText(complaint.getCustomerPhoneNum());
    	complainTextField.setText(complaint.getComplaintDescription());
    	orderPriceTextFiled.setText(complaint.getMaxCompensationAmount() + " nis");
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
  //===============================================================================================================
  	public void setUser(User user)
  	{
  		this.user = user;
  	}
    //===============================================================================================================
  	public void setComplaint(OrderComplaint complaint) {
  		this.complaint=complaint;
  	}
}
