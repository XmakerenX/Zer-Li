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
import product.Product;
import prototype.FormController;
import serverAPI.GetRequest;
import serverAPI.Response;
import store.Store;
import user.LoginGUI;
import user.User;

public class CustomerGUI extends FormController implements ClientInterface {

	private User currentUser = null;
	private CatalogGUI catalogGui;
	private Response replay = null;
	
    @FXML
    private Label welcomeLbl;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button viewCatalogBtn;
    
    @FXML
    private ComboBox<String> storeCombo;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	catalogGui = FormController.<CatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/CatalogGUI.fxml"), this);
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
	
    @FXML
    void onViewCatalog(ActionEvent event) {
    	Customer currentCustomer = null;
    	if (catalogGui != null)
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
    				currentCustomer = customer.get(0);
    			}
    			else
    			{
    				currentCustomer = null;
    			}
    		}
    		else
    		{
    			Alert alert = new Alert(AlertType.ERROR);
    		  	alert.setTitle("Server Respone timed out");
    	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
    	    	alert.showAndWait();
    	    	return;
    		}
    		
    		catalogGui.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
    		catalogGui.setCurrentCustomer(currentCustomer);
    		client.setUI(catalogGui);
    		catalogGui.setClinet(client);
    		FormController.primaryStage.setScene(catalogGui.getScene());
    		replay = null;
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
   	    			// igonre store 0(base store) as it isn't a real store
   	    			if (store.getStoreID() != 0)
   	    			{
   	    				comboboxStoreStrings.add(store.getStoreAddress());
   	    			}
   	    		}
   	        	
   	        	storeCombo.setItems(FXCollections.observableArrayList(comboboxStoreStrings));
   	        	storeCombo.getSelectionModel().select(0);
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
