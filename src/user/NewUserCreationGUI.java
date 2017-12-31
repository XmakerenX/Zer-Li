package user;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import systemManager.SystemManagerGUI;

public class NewUserCreationGUI extends FormController {

    @FXML
    private Label passwordLbl;

    @FXML
    private Label permissionLbl;

    @FXML
    private Label descriptionLbl;

    @FXML
    private TextField usernameTxtField;

    @FXML
    private TextField personIDTxtField;

    @FXML
    private Label personIDLbl;

    @FXML
    private Button createUserBtn;

    @FXML
    private Button backBtn;
    
    @FXML
    private TextField permissionTxtField;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField passwordTxtField;

    @FXML
    void onCreateUser(ActionEvent event) {
    	
    	String userName = usernameTxtField.getText();
    	String userPassword = passwordTxtField.getText();
    	String userPermission = permissionTxtField.getText();
    	String personID = personIDTxtField.getText();
    	
    	if(userName != null && userPassword != null && userPermission != null && personID != null)
    	{
        	UserController.createNewUser(userName, userPassword, User.Permissions.valueOf(userPermission), Integer.parseInt(personID), client);
    	}
    }
    
    @FXML
    void onBack(ActionEvent event) 
    {
    	SystemManagerGUI sysManagerGUI = (SystemManagerGUI)parent;
    	client.setUI(sysManagerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    
	public void onSwitch(Client newClient)
	{
		
	}

}

