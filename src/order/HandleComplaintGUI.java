package order;

import java.util.Optional;

import catalog.CatalogController;
import catalog.EditableCatalogItemView;
import client.Client;
import client.ClientInterface;
import customer.CustomerView;
import prototype.FormController;
import serverAPI.Response;
import user.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import product.ProdcutController;
import product.EditableProductView.EditableProductViewButton;

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
    	
		Alert alert = new Alert(AlertType.WARNING, "Are you sure the complaint has been taken care of?", ButtonType.YES, ButtonType.NO);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES)
		{
			if(!refundTextField.getText().isEmpty())
				complaint.setComplaintCompensation(Float.valueOf(refundTextField.getText()));
			else
				complaint.setComplaintCompensation(0);
	    	OrderComplaintController.handleOrderComplaint(String.valueOf(complaint.getComplaintID()), complaint);
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
