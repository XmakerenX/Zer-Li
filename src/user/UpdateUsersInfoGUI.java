package user;

import client.Client;
import client.ClientInterface;
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
import java.util.ArrayList;

public class UpdateUsersInfoGUI extends FormController implements ClientInterface {

	
	private Response replay = null;
	

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
    private ComboBox<?> newPermissionComboBox; // Value injected by FXMLLoader

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

