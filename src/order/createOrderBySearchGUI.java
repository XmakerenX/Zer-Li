package order;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeSet;

import catalog.CatalogController;
import catalog.CatalogGUI;
import catalog.CatalogItemView;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import customer.CustomerGUI;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import product.CatalogItem;
import product.ProdcutController;
import product.Product;
import serverAPI.GetJoinedTablesWhereRequest;
import serverAPI.Response;
import user.User;
import utils.FormController;
import utils.ImageData;



//*************************************************************************************************
	/**
	*  Provides a GUI to create an order by search(without the catalog)
	*/
//*************************************************************************************************
//------------------------------------------------------------------------

public class createOrderBySearchGUI extends CreateOrderGUI implements ClientInterface
{
	//gui:
	   @FXML
	    private TextField nameFIeld;

	    @FXML
	    private TextField idField;

	    @FXML
	    private Button searchButton;
	    
	    private int currentStoreID;
	    private User currentUser;
	    //local list of all items in the specific store catalog
	    final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
    	TreeSet<CatalogItem> productItemSet = new TreeSet<CatalogItem>();
    	TreeSet<CatalogItem> CatalogItemSet = new TreeSet<CatalogItem>();

    	
	    Response res;
	   
	    
	    //initializes the table view
	    public void doInit()
	    {
	    	this.orderTable.getItems().clear();
	    	this.orderTable.getColumns().remove(removeCol);
	    	this.orderTable.getColumns().remove(viewCol);
	    	this.orderTable.getColumns().remove(imageCol);

	    }
	    @FXML
	    void OnCancel(ActionEvent event) 
	    {
	    	Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("About to cancel order");
			alert.setContentText("Are you sure you want to cancel the order?");

			ButtonType result = alert.showAndWait().get();
			if (result == ButtonType.YES)
			{
				returnToCustomerGUI();
			}
	    }
//----------------------------------------------------------------
	    /**
	     * back to the customer gui
	     */
	    void returnToCustomerGUI()
		{
			this.addressTxt.clear();
			this.hourTxt.clear();
			this.minsTxt.clear();
			this.receiverNameTxt.clear();
			this.receiverPhoneTxt.clear();
			this.date.getEditor().clear();
			
			CustomerGUI customerGUI = (CustomerGUI)parent;
	    	//CustomerGUI customerGUI = FormController.<CustomerGUI, AnchorPane>loadFXML(getClass().getResource("/customer/CustomerGUI.fxml"), this);
	    	Client.getInstance().setUI(customerGUI);
			customerGUI.loadStores();
			FormController.primaryStage.setScene(customerGUI.getScene());
		    FormController.primaryStage.hide();
		    FormController.primaryStage.setTitle("Customer menu");
		    FormController.primaryStage.show();
	
		}
//----------------------------------------------------------------
	    public void setCurrentCustomer(Customer currentCustomer)
	    {
	    	super.setCurrentCustomer(currentCustomer);

	    }
//----------------------------------------------------------------
	    /**
	     * sets the current user to the one we receive
	     * @param user		the user received
	     */
	    public void setCurrentUser(User user)
	    {
	    	this.currentUser = user;
	    }
//----------------------------------------------------------------
	    /**
	     * sets the current store to the one we receive
	     * @param storeID		received store id
	     */
	    public void setCurrentStoreID(int storeID)
	    {
	    	
	    	this.currentStoreID = storeID;
	    	super.setCurrentStore(storeID);
	    	addStoreProductsToSet(productItemSet);
	    	
	    	// get Base catalog items
	    	addStoreCatalogToSet(0, CatalogItemSet);
	    	
	    	// get currentStore catalog items
	    	if (currentStoreID != 0)
	    	{
	    		addStoreCatalogToSet(currentStoreID, CatalogItemSet);
	    	}
	    	
	    	
	    	
	    }
//------------------------------------------------------------------------
	    /*
	     * search local catalogProducts for a match
	     */
	    @FXML
	    void onSearchButton(ActionEvent event) 
	    {
	    	//Check which conditions to check:
	    	Boolean searchByName = !nameFIeld.getText().equals("");
	        Boolean searchByID = !idField.getText().equals("");
	        CatalogItem foundItem = null;
	        long ProductIDToSearch = -1;//it must be initialized. 
	        
	        CatalogItem[] reference = new CatalogItem[1];
	        reference[0] = null;
	        if((!searchByName) && (!searchByID))
	        {
	        	return;
	       }
	        Boolean foundInCatalog = SearchItemIn(CatalogItemSet,reference);


	        if(!foundInCatalog)
	        {
	        	Boolean foundInProducts = SearchItemIn(productItemSet,reference);
	        	if(!foundInProducts)
	        	{
	        		informAlert("Sorry,Item was not found");
	        		return;
	        	}
	        }
	        foundItem = reference[0];
	        
	        if(foundItem!=null)
	        {
	        	loadItem(foundItem); //loads to table;
	        }
	        
	    }
//------------------------------------------------------------------------
	    /**
	     * looks for the item in the set
	     * @param set set of items
	     * @param catItem	the item
	     * @return true if found, else returns false
	     */
private Boolean SearchItemIn(TreeSet<CatalogItem> set,CatalogItem[] catItem)
{
	Boolean searchByName = !nameFIeld.getText().equals("");
    Boolean searchByID = !idField.getText().equals("");
	long ProductIDToSearch =-1; 
    //Get search condition: 
	String nameToSearch = nameFIeld.getText();
	if(searchByID)
	{
		ProductIDToSearch = Long.parseLong(idField.getText());
	}
	Boolean suitable = false;

    Boolean itemWasFound = false;
    
    for(CatalogItem element: set)
	{
    	suitable = true;
		if(searchByName)
		{
			suitable  = element.getName().equals(nameToSearch);
		}
		
		if(searchByID)
		{
			suitable  = element.getID() == ProductIDToSearch;
		}
		
		if(!suitable) continue;
		else
		{	
			catItem[0] = element;
		
			itemWasFound = true;
			break;
		}
		
	}
    
    	return itemWasFound;
}
//------------------------------------------------------------------------
/**
 * loads a catalog item
 * @param catItem	a catalogue item to load
 */
private void loadItem(CatalogItem catItem)
{

	ObservableList<CatalogItemView> newTableData = FXCollections.observableArrayList();
	CatalogItemView catItemView = new CatalogItemView(catItem,catItem.getImageName());
	newTableData.add(catItemView);
	
	loadItemsInOrder(FXCollections.observableArrayList(newTableData));
}



//------------------------------------------------------------------------
    	private void informAlert(String informMessege)
	    {
	    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setContentText(informMessege);
			alert.showAndWait();
	    }
//------------------------------------------------------------------------
    	/**
    	 * loads the item in the order
    	 */
	    public void loadItemsInOrder(ObservableList<CatalogItemView> orderCatalogItems)
	    {
	    	super.orderTotalPrice = 0;
	    	ObservableList<OrderItemView> orderItems = FXCollections.observableArrayList();
	    	
	    	for (CatalogItemView item : orderCatalogItems)
	    	{
	    		orderTotalPrice += item.getPrice();
	    		OrderItemView itemView = new OrderItemView(item);
	    		//itemView.getObservableRemoveButton().addObserver(this);
	    		itemView.getRemoveBtn().setOnAction(orderItemRemoveAction);
	    		orderItems.add(itemView);
	    	}
	    	
	    	this.orderTable.setItems(orderItems);
	    	totalPrice.setText(""+orderTotalPrice);
	    	selfPickupRadio.setSelected(true);
	    	this.addressTxt.setDisable(true);
	    	this.receiverNameTxt.setDisable(true);
	    	this.receiverPhoneTxt.setDisable(true);
	    	creditCardRadio.setSelected(true);
	    	customOrder = false;
	    }
//------------------------------------------------------------------------
	    /**
	     * adds a selected product to a given set
	     * @param productItemSet	the set we add a product to
	     */
	    private void addStoreProductsToSet(TreeSet<CatalogItem> productItemSet)
	    {
	    	res = null;
	    	ProdcutController.requestProducts(Client.getInstance());
	    	
	    	//Client.client.handleMessageFromClientUI(new GetJoinedTablesWhereRequest("Product", "CatalogProduct", 0, "StoreID", ""+storeID));
	    	
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
			
			if (res != null)
			{
				if (res.getType() == Response.Type.SUCCESS)
				{
					ArrayList<Product> resArray = (ArrayList<Product>)res.getMessage();
					ArrayList<CatalogItem> catalogItems  = new ArrayList<CatalogItem>();
					
					for(Product prod : resArray)
					{
						if(!prod.getType().equals("FLOWER"))
						{
							CatalogItem catItem = new CatalogItem(prod, -1, null, null, this.currentStoreID);
							catalogItems.add(catItem);
						}
					}
					
					for (CatalogItem item : catalogItems)
					{
						productItemSet.add(item);
					}
				}
			}
	    }
//------------------------------------------------------------------------	  
	    /**
	     * adds a store catalog to set	
	     * @param storeID	of the catalog
	     * @param set	the set we add to
	     */
	    private void addStoreCatalogToSet(int storeID, TreeSet<CatalogItem> set)
	    {
	    	res = null;
	    
	    	
	    	Client.getInstance().handleMessageFromClientUI(
	    			new GetJoinedTablesWhereRequest("Product", "CatalogProduct", 0, "StoreID", ""+storeID));
	    	
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
			
			if (res != null)
			{
				if (res.getType() == Response.Type.SUCCESS)
				{
					ArrayList<CatalogItem> resArray = (ArrayList<CatalogItem>)res.getMessage();
			
					
					for (CatalogItem item : resArray)
					{
						set.add(item);
					}
				}
			}
	    }
	    //-------------------------------------------------------------------------
	    /**
	     * downloading the missing images for the catalog 
	     * @param productItemSet	the items that should be in it
	     */
	    public void downloadMissingCatalogImages(TreeSet<CatalogItem> productItemSet)
	    {
	    	ArrayList<String> missingImages = CatalogController.scanForMissingCachedImages(productItemSet);
			if (missingImages.size() > 0)
			{
				System.out.println("Missing images "+ missingImages);
				res = null;
				CatalogController.requestCatalogImages(missingImages, Client.getInstance());

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
				
	    		if (res != null)
	    			CatalogController.saveCatalogImages((ArrayList<ImageData>)res.getMessage());
			}
	    }

	    @FXML
	    void onConfirmOrder(ActionEvent event) 
	    {
	    	super.onConfirmOrder(event);	    	
	    		
	    	
	    	
	    }
	    @Override
	    public void display(Object message) 
	    {
	    	super.replay = (Response)message;
	    	res = super.replay;

	    	synchronized(this)
	    	{
	    	  this.notify();
	    	}
	    }
	
}
