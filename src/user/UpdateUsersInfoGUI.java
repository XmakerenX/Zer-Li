package user;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.SystemManagerGUI;
import prototype.FormController;
import serverAPI.Response;
import java.util.ArrayList;

public class UpdateUsersInfoGUI extends FormController implements ClientInterface {

	
	private Response replay = null;
	
    @FXML // fx:id="findUserNameTxtField"
    private TextField findUserNameTxtField;

    @FXML // fx:id="findUserNameLbl"
    private Label findUserNameLbl;

    @FXML // fx:id="findUserNameBtn"
    private Button findUserNameBtn;
    
    @FXML
    private Button backBtn;

    @FXML
    void onFindUser(ActionEvent event) {
    	
    	String userName = findUserNameTxtField.getText();
    	
    	UserController.getUser(userName, client);
    	
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
    		User user = (User)((ArrayList<?>) replay.getMessage()).get(0);
    		String returnedUserName = user.getUserName();
    		
        	// show failure  
    		Alert alert = new Alert(AlertType.CONFIRMATION, returnedUserName+" is found!", ButtonType.OK);
    		alert.showAndWait();
    		// clear replay
    		replay = null;
    	}
    	else
    	{
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, "User doesn't exists!", ButtonType.OK);
    		alert.showAndWait();
    		// clear replay
    		replay = null;
    	}
    	
    	}catch(InterruptedException e) {}
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	SystemManagerGUI sysManagerGUI = (SystemManagerGUI)parent;
    	client.setUI(sysManagerGUI);
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
		
		
	}

}

