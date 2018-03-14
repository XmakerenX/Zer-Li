package networkGUI;

import client.Client;
import client.ClientInterface;
import customer.NewCustomerCreationGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import report.ViewReportsGUI;
import serverAPI.GetEmployeeStoreRequest;
import serverAPI.Response;
import user.LoginGUI;
import user.User;
import user.UserController;
import utils.FormController;

//*************************************************************************************************
	/**
	*  Provides a GUI to handle Store Manager actions: view report, create new customer
	*/
//*************************************************************************************************

public class StoreManagerGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;
	
	//Current user's name
	private User user;
	
	ViewReportsGUI viewReportsGUI;
	NewCustomerCreationGUI customerCreationGUI;
	
    @FXML // fx:id="welcomeLbl"
    private Label welcomeLbl; 

    @FXML // fx:id="viewReportsBtn"
    private Button viewReportsBtn;

    @FXML // fx:id="logOutBtn"
    private Button logOutBtn;

    @FXML // fx:id="createCustomerBtn"
    private Button createCustomerBtn;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
    	viewReportsGUI = FormController.<ViewReportsGUI, AnchorPane>loadFXML(getClass().getResource("/report/ViewReportsGUI.fxml"), this);
    	customerCreationGUI = FormController.<NewCustomerCreationGUI, AnchorPane>loadFXML(getClass().getResource("/customer/NewCustomerCreationGUI.fxml"), this);
    	
    }
    
    /**
     * Displays reports' menu GUI which allows to view reports from manager's store
     * @param event - "View reports" button is clicked
     */

	@FXML
	void onViewReports(ActionEvent event) {
		
		if (viewReportsGUI != null) {

			Client.getInstance().handleMessageFromClientUI(
					new GetEmployeeStoreRequest(user.getUserName()));

			try {
				synchronized (this) {
					// wait for server response
					this.wait();
				}

				if (replay == null)
					return;

				// show success
				if (replay.getType() == Response.Type.SUCCESS) {

					Integer storeID = (Integer) replay.getMessage();
					viewReportsGUI.setManagersStoreID((long)storeID);
					
					Client.getInstance().setUI(viewReportsGUI);
					FormController.primaryStage.setScene(viewReportsGUI.getScene());
					
				} else
					showErrorMessage("\"Store\" table is empty!");

			} catch (InterruptedException e) {
			}
			
			replay = null;
		}
	}

    /**
     * Display customer creation GUI which allows to enter new customer's info
     * @param event - "Create new customer" button is clicked
     */
    
    @FXML
    void onCreateCustomer(ActionEvent event) {
		
		if (customerCreationGUI != null) {

			Client.getInstance().handleMessageFromClientUI
			(new GetEmployeeStoreRequest(user.getUserName()));

			try {
				synchronized (this) {
					// wait for server response
					this.wait();
				}

				if (replay == null)
					return;

				// show success
				if (replay.getType() == Response.Type.SUCCESS) {

					Integer storeID = (Integer) replay.getMessage();
					customerCreationGUI.setManagersStoreID((long)storeID);
					Client.getInstance().setUI(customerCreationGUI);
					FormController.primaryStage.setScene(customerCreationGUI.getScene());
					
				} else
					showErrorMessage("\"Store\" table is empty!");

			} catch (InterruptedException e) {
			}
			
			replay = null;
		}
    }

    /**
     * Logs the user out of the system
     * @param event - "Log out" button is clicked
     */
    @FXML
    void onLogOut(ActionEvent event) {
    	
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, Client.getInstance());
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	Client.getInstance().setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }

	/**
	 * Display reply message from server
	 */
	public void display(Object message) {
		
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
		
		Response replay = (Response)message;
		this.replay = replay;
		
		synchronized(this)
		{
			this.notify();
		}
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

}

