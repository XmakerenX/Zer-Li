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
import report.ViewDifferentReportsGUI;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import user.LoginGUI;
import user.UpdateUsersInfoGUI;
import user.User;
import user.UserController;

public class NetworkManagerGUI extends  FormController implements ClientInterface{

	private Response replay = null;
	
	//Current user's name
	private User user;
	
	ViewDifferentReportsGUI viewDifferentReportsGUI;
	
	//Temporary ArrayList of stores that will be set to ViewDifferentReportsGUI on switch
	private ArrayList<Store> storesList;
	
    @FXML // fx:id="welcomeLbl"
    private Label welcomeLbl; // Value injected by FXMLLoader

    @FXML // fx:id="viewDifferentReportsBtn"
    private Button viewDifferentReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="logOutBtn"
    private Button logOutBtn; // Value injected by FXMLLoader

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	viewDifferentReportsGUI = FormController.<ViewDifferentReportsGUI, AnchorPane>loadFXML(getClass().getResource("/report/ViewDifferentReportsGUI.fxml"), this);
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

    /**
     * Displays different reports' menu GUI
     * @param event - "View different reports" button is pressed
     */
    @SuppressWarnings("unchecked")
	@FXML
    void onViewDifferentReports(ActionEvent event) {
		
    	if(viewDifferentReportsGUI != null)
    	{
    		client.handleMessageFromClientUI(new GetRequest("Store"));
    		
    		try
	    	{
	    		synchronized(this)
	    		{
	    			// wait for server response
	    			this.wait();
	    		}
	    	
	    		if (replay == null)
	    			return;
	    		
	    	// show success 
	    	if (replay.getType() == Response.Type.SUCCESS)
	    	{
	    		HashMap<String, Long> tempHashMap = new HashMap<String, Long>();
	    		storesList = (ArrayList<Store>) replay.getMessage();
	    		
	    		for(Store store : storesList)
	    			if(!store.getStoreAddress().equals("Base"))
	    				tempHashMap.put(store.getStoreAddress(), store.getStoreID());

	    		viewDifferentReportsGUI.setStores(tempHashMap);
	    		viewDifferentReportsGUI.setComboBoxes();
	    	}
	    	else
	    	{
	        	// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "\"Store\" table is empty!", ButtonType.OK);
	    		alert.showAndWait();
	    		// clear replay
	    	}
	    	
			replay = null;
			
	    	}catch(InterruptedException e) {}
		
			viewDifferentReportsGUI.setClinet(client);
			client.setUI(viewDifferentReportsGUI);
			FormController.primaryStage.setScene(viewDifferentReportsGUI.getScene());
    	}
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

		
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
