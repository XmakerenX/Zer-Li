package order;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import networkGUI.CustomerServiceWorkerGUI;
import prototype.FormController;
import serverAPI.Response;

public class ComplaintManageGUI extends FormController implements ClientInterface{

	Response response = null;
	
    @FXML
    private TableColumn<OrderComplaintView, ?> customerIDColumn;

    @FXML
    private TableColumn<OrderComplaintView, ?> storeIDComlumn;

    @FXML
    private TableColumn<OrderComplaintView, ?> complaintTimeColumn;

    @FXML
    private TableColumn<OrderComplaintView, ?> complaintDateColumn;

    @FXML
    private Button backButton;

    @FXML
    private TableView<OrderComplaintView> complaintTable;

    @FXML
    private TableColumn<OrderComplaintView, ?> selectColumn;

    @FXML
    void onBackButton(ActionEvent event) {
    	CustomerServiceWorkerGUI customerServiceWorkerGUI = (CustomerServiceWorkerGUI)parent;
    	client.setUI(customerServiceWorkerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
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
