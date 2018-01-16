package order;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceWorkerGUI;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class NewComplaintCreationGUI extends FormController implements ClientInterface{
	
	Customer customer;
	Order order;
	User user;
	Response response=null;
	
    //===============================================================================================================

	 @FXML
    private Button cancelButton;

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

    @FXML
    void onCreateButton(ActionEvent event) {

    }

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

	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}

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


}
