package networkGUI;

import java.io.IOException;

import catalog.CatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import serverAPI.CheckExistsRequest;
import serverAPI.RemoveRequest;
import serverAPI.Response;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.UpdateUsersInfoGUI;
import user.User;
import user.UserController;



public class SystemManagerGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;
	
	//Current user's name
	private User user;
	
	//NewUserCreationGUI userCreationGUI;
	UpdateUsersInfoGUI updateUserGUI;
	
	@FXML // fx:id="welcomeLbl"
	private Label welcomeLbl;

//	@FXML // fx:id="createUserBtn"
//	private Button createUserBtn; 
	
    @FXML
    private Button updateUserBtn;
    
    @FXML // fx:id="removeUserBtn"
    private Button removeUserBtn;
    
	@FXML // fx:id="logOutBtn"
	private Button logOutBtn;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    //	userCreationGUI = FormController.<NewUserCreationGUI, AnchorPane>loadFXML(getClass().getResource("/user/NewUserCreationGUI.fxml"), this);
    	updateUserGUI = FormController.<UpdateUsersInfoGUI, AnchorPane>loadFXML(getClass().getResource("/user/UpdateUsersInfoGUI.fxml"), this);
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

//    @FXML
//    void onCreateNewUser(ActionEvent event) {    	
//    	
//		if ( userCreationGUI != null)
//		{
//			userCreationGUI.setClinet(client);
//			client.setUI(userCreationGUI);
//			FormController.primaryStage.setScene(userCreationGUI.getScene());
//		}
//    }
    
    @FXML
    void onUpdateUser(ActionEvent event) {
    	
		if ( updateUserGUI != null)
		{
			updateUserGUI.setClinet(client);
			client.setUI(updateUserGUI);
			FormController.primaryStage.setScene(updateUserGUI.getScene());
		}
    }
    
    @FXML
    void onRemoveUser(ActionEvent event) 
    {
    	TextInputDialog getInputDialog = new TextInputDialog();
    	getInputDialog.setHeaderText("Enter username to remove");
    	getInputDialog.showAndWait();
    	String username = getInputDialog.getResult();
    	
    	UserController.getUser(username, client);
    	
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
        	Alert alert = new Alert(AlertType.INFORMATION);
        	alert.setTitle("User deletion");
        	alert.setHeaderText(username + " is successfully deleted");
        	alert.showAndWait();
        	UserController.RemoveUser(username, client);
    	}
    	else
    	{
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, username + " doesn't exists", ButtonType.OK);
        	alert.setTitle("User deletion");
    		alert.showAndWait();
    		// clear replay
    	}
    	
		replay = null;
		
    	}catch(InterruptedException e) {}
    	
    }
    
    
    
	public void onSwitch(Client newClient)
	{
		
	}
	
	
	public void display(Object message)
	{
		
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
