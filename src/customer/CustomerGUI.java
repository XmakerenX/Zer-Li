package customer;

import java.util.ArrayList;

import catalog.CatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class CustomerGUI extends FormController implements ClientInterface {

	private CatalogGUI catalogGui;
	private Customer currentCustomer = null;
	
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
    	if (catalogGui != null)
    	{
    		catalogGui.setCurrentStoreID(storeCombo.getSelectionModel().getSelectedIndex() + 1);
    		catalogGui.setCurrentCustomer(currentCustomer);
    		client.setUI(catalogGui);
    		catalogGui.setClinet(client);
    		FormController.primaryStage.setScene(catalogGui.getScene());
    	}
    }
    
    public void loadStores()
    {
    	Client.client.handleMessageFromClientUI(new GetRequest("Store"));
    }
    
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public void display(Object message)
	{
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	Response replay = (Response)message;
    	
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
	}
	
	public void onSwitch(Client newClient)
	{
		
	}
}
