package networkGUI;

import java.util.ArrayList;
import java.util.HashMap;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import report.ViewReportsGUI;
import serverAPI.GetEmployeeStoreRequest;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import store.StoreEmployee;
import user.LoginGUI;
import user.UpdateUsersInfoGUI;
import user.User;
import user.UserController;

public class StoreManagerGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;
	
	//Current user's name
	private User user;
	
	ViewReportsGUI viewReportsGUI;
	
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
    	
    }
    
    /**
     * Displays reports' menu GUI
     * @param event - "View reports" button is pressed
     */

	@FXML
	void onViewReports(ActionEvent event) {

		if (viewReportsGUI != null) {
			
			client.handleMessageFromClientUI(new GetEmployeeStoreRequest(user.getUserName()));

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
					
				} else {
					// show failure
					Alert alert = new Alert(AlertType.ERROR, "\"Store\" table is empty!", ButtonType.OK);
					alert.showAndWait();
					// clear replay
				}

			} catch (InterruptedException e) {
			}
			
			replay = null;
			
			viewReportsGUI.setClinet(client);
			client.setUI(viewReportsGUI);
			FormController.primaryStage.setScene(viewReportsGUI.getScene());
			
		}

	}

    /**
     * Display customer creation GUI
     * @param event - "Create new customer" button is pressed
     */
    
    @FXML
    void onCreateCustomer(ActionEvent event) {

    }

    /**
     * Logs the user out of the system
     * @param event - "Log out" button is pressed
     */
    @FXML
    void onLogOut(ActionEvent event) {
    	
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }

	
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

	
	public void onSwitch(Client newClient) {
		
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

}

