package order;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
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
import javafx.scene.web.WebView;
import javafx.util.Callback;
import serverAPI.Response;
import utils.FormController;

//*************************************************************************************************
	/**
	*  Provides a GUI that allow the customer to finalize his order
	*/
//*************************************************************************************************
public class CreateOrderGUI extends FormController implements ClientInterface {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	protected Customer currentCustomer = null;
	private long currentStoreID = 0;
	protected float orderTotalPrice;
	protected boolean customOrder = false;
	private boolean subsOrder = false;
	private float subsAmount = 0;
	 // holds the last replay we got from server
 	public Response replay = null;
	
    @FXML
	protected TableView<OrderItemView> orderTable;

    @FXML
	protected TableColumn<OrderItemView, ImageView> imageCol;

    @FXML
    private TableColumn<OrderItemView, TextArea> nameCol;

    @FXML
    private TableColumn<OrderItemView, String> typeCol;

    @FXML
    private TableColumn<OrderItemView, String> colorCol;

    @FXML
    private TableColumn<OrderItemView, WebView> priceCol;

    @FXML
    private TableColumn<OrderItemView, TextArea> greetingCardCol;
    
    @FXML
	protected TableColumn<OrderItemView, Button> removeCol;

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
    protected RadioButton StoreAccountRadio;
    
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
  	*  Initializes the table view and text fields listeners
  	*/
 	//*************************************************************************************************
    @FXML
    public void initialize()
    {
    	InitTableView();
    	
    	// Set the radio button userData to be the corresponding enum to be retrieved later
    	cashRadio.setUserData(Order.PayMethod.CASH);
    	creditCardRadio.setUserData(Order.PayMethod.CREDITCARD);
    	subscriptonRadio.setUserData(Order.PayMethod.SUBSCRIPTION);
    	StoreAccountRadio.setUserData(Order.PayMethod.STORE_ACCOUNT);
    	
    	// set hourTxt changed Listener
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
    	
    	// set minsTxt changed Listener
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
    	
    	// set receiverPhoneTxt changed Listener
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
    	
    	final Callback<DatePicker, DateCell> dayCellFactory;

    	dayCellFactory = (final DatePicker datePicker) -> new DateCell() {
    	    @Override
    	    public void updateItem(LocalDate item, boolean empty) {
    	        super.updateItem(item, empty);
    	        LocalDate minDate = LocalDate.now();
    	        if (item.isBefore(minDate))
    	        { //Disable all dates after required date
    	            setDisable(true);
    	            setStyle("-fx-background-color: #ffc0cb;"); //To set background on different color
    	        }
    	    }
    	};
    	date.setDayCellFactory(dayCellFactory);
    	
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
    	
    	priceCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,WebView>("SalePriceView"));
    	greetingCardCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,TextArea>("greetingCard"));
    	removeCol.setCellValueFactory(new PropertyValueFactory<OrderItemView,Button>("removeBtn"));
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
	    	
	    	ButtonType result;
	    	if (orderTable.getItems().size() > 1)	    
	    		result = showConfirmationDialog("About to remove item from order",
	    				"Are you sure you want to remove item from order?");
	    	else
	    		result = showConfirmationDialog("About cancel order",
	    				"Are you sure you want to remove this item from order? this will cancel the order!");

			if (result == ButtonType.YES)
			{
				OrderItemView orderItem = (OrderItemView)b.getUserData();
				if (orderItem.getSalePrice() > 0)
					setTotalPriceText(-orderItem.getSalePrice());
				else
					setTotalPriceText(-orderItem.getPrice());
				
				if (subscriptonRadio.isSelected())
				{
					// a beautiful hack
					// updates to the new price
					onCreditCard(null);
					onSubscription(null);
				}
				
				// remove item form items list
				orderTable.getItems().remove(orderItem);
				if (orderTable.getItems().size() == 0)
					returnToParent();
				
		    	if (orderTotalPrice < currentCustomer.getAccountBalance())
		    	{
		    		StoreAccountRadio.setDisable(false);
		    	}
		    	else
		    		StoreAccountRadio.setDisable(true);
			}
	    }
	};

	//*************************************************************************************************
    /**
  	*  Clears the GUI text fields
  	*/
	//*************************************************************************************************
	private void clearFields()
	{
		addressTxt.clear();
		hourTxt.clear();
		minsTxt.clear();
		receiverNameTxt.clear();
		receiverPhoneTxt.clear();
		date.getEditor().clear();
	}
	
	//*************************************************************************************************
    /**
  	*  Reset the GUI controls to their default status
  	*/
	//*************************************************************************************************
	private void resetControls()
	{
    	selfPickupRadio.setSelected(true);
    	addressTxt.setDisable(true);
    	receiverNameTxt.setDisable(true);
    	receiverPhoneTxt.setDisable(true);
    	creditCardRadio.setSelected(true);
	}
	
	//*************************************************************************************************
    /**
  	*  Switches to this GUI parent
  	*/
	//*************************************************************************************************
	private void returnToParent()
	{
		clearFields();
		
		if (!customOrder)
		{
			CatalogGUI catalogGUI = (CatalogGUI)parent;
	    	Client.client.setUI(catalogGUI);
	    	catalogGUI.onRefresh(null);
		}
		else
		{
			CustomerGUI customerGUI = (CustomerGUI)parent;
			Client.client.setUI(customerGUI);
			customerGUI.loadStores();
		}
		
    	FormController.primaryStage.setScene(parent.getScene());
    	FormController.primaryStage.setTitle("Catalog");
    	FormController.primaryStage.hide();
    	FormController.primaryStage.show();
	}

	//*************************************************************************************************
    /**
  	*  Sets the current time in date field and hour and mins text fields
  	*/
	//*************************************************************************************************
	private void setCurrentTime()
	{
		date.setValue(null);
    	Calendar currentTime = Calendar.getInstance();
    	// Add 3 hours to current time
    	currentTime.setTimeInMillis(currentTime.getTimeInMillis() + 10800000);
    	
		// set hour and mins textFields to 3 hours from now
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
		String[] times = sdf.format(currentTime.getTime()).split(":"); 
		hourTxt.setText(times[0]);
		minsTxt.setText(times[1]);
		// set the date picker to current Date + 3 hours
		LocalDate currentDate = currentTime.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		this.date.setValue(currentDate);
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
		ButtonType result = showConfirmationDialog("About to cancel order", "Are you sure you want to cancel the order?");
		if (result == ButtonType.YES)		
			returnToParent();
    }

	//*************************************************************************************************
    /**
  	*  Verify that the Delivery information is entered correctly
  	*  @return true for everything is entered OK.
  	*  		   false for there is an error in one of the fields
  	*/
	//*************************************************************************************************
    private boolean verifyDeliveryInformation()
    {
		if (addressTxt.getText().trim().equals(""))
		{
			showErrorMessage("Please fill the Delivery Adress textfield");
			return false;
		}
		
		if (receiverNameTxt.getText().trim().equals(""))
		{
			showErrorMessage("Please fill the Receiver Name textfield");
			return false;
		}
		
		if (receiverPhoneTxt.getText().trim().equals(""))
		{
			showErrorMessage("Please fill the Receiver phone textfield");
			return false;
		}
		
		return true;
    }
    
	//*************************************************************************************************
    /**
  	*  Verify that the Date Time of the order is entered correctly
  	*  prints error message if some field is wrong
  	*  @return true for everything is entered OK.
  	*  		   false for there is an error in one of the fields
  	*/
	//*************************************************************************************************
    private boolean verifyDateTimeInformation()
    {
    	// verify hour 
    	if (this.hourTxt.getText().trim().equals(""))
    	{
    		showErrorMessage("Please fill the Order Time textfield");
    		return false;
    	}
    	
    	// verify mins
    	if (this.minsTxt.getText().trim().equals(""))
    	{
    		showErrorMessage("Please fill the Order Time textfield");
    		return false;
    	}
    	
    	// load current time
    	Calendar currentTime = Calendar.getInstance();
    	// load orderRequiredDateTime
    	Calendar orderRequiredDateTime;
    	LocalDate orderDate = this.date.getValue();
    	String orderTime = this.hourTxt.getText() + ":" + this.minsTxt.getText();
		String[] time = orderTime.trim().split(":");
		orderRequiredDateTime = new GregorianCalendar(orderDate.getYear(), orderDate.getMonth().getValue() - 1, orderDate.getDayOfMonth(),
				Integer.parseInt(time[0]), Integer.parseInt(time[1]));
    	
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		// verify valid orderRequiredDateTime
    	if (orderRequiredDateTime.before(currentTime))
    	{
    		String timeSring =  sdf.format(currentTime.getTime());
    		showErrorMessage("Please enter Time after the current time : " + timeSring);
    		return false;
    	}
    	// current + 3 hours
    	currentTime.setTimeInMillis(currentTime.getTimeInMillis() + 10740000);
    	
		// warn about rounding up of the order time 
    	if (orderRequiredDateTime.before(currentTime))
    	{
    		
    		ButtonType result = showConfirmationDialog("Change in Order Date Time", "The Store can only guarantee supply"
    				+ " of your order in 3 hours from the current time , you order will be ready in "
    				+ sdf.format(currentTime.getTime()) + " Do you accept ?");
    		
    		if (result.equals(ButtonType.NO))
    			return false;
    	}
    	
    	return true;
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
    	String deliveryAddress = null;
    	String receiverName = null;
    	String receiverPhoneNumber = null;
    	
    	if ( ((RadioButton)pickupMethod.getSelectedToggle()).getText().contains("Delivery") )
    	{
    		if (verifyDeliveryInformation())
    		{
    			deliveryAddress     = addressTxt.getText();
    			receiverName        = receiverNameTxt.getText();
    			receiverPhoneNumber = receiverPhoneTxt.getText();
    		}
    		else
    			return;
    	}
    	
    	if (!verifyDateTimeInformation())
    		return;
    	
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
    				deliveryAddress, receiverName, receiverPhoneNumber,
    				payMethod, currentStoreID, currentCustomer.getID());

    		if (!customOrder)
    			OrderController.CreateNewOrder(order, orderTable.getItems());
    		else
    			OrderController.CreateNewCustomOrder(order, orderTable.getItems());
    		
    		waitForResponse();

    		if (replay == null)
    			return;

    		if (replay.getType() == Response.Type.SUCCESS)
    		{
    			// update our customer locally , no need to reload it from database for a change we know
    			if (order.getOrderPaymentMethod() == Order.PayMethod.STORE_ACCOUNT)
    				currentCustomer.setAccountBalance(currentCustomer.getAccountBalance() - order.orderPrice);
    			showInformationMessage("Order was added");
    			returnToParent();
    			replay = null;
    		}
    		else
    		{
    			showErrorMessage("Failed to create new order");
    			replay = null;
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
    void onDelivery(ActionEvent event) {
    	this.addressTxt.setDisable(false);
    	this.receiverNameTxt.setDisable(false);
    	this.receiverPhoneTxt.setDisable(false);
    	
    	if (this.subscriptonRadio.isSelected())
    	{
    		float discountRate;
    		if (currentCustomer.getPayMethod() == Customer.PayType.MONTHLY_SUBSCRIPTION)
    			discountRate = 0.1f;
    		else
    			discountRate = 0.25f;
    		
    		float priceWithoutDiscount = this.subsAmount / discountRate;
    		float newPrice = priceWithoutDiscount + Order.deliveryCost;
    		this.subsAmount = newPrice * discountRate;
    		newPrice = newPrice - subsAmount;
    		this.setTotalPriceText(newPrice - this.orderTotalPrice);
    	}
    	else
    		this.setTotalPriceText(Order.deliveryCost);
    	
    	if (this.StoreAccountRadio.isSelected())
    	{
	    	if (orderTotalPrice < currentCustomer.getAccountBalance())
	    	{
	    		this.StoreAccountRadio.setDisable(false);
	    		creditCardRadio.setSelected(true);
	    	}
	    	else
	    		this.StoreAccountRadio.setDisable(true);
    	}
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
    	
    	if (this.subscriptonRadio.isSelected())
    	{
    		float discountRate;
    		if (currentCustomer.getPayMethod() == Customer.PayType.MONTHLY_SUBSCRIPTION)
    			discountRate = 0.1f;
    		else
    			discountRate = 0.25f;
    		
    		float priceWithoutDiscount = this.subsAmount / discountRate;
    		float newPrice = priceWithoutDiscount - Order.deliveryCost;
    		this.subsAmount = newPrice * discountRate;
    		newPrice = newPrice - this.subsAmount;
    		this.setTotalPriceText(newPrice - this.orderTotalPrice);
    	}
    	else
    		this.setTotalPriceText(-Order.deliveryCost);
    	
    	if (orderTotalPrice < currentCustomer.getAccountBalance())
    		this.StoreAccountRadio.setDisable(false);
    	else
    		this.StoreAccountRadio.setDisable(true);
    }
    

    //*************************************************************************************************
    /**
  	*  removes the discount received from subscription
  	*  @param e the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onCash(ActionEvent event) {
    	if (subsOrder)
    	{
    		subsOrder = false;
    		this.setTotalPriceText(subsAmount);
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  removes the discount received from subscription
  	*  @param e the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onCreditCard(ActionEvent event) {
    	if (subsOrder)
    	{
    		subsOrder = false;
    		this.setTotalPriceText(subsAmount);
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  adds the discount received from subscription
  	*  @param e the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void onSubscription(ActionEvent event) {
    	subsOrder = true;
    	if (currentCustomer.getPayMethod() == Customer.PayType.MONTHLY_SUBSCRIPTION)
    	{
    		subsAmount = (orderTotalPrice * 0.1f);
    		this.setTotalPriceText(-subsAmount);
    	}
    	else
    	{
    		subsAmount = (orderTotalPrice * 0.25f);
    		this.setTotalPriceText(-subsAmount);
    	}
    }
    
    //*************************************************************************************************
    /**
  	*  removes the discount received from subscription
  	*  @param e the event that triggered this function
  	*/
    //*************************************************************************************************
    @FXML
    void OnStoreAccount(ActionEvent event) {
    	if (subsOrder)
    	{
    		subsOrder = false;
    		setTotalPriceText(subsAmount);
    	}
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
    		if (item.getSalePrice() > 0)
    			orderTotalPrice += item.getSalePrice();
    		else
    			orderTotalPrice += item.getPrice();
    		OrderItemView itemView = new OrderItemView(item);
    		itemView.getRemoveBtn().setOnAction(orderItemRemoveAction);
    		orderItems.add(itemView);
    	}
    	
    	orderTable.setItems(orderItems);
    	if (orderTotalPrice < currentCustomer.getAccountBalance())
    		this.StoreAccountRadio.setDisable(false);
    	else
    		this.StoreAccountRadio.setDisable(true);
    	
    	totalPrice.setText(""+orderTotalPrice+" ILS");
    	customOrder = false;
    	
    	resetControls();
    	setCurrentTime();
    }

    //*************************************************************************************************
    /**
  	*  loads the given CustomItemView to orderTable
  	*  @param customItem the custom item to add the orderTable
  	*/
    //*************************************************************************************************
    public void loadCustomItemInOrder(CustomItemView customItem)
    {
    	orderTotalPrice = 0;
    	
    	ObservableList<OrderItemView> orderItems = FXCollections.observableArrayList();
    	orderItems.add(customItem);
    	
    	customItem.getRemoveBtn().setOnAction(orderItemRemoveAction);
    	
    	orderTable.setItems(orderItems);
    	orderTotalPrice = customItem.getPrice();
    	totalPrice.setText(""+customItem.getPrice()+" ILS");
    	customOrder = true;
    	resetControls();
    	setCurrentTime();
    }
    
    //*************************************************************************************************
    /**
  	*  Called from the client when the server sends a response
  	*  fills the TableView with the received products data
  	*  @param message The Server response , an ArrayList of products
  	*/
    //*************************************************************************************************
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

    //*************************************************************************************************
    /**
    *  Sets the current Customer that is viewing the catalog
  	*  @param currentCustomer the customer to be set
  	*/
    //*************************************************************************************************
	public void setCurrentCustomer(Customer currentCustomer) {
		Calendar currentTime = Calendar.getInstance();
		this.currentCustomer = currentCustomer;
		
//		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(currentTime.getTime()));
//		System.out.println(sdf.format(currentCustomer.getExpirationDate().getTime()));
//		System.out.println(currentCustomer.getAccountStatus());
		
		if ( (currentCustomer.getPayMethod() == Customer.PayType.MONTHLY_SUBSCRIPTION ||
				currentCustomer.getPayMethod() == Customer.PayType.YEARLY_SUBSCRIPTION) &&
				(currentTime.before(currentCustomer.getExpirationDate())))
		{
			//Subscription
			if (currentCustomer.getPayMethod() == Customer.PayType.MONTHLY_SUBSCRIPTION)
				subscriptonRadio.setText("Subscription(-10%)");
			else
				subscriptonRadio.setText("Subscription(-25%)");
			
			subscriptonRadio.setDisable(false);
		}
		else
		{
			subscriptonRadio.setDisable(true);
			subscriptonRadio.setText("Subscription");
		}
	}

    //*************************************************************************************************
    /**
    *  Sets the current store whose catalog is being viewed
  	*  @param currentStore the currentStore ID to be set
  	*/
    //*************************************************************************************************
	public void setCurrentStore(long currentStore) {
		this.currentStoreID = currentStore;
	}
	
    //*************************************************************************************************
    /**
    *  Sets the total price in the label
  	*  @param amount how much to add to orderTotalPrice
  	*/
    //*************************************************************************************************
	private void setTotalPriceText(float amount)
	{
		System.out.println("-------------------------------------");
		System.out.println(amount);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		orderTotalPrice += amount;
		this.totalPrice.setText(""+df.format(orderTotalPrice)+" ILS");
	}
	
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
}
