package order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import product.Product;
import java.util.ArrayList;
import java.util.TreeSet;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import prototype.FormController;
import serverAPI.GetRequestWhere;
import serverAPI.Response;
import utils.ImageData;

//*************************************************************************************************
	/**
	*  Provides a GUI allows the customer to order custom Item
	*/
//*************************************************************************************************
public class CustomItemGUI extends FormController implements ClientInterface {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private Response replay = null;
	private long currentStoreID = 0;
	private Customer currentCustomer = null;
	private float itemTotalPrice = 0;
	private CreateOrderGUI createOrderGUI = null;
	
    @FXML
    private ComboBox<String> itemTypeCbx;

    @FXML
    private TextField rangeMin;

    @FXML
    private TextField rangeMax;

    @FXML
    private ComboBox<String> dominateColorCbx;

    @FXML
    private Button CreateCustomItemBtn;

    @FXML
    private TableView<Product> customTable;
    
    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, Number> priceCol;

    @FXML
    private TableColumn<Product, String> colorCol;
    
    @FXML
    private TableColumn<Product, Number> amountCol;

    @FXML
    private Button orderCustomItemBtn;

    private String toStringType(Product.Type t)
    {
    	String typeName = t.toString();
    	typeName = typeName.replaceAll("_", " ");
    	typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
    	return typeName;
    }
    
 	//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*  Initializes the table view and child GUI's
  	*/
 	//*************************************************************************************************
    @FXML
    public void initialize()
    {
    	InitTableView();
    	
    	ArrayList<String> comboboxTypeStrings = new ArrayList<String>();
    	
    	comboboxTypeStrings.add(toStringType(Product.Type.BOUQUET));
    	comboboxTypeStrings.add(toStringType(Product.Type.BRIDE_BOUQUET));
    	comboboxTypeStrings.add(toStringType(Product.Type.FLOWERPOT));
    	
    	itemTypeCbx.setItems(FXCollections.observableArrayList(comboboxTypeStrings));
    	
    	createOrderGUI = FormController.<CreateOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CreateOrderGUI.fxml"), parent);
    	
    } 
    
    //*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
    //*************************************************************************************************
    private void InitTableView()
    {   	
    	nameCol.setCellValueFactory( new PropertyValueFactory<Product,String>("Name"));
    	nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    			
    	priceCol.setCellValueFactory( new PropertyValueFactory<Product,Number>("Price"));
    	priceCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
    	
    	colorCol.setCellValueFactory( new PropertyValueFactory<Product,String>("Color"));
    	colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	amountCol.setCellValueFactory( new PropertyValueFactory<Product,Number>("Amount"));
    	amountCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
    }
    
    //*************************************************************************************************
    /**
  	*  Called when the Create Custom Item button is pressed
  	*  Generates a custom Item based on customer entered preferences
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onCreateCustomItem(ActionEvent event) 
    {
    	ArrayList<Product> flowers = getFlowers();
    	if (flowers != null)
    	{
    		Product mainFlower = null;
    		ArrayList<Product> customFlowers = new ArrayList<Product>();
    		for (Product flower : flowers)
    		{
    			if (flower.getColor().equals(dominateColorCbx.getSelectionModel().getSelectedItem()))
    			{
    				customFlowers.add(flower);
    				mainFlower = flower;
    				break;
    			}
    		}
    		
    		for (Product flower : flowers)
    		{
    			if (!flower.getColor().equals(dominateColorCbx.getSelectionModel().getSelectedItem()))
    			{
    				customFlowers.add(flower);
    				if (customFlowers.size() == 3)
    					break;
    			}
    		}

    		float maxPrice = Float.parseFloat(rangeMax.getText());
    		final ObservableList<Product> customProducts = FXCollections.observableArrayList();
    		
    		float currentPrice = 0;
    		for (int i = 0; i < customFlowers.size(); i++)
    		{
    			float ratio;
    			if (i == 0)
    			{
    				ratio = 1 / (float)customFlowers.size() * 2;
    				if (ratio > 1)
    					ratio = 1;
    			}
    			else
    			{
    				ratio = 1 / (float)customFlowers.size() / 2;
    			}
    			
				int flowerNum = (int)(maxPrice*ratio / customFlowers.get(i).getPrice());
				currentPrice += flowerNum * customFlowers.get(i).getPrice();
				customFlowers.get(i).setPrice(flowerNum * customFlowers.get(i).getPrice());
				customFlowers.get(i).setAmount(flowerNum);
				customProducts.add(customFlowers.get(i));
    		}
    		
    		Product minFlower = customFlowers.get(0);
    		for (int i = 1; i < customFlowers.size(); i++)
    		{
    			if (customFlowers.get(i).getPrice() < minFlower.getPrice())
    				minFlower = customFlowers.get(i);
    		}
    		
    		float priceLeft = maxPrice - currentPrice;

    		float originalPrice = minFlower.getPrice() / minFlower.getAmount();
    		int flowerNum = (int)(priceLeft / originalPrice);
    		minFlower.setPrice(minFlower.getPrice() + flowerNum * originalPrice);
    		currentPrice += flowerNum * originalPrice;
    		minFlower.setAmount(minFlower.getAmount() + flowerNum);
    		
    		itemTotalPrice = currentPrice;
    		customTable.setItems(customProducts);
    		
    		orderCustomItemBtn.setDisable(false);
    	}
    }

    //*************************************************************************************************
    /**
  	*  Called when the Create Order Custom Item button is pressed
  	*  loads the selected custom Item to createOrderGUI and pass to it so customer can finalize his 
  	*  order
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onOrderCustomItem(ActionEvent event) 
    {
    	String itemType = itemTypeCbx.getSelectionModel().getSelectedItem();
    	itemType = itemType.replaceAll(" ", "_");
    	itemType = itemType.toUpperCase();
    	String color = this.dominateColorCbx.getSelectionModel().getSelectedItem();
    	Product customItem = new Product((long)0, "Custom Item", itemType, itemTotalPrice, 1, color);
    	CustomItemView customItemComp = new CustomItemView(customItem, ImageData.ClientImagesDirectory + "customItem.jpg"
    			, null, customTable.getItems());
    	
    	if (createOrderGUI != null)
    	{
    		createOrderGUI.setCurrentCustomer(currentCustomer);
    		createOrderGUI.setCurrentStore(currentStoreID);
    		Client.client.setUI(createOrderGUI);
    		createOrderGUI.loadCustomItemInOrder(customItemComp);
    		FormController.primaryStage.setScene(createOrderGUI.getScene());
    	}
    }
    	
    //*************************************************************************************************
    /**
    *  Requests from server all the flowers items 
  	*  @return an Arraylist of the flowers items
  	*/
    //*************************************************************************************************
    public ArrayList<Product> getFlowers()
    {
    	replay = null;
    	Client.client.handleMessageFromClientUI(new GetRequestWhere("Product", "ProductType", ""+Product.Type.FLOWER));
    	
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
   				ArrayList<Product> flowers = (ArrayList<Product>)replay.getMessage();
   				replay = null;
   				return flowers;
   			}
   			else
   			{
   	   			Alert alert = new Alert(AlertType.ERROR);
   			  	alert.setTitle("Flower Error");
   		    	alert.setHeaderText((String)replay.getMessage());
   		    	alert.showAndWait();
   		    	replay = null;
   		    	return null;
   			}
   		}
   		else
   		{
   			Alert alert = new Alert(AlertType.ERROR);
		  	alert.setTitle("Server Respone timed out");
	    	alert.setHeaderText("Server Failed to response to request after "+ClientInterface.TIMEOUT+" Seconds");
	    	return null;
   		}
	    
    }
    
    //*************************************************************************************************
    /**
    *  Initialize the dominateColorCbx with all the flowers item dominate colors
  	*/
    //*************************************************************************************************
    public void loadDominateColors()
    {
    	dominateColorCbx.getSelectionModel().clearSelection();
    	itemTypeCbx.getSelectionModel().clearSelection();
    	rangeMin.clear();
    	rangeMax.clear();
    	customTable.getItems().clear();
    	
    	createOrderGUI.setParent(parent);
    	orderCustomItemBtn.setDisable(true);
    	
    	ArrayList<Product> flowers = getFlowers();
    	if (flowers != null)
    	{
    		TreeSet<String> comboboxColorStrings = new TreeSet<String>();
    		
    		for (Product flower : flowers)
    		{
    			comboboxColorStrings.add(flower.getColor());
    		}
    		
    		dominateColorCbx.setItems(FXCollections.observableArrayList(comboboxColorStrings));
    	}
    }

    //*************************************************************************************************
    /**
  	*  Called from the client when the server sends a response
  	*  fills the TableView with the received products data
  	*  @param message The Server response , an ArrayList of products
  	*/
    //*************************************************************************************************
    @Override
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
     * Sets the current Customer that is viewing the catalog
  	*  @param currentCustomer the customer to be set
  	*/
    //*************************************************************************************************
	public void setCurrentStoreID(long currentStoreID) {
		this.currentStoreID = currentStoreID;
	}

    //*************************************************************************************************
    /**
     * Sets the current store whose catalog is being viewed
  	*  @param storeID the storeID to be set
  	*/
    //*************************************************************************************************
	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
}
