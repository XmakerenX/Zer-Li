package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import prototype.FormController;
import serverAPI.Response;
import user.LoginGUI;
import user.User;
import user.UserController;

public class StoreManagerGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;
	
	//Current user's name
	private User user;
	
    @FXML // fx:id="welcomeLbl"
    private Label welcomeLbl; 

    @FXML // fx:id="viewReportsBtn"
    private Button viewReportsBtn;

    @FXML // fx:id="logOutBtn"
    private Button logOutBtn;

    @FXML // fx:id="createCustomerBtn"
    private Button createCustomerBtn;

    @FXML
    void onViewReports(ActionEvent event) {

    }

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

	@Override
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

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}

}

