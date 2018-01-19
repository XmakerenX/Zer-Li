package order;

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
import networkGUI.CustomerServiceWorkerGUI;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerView;
import customer.Customer.CustomerException;
import prototype.FormController;
import serverAPI.Response;
import user.User;


//*************************************************************************************************
	/**
	*  Provides a gui to enable the creation of a new complaint
	*/
//*************************************************************************************************
public class NewComplaintCreationGUI extends FormController implements ClientInterface
{
	
	Customer customer;
	Order order;
	User user;
	Response response=null;
	
    //===============================================================================================================

	 @FXML
    private Button cancelButton;

	 @FXML
	private TextField compensationAmountTextFiel;
	 
    @FXML
    private TextField orderPriceTextField;
    @FXML
    private Button checkUserBUtton;

    @FXML
    private Button checkOrderButton;

    @FXML
    private Button createButton;

    @FXML
    private TextArea complaintBodyTextField;

  //===============================================================================================================
    @FXML
    void onCancelButton(ActionEvent event) {
    	complaintBodyTextField.clear();
    	SelectOrderForComplaintGUI selectOrderForComplaintGUI = (SelectOrderForComplaintGUI)parent;
    	client.setUI(selectOrderForComplaintGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    //===============================================================================================================
    @FXML
    void onCreateButton(ActionEvent event) {
    	System.out.println(customer.getStoreID());
    	String complaint = complaintBodyTextField.getText();
    	if(!complaint.isEmpty()) 
    	{
	    	LocalDate todayLocalDate = LocalDate.now();
	    	String time = new SimpleDateFormat("HH:mm").format(new Date());
	    	OrderComplaintController.addNewComplaint(customer.getID(), customer.getName(), customer.getPhoneNumber(), complaint, todayLocalDate, time, (int)order.getOrderOriginStore(),order.getPrice());
	    	waitForServerResponse();
	    	if(response.getType() == Response.Type.SUCCESS)
	    	{
	    		Alert alert = new Alert(AlertType.CONFIRMATION, "Complaint added!", ButtonType.OK);
	    		alert.showAndWait();
		    	//clearing response
	    		response = null;
	    	}
	    	else if(response.getType() == Response.Type.ERROR)
	    	{
	    		Alert alert = new Alert(AlertType.ERROR, "Could not create a new comlaint!", ButtonType.OK);
	    		alert.showAndWait();
	    		//clearing response
	    		response = null;
	    	}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR, "No complaint has been written!", ButtonType.OK);
    		alert.showAndWait();
    	}
    }
    //===============================================================================================================
    @FXML
    void onCheckUser(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Customer(User) received: "+customer, ButtonType.OK);
		alert.showAndWait();

    }

    @FXML
    void onCheckOrder(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Order received: "+order, ButtonType.OK);
		alert.showAndWait();

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
 	public void setCustomer(Customer customer)
  	{
  		this.customer = customer;
  	}
	//===============================================================================================================
 	public void setOrder(Order order)
  	{
  		this.order = order;
  	}
	//===============================================================================================================
 	public void setUser(User user)
  	{
  		this.user = user;
  	}
	//===============================================================================================================
 	public void doInit()
 	{
 		//orderPriceTextField.setText(order.getPrice()+" nis");
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
