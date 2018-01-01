package systemManager;

import java.io.IOException;

import catalog.CatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import networkGUI.NetworkWorkerGUI;
import prototype.FormController;
import serverAPI.CheckExistsRequest;
import serverAPI.RemoveRequest;
import serverAPI.Response;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;



public class SystemManagerGUI extends NetworkWorkerGUI implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;
	
	//Current user's name
	private User user;
	
	NewUserCreationGUI userCreationGUI;
	
	@FXML // fx:id="welcomeLbl"
	private Label welcomeLbl;

	@FXML // fx:id="createUserBtn"
	private Button createUserBtn; 
	
    @FXML // fx:id="removeUserBtn"
    private Button removeUserBtn;
    
	@FXML // fx:id="logOutBtn"
	private Button logOutBtn;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	userCreationGUI = FormController.<NewUserCreationGUI, AnchorPane>loadFXML(getClass().getResource("/user/NewUserCreationGUI.fxml"), this);
    }
	
    @FXML
    void onLogOut(ActionEvent event) {
    	
    	//UserController.requestLogin(usernameTxt.getText(), passwordTxt.getText(), client);
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }

    @FXML
    void onCreateNewUser(ActionEvent event) {    	
    	
		if ( userCreationGUI != null)
		{
			userCreationGUI.setClinet(client);
			client.setUI(userCreationGUI);
			FormController.primaryStage.setScene(userCreationGUI.getScene());
		}
    }
    
    @FXML
    void onRemoveUser(ActionEvent event) 
    {
    	TextInputDialog getInputDialog = new TextInputDialog();
    	getInputDialog.setHeaderText("Enter username to remove");
    	getInputDialog.showAndWait();
    	String username = getInputDialog.getResult();
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Bla");
    	alert.setHeaderText(username);
    	alert.showAndWait();
    	client.handleMessageFromClientUI(new RemoveRequest("User",username));
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
