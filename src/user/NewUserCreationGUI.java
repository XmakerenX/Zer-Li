package user;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import serverAPI.Response;
import systemManager.SystemManagerGUI;

public class NewUserCreationGUI extends FormController {
	
	// holds the last replay we got from server
	private Response replay = null;

	ObservableList<String> permissionsList = FXCollections.observableArrayList("Customer", "Store worker", "Store manager", "Network worker",
																				"Network manager", "Customer service", "Customer service expert",
																				"Customer service worker", "System manager");
	
    @FXML
    private Label permissionLbl;

    @FXML
    private TextField usernameTxtField;

    @FXML
    private TextField personIDTxtField;

    @FXML
    private Label okLbl1;

    @FXML
    private Label okLbl3;

    @FXML
    private Label tooShortLbl2;

    @FXML
    private Label okLbl2;

    @FXML
    private Button backBtn;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label invCharacterLbl;

    @FXML
    private Label okLbl4;

    @FXML
    private ComboBox<String> permissionComboBox;

    @FXML
    private Label tooShortLbl1;

    @FXML
    private Label confirmPasswordLbl;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label notMatchLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label descriptionLbl;

    @FXML
    private Label personIDLbl;

    @FXML
    private Button createUserBtn;

    @FXML
    private PasswordField passwordField;
    
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){

		permissionComboBox.setItems(permissionsList);
		
    }

    @FXML
    void onCreateUser(ActionEvent event) {
    	
    	String userName = usernameTxtField.getText();
    	
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
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, "User already exists", ButtonType.OK);
    		alert.showAndWait();
    		// clear replay
    		replay = null;
    	}
    	else
    	{
    		
    	}
    	
    	}catch(InterruptedException e) {}
    	
//    	String userName = usernameTxtField.getText();
//    	String userPassword = passwordTxtField.getText();
//    	String userPermission = permissionTxtField.getText();
//    	String personID = personIDTxtField.getText();
//    	
//    	if(userName != null && userPassword != null && userPermission != null && personID != null)
//    	{
//        	UserController.createNewUser(userName, userPassword, User.Permissions.valueOf(userPermission), Integer.parseInt(personID), client);
//    	}
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

