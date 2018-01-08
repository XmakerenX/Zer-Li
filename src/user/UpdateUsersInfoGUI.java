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
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.SystemManagerGUI;
import prototype.FormController;
import serverAPI.Response;
import user.User.UserException;

import java.util.ArrayList;

public class UpdateUsersInfoGUI extends FormController implements ClientInterface {

	
	private Response replay = null;
	
	//Current User's entity with all attributes for further updating
	User userToUpdate;
	String formerUsername;
	
	//List of permissions for the combo box
	ObservableList<String> permissionsList = FXCollections.observableArrayList("Customer", "Store worker", "Store manager", "Network worker",
			"Network manager", "Customer service", "Customer service expert",
			"Customer service worker", "System manager");

    @FXML // fx:id="newUsernameTxtField"
    private TextField newUsernameTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="personIDTxtField"
    private TextField personIDTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="newAccoundBalanceTxtField"
    private TextField newAccoundBalanceTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="updateBtn"
    private Button updateBtn; // Value injected by FXMLLoader

    @FXML // fx:id="newPasswordTxtField"
    private TextField newPasswordTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="usersStatusLbl"
    private Label usersStatusLbl; // Value injected by FXMLLoader				

    @FXML // fx:id="newCustomersInfoLbl"
    private Label newCustomersInfoLbl; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberLbl"
    private Label phoneNumberLbl; // Value injected by FXMLLoader

    @FXML // fx:id="newFullNameTxtField"
    private TextField newFullNameTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="paymentMethodLbl"
    private Label paymentMethodLbl; // Value injected by FXMLLoader

    @FXML // fx:id="accoundBalanceTxtField"
    private TextField accoundBalanceTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="unsuccessfulTriesTxtField"
    private TextField unsuccessfulTriesTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="newAccountStatusComboBox"
    private ComboBox<?> newAccountStatusComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="newCreditCardNumberTxtField"
    private TextField newCreditCardNumberTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="newUnsuccessfulTriesTxtField"
    private TextField newUnsuccessfulTriesTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="newCustomersPersonIDTxtField"
    private TextField newCustomersPersonIDTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="findUserNameBtn"
    private Button findUserNameBtn; // Value injected by FXMLLoader

    @FXML // fx:id="passwordLbl"
    private Label passwordLbl; // Value injected by FXMLLoader

    @FXML // fx:id="personIDLbl"
    private Label personIDLbl; // Value injected by FXMLLoader

    @FXML // fx:id="windowLbl"
    private Label windowLbl; // Value injected by FXMLLoader

    @FXML // fx:id="customersTab"
    private Tab customersTab; // Value injected by FXMLLoader

    @FXML // fx:id="accountStatusTxtField"
    private TextField accountStatusTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="paymentMethodTxtField"
    private TextField paymentMethodTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="creditCardNumberTxtField"
    private TextField creditCardNumberTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="findUserNameTxtField"
    private TextField findUserNameTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="permissionLbl"
    private Label permissionLbl; // Value injected by FXMLLoader

    @FXML // fx:id="usernameTxtField"
    private TextField usernameTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="customersHorizontalSeparator"
    private Separator customersHorizontalSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="newPaymentMethodComboBox"
    private ComboBox<?> newPaymentMethodComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="findUserNameLbl"
    private Label findUserNameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="usersTab"
    private Tab usersTab; // Value injected by FXMLLoader

    @FXML // fx:id="newPermissionComboBox"
    private ComboBox<String> newPermissionComboBox; // Value injected by FXMLLoader

    @FXML // fx:id="creditCardNumberLbl"
    private Label creditCardNumberLbl; // Value injected by FXMLLoader

    @FXML // fx:id="accountStatusLbl"
    private Label accountStatusLbl; // Value injected by FXMLLoader

    @FXML // fx:id="newPersonIDTxtField"
    private TextField newPersonIDTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTxtField"
    private TextField passwordTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="usersVerticalSeparator"
    private Separator usersVerticalSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameTxtField"
    private TextField fullNameTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="customersPersonIDLbl"
    private Label customersPersonIDLbl; // Value injected by FXMLLoader

    @FXML // fx:id="customersPersonIDTxtField"
    private TextField customersPersonIDTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="unsuccessfulTriesLbl"
    private Label unsuccessfulTriesLbl; // Value injected by FXMLLoader

    @FXML // fx:id="usersStatusTxtField"
    private TextField usersStatusTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="usersHorizontalSeparator"
    private Separator usersHorizontalSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="newUsersStatusTxtField"
    private TextField newUsersStatusTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="usernameLbl"
    private Label usernameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="permissionTxtField"
    private TextField permissionTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="newUsersInfoLbl"
    private Label newUsersInfoLbl; // Value injected by FXMLLoader

    @FXML // fx:id="accoundBalanceLbl"
    private Label accoundBalanceLbl; // Value injected by FXMLLoader

    @FXML // fx:id="customersCurrentInfoLbl"
    private Label customersCurrentInfoLbl; // Value injected by FXMLLoader

    @FXML // fx:id="customersVerticalSeparator"
    private Separator customersVerticalSeparator; // Value injected by FXMLLoader

    @FXML // fx:id="fullNameLbl"
    private Label fullNameLbl; // Value injected by FXMLLoader

    @FXML // fx:id="newPhoneNumberTxtField"
    private TextField newPhoneNumberTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberTxtField"
    private TextField phoneNumberTxtField; // Value injected by FXMLLoader

    @FXML // fx:id="usersCurrentInfoLbl"
    private Label usersCurrentInfoLbl; // Value injected by FXMLLoader

    
    /**
     * Initializes the combo box of permissions
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){

    	newPermissionComboBox.setItems(permissionsList);
    	
    }
    
    /**
     * Finds specific user in database and brings its entity
     * @param event - "Find user" button is pressed
     */
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
    	    userToUpdate = (User)((ArrayList<?>) replay.getMessage()).get(0);
    		
    		//Filling the GUI's User's tab with info
    		formerUsername = ""+userToUpdate.getUserName();
    		usernameTxtField.setText(""+userToUpdate.getUserName());
    		passwordTxtField.setText(""+userToUpdate.getUserPassword());
    		permissionTxtField.setText(""+userToUpdate.getUserPermission());
    		personIDTxtField.setText(""+userToUpdate.getPersonID());
    		usersStatusTxtField.setText(""+userToUpdate.getUserStatus());
    		unsuccessfulTriesTxtField.setText(""+userToUpdate.getUnsuccessfulTries());
    		
    		//Filling the GUI's Customer's tab with info
    		/*	HERE WILL BE CUSTOMER'S*/

    	}
    	else
    	{
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, "User doesn't exists!", ButtonType.OK);
    		alert.showAndWait();

    	}
    	
    	}catch(InterruptedException e) {}
     	
     	// clear replay
     	replay = null;
    }
    
    /**
     * Changes specific user's entry in database table
     * @param event - "Update" button is pressed
     */
    @FXML
    void onUpdate(ActionEvent event) {
    	
    	String [] splittedPermission;
    	String temporaryPermission = "";
    	
    	if( !newUsernameTxtField.getText().equals("") ||  !newPasswordTxtField.getText().equals("") || newPermissionComboBox.getValue() != null
    			|| !newPersonIDTxtField.getText().equals("") )
    	{
    		
			try {
		   		if(!newUsernameTxtField.getText().equals(""))
		   			userToUpdate.setUserName(""+newUsernameTxtField.getText());
		   		
		   		if(!newPasswordTxtField.getText().equals(""))
		   			userToUpdate.setUserPassword(""+newPasswordTxtField.getText());
		   		
		   		if(newPermissionComboBox.getValue() != null)
		   		{
		   			
		   			splittedPermission = newPermissionComboBox.getValue().split(" ");
		   			for(String permission : splittedPermission )
		   			{
		   				if( !temporaryPermission.equals(""))
		   					temporaryPermission = temporaryPermission + "_";
		   				temporaryPermission = temporaryPermission + permission.toUpperCase();
		   			}
		   			
		   			userToUpdate.setUserPermission(User.Permissions.valueOf(temporaryPermission));
		   		}
		   		
		   		if(!newPersonIDTxtField.getText().equals(""))
		   			userToUpdate.setPersonID(Integer.parseInt(newPersonIDTxtField.getText()));
		   			
			} catch (UserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    			
	    	UserController.updateUserDetails(userToUpdate, formerUsername, client);
	    	clearFieldsMethod();
	    	
	     	try
	    	{
	    		synchronized(this)
	    		{
	    			// wait for server response
	    			this.wait();
	    		}
	    	
	    		if (replay == null)
	    			return;
	    		
	    	if (replay.getType() == Response.Type.SUCCESS)
	    	{
	        	// show success  
	    		Alert alert = new Alert(AlertType.INFORMATION, "User's info is successfully updated!", ButtonType.OK);
	    		alert.showAndWait();

	    	}
	    	else
	    	{
	        	// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "The update is failed!", ButtonType.OK);
	    		alert.showAndWait();

	    	}
	    	
	    	}catch(InterruptedException e) {}
	     	
    		// clear replay
    		replay = null;
    	}
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
	
	public void clearFieldsMethod()
	{
		usernameTxtField.setText("");
		passwordTxtField.setText("");
		permissionTxtField.setText("");
		personIDTxtField.setText("");
		usersStatusTxtField.setText("");
		unsuccessfulTriesTxtField.setText("");
		newUsernameTxtField.setText("");
		newPasswordTxtField.setText("");
		newPermissionComboBox.setValue(null);
	    newPersonIDTxtField.setText("");
	}

}

