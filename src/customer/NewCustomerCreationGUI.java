package customer;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import networkGUI.StoreManagerGUI;
import prototype.FormController;
import serverAPI.Response;

/**
 * A GUI for store manager to create new customer in his/her store. 
 */
public class NewCustomerCreationGUI extends FormController implements ClientInterface{
	
	// holds the last replay we got from server
	private Response replay = null;
	
	//List of payment methods for the combo box
	ObservableList<String> paymentMethodList = FXCollections.observableArrayList();
	
	// Store manager's store ID
	private long managersStoreID;

    @FXML // fx:id="personIDTxtField"
    private TextField personIDTxtField;

    @FXML // fx:id="windowTitleLbl"
    private Label windowTitleLbl;

    @FXML // fx:id="lastNameLbl"
    private Label lastNameLbl;

    @FXML // fx:id="creditCardNumberLbl"
    private Label creditCardNumberLbl;

    @FXML // fx:id="createBtn"
    private Button createBtn;

    @FXML // fx:id="lastNameTxtField"
    private TextField lastNameTxtField;

    @FXML // fx:id="phoneNumberLbl"
    private Label phoneNumberLbl;

    @FXML // fx:id="firstNameLbl"
    private Label firstNameLbl;

    @FXML // fx:id="personIDLbl"
    private Label personIDLbl;

    @FXML // fx:id="firstNameTxtField"
    private TextField firstNameTxtField;

    @FXML // fx:id="backBtn"
    private Button backBtn;

    @FXML // fx:id="instructionLbl"
    private Label instructionLbl;

    @FXML // fx:id="paymentMethodLbl"
    private Label paymentMethodLbl;

    @FXML // fx:id="phoneNumberTxtField"
    private TextField phoneNumberTxtField;

    @FXML // fx:id="paymentMethodComboBox"
    private ComboBox<String> paymentMethodComboBox;

    @FXML // fx:id="creditCardNumberTxtField"
    private TextField creditCardNumberTxtField; 

    /**
     * Initializes the combo box of payment method.
     */
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	
		String temporaryString = "";
    	ArrayList<String> paymentMethods = new ArrayList<String>();
    	
    	for(Customer.PayType paytype : Customer.PayType.values())
    	{
    		temporaryString = handleSplittedStringFromDataBase(""+paytype);
    		paymentMethods.add(""+temporaryString);
    	}
    	
    	paymentMethodList.addAll(paymentMethods);
    	paymentMethodComboBox.setItems(paymentMethodList);
    }
    
    /**
     * Creates new customer and adds him/her to data base
     * @param event - "Create" button is pressed
     */
    @FXML
    void onCreate(ActionEvent event) {

    }
    
    /**
     * Returns to previous GUI window 
     * @param event - "Back" button is pressed
     */
    @FXML
    void onBack(ActionEvent event) {
    	
    	StoreManagerGUI storeManagerGUI = (StoreManagerGUI)parent;
    	client.setUI(storeManagerGUI);
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

	@Override
	public void onSwitch(Client newClient) {
		
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
	
	public void setManagersStoreID(long managersStoreID) {
		this.managersStoreID = managersStoreID;
	}

}
