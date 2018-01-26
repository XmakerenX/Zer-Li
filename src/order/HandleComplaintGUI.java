package order;

import java.util.ArrayList;

import Server.ProtoTypeServer;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import prototype.FormController;
import serverAPI.GetRequestWhere;
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
/**
 * a class that holds all the needed functions for the handle Complaint GUI, which takes care of a selected complaint
 * @author dk198
 *
 */
public class HandleComplaintGUI extends FormController implements ClientInterface{

	//class variables:
	Response response = null;
	User user;
	OrderComplaint complaint;

	//gui:
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
    /**
     * runs when the handle button is pressed, refunds the customer and changes the complaint status to closed
     * @param event
     */
    void onHandleButton(ActionEvent event) {
    	try
    	{
    	System.out.println(complaint);
		Alert alert = new Alert(AlertType.WARNING, "Are you sure the complaint has been taken care of?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		{
			if(!refundTextField.getText().isEmpty())
				complaint.setComplaintCompensation(Float.valueOf(refundTextField.getText()));
			
			else
				complaint.setComplaintCompensation(0);
	    	OrderComplaintController.handleOrderComplaint(String.valueOf(complaint.getComplaintID()), complaint.getOrderComplaint());
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
		    			OrderController.getOrdersOfaUser(Long.toString(complaint.getCustomerID()));
		    			
		    			
		    			waitForServerResponse();
		    			System.out.println("");
		    			
		    			
		    			ArrayList<Order> customerOrders = (ArrayList<Order>)response.getMessage();
		    			
		    			Order myOrder = null;
		    			for(Order o : customerOrders)
		    			{
		    				if(o.getID() == complaint.getOrderID())
		    				{
		    					myOrder = o;
		    				}
		    			}
		    			myOrder.setRefund(Float.valueOf(refundTextField.getText()));
		    			OrderController.updateOrder(myOrder.orderID, myOrder);
			    		waitForServerResponse();

			    		if(response.getType() == Response.Type.SUCCESS)
						{
			    			Alert alert2 = new Alert(AlertType.CONFIRMATION, "A refund has been issued successfuly!", ButtonType.OK);
			    			alert2.showAndWait();
						}
						else
						{
			    			Alert alert2 = new Alert(AlertType.ERROR, "Could not update Order refund", ButtonType.OK);
				    		alert2.showAndWait();
						}
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
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		throw e;
    	}
    }
    //===============================================================================================================

    /**
     * changes the default listener
     */
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
    /**
     * going back to the previous GUI when "back" is pressed
     * @param event
     */
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
    /**
     * initializes customer info
     */
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
