package order;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;
import product.Product;
import product.Product.Type;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeSet;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import serverAPI.GetRequestWhere;
import serverAPI.Response;
import utils.FormController;
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
	private final float bridePrice = 20;
	private final float bouquetPrice = 10;
	
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
    private Label noteLabel;
    
    @FXML
    private Label totalPrice;
    
    @FXML
    private Button orderCustomItemBtn;

    @FXML
    private Button backBtn;
    
    private String toStringType(Product.Type t)
    {
    	String typeName = t.toString();
    	typeName = typeName.replaceAll("_", " ");
    	typeName = typeName.substring(0, 1).toUpperCase() + typeName.substring(1).toLowerCase();
    	return typeName;
    }
    
    private Product.Type toTypeFromString(String t)
    {
    	String typeName = t.replaceAll(" ", "_");
    	typeName = typeName.toUpperCase();
    	return Product.Type.valueOf(typeName);
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
    	comboboxTypeStrings.add(toStringType(Product.Type.FLOWERS_CLUSTER));
    	
    	itemTypeCbx.setItems(FXCollections.observableArrayList(comboboxTypeStrings));
    	
      	rangeMin.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if (newValue.length() > 3)
            	{
            		rangeMin.setText(oldValue);
            		return;
            	}
            	
                if (newValue.matches("([0-9]*)")) 
                {
                	rangeMin.setText(newValue);
                }
                else
                	rangeMin.setText(oldValue);
            }
        });
    	
      	rangeMax.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if (newValue.length() > 3)
            	{
            		rangeMax.setText(oldValue);
            		return;
            	}
            	
                if (newValue.matches("([0-9]*)")) 
                {
                	rangeMax.setText(newValue);
                }
                else
                	rangeMax.setText(oldValue);
            }
        });
      	
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
  	*  Called when the back button is pressed
  	*  Goes back to the parent GUI
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onBack(ActionEvent event) {
    	Client.client.setUI((ClientInterface)parent);
    	FormController.primaryStage.setScene(parent.getScene());
    	FormController.primaryStage.setTitle("Customer menu");
    }
    
    //*************************************************************************************************
    /**
     * Verifies that we got good input if not show an appropriate error message
     * @return true for valid input , false for invliad input
  	*/
    //*************************************************************************************************
    boolean verifyInput()
    {
    	if (rangeMin.getText().trim().equals(""))
    	{
    		showErrorMessage("Please Enter the minium price range");
    		return false;
    	}
    	
    	if (rangeMax.getText().trim().equals(""))
    	{
    		showErrorMessage("Please Enter the maxium price range");
    		return false;
    	}
    	
    	if (itemTypeCbx.getSelectionModel().getSelectedIndex() == -1)
    	{
    		showErrorMessage("Please Select a type");
    		return false;
    	}
    	
    	if(dominateColorCbx.getSelectionModel().getSelectedIndex() == -1 )
    	{
    		showErrorMessage("Please Select a dominate color");
    		return false;
    	}
    	
    	return true;
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
    	customTable.getItems().clear();
    	if (!verifyInput())
    		return;
    	
    	Product.Type type = this.toTypeFromString(itemTypeCbx.getSelectionModel().getSelectedItem().toUpperCase());
    	
    	if (type == Product.Type.BOUQUET || type == Product.Type.BRIDE_BOUQUET || 
    			type == Product.Type.FLOWERS_CLUSTER)
    		generateCustomFlowers();
    	
    	if (type == Product.Type.FLOWERPOT)
    		generateCustomPlants();
    	
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
  	*  Called when the item types checkbox is pressed
  	*  loads the dominate colors
  	*  @param event the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onTypeSelected(ActionEvent event) {
    	Product.Type type = this.toTypeFromString(itemTypeCbx.getSelectionModel().getSelectedItem().toUpperCase());
    	if (type == Product.Type.BRIDE_BOUQUET)
    		this.noteLabel.setText("Note : Bride Bouquet Cost an addional "+ bridePrice +" ILS");
    	else
	    	if (type == Product.Type.BOUQUET)
	    		this.noteLabel.setText("Note : Bouquet Cost an addional "+ bouquetPrice +" ILS");
	    	else
	    		this.noteLabel.setText("");
    	
    	getDominateColors(type);
    }
    
    //*************************************************************************************************
    /**
    *  Requests from server all the custom items components with the given type 
  	*  @return an Arraylist of the flowers items
  	*/
    //*************************************************************************************************
    private ArrayList<Product> getCustomComponents(Product.Type type)
    {
    	replay = null;
    	Client.client.handleMessageFromClientUI(new GetRequestWhere("Product", "ProductType", ""+type));
    	
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
    *  generate a custom item based on flowers (BOUQUET, BRIDE_BOUQUET, FLOWERS_CLUSTER)
  	*/
    //*************************************************************************************************
    public void generateCustomFlowers()
    {   	
    	ArrayList<Product> flowers = getCustomComponents(Product.Type.FLOWER);
    	ArrayList<Float> prices = new ArrayList<Float>();
    	
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
    		
    		for (Product flower : customFlowers)
    		{
    			prices.add(flower.getPrice());
    		}
    		
    		float maxPrice = Float.parseFloat(rangeMax.getText());
			Product.Type type = this.toTypeFromString(itemTypeCbx.getSelectionModel().getSelectedItem().toUpperCase());
			if (type == Product.Type.BOUQUET)
				maxPrice -= bouquetPrice;
			if (type == Product.Type.BRIDE_BOUQUET)
				maxPrice -= bridePrice;
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
    		
    		int min;
    		Product minFlower = customFlowers.get(0);
    		min = 0;
    		
    		for (int i = 1; i < customFlowers.size(); i++)
    		{
    			if (customFlowers.get(i).getPrice() < minFlower.getPrice())
    			{
    				min = i;
    				minFlower = customFlowers.get(i);
    			}
    		}
    		
    		float priceLeft = maxPrice - currentPrice;
    		float originalPrice = prices.get(min);
    		
    		int	flowerNum = (int)(priceLeft / originalPrice);
    		minFlower.setPrice(minFlower.getPrice() + flowerNum * originalPrice);
    		currentPrice += flowerNum * originalPrice;
    		minFlower.setAmount(minFlower.getAmount() + flowerNum);
    		
    		for (int i = customProducts.size() - 1; i >= 0; i--)
    		{
    			if (customProducts.get(i).getPrice() == 0)
    				customProducts.remove(i);
    		}
    		
    		if (currentPrice > 0)
    		{
    			type = this.toTypeFromString(itemTypeCbx.getSelectionModel().getSelectedItem().toUpperCase());
    			if (type == Product.Type.BOUQUET)
    				currentPrice += bouquetPrice;
    			if (type == Product.Type.BRIDE_BOUQUET)
    				currentPrice += bridePrice;
	    		itemTotalPrice = currentPrice;
	    		
	    		DecimalFormat df = new DecimalFormat();
	    		df.setMaximumFractionDigits(2);
	    		totalPrice.setText(df.format(itemTotalPrice)+" ILS");
	    		
	    		customTable.setItems(customProducts);
	    		orderCustomItemBtn.setDisable(false);
    		}
    		else
    		{
    			this.showErrorMessage("No Item found for the given parameters");
    		}
    	}
    }
    
    //*************************************************************************************************
    /**
    *  generate a custom item based on Plants
  	*/
    //*************************************************************************************************
    public void generateCustomPlants()
    {
    	ArrayList<Product> planets = getCustomComponents(Product.Type.PLANT);
    	
    	float maxPrice = Float.parseFloat(rangeMax.getText());
    	float minPrice = Float.parseFloat(rangeMin.getText());
    	
    	if (planets != null)
    	{
    		Product maxPlant = null;
    		for (Product plant : planets)
    		{
    			if (plant.getColor().equals(dominateColorCbx.getSelectionModel().getSelectedItem()))
    			{
    				if (plant.getPrice() > minPrice && plant.getPrice() < maxPrice)
    				{
    					if (maxPlant == null)
        					maxPlant = plant;
    					
    					if (maxPlant != null && plant.getPrice() > maxPlant.getPrice())
    						maxPlant = plant;
    				}
    			}
    		}
    		
    		if (maxPlant != null)
    		{
    			maxPlant.setAmount(1);
        		final ObservableList<Product> customProducts = FXCollections.observableArrayList();
        		customProducts.add(maxPlant);
        		itemTotalPrice = maxPlant.getPrice();
        		
	    		DecimalFormat df = new DecimalFormat();
	    		df.setMaximumFractionDigits(2);
	    		totalPrice.setText(df.format(itemTotalPrice)+" ILS");
	    		
	    		customTable.setItems(customProducts);
	    		orderCustomItemBtn.setDisable(false);
    		}
       		else
    		{
    			this.showErrorMessage("No Item found for the given parameters");
    		}
    	}  	
    }
    
    //*************************************************************************************************
    /**
    *  Clear and reset the GUI fields
  	*/
    //*************************************************************************************************
    private void clearFields()
    {
    	dominateColorCbx.getSelectionModel().clearSelection();
    	itemTypeCbx.getSelectionModel().clearSelection();
    	rangeMin.clear();
    	rangeMax.clear();
    	customTable.getItems().clear();
    	totalPrice.setText("");
    }
    
    //*************************************************************************************************
    /**
    *  Initialize the dominateColorCbx with all the flowers item dominate colors
  	*/
    //*************************************************************************************************
    public void initFields()
    {
    	clearFields();
    	createOrderGUI.setParent(parent);
    	orderCustomItemBtn.setDisable(true);
    	
    	getDominateColors(Product.Type.FLOWER);
    	
//    	ArrayList<Product> flowers = getCustomComponents(Product.Type.FLOWER);
//    	if (flowers != null)
//    	{
//    		TreeSet<String> comboboxColorStrings = new TreeSet<String>();
//    		
//    		for (Product flower : flowers)
//    		{
//    			comboboxColorStrings.add(flower.getColor());
//    		}
//    		
//    		dominateColorCbx.setItems(FXCollections.observableArrayList(comboboxColorStrings));
//    	}
    }

    //*************************************************************************************************
    /**
    *  Loads the custom components dominate colors to the dominateColorCbx
    *  @param  type 
  	*/
    //*************************************************************************************************
    private void getDominateColors(Product.Type type)
    {
    	ArrayList<Product> flowers = null;
    	if (type == Product.Type.BOUQUET || type == Product.Type.BRIDE_BOUQUET || 
    			type == Product.Type.FLOWERS_CLUSTER)
    		flowers = getCustomComponents(Product.Type.FLOWER);
    	
    	if (type == Product.Type.FLOWERPOT)
    		flowers = getCustomComponents(Product.Type.PLANT);
    
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
  	*  @param currentStoreID the current Store ID to be set
  	*/
    //*************************************************************************************************
	public void setCurrentStoreID(long currentStoreID) {
		this.currentStoreID = currentStoreID;
	}

    //*************************************************************************************************
    /**
     * Sets the current store whose catalog is being viewed
  	*  @param currentCustomer the current Customer to be set
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
