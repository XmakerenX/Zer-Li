package catalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

import client.Client;
import client.ClientInterface;
import customer.Customer;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import order.CreateOrderGUI;
import product.CatalogItem;
import prototype.FormController;
import serverAPI.GetJoinedTablesWhereRequest;
import serverAPI.Response;
import utils.ImageData;

//*************************************************************************************************
	/**
	*  Provides a GUI that shows a store catalog items  
	*/
//*************************************************************************************************
public class CatalogGUI extends FormController implements ClientInterface {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private CreateOrderGUI createOrderGUI;
	private long currentStoreID = 0;
	private Customer currentCustomer = null;
	
    @FXML
    private TableView<CatalogItemView> catalogTable;

    @FXML
    private TableColumn<CatalogItemView, ImageView> imageCol;

    @FXML
    private TableColumn<CatalogItemView, String> nameCol;

    @FXML
    private TableColumn<CatalogItemView, String> typeCol;

    @FXML
    private TableColumn<CatalogItemView, String> colorCol;

    @FXML
    private TableColumn<CatalogItemView, WebView> priceCol;
	
    @FXML
    private TableColumn<CatalogItemView, Boolean> checkboxCol;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button printBtn;
    
    @FXML
    private Button createOrderBtn;
    
    // holds the last replay we got from server
 	private Response replay = null;
    
 	//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*  Initializes the table view and child GUI's
  	*/
 	//*************************************************************************************************
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    	
    	createOrderGUI = FormController.<CreateOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CreateOrderGUI.fxml"), this);
    }
    
    //*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
    //*************************************************************************************************
    private void InitTableView()
    {
    	imageCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView, ImageView>("image"));
    	nameCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,String>("Name"));
    	typeCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView,String>("Type"));
    	colorCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView,String>("Color"));    	
    	priceCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,WebView>("SalePriceView"));
    	
    	checkboxCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,Boolean>("CheckBox"));
    	checkboxCol.setCellValueFactory(
    			new Callback<CellDataFeatures<CatalogItemView, Boolean>, ObservableValue<Boolean>>()
    	{
    		@Override
    		public ObservableValue<Boolean> call(CellDataFeatures<CatalogItemView, Boolean> param)
    		{
    			return param.getValue().selectedProperty();
    		}
    	}
    	);
    	checkboxCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxCol));
    	

    	
    	catalogTable.setEditable(true);
    	checkboxCol.setEditable(true);
    }
    
    //*************************************************************************************************
    /**
  	*  Loads from the server the requested store catalog products to the  catalogItemsSet
  	*  @param storeID the store ID for which to load the catalog products
  	*  @param catalogItemsSet the catalog Items set to which to add the items
  	*/
    //*************************************************************************************************
    private void addStoreProductsToSet(long storeID, TreeSet<CatalogItem> catalogItemsSet)
    {
    	replay = null;
    	Client.client.handleMessageFromClientUI(new GetJoinedTablesWhereRequest("Product", "CatalogProduct", 0,"StoreID", ""+storeID));
    	
    	// wait for response
		synchronized(this)
		{
			// wait for server response
			try {
				this.wait();
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
				ArrayList<CatalogItem> catalogItems = (ArrayList<CatalogItem>)replay.getMessage();
				for (CatalogItem item : catalogItems)
				{
					catalogItemsSet.add(item);
				}
			}
		}
    }
    
    //*************************************************************************************************
    /**
  	*  Requests from the server the images that the client is missing to show the catalog and saves
  	*  them to the client cache
  	*  @param catalogItemsSet the catalog items to show in the catalog
  	*/
    //*************************************************************************************************
    @SuppressWarnings("unchecked")
	public void downloadMissingCatalogImages(TreeSet<CatalogItem> catalogItemsSet)
    {
    	ArrayList<String> missingImages = CatalogController.scanForMissingCachedImages(catalogItemsSet);
		if (missingImages.size() > 0)
		{
			System.out.println("Missing images "+ missingImages);
			replay = null;
			CatalogController.requestCatalogImages(missingImages, Client.client);

			// wait for response 
			synchronized(this)
			{
				try {
					this.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
    		if (replay != null)
    			CatalogController.saveCatalogImages((ArrayList<ImageData>)replay.getMessage());
		}
    }
    
    //*************************************************************************************************
    /**
  	*  Request from the Server the catalog items for the current selected Store
  	*  TODO: delete the event parameter
  	*/
    //*************************************************************************************************
    public void onRefresh(ActionEvent event) {
    	//TODO: add check if customer is blocked
    	if (currentCustomer == null)
    		createOrderBtn.setDisable(true);
    	else
    	{
    		if (currentCustomer.getStoreID() == currentStoreID)
    			createOrderBtn.setDisable(false);
    		else
    			createOrderBtn.setDisable(true);
    	}
    		
    	
    	final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
    	// item set to combine the store catalog with the base catalog without duplicates
    	TreeSet<CatalogItem> catalogItemsSet = new TreeSet<CatalogItem>();
    	
    	// get currentStore catalog items
    	if (currentStoreID != 0)
    	{
    		addStoreProductsToSet(currentStoreID, catalogItemsSet);
    	}

    	// get Base catalog items
    	addStoreProductsToSet(0, catalogItemsSet);
    	
    	downloadMissingCatalogImages(catalogItemsSet);
    	
    	for (CatalogItem item : catalogItemsSet)
    	{
    		itemData.add(new CatalogItemView(item, ImageData.ClientImagesDirectory));
    	}
    	
		catalogTable.setItems(itemData);
		Collections.sort(catalogTable.getItems());
    	
    	replay = null;
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the back button is pressed
  	*  Goes back to the parent GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onBack(ActionEvent event) {
    	Client.client.setUI((ClientInterface)parent);
    	FormController.primaryStage.setScene(parent.getScene());
    }

    //*************************************************************************************************
    /**
  	*  Called when the Create Order button is pressed
  	*  Creates a new order and send a Request to add it to the server
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onCreateOrder(ActionEvent event) 
    {
    	// the items in the table
    	final ObservableList<CatalogItemView> itemData = catalogTable.getItems();
    	// the selected items in the table
    	final ObservableList<CatalogItemView> itemsSelected = FXCollections.observableArrayList();
    	
    	// get the selected items
    	for (CatalogItemView item : itemData)
    	{
    		if (item.isSelected())
    			itemsSelected.add(item);
    	}
    	
    	if (itemsSelected.size() > 0)
    	{        	
        	if (createOrderGUI != null)
        	{
        		createOrderGUI.setCurrentCustomer(currentCustomer);
        		createOrderGUI.setCurrentStore(currentStoreID);
        		Client.client.setUI(createOrderGUI);
        		createOrderGUI.loadItemsInOrder(itemsSelected);
        		FormController.primaryStage.setScene(createOrderGUI.getScene());
        	}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No item selected!");
			alert.setContentText("please select an item from the catalog");
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
    	
    	replay = (Response)message;

		synchronized(this)
		{
			this.notify();
		}
    	    	
    }
    
    //TODO: delete this function
    @Override
	public void setClinet(Client client)
	{
    	onRefresh(null);
	}
    
    //*************************************************************************************************
    /**
     * Sets the current Customer that is viewing the catalog
  	*  @param currentCustomer the customer to be set
  	*/
    //*************************************************************************************************
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

    //*************************************************************************************************
    /**
     * Sets the current store whose catalog is being viewed
  	*  @param storeID the storeID to be set
  	*/
    //*************************************************************************************************
	public void setCurrentStoreID(long storeID)
	{
		this.currentStoreID = storeID;
	}

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

	
	
}
