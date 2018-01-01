package user;

import client.Client;
import client.ClientInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

public class NewUserCreationGUI extends FormController implements ClientInterface {
	
	// holds the last replay we got from server
	private Response replay = null;
	private boolean usernameFlag = false;
	private boolean passwordFlag = false;
	private boolean personIDFlag = false;

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
    
    /**
     * Initializes the combo box of permissions
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){

		permissionComboBox.setItems(permissionsList);
		
    }

    /**
     * Creates user, if such doesn't exists already
     * @param event - "Create user" button is pressed
     */
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
    		
//        	String userName = usernameTxtField.getText();
//        	String userPassword = passwordTxtField.getText();
//        	String userPermission = permissionTxtField.getText();
//        	String personID = personIDTxtField.getText();
//        	
//        	if(userName != null && userPassword != null && userPermission != null && personID != null)
//        	{
//            	UserController.createNewUser(userName, userPassword, User.Permissions.valueOf(userPermission), Integer.parseInt(personID), client);
//        	}
    	}
    	
    	}catch(InterruptedException e) {}
    	

    }
    
    /**
     * Returns to previous GUI
     * @param event - "Back" button is pressed
     */
    @FXML
    void onBack(ActionEvent event) 
    {
    	SystemManagerGUI sysManagerGUI = (SystemManagerGUI)parent;
    	client.setUI(sysManagerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    

    
//    @FXML
//    void onCheckUsernameLength(ActionEvent event) {
//    	String username = usernameTxtField.getText();
//    	
//    	if(username.length() < 6)
//    	{
//    		okLbl1.setVisible(false);
//    		tooShortLbl1.setVisible(true);
//    		usernameFlag = false;	
//    	}
//    	else
//    	{
//    		tooShortLbl1.setVisible(false);
//    		okLbl1.setVisible(true);
//    		usernameFlag = true;
//    	}
//    }
    
	@Override
	public void onSwitch(Client newClient) {


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

