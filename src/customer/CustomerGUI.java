package customer;

import java.util.ArrayList;

import catalog.CatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import networkGUI.SystemManagerGUI;
import order.CancelOrderGUI;
import order.CustomItemGUI;
import product.Product;
import prototype.FormController;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import user.LoginGUI;
import user.User;
import user.UserController;

public class CustomerGUI extends FormController implements ClientInterface {

	private User currentUser = null;
	private Customer currentCustomer = null;
	private Response replay = null;
	private CatalogGUI catalogGui;
	private CustomItemGUI  customItemGUI;
	private CancelOrderGUI cancelOrderGUI;
	
    @FXML
    private Label welcomeLbl;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button viewCatalogBtn;
    
    @FXML
    private ComboBox<String> storeCombo;
    
    @FXML
    private Button orderCustomItemBtn;
    
    @FXML
    private Button viewOrdersBtn;


    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	catalogGui = FormController.<CatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/CatalogGUI.fxml"), this);
    	customItemGUI = FormController.<CustomItemGUI, AnchorPane>loadFXML(getClass().getResource("/order/CustomItemGUI.fxml"), this);
    	cancelOrderGUI = FormController.<CancelOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CancelOrderGUI.fxml"), this);
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	UserController.requestLogout(currentUser, client);
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
	
    @FXML
    void onOrderCustomItem(ActionEvent event) 
    {
    	if (customItemGUI != null)
    	{
	    	customItemGUI.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
	    	customItemGUI.setCurrentCustomer(currentCustomer);
			client.setUI(customItemGUI);
			customItemGUI.setClinet(client);
			customItemGUI.loadDominateColors();
			FormController.primaryStage.setScene(customItemGUI.getScene());
    	}
    }
    
    @FXML
    void onViewCatalog(ActionEvent event) {
    	Customer currentCustomer = null;
    	if (catalogGui != null)
    	{
    		currentCustomer = loadCustomer();
    		if (catalogGui!= null)
    		//if (currentCustomer != null && catalogGui!= null)
    		{
	    		catalogGui.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
	    		catalogGui.setCurrentCustomer(currentCustomer);
	    		client.setUI(catalogGui);
	    		catalogGui.setClinet(client);
	    		FormController.primaryStage.setScene(catalogGui.getScene());
    		}
    	}
    }
    
    @FXML
    void onStoreChanged(ActionEvent event) {
    	currentCustomer = loadCustomer();
    }
    
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
				ArrayList<Customer> customer = (ArrayList<Customer>)replay.getMessage();
				orderCustomItemBtn.setDisable(false);
				return customer.get(0);
			}
			else
			{
				orderCustomItemBtn.setDisable(true);
				return null;
			}
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
   	    		ArrayList<Store> stores = (ArrayList<Store>)replay.getMessage(); 	
   	        	
   	    		for (Store store : stores)
   	    		{
   	    			// ignore store 0(base store) as it isn't a real store
   	    			if (store.getStoreID() != 0)
   	    			{
   	    				comboboxStoreStrings.add(store.getStoreAddress());
   	    			}
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
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	public void onSwitch(Client newClient)
	{
		
	}
}
