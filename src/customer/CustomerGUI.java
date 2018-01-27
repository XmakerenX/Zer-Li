package customer;

import java.util.ArrayList;

import catalog.CatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import order.CancelOrderGUI;
import order.CustomItemGUI;
import order.createOrderBySearchGUI;
import prototype.FormController;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import user.LoginGUI;
import user.User;
import user.UserController;

//*************************************************************************************************
	/**
	*  Provides a GUI that shows the action the customer can do  
	*/
//*************************************************************************************************
public class CustomerGUI extends FormController implements ClientInterface {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private User currentUser = null;
	private Customer currentCustomer = null;
	private Response replay = null;
	private CatalogGUI catalogGui;
	private CustomItemGUI  customItemGUI;
	private createOrderBySearchGUI orderBySearchGUI;
	private CancelOrderGUI cancelOrderGUI;
	
    @FXML
    private Label welcomeLbl;
    
    @FXML
    private Button logOutBtn;
    
    @FXML
    private Button viewCatalogBtn;
    
    @FXML
    private ComboBox<String> storeCombo;
    
    @FXML
    private Button orderCustomItemBtn;
    
    @FXML
    private Button viewOrdersBtn;

    @FXML
    private Button orderBySearch;
    
    //*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*  Initializes child GUI's
  	*/
    //*************************************************************************************************
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	catalogGui = FormController.<CatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/CatalogGUI.fxml"), this);
    	orderBySearchGUI = FormController.<createOrderBySearchGUI, AnchorPane>loadFXML(getClass().getResource("/order/createOrderBySearchGUI.fxml"), this);
    	customItemGUI = FormController.<CustomItemGUI, AnchorPane>loadFXML(getClass().getResource("/order/CustomItemGUI.fxml"), this);
    	cancelOrderGUI = FormController.<CancelOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CancelOrderGUI.fxml"), this);
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the back button is pressed
  	*  Goes back to the parent GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onLogOut(ActionEvent event) {
    	
    	UserController.requestLogout(currentUser, client);
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
	
    //*************************************************************************************************
    /**
  	*  Called when the Order Custom Item button is pressed
  	*  Opens the Custom Item GUI 
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onOrderCustomItem(ActionEvent event) 
    {
    	if (customItemGUI != null)
    	{
	    	customItemGUI.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
	    	customItemGUI.setCurrentCustomer(currentCustomer);
			client.setUI(customItemGUI);
			customItemGUI.setClinet(client);
			customItemGUI.initFields();
			FormController.primaryStage.setScene(customItemGUI.getScene());
    	}
    }

    //*************************************************************************************************
    /**
  	*  Called when the View Catalog button is pressed
  	*  Opens the Catalog GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onViewCatalog(ActionEvent event) 
    {
    	Customer currentCustomer = null;
    	if (catalogGui != null)
    	{
    		currentCustomer = loadCustomer();
    		if (catalogGui!= null)
    		{
	    		catalogGui.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
	    		catalogGui.setCurrentCustomer(currentCustomer);
	    		client.setUI(catalogGui);
	    		catalogGui.setClinet(client);
	    		FormController.primaryStage.setScene(catalogGui.getScene());
    		}
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the Order By Search button is pressed
  	*  Opens the create Order By Search GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onOrderBySearch(ActionEvent event) 
    {
    	Customer currentCustomer = null;
    	if (orderBySearchGUI != null)
    	{
    		currentCustomer = loadCustomer();
    		client.setUI(orderBySearchGUI);
    		orderBySearchGUI.setClinet(client);
    		orderBySearchGUI.doInit();
    		orderBySearchGUI.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
    		orderBySearchGUI.setCurrentCustomer(currentCustomer);
    		orderBySearchGUI.setCurrentUser(this.currentUser);
	        FormController.primaryStage.setScene(orderBySearchGUI.getScene());	
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the store combo box is pressed
  	*  Loads the user customer matching the selected store
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onStoreChanged(ActionEvent event) {
    	currentCustomer = loadCustomer();
    }

    //*************************************************************************************************
    /**
  	*  Called when the store combo box is pressed
  	*  Loads the user customer matching the selected store
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onViewOrders(ActionEvent event) 
    {
    	if (cancelOrderGUI != null)
    	{
    		cancelOrderGUI.setCurrentCustomer(currentCustomer);
    		client.setUI(cancelOrderGUI);
    		cancelOrderGUI.onRefresh(null);
    		FormController.primaryStage.setScene(cancelOrderGUI.getScene());
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  Loads the user customer matching the current store
  	*  @return the customer matching the current chosen store
  	*/
    //*************************************************************************************************
    public Customer loadCustomer()
    {
		replay = null;
		CustomerController.getCustomer(""+currentUser.getPersonID(), ""+(storeCombo.getSelectionModel().getSelectedIndex() + 1), Client.client);
		
		synchronized(this)
		{
			// wait for server response
			try {
				this.wait(ClientInterface.TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (replay != null)
		{
			if (replay.getType() == Response.Type.SUCCESS)
			{
				@SuppressWarnings("unchecked")
				ArrayList<Customer> customer = (ArrayList<Customer>)replay.getMessage();
				if (customer.get(0).getAccountStatus())
				{
					orderCustomItemBtn.setDisable(false);
					orderBySearch.setDisable(false);
					return customer.get(0);
				}
			}
			// disable the button if there was no valid customer
			orderCustomItemBtn.setDisable(true);
			orderBySearch.setDisable(true);
			return null;
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR);
		  	alert.setTitle("Server Respone timed out");
	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
	    	alert.showAndWait();
	    	return null;
		}
    }
    
    //*************************************************************************************************
    /**
  	*  Loads from the server all the stores and sets the store combo box
  	*/
    //*************************************************************************************************
    public void loadStores()
    {
    	Client.client.handleMessageFromClientUI(new GetRequest("Store"));
    	
   		synchronized(this)
		{
			// wait for server response
			try {
				this.wait(ClientInterface.TIMEOUT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
   		
   		if (replay != null)
   		{
   	    	if (replay.getType() == Response.Type.SUCCESS)
   	    	{
   	    		ArrayList<String> comboboxStoreStrings = new ArrayList<String>();
   	    		@SuppressWarnings("unchecked")
				ArrayList<Store> stores = (ArrayList<Store>)replay.getMessage(); 	
   	        	
   	    		for (Store store : stores)
   	    		{
   	    			// ignore store 0(base store) as it isn't a real store
   	    			if (store.getStoreID() != 0)
   	    				comboboxStoreStrings.add(store.getStoreAddress());
   	    		}
   	        	
   	        	storeCombo.setItems(FXCollections.observableArrayList(comboboxStoreStrings));
   	        	storeCombo.getSelectionModel().select(0);
   	        	currentCustomer = loadCustomer();
   	    	}
   	    	replay = null;
   		}
   		else
   		{
   			Alert alert = new Alert(AlertType.ERROR);
		  	alert.setTitle("Server Respone timed out");
	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
	    	alert.showAndWait();
   		}
    }
    
    //*************************************************************************************************
    /**
  	*  Called from the client when the server sends a response
  	*  fills the TableView with the received products data
  	*  @param message The Server response , an ArrayList of products
  	*/
    //*************************************************************************************************
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
	
    //*************************************************************************************************
    /**
  	*  sets the current user
  	*  @param currentUser The user to set
  	*/
    //*************************************************************************************************
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void onSwitch(Client newClient)
	{
		
	}
}