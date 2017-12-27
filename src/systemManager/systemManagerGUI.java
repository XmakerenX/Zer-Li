package systemManager;

import java.io.IOException;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import serverAPI.Response;
import user.LoginGUI;
import user.NewUserCreationGUI;



public class systemManagerGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;
	
	NewUserCreationGUI userCreationGUI;
	
	@FXML // fx:id="welcomeLbl"
	private Label welcomeLbl;

	@FXML // fx:id="createUserBtn"
	private Button createUserBtn; 

	@FXML // fx:id="backBtn"
	private Button backBtn;

	
	
    @FXML
    void onBack(ActionEvent event) {
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }

    @FXML
    void onCreateNewUser(ActionEvent event) {
        //Will be called by FXMLLoader
    	try
    	{
    		userCreationGUI = FormController.<NewUserCreationGUI, AnchorPane>loadFXML(getClass().getResource("/user/NewUserCreationGUI.fxml"), this);
    	} catch(IOException e)
    	{
    		System.out.println("Failed to load NewUserCreationGUI.fxml");
    		e.printStackTrace();
    		userCreationGUI = null;
    	}
    	
		if ( userCreationGUI != null)
		{
			userCreationGUI.setClinet(client);
			FormController.primaryStage.setScene(userCreationGUI.getScene());
		}
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


}
