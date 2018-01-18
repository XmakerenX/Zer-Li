package order;

import java.util.Observable;
import java.util.Observer;
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
import javafx.scene.layout.Region;
import product.Product;
import prototype.FormController;
import serverAPI.Response;

public class CreateOrderGUI extends FormController implements ClientInterface, Observer {

	protected Customer currentCustomer = null;
	private long currentStoreID = 0;
	protected float orderTotalPrice;
	protected boolean customOrder = false;
	 // holds the last replay we got from server
 	private Response replay = null;
	
    @FXML
	protected TableView<OrderItemView> orderTable;

    @FXML
    private TableColumn<OrderItemView, ImageView> imageCol;

    @FXML
    private TableColumn<OrderItemView, TextArea> nameCol;

    @FXML
    private TableColumn<OrderItemView, String> typeCol;

    @FXML
    private TableColumn<OrderItemView, String> colorCol;

    @FXML
    private TableColumn<OrderItemView, Number> priceCol;

    @FXML
    private TableColumn<OrderItemView, TextArea> greetingCardCol;
    
    @FXML
	protected TableColumn<OrderItemView, OrderItemViewButton> removeCol;

    @FXML
	protected TableColumn<OrderItemView, Button> viewCol;
    
    @FXML
	protected Label totalPrice;

    @FXML
    protected DatePicker date;
    
    @FXML
	protected RadioButton selfPickupRadio;
    
    @FXML
    protected ToggleGroup pickupMethod;

    @FXML
	protected TextField addressTxt;

    @FXML
    protected RadioButton cashRadio;
    
    @FXML
    protected ToggleGroup payMethod;

    @FXML
	protected RadioButton creditCardRadio;
    
    @FXML
    protected RadioButton subscriptonRadio;
    
    @FXML
    protected TextField hourTxt;

    @FXML
    protected TextField minsTxt;

    @FXML
    private Button confirmOrderBtn;

    @FXML
    private Button cancelBtn;
    
    @FXML
	protected TextField receiverPhoneTxt;

    @FXML
	protected TextField receiverNameTxt;
	
//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*/
//*************************************************************************************************
    @FXML
    //Will be called by FXMLLoader
    public void initialize()
    {
    	InitTableView();
    	
    	// Set the radio button userData to be the corresponding enum to be retrieved later
    	this.cashRadio.setUserData(Order.PayMethod.CASH);
    	this.creditCardRadio.setUserData(Order.PayMethod.CREDITCARD);
    	this.subscriptonRadio.setUserData(Order.PayMethod.SUBSCRIPTION);
    	
    	hourTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() < 3 && newValue.matches("([0-9]*)")) 
                {
                	if (newValue.length() > 0)
                	{
	                	int hour = Integer.parseInt(newValue);
	                	if (hour < 24)
	                		hourTxt.setText(newValue);
	                	else
	                		hourTxt.setText(oldValue);
                	}
                	else
                		hourTxt.setText(newValue);
                }
                else
                	hourTxt.setText(oldValue);
            }
        });
    	
    	minsTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() < 3 && newValue.matches("([0-9]*)")) 
                {
                	if (newValue.length() > 0)
                	{
                		int min = Integer.parseInt(newValue);
                		if (min < 60)
                			minsTxt.setText(newValue);
                		else
                			minsTxt.setText(oldValue);
                	}
                	else
                		minsTxt.setText(newValue);
                }
                else
                	minsTxt.setText(oldValue);
            }
        });
    	
    	receiverPhoneTxt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("([0-9]*)")) 
                {
                	receiverPhoneTxt.setText(newValue);
                }
                else
                	receiverPhoneTxt.setText(oldValue);
            }
        });
    	
    }
    	
    
//*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
//*************************************************************************************************
    private void InitTableView()
    {
    	imageCol.setCellValueFactory(new PropertyValueFactory<OrderItemView, ImageView>("image"));
    	nameCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,TextArea>("nameArea"));    	
    	typeCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,String>("Type"));
    	colorCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,String>("Color"));
    	
    	priceCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,Number>("Price"));
    	greetingCardCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,TextArea>("greetingCard"));
    	removeCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,OrderItemViewButton>("removeBtn"));
    	viewCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,Button>("viewBtn"));
    	
    	this.orderTable.setEditable(false);
    }
    
//*************************************************************************************************
    /**
  	*  The action to perform when the remove button is pressed
  	*  "Changes" the state of the observable remove button in the tableview to trigger our update method 
  	*  to signal us what item in the table to remove
  	*  @param e the event that triggered this function
  	*/
//*************************************************************************************************
	EventHandler<ActionEvent> orderItemRemoveAction  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {
	    	Button b = (Button)e.getSource();
	    	OrderItemViewButton obsButton = (OrderItemViewButton)b.getUserData();
	    	obsButton.change();
	    	obsButton.notifyObservers(obsButton.getOrderItem());
	    }
	};
	    
//*************************************************************************************************
    /**
  	*  Switches to this GUI parent
  	*/
//*************************************************************************************************
	void returnToParent()
	{
		this.addressTxt.clear();
		this.hourTxt.clear();
		this.minsTxt.clear();
		this.receiverNameTxt.clear();
		this.receiverPhoneTxt.clear();
		this.date.getEditor().clear();
		
		if (!this.customOrder)
		{
			CatalogGUI catalogGUI = (CatalogGUI)parent;
	    	Client.client.setUI(catalogGUI);
	    	catalogGUI.onRefresh(null);
	    	FormController.primaryStage.setScene(parent.getScene());
	    	FormController.primaryStage.hide();
	    	FormController.primaryStage.show();
		}
		else
		{
			CustomerGUI customerGUI = (CustomerGUI)parent;
			Client.client.setUI(customerGUI);
			customerGUI.loadStores();
	    	FormController.primaryStage.setScene(parent.getScene());
	    	FormController.primaryStage.hide();
	    	FormController.primaryStage.show();
		}
	}

//*************************************************************************************************
    /**
  	*  Cancels this order and Switches to this GUI parent
  	*  @param e the event that triggered this function
  	*/
//*************************************************************************************************
    @FXML
    void OnCancel(ActionEvent event) 
    {
    	Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
		alert.setHeaderText("About to cancel order");
		alert.setContentText("Are you sure you want to cancel the order?");

		ButtonType result = alert.showAndWait().get();
		if (result == ButtonType.YES)
		{
			returnToParent();
		}
    }

//*************************************************************************************************
    /**
  	*  Creates a new Order
  	*  @param e the event that triggered this function
  	*/
//*************************************************************************************************
    @FXML
    void onConfirmOrder(ActionEvent event) 
    {
    	String delivaryAddress = null;
    	String receiverName = null;
    	String receiverPhoneNumber = null;
    	
    	if ( ((RadioButton)pickupMethod.getSelectedToggle()).getText().contains("Delivery") )
    	{
    		delivaryAddress= this.addressTxt.getText();
    		receiverName = this.receiverNameTxt.getText();
    		receiverPhoneNumber = this.receiverPhoneTxt.getText();
    	}
    	
    	String orderTime = this.hourTxt.getText() + ":" + this.minsTxt.getText();
    	Order.PayMethod payMethod = (Order.PayMethod)this.payMethod.getSelectedToggle().getUserData();
    	
    	try 
    	{
    		if (currentCustomer == null)
    		{
    			System.out.println("currentCustomer is null aborting!!!!");
    			return;
    		}
    		
    		if (currentStoreID == 0)
    		{
    			System.out.println("currentStoreID is not set aborting!!!!");
    			return;
    		}
        	// create our order
        	// we don't give the order ID , the database(on the server side) will do it for us :)
    		Order order = new Order(0, Order.Status.NEW, orderTotalPrice, null,this.date.getValue(), orderTime,
    				delivaryAddress, receiverName, receiverPhoneNumber,
    				payMethod, currentStoreID, currentCustomer.getID());

    		if (!customOrder)
    			OrderController.CreateNewOrder(order, orderTable.getItems());
    		else
    			OrderController.CreateNewCustomOrder(order, orderTable.getItems());
    		
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
    		{
    			if (replay.getType() == Response.Type.SUCCESS)
    			{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setHeaderText("Order success");
    				alert.setContentText("Order was added");
    				alert.showAndWait();

    				returnToParent();
    				replay = null;
    			}
    			else
    			{
    				Alert alert = new Alert(AlertType.ERROR);
    				alert.setHeaderText("Order Failure");
    				alert.setContentText("Failed to create new order");
    				alert.showAndWait();
    				replay = null;
    			}
    		}	
    	}
    	catch (OrderException e) {
    		System.out.println(e.getMessage());
    		e.printStackTrace();
    	}
    }

//*************************************************************************************************
    /**
  	*  Updates the order GUI to enable the Delivery fields
  	*  @param e the event that triggered this function
  	*/
//*************************************************************************************************
    @FXML
    void onDelivary(ActionEvent event) {
    	this.addressTxt.setDisable(false);
    	this.receiverNameTxt.setDisable(false);
    	this.receiverPhoneTxt.setDisable(false);
    	orderTotalPrice += Order.delivaryCost;
    	this.totalPrice.setText(""+orderTotalPrice);
    }

//*************************************************************************************************
    /**
  	*  Updates the order GUI to disable the Delivery fields
  	*  @param e the event that triggered this function
  	*/
//*************************************************************************************************
    @FXML
    void onSelfPickup(ActionEvent event) {
    	this.addressTxt.setDisable(true);
    	this.receiverNameTxt.setDisable(true);
    	this.receiverPhoneTxt.setDisable(true);
    	orderTotalPrice -= Order.delivaryCost;
    	this.totalPrice.setText(""+orderTotalPrice);
    }
    
//*************************************************************************************************
    /**
  	*  loads the given orderCatalogItems to orderTable
  	*  @param orderCatalogItems an Arraylist of the items to load in this order
  	*/
//*************************************************************************************************
    public void loadItemsInOrder(ObservableList<CatalogItemView> orderCatalogItems)
    {
    	orderTotalPrice = 0;
    	ObservableList<OrderItemView> orderItems = FXCollections.observableArrayList();
    	
    	for (CatalogItemView item : orderCatalogItems)
    	{
    		orderTotalPrice += item.getPrice();
    		OrderItemView itemView = new OrderItemView(item);
    		itemView.getObservableRemoveButton().addObserver(this);
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
    
    public void loadCustomItemInOrder(CustomItemView customItem)
    {
    	orderTotalPrice = 0;
    	
    	ObservableList<OrderItemView> orderItems = FXCollections.observableArrayList();
    	orderItems.add(customItem);
    	
    	customItem.getObservableRemoveButton().addObserver(this);
    	customItem.getRemoveBtn().setOnAction(orderItemRemoveAction);
    	
    	this.orderTable.setItems(orderItems);
    	orderTotalPrice = customItem.getPrice();
    	totalPrice.setText(""+customItem.getPrice());
    	selfPickupRadio.setSelected(true);
    	this.addressTxt.setDisable(true);
    	this.receiverNameTxt.setDisable(true);
    	this.receiverPhoneTxt.setDisable(true);
    	creditCardRadio.setSelected(true);
    	customOrder = true;
    }
    
	@Override
	public void display(Object message) {
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	replay = (Response)message;

		synchronized(this)
		{
			this.notify();
		}
	}

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

//*************************************************************************************************
    /**
  	*  Triggered by the observable remove button in the table to indicate what item to remove form
  	*  the table
  	*  @param o the Observable button triggering this method
  	*  @param arg the item to remove from the table
  	*/
//*************************************************************************************************
	@Override
	public void update(Observable o, Object arg)
	{
    	Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
    	if (this.orderTable.getItems().size() > 1)
    	{
			alert.setHeaderText("About to remove item from order");
			alert.setContentText("Are you sure you want to remove item from order?");
    	}
    	else
    	{
    		alert.setHeaderText("About cancel order");
			alert.setContentText("Are you sure you want to remove item from order? this will cancel the order!");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
    	}
		ButtonType result = alert.showAndWait().get();
		if (result == ButtonType.YES)
		{
			OrderItemView orderItem = (OrderItemView)arg;
			this.orderTotalPrice -= orderItem.getPrice();
			this.totalPrice.setText(""+orderTotalPrice);
			this.orderTable.getItems().remove(arg);
			if (this.orderTable.getItems().size() == 0)
				returnToParent();
		}
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}

	public void setCurrentStore(long currentStore) {
		this.currentStoreID = currentStore;
	}
	
	
	
}
