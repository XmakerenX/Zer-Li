package user;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
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
	
	//Current Customer's entity with all attributes for further updating
	Customer customerToUpdate;
	String formerPersonID;
	
	//List of permissions for the combo box
	ObservableList<String> permissionsList = FXCollections.observableArrayList("Customer", "Store worker", "Store manager", "Network worker",
			"Network manager", "Customer service", "Customer service expert",
			"Customer service worker", "System manager");
	
	//List of user's statuses for the combo box
	ObservableList<String> usersStatusesList = FXCollections.observableArrayList("Regular", "Logged in", "Blocked");
	
	//List of payment methods for the combo box
	ObservableList<String> paymentMethodList = FXCollections.observableArrayList("Credit card", "Subscription");
	
	//List of account statuses for the combo box
	ObservableList<String> accountStatusesList = FXCollections.observableArrayList("Active", "Blocked");
	
	//List of stores' names for the combo box
	ObservableList<String> storesNamesList = FXCollections.observableArrayList(); // HAS TO BE FILLED



    @FXML
    private TextField newUsernameTxtField;

    @FXML
    private TextField personIDTxtField;

    @FXML
    private TextField newAccoundBalanceTxtField;

    @FXML
    private Button updateBtn;

    @FXML
    private TextField newPasswordTxtField;

    @FXML
    private Label usersStatusLbl;

    @FXML
    private Label newCustomersInfoLbl;

    @FXML
    private Label phoneNumberLbl;

    @FXML
    private TextField newFullNameTxtField;

    @FXML
    private CheckBox clearTriesCheckBox;

    @FXML
    private Label paymentMethodLbl;

    @FXML
    private TextField accoundBalanceTxtField;

    @FXML
    private TextField unsuccessfulTriesTxtField;

    @FXML
    private ComboBox<String> newAccountStatusComboBox;

    @FXML
    private TextField newCreditCardNumberTxtField;

    @FXML
    private TextField newCustomersPersonIDTxtField;

    @FXML
    private Button findUserNameBtn;

    @FXML
    private Label passwordLbl;

    @FXML
    private Label personIDLbl;

    @FXML
    private Label windowLbl;

    @FXML
    private Tab customersTab;

    @FXML
    private TextField accountStatusTxtField;

    @FXML
    private Button backBtn;

    @FXML
    private TextField paymentMethodTxtField;

    @FXML
    private TextField creditCardNumberTxtField;

    @FXML
    private TextField findUserNameTxtField;

    @FXML
    private Label permissionLbl;

    @FXML
    private TextField usernameTxtField;

    @FXML
    private Separator customersHorizontalSeparator;

    @FXML
    private ComboBox<String> newPaymentMethodComboBox;

    @FXML
    private Label findUserNameLbl;

    @FXML
    private Tab usersTab;

    @FXML
    private ComboBox<String> newPermissionComboBox;

    @FXML
    private ComboBox<String> newUsersStatusComboBox;

    @FXML
    private Label creditCardNumberLbl;

    @FXML
    private Label accountStatusLbl;

    @FXML
    private TextField newPersonIDTxtField;

    @FXML
    private TextField passwordTxtField;

    @FXML
    private Separator usersVerticalSeparator;

    @FXML
    private TextField fullNameTxtField;

    @FXML
    private Label customersPersonIDLbl;

    @FXML
    private TextField customersPersonIDTxtField;

    @FXML
    private Label storeNamelbl;

    @FXML
    private Label unsuccessfulTriesLbl;

    @FXML
    private TextField usersStatusTxtField;

    @FXML
    private Separator usersHorizontalSeparator;

    @FXML
    private Label usernameLbl;

    @FXML
    private TextField permissionTxtField;

    @FXML
    private ComboBox<String> storeNameComboBox;

    @FXML
    private Label newUsersInfoLbl;

    @FXML
    private Label accoundBalanceLbl;

    @FXML
    private Label customersCurrentInfoLbl;

    @FXML
    private Separator customersVerticalSeparator;

    @FXML
    private Label fullNameLbl;

    @FXML
    private TextField newPhoneNumberTxtField;

    @FXML
    private TextField phoneNumberTxtField;

    @FXML
    private Label usersCurrentInfoLbl;
    
    /**
     * Initializes the combo box of permissions
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){

    	newPermissionComboBox.setItems(permissionsList);
    	newUsersStatusComboBox.setItems(usersStatusesList);
    	newPaymentMethodComboBox.setItems(paymentMethodList);
    	newAccountStatusComboBox.setItems(accountStatusesList);
    	
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
     	
    	String personID = ""+userToUpdate.getPersonID();
    	
    	CustomerController.getCustomer(personID, client);
    	
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
    	    customerToUpdate = (Customer)((ArrayList<?>) replay.getMessage()).get(0);

    		//Filling the GUI's Customer's tab with info
    		formerPersonID= ""+customerToUpdate.getID();
    		customersPersonIDTxtField.setText(""+customerToUpdate.getID());
    		fullNameTxtField.setText(""+customerToUpdate.getName());
    		phoneNumberTxtField.setText(""+customerToUpdate.getPhoneNumber());
    		paymentMethodTxtField.setText(""+customerToUpdate.getPayMethod());
    		accoundBalanceTxtField.setText(""+customerToUpdate.getAccountBalance());
    		creditCardNumberTxtField.setText(""+customerToUpdate.getCreditCardNumber());
    		accountStatusTxtField.setText(""+customerToUpdate.getAccountStatus());

    	}
    	else
    	{
        	// show failure  
    		Alert alert = new Alert(AlertType.ERROR, "This user is not a customer!", ButtonType.OK);
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
    			|| !newPersonIDTxtField.getText().equals("") || newUsersStatusComboBox.getValue() != null)
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
		   		
		   		if(newUsersStatusComboBox.getValue() != null)
		   			userToUpdate.setUserStatus(User.Status.valueOf(newUsersStatusComboBox.getValue().toUpperCase()));
		   		
		   		if(clearTriesCheckBox.isSelected() == true)
		   			userToUpdate.clearUnsuccessfulTries();
		   			
		   			
			} catch (UserException e) {
				e.printStackTrace();
	        	// show failure  
	    		Alert alert = new Alert(AlertType.ERROR, "The update is failed!", ButtonType.OK);
	    		alert.showAndWait();
	    		return;
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
	     	  		
    	}
    	
    	if( !newCustomersPersonIDTxtField.getText().equals("") || !newFullNameTxtField.getText().equals("") || !newPhoneNumberTxtField.getText().equals("")
    			|| newPaymentMethodComboBox.getValue() != null || !newAccoundBalanceTxtField.getText().equals("") 
    			|| !newCreditCardNumberTxtField.getText().equals("") || newAccountStatusComboBox.getValue() != null)
    	{
    		
    	}
    	
		// clear replay
		replay = null; 
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	clearFieldsMethod();
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

