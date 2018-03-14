package user;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.Customer.CustomerException;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import networkGUI.SystemManagerGUI;
import serverAPI.Response;
import user.User.UserException;
import utils.FormController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/**
 * holds all the needed functionality for the Update USer GUI
 * @author dk198
 *
 */
public class UpdateUsersInfoGUI extends FormController implements ClientInterface {

	// holds the last replay we got from server
	private Response replay = null;
	// A flag symbolizes affirmation whether to clear all the fields or not
	private boolean toClearFlag = false;
	// A flag symbolizes affirmation whether to update user's info or not
	private boolean toUpdateFlag = true;
	
	//List of text fields to clear them later
	ArrayList<TextField> allTextFields = new ArrayList<TextField>();
	ArrayList<TextField> onlyNewTextFields = new ArrayList<TextField>();
	
	//List of combo boxes to clear them later
	ArrayList<ComboBox<String>> allComboBoxes = new ArrayList<ComboBox<String>>();
	ArrayList<ComboBox<String>> onlyNewComboBoxes = new ArrayList<ComboBox<String>>();
	
	//Current User's entity with all attributes for later updating
	User userToUpdate;
	String formerUsername;
	
	//Current Customer's entity with all attributes for later updating
	Customer customerToUpdate;
	String formerPersonID;
	
	//In case there are multiple customers to one specific user
	ArrayList<Customer> customersFromDB;
	
	//List of permissions for the combo box
	ObservableList<String> permissionsList = FXCollections.observableArrayList();
	
	//List of user's statuses for the combo box
	ObservableList<String> usersStatusesList = FXCollections.observableArrayList();
	
	//List of payment methods for the combo box
	ObservableList<String> paymentMethodList = FXCollections.observableArrayList();
	
	//List of account statuses for the combo box
	ObservableList<String> accountStatusesList = FXCollections.observableArrayList("Blocked", "Active");
	
	//List of stores' names for the combo box; Note: will be filled only when customers are found. 
	ObservableList<String> storesNamesList = FXCollections.observableArrayList();
	
	//Hash map for stores: key - store ID, value - store name
	private HashMap<Long, String> stores = new HashMap<Long, String>();


    @FXML
    private TabPane infosTabPane;
    
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
    private Label firstNameLbl;
    
    @FXML
    private TextField firstNameTxtField;
    
	@FXML
    private TextField newFirstNameTxtField;
	
	@FXML
    private Label lastNameLbl;
	
    @FXML
    private TextField lastNameTxtField;
    
    @FXML
    private TextField newLastNameTxtField;

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
    private TextField newPhoneNumberTxtField;

    @FXML
    private TextField phoneNumberTxtField;

    @FXML
    private Label usersCurrentInfoLbl;
    
    /**
     * Initializes the combo box of permissions.
     * Initializes ArrayLists of text fields and combo boxes to clear them later
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
    	setComboBoxes();
    	setArrayListsOfFields();
    	setListenersForTextFields();
    	
    }
    
    
    /**
     * Finds specific user in database and brings its entity
     * @param event - "Find user" button is pressed
     */
    
    @SuppressWarnings("unchecked")
	@FXML
    void onFindUser(ActionEvent event) {
    	
    	storeNameComboBox.setItems(null);
    	storesNamesList.clear();
    	customersPersonIDTxtField.setText("");
    	firstNameTxtField.setText("");
    	lastNameTxtField.setText("");
    	phoneNumberTxtField.setText("");
    	paymentMethodTxtField.setText("");
    	accoundBalanceTxtField.setText("");
    	creditCardNumberTxtField.setText("");
    	accountStatusTxtField.setText("");
		ArrayList<String> storeNames = new ArrayList<String>();

    	String userName = findUserNameTxtField.getText();
    	
    	UserController.getUser(userName, Client.getInstance());
    	
	    waitForResponse();
			
	  	// show success 
	  	if (replay.getType() == Response.Type.SUCCESS)
	  	{
			userToUpdate = (User)((ArrayList<?>) replay.getMessage()).get(0);
			
			//Filling the GUI's User's tab with info
			fillUsersCurrentInfoFields();
	
	  	}
	  	else
			showWarningMessage("User doesn't exists!");
     	
     	// clear replay
     	replay = null;
     	
     	if(userToUpdate.getUserPermission().equals(User.Permissions.CUSTOMER))
     	{
     		infosTabPane.getTabs().get(1).setDisable(false);
	    	String personID = ""+userToUpdate.getPersonID();
	    	
	    	CustomerController.getCustomer(personID, null, Client.getInstance());
	    	
			waitForResponse();
					
		  	// show success 
		  	if (replay.getType() == Response.Type.SUCCESS)
		  	{
					
					//Attaining stores' names from Customer's entities - to set storeNameComboBox
					customersFromDB = (ArrayList<Customer>) replay.getMessage();
					
					for(Customer customer : customersFromDB)
						storeNames.add(stores.get(customer.getStoreID()));
		
					storesNamesList.addAll(storeNames);
					storeNameComboBox.setItems(storesNamesList);
		  	}
		  	else
					showErrorMessage("Failed to get response from data base or the return value is empty!");
    	}
     	else
     	{
     		infosTabPane.getTabs().get(1).setDisable(true);
    		showWarningMessage("This user has no customers!");
     	}
    	
     	// clear replay
     	replay = null;
    }
    
    
    /**
     * Changes specific user's entry in database table
     * @param event - "Update" button is pressed
     */
    
    @FXML
    void onUpdate(ActionEvent event) {

    	toUpdateFlag = true;
    	String temporaryString = "";
    	
    	//Updates user only if at least one field of new fields' attributes were filled
    	if( !newUsernameTxtField.getText().equals("") || !newUsernameTxtField.getText().equals(userToUpdate.getUserName()) || !newPasswordTxtField.getText().equals("")
    			|| newPermissionComboBox.getValue() != null || !newPersonIDTxtField.getText().equals("") || newUsersStatusComboBox.getValue() != null
    			|| clearTriesCheckBox.isSelected())
    	{
    		
			try {
		   		if(!newUsernameTxtField.getText().equals(""))
		   		{
					UserController.getUser(""+newUsernameTxtField.getText(), Client.getInstance());
			    	
					waitForResponse();
									
				  	// show success
				  	if (replay.getType() == Response.Type.SUCCESS)
				  	{
									showWarningMessage("Such user name is already exists!");
									newUsernameTxtField.setText("");
									toUpdateFlag = false;
				  	}
			     	
			     	// clear replay
			     	replay = null;
			     	
			     	if(toUpdateFlag)
			     		userToUpdate.setUserName(""+newUsernameTxtField.getText());
		   		}
		   		
		   		if(!newPasswordTxtField.getText().equals(""))
		   			userToUpdate.setUserPassword(""+newPasswordTxtField.getText());
		   		
		   		if(newPermissionComboBox.getValue() != null)
		   		{
		   			temporaryString = handleSplittedStringFromGUI(newPermissionComboBox.getValue());
		   			userToUpdate.setUserPermission(User.Permissions.valueOf(temporaryString));
		   		}
		   		
		   		if(!newPersonIDTxtField.getText().equals(""))
		   			userToUpdate.setPersonID(Integer.parseInt(newPersonIDTxtField.getText()));
		   		
		   		if(newUsersStatusComboBox.getValue() != null)
		   		{
		   			temporaryString = handleSplittedStringFromGUI(newUsersStatusComboBox.getValue());
		   			userToUpdate.setUserStatus(User.Status.valueOf(temporaryString));
		   		}
		   		
		   		if(clearTriesCheckBox.isSelected())
		   			userToUpdate.clearUnsuccessfulTries();
		   			
		   			
			} catch (UserException ue) {
				ue.printStackTrace();
	    		showErrorMessage("One of the fields' input is invalid!");
	    		return;
			}
			
			if(toUpdateFlag)
			{
				UserController.updateUserDetails(userToUpdate, formerUsername, Client.getInstance());
		    	
				waitForResponse();
							
			  	if (replay.getType() == Response.Type.SUCCESS)
			  	{
							showInformationMessage("User's info is successfully updated!");
							toClearFlag = true;
							fillUsersCurrentInfoFields();
			  	}
			  	else
							showErrorMessage("The update is failed!");
			}
    	}
    	
    	//Updates customer only if at least one field of new fields' attributes were filled
    	
    	if( !newCustomersPersonIDTxtField.getText().equals("") || !newFirstNameTxtField.getText().equals("") || !newLastNameTxtField.getText().equals("") 
    			|| !newPhoneNumberTxtField.getText().equals("") || newPaymentMethodComboBox.getValue() != null
    			|| !newAccoundBalanceTxtField.getText().equals("") || !newCreditCardNumberTxtField.getText().equals("")
    			|| newAccountStatusComboBox.getValue() != null)
    	{
    		
        	String currentName = "";
        	String [] separatedFirstAndLastNames = customerToUpdate.getName().split(" ");
        	
			try{
				if(!newCustomersPersonIDTxtField.getText().equals(""))
					customerToUpdate.setID(Long.parseLong(newCustomersPersonIDTxtField.getText()));
				
				if(!newFirstNameTxtField.getText().equals("") )
				{
					separatedFirstAndLastNames[0] = newFirstNameTxtField.getText();
				}
				
				if(!newLastNameTxtField.getText().equals("") )
				{
					separatedFirstAndLastNames[1] = newLastNameTxtField.getText();
				}
				
				currentName = separatedFirstAndLastNames[0] + " " + separatedFirstAndLastNames[1];
				
				customerToUpdate.setName(currentName);
				
				if(!newPhoneNumberTxtField.getText().equals(""))
					customerToUpdate.setPhoneNumber(newPhoneNumberTxtField.getText());
				
				if(newPaymentMethodComboBox.getValue() != null)
				{
		   			temporaryString = handleSplittedStringFromGUI(newPaymentMethodComboBox.getValue());
					customerToUpdate.setPayMethod(Customer.PayType.valueOf(temporaryString));
				}
				
				if(!newAccoundBalanceTxtField.getText().equals(""))
					customerToUpdate.setAccountBalance(Float.parseFloat(newAccoundBalanceTxtField.getText()));
				
				if(!newCreditCardNumberTxtField.getText().equals(""))
					customerToUpdate.setCreditCardNumber(newCreditCardNumberTxtField.getText());
				
				if(newAccountStatusComboBox.getValue() != null)
				{
					if(newAccountStatusComboBox.getValue().equals("Blocked"))
						customerToUpdate.setAccountStatus(false);
					else
						customerToUpdate.setAccountStatus(true);
				}
				
			} catch (CustomerException ce) {
				ce.printStackTrace();
	    		showErrorMessage("One of the fields' input is invalid!");
	    		return;
			}

	    	CustomerController.updateCustomerDetails(
	    			customerToUpdate, formerPersonID, Client.getInstance());
	
			waitForResponse();
					
		  	if (replay.getType() == Response.Type.SUCCESS)
		  	{
					showInformationMessage("Customer's info is successfully updated!");
					toClearFlag = true;
					fillCustomersCurrentInfoFields();
		  	}
		  	else
					showErrorMessage("The update is failed!");
    	}
    	
    	
		// clear replay
		replay = null; 
		if(toClearFlag)
		{
			clearFieldsMethod(onlyNewTextFields, onlyNewComboBoxes);
			toClearFlag = false;
		}
		
    }
    
    
    /**
     * According to selected store name customer's info , who is in that specific store, will be loaded.
     * @param event - store name selected from storeNameComboBox
     */
    
    @FXML
    void onStoreNameSelection(ActionEvent event) {

    		String currentStoreName = storeNameComboBox.getValue();

    		//Finds specific customer that will be updated later
    		for(Customer customer : customersFromDB)
    		{
    			if(stores.get(customer.getStoreID()).equals(currentStoreName))
    				customerToUpdate = customer;
    		}
    		
    		//Filling the GUI's Customer's tab with info
    		fillCustomersCurrentInfoFields();
    }
    
    
    /**
     * Returns to previous GUI window 
     * @param event - "Back" button is pressed
     */
    
    @FXML
    void onBack(ActionEvent event) {
    	
    	clearFieldsMethod(allTextFields, allComboBoxes);
    	
    	SystemManagerGUI sysManagerGUI = (SystemManagerGUI)parent;
    	Client.getInstance().setUI(sysManagerGUI);
    	FormController.primaryStage.setScene(parent.getScene());
    }

    
    /**
     * Displays reply message from the server
     */
    
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
	
	/**
	 * Clear specific fields and combo boxes in the GUI
	 * @param textFieldsToClear - clears only text fields that are in the ArrayList
	 * @param comboBoxesToClear - clears only combo boxes that are in the ArrayList
	 */
	
	public void clearFieldsMethod(ArrayList<TextField> textFieldsToClear, ArrayList<ComboBox<String>> comboBoxesToClear)
	{
		
		for(TextField txtfield : textFieldsToClear)
			txtfield.setText("");
		
		for(ComboBox<String> combobox : comboBoxesToClear)
			combobox.setValue(null);
		
	}
	
	
	/**
	 * Receives message that will be splitted by "_" symbol and transformed to user friendly view.
	 * For example: "CREDIT_CARD" is transformed to "Credit card"
	 * @param stringToSplit - message to be splitted by specific symbol
	 * @return transformed string
	 */
	
	public String handleSplittedStringFromDataBase(String stringToSplit)
	{
		String [] splittedString;
		splittedString = stringToSplit.split("_");
		
		String tempString = "";
		
		for(String splitted : splittedString)
		{
			splitted = splitted.toLowerCase();
			
			if(!tempString.equals(""))
				tempString = tempString + " ";
			else
				splitted = Character.toUpperCase(splitted.charAt(0)) + splitted.substring(1);
			
			tempString = tempString + splitted;
		}
		
		return tempString;
	}
	
	
	/**
	 * Receives message that will be splitted by " " symbol and transformed to data base view.
	 * For example: "Credit card" is transformed to "CREDIT_CARD"
	 * @param stringToSplit - message to be splitted by specific symbol
	 * @return transformed string
	 */
	
	public String handleSplittedStringFromGUI(String stringToSplit)
	{
		String [] splittedString;
		splittedString = stringToSplit.split(" ");
		
		String tempString = "";
		
		for(String splitted : splittedString )
			{
		   		if( !tempString.equals(""))
		   			tempString = tempString + "_";
		   		tempString = tempString + splitted.toUpperCase();
		   	}
		
		return tempString;
	}

	public HashMap<Long, String> getStores() {
		return stores;
	}

	public void setStores(HashMap<Long, String> stores) {
		this.stores = stores;
	}
	
	
	/**
	 * Initiating ArrayLists of text fields and combo boxes to clear them later
	 */
	
	private void setArrayListsOfFields()
	{

    	allComboBoxes.addAll(Arrays.asList(newAccountStatusComboBox, newPaymentMethodComboBox, newPermissionComboBox,
    			newUsersStatusComboBox, storeNameComboBox));
    	
    	onlyNewComboBoxes.addAll(Arrays.asList(newAccountStatusComboBox, newPaymentMethodComboBox, newPermissionComboBox,
    			newUsersStatusComboBox));
    	
    	allTextFields.addAll(Arrays.asList(newUsernameTxtField, personIDTxtField, newAccoundBalanceTxtField, newPasswordTxtField,
    			newFirstNameTxtField, accoundBalanceTxtField, unsuccessfulTriesTxtField, newCreditCardNumberTxtField, 
    			newCustomersPersonIDTxtField, accountStatusTxtField, paymentMethodTxtField, creditCardNumberTxtField,
    			findUserNameTxtField, usernameTxtField, newPersonIDTxtField, passwordTxtField, firstNameTxtField,
    			customersPersonIDTxtField, usersStatusTxtField, permissionTxtField, newPhoneNumberTxtField, phoneNumberTxtField,
    			 newLastNameTxtField, lastNameTxtField));
    	
    	onlyNewTextFields.addAll(Arrays.asList(newUsernameTxtField, newAccoundBalanceTxtField, newPasswordTxtField,
    			newFirstNameTxtField, newCreditCardNumberTxtField, newCustomersPersonIDTxtField, newPersonIDTxtField,
    			newPhoneNumberTxtField, newLastNameTxtField));
	}
	
	
	/**
	 * Initiating listeners for specific text fields to run check whether the input is valid
	 */
	
	private void setListenersForTextFields()
	{
    	newPersonIDTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*)") && newValue.length() <= 9) 
                {
                	newPersonIDTxtField.setText(newValue);
                }
                else
                	newPersonIDTxtField.setText(oldValue);
            }
        });
    	
    	newCustomersPersonIDTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*)") && newValue.length() <= 9) 
                {
                	newCustomersPersonIDTxtField.setText(newValue);
                }
                else
                	newCustomersPersonIDTxtField.setText(oldValue);
            }
        });
    	
    	newPhoneNumberTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*)")) 
                {
                	newPhoneNumberTxtField.setText(newValue);
                }
                else
                	newPhoneNumberTxtField.setText(oldValue);
            }
        });

    	newAccoundBalanceTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*.[0-9]*)")) 
                {
                	newAccoundBalanceTxtField.setText(newValue);
                }
                else
                	newAccoundBalanceTxtField.setText(oldValue);
            }
        });
    	
    	newCreditCardNumberTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*)") && newValue.length() <= 16) 
                {
                	newCreditCardNumberTxtField.setText(newValue);
                }
                else
                	newCreditCardNumberTxtField.setText(oldValue);
            }
        });
    	
    	newFirstNameTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([a-zA-Z ]*)") && newValue.length() <= 16) 
                {
                	newFirstNameTxtField.setText(newValue);
                }
                else
                	newFirstNameTxtField.setText(oldValue);
            }
        });
    	
    	newLastNameTxtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([a-zA-Z ]*)") && newValue.length() <= 16) 
                {
                	newLastNameTxtField.setText(newValue);
                }
                else
                	newLastNameTxtField.setText(oldValue);
            }
        });
	}
	
	
	/**
	 * Filling combo boxes according to enum declarations
	 */
	
	private void setComboBoxes()
	{
		String temporaryString = "";
		
    	ArrayList<String> permissions = new ArrayList<String>();
    	ArrayList<String> usersStatuses = new ArrayList<String>();
    	ArrayList<String> paymentMethods = new ArrayList<String>();
    	

    	for(User.Permissions permission : User.Permissions.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+permission);
    		permissions.add(""+temporaryString);
    	}
    	
    	permissionsList.addAll(permissions);
    	newPermissionComboBox.setItems(permissionsList);
    	
    	for(User.Status status : User.Status.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+status);
    		usersStatuses.add(""+temporaryString);
    	}
    	
    	usersStatusesList.addAll(usersStatuses);
    	newUsersStatusComboBox.setItems(usersStatusesList);
    	
    	for(Customer.PayType paytype : Customer.PayType.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+paytype);
    		paymentMethods.add(""+temporaryString);
    	}
    	
    	paymentMethodList.addAll(paymentMethods);
    	newPaymentMethodComboBox.setItems(paymentMethodList);
    	
    	newAccountStatusComboBox.setItems(accountStatusesList);
	}
	
	/**
	 * Fills user's current info in the GUI
	 */
	private void fillUsersCurrentInfoFields()
	{
		String temporaryString = "";
		formerUsername = ""+userToUpdate.getUserName();
		usernameTxtField.setText(""+userToUpdate.getUserName());
		passwordTxtField.setText(""+userToUpdate.getUserPassword());
		
		temporaryString = handleSplittedStringFromDataBase(""+userToUpdate.getUserPermission());
		
		permissionTxtField.setText(temporaryString);
		personIDTxtField.setText(""+userToUpdate.getPersonID());
		
		temporaryString = handleSplittedStringFromDataBase(""+userToUpdate.getUserStatus());

		usersStatusTxtField.setText(temporaryString);
		unsuccessfulTriesTxtField.setText(""+userToUpdate.getUnsuccessfulTries());
	}
	
	/**
	 * Fills customer's current info in the GUI
	 */
	private void fillCustomersCurrentInfoFields()
	{
		String temporaryString = "";
		String [] seperatedFirstAndLastNames = customerToUpdate.getName().split(" ");
		formerPersonID= ""+customerToUpdate.getID();
		customersPersonIDTxtField.setText(""+customerToUpdate.getID());
		firstNameTxtField.setText(seperatedFirstAndLastNames[0]);
		lastNameTxtField.setText(seperatedFirstAndLastNames[1]);
		phoneNumberTxtField.setText(""+customerToUpdate.getPhoneNumber());

		temporaryString = handleSplittedStringFromDataBase(""+customerToUpdate.getPayMethod());
		
		paymentMethodTxtField.setText(temporaryString);
		accoundBalanceTxtField.setText(""+customerToUpdate.getAccountBalance());
		creditCardNumberTxtField.setText(""+customerToUpdate.getCreditCardNumber());
		if(customerToUpdate.getAccountStatus() == true)
			accountStatusTxtField.setText("Active");
		else
			accountStatusTxtField.setText("Blocked");
	}

}

