package order;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import networkGUI.CustomerServiceWorkerGUI;
import prototype.FormController;
import serverAPI.Response;
import user.User;

public class SelectOrderForComplaintGUI extends FormController implements ClientInterface{

	User user;
	Response response=null;
	Customer customer;
	
	//==============================================================================================================
    @FXML
    private TableColumn<?, ?> selectCulomn;

    @FXML
    private TableView<?> orderTable;

    @FXML
    private Button backButton;
    
    @FXML
    private Button testCustomer;

  //==============================================================================================================
    @FXML
    void onTestCustomer(ActionEvent event) {
    	Alert alert = new Alert(AlertType.INFORMATION, "Customer received: "+customer, ButtonType.OK);
		alert.showAndWait();

    }
  //==============================================================================================================
    @FXML
    void onBackButton(ActionEvent event) {
    	orderTable.getItems().clear();
    	
    	ComplaintCreationGUI complaintCreationGUI = (ComplaintCreationGUI)parent;
    	client.setUI(complaintCreationGUI);
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
  	//==============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	//==============================================================================================================
  	public void setUser(User user)
  	{
  		this.user = user;
  	}
  //==============================================================================================================
  	public void setCustomer(Customer customer)
  	{
  		this.customer = customer;
  	}
}
