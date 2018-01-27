package order;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import product.Product;


//*************************************************************************************************
	/**
	*  The Order Entity
	*  holds all the information for the order table
	*/
//*************************************************************************************************
public class Order implements Serializable 
{		
	//*********************************************************************************************
	// Order nested classes
	//*********************************************************************************************
	
	//*********************************************************************************************
		/**
		* Holds the order DeliveryInfo
		*/
	//*********************************************************************************************
	public class DeliveryInfo implements Serializable 
	{
		private static final long serialVersionUID = -5049139891642016909L;
		String deliveryAddress;
		String receiverName;
		String receiverPhoneNumber;
		
		//*****************************************************************************************
		/**
		* Creates a new DeliveryInfo with the following parameters
		* @param deliveryAddress The delivery Address
		* @param receiverName The delivery receiver name
		* @param receiverPhoneNumber the delivery receiver phone number
		*/
		//*****************************************************************************************
		public DeliveryInfo(String deliveryAddress, String receiverName, String receiverPhoneNumber)
		{
			this.deliveryAddress = deliveryAddress;
			this.receiverName = receiverName;
			this.receiverPhoneNumber = receiverPhoneNumber;
		}

		public String getDeliveryAddress() {
			return deliveryAddress;
		}

		public String getReceiverName() {
			return receiverName;
		}

		public String getReceiverPhoneNumber() {
			return receiverPhoneNumber;
		}
	}

	//*********************************************************************************************
	/**
	* Holds the Item data that is in the current Order
	*/
	//*********************************************************************************************
	public class ItemInOrder implements Serializable
	{
		private static final long serialVersionUID = -6350787296742606198L;
		private long productID;
		private String greetingCard;

		//*****************************************************************************************
		/**
		* Creates a new ItemInOrder with the following parameters
		* @param productID The item productID
		* @param greetingCard The item greetingCard
		*/
		//*****************************************************************************************
		public ItemInOrder(long productID, String greetingCard)
		{
			this.productID = productID;
			this.greetingCard = greetingCard;
		}

		public long getProductID() {
			return productID;
		}

		public String getGreetingCard() {
			return greetingCard;
		}
		
	}
	
	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private static final long serialVersionUID = -4572335109967371787L;
	public final static float deliveryCost = 10;
	public enum Status{NEW, READLY, DELIVERED,CANCELED}
	public enum PayMethod {CASH, CREDITCARD, SUBSCRIPTION, SUBSCRIPTION_PAID, STORE_ACCOUNT}

	int orderID;
	Status orderStatus;
	float  orderPrice;
	float  refund;

	Calendar orderCreationDateTime;
	Calendar orderRequiredDateTime;
	DeliveryInfo deliveryInfo = null;
	PayMethod orderPaymentMethod;
	long orderOriginStore;
	long customerID;
	// 
	ArrayList<ItemInOrder> itemsInOrder;
	ArrayList<CustomItemInOrder> customItemInOrder;

	//*****************************************************************************************
	/**
	* Creates a new Order with the following parameters
	* @param id the order ID
	* @param status the Order status
	* @param price  the order total price
	* @param orderCreationDateTime  the Order creation date and time
	* @param date the order required date 
	* @param time the order required time
	* @param deliveryAddress the order delivery address 
	* @param receiverName The order receiver Name
	* @param receiverPhoneNumber The order receiver phone number
	* @param payMethod the way the order was paid for {CASH, CREDITCARD, SUBSCRIPTION}
	* @param originShop the order origin shop ID
	* @param customerID the customer ID who created the order
	* @throws OrderException thrown if invalid parameters were given
	*/
	//*****************************************************************************************
	public Order(int id,Status status,float price,Calendar orderCreationDateTime,LocalDate date,String time,
			String deliveryAddress, String receiverName, String receiverPhoneNumber 
			,PayMethod payMethod,long originShop, long customerID) throws OrderException
	{
		itemsInOrder = new ArrayList<ItemInOrder>();
		customItemInOrder = new ArrayList<CustomItemInOrder>();
		refund = 0;
		setID(id);
		setStatus(status);
		setPrice(price);
		this.orderCreationDateTime = orderCreationDateTime;
		setOrderRequiredDateTime(date, time);
		if (deliveryAddress != null)
			deliveryInfo = new DeliveryInfo(deliveryAddress, receiverName, receiverPhoneNumber);
		setOrderPaymentMethod(payMethod);
		setOrderOriginStore(originShop);
		setCustomerID(customerID);
	}

	//*****************************************************************************************
	/**
	* Creates a new Order with the following parameters
	* @param id the order ID
	* @param status the Order status
	* @param price  the order total price
	* @param orderCreationDateTime  the Order creation date and time
	* @param orderRequiredDateTime the order required date and time
	* @param deliveryAddress the order delivery address 
	* @param receiverName The order receiver Name
	* @param receiverPhoneNumber The order receiver phone number
	* @param payMethod the way the order was paid for {CASH, CREDITCARD, SUBSCRIPTION}
	* @param originShop the order origin shop ID
	* @param customerID the customer ID who created the order
	* @throws OrderException thrown if invalid parameters were given
	*/
	//*****************************************************************************************
	public Order(int id,Status status,float price,Calendar orderCreationDateTime, Calendar orderRequiredDateTime,
			String deliveryAddress, String receiverName, String receiverPhoneNumber 
			,PayMethod payMethod,long originShop, long customerID) throws OrderException
	{
		itemsInOrder = new ArrayList<ItemInOrder>();
		customItemInOrder = new ArrayList<CustomItemInOrder>();
		refund = 0;
		
		setID(id);
		setStatus(status);
		setPrice(price);
		this.orderCreationDateTime = orderCreationDateTime;
		setOrderRequiredDateTime(orderRequiredDateTime);
		if (deliveryAddress != null)
			deliveryInfo = new DeliveryInfo(deliveryAddress, receiverName, receiverPhoneNumber);
		setOrderPaymentMethod(payMethod);
		setOrderOriginStore(originShop);
		setCustomerID(customerID);
	}
	
	//*****************************************************************************************
	/**
	* Creates a new Order with the following parameters
	* @param id the order ID
	* @param status the Order status
	* @param price  the order total price
	* @param orderCreationDateTime  the Order creation date and time
	* @param orderRequiredDateTime the order required date and time
	* @param info the order delivery information
	* @param payMethod the way the order was paid for {CASH, CREDITCARD, SUBSCRIPTION}
	* @param originShop the order origin shop ID
	* @param customerID the customer ID who created the order
	* @throws OrderException thrown if invalid parameters were given
	*/
	//*****************************************************************************************
	public Order(int id, Status status, float price, Calendar orderCreationDateTime,Calendar orderRequiredDateTime, DeliveryInfo info,
			PayMethod payMethod, long originShop, long customerID) throws OrderException
	{
		this(id, status, price, orderCreationDateTime, orderRequiredDateTime , null, null, null ,
				payMethod, originShop, customerID);
		
		this.setDeliveryInfo(info);
	}

    //*************************************************************************************************
    /**
     * Sets the OrderID
  	*  @param orderID the orderID to be set
  	*/
    //*************************************************************************************************
	public void setID(int orderID)
	{
		this.orderID = orderID;
	}

	//*************************************************************************************************
    /**
     * Returns the OrderID
  	*  @return the OrderID
  	*/
    //*************************************************************************************************
	public int getID()
	{
		return orderID;
	}

    //*************************************************************************************************
    /**
     * Sets the order Status
  	*  @param orderStatus the order Status to be set
  	*/
    //*************************************************************************************************
	public void setStatus(Status orderStatus)
	{
		this.orderStatus = orderStatus;
	}

	//*************************************************************************************************
    /**
     * Returns the order Status
  	*  @return the order Status
  	*/
    //*************************************************************************************************
	public Status getStatus()
	{
		return this.orderStatus;
	}

    //*************************************************************************************************
    /**
     * Sets the order price
  	*  @param price the order price to be set
  	*  @throws OrderException thrown if invliad price was given
  	*/
    //*************************************************************************************************
	public void setPrice(float price) throws OrderException
	{
		if(price >= 0)
			this.orderPrice = price;
		else
			throw new OrderException("price must be above or equal zero");
	}

	//*************************************************************************************************
    /**
     * Returns the order price
  	*  @return the order price
  	*/
    //*************************************************************************************************
	public float getPrice()
	{
		return this.orderPrice;
	}
	
    //*************************************************************************************************
    /**
     * Sets the order Delivery Info
  	*  @param deliveryInfo the order delivery Info to be set
  	*/
    //*************************************************************************************************
	public void setDeliveryInfo(DeliveryInfo deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	//*************************************************************************************************
    /**
     * Returns the delivery Info
  	*  @return the delivery Info
  	*/
    //*************************************************************************************************
	public DeliveryInfo getDeliveryInfo() {
		return deliveryInfo;
	}

    //*************************************************************************************************
    /**
     * Sets the order Payment Method
  	*  @param orderPaymentMethod the order Payment Method to be set
  	*/
    //*************************************************************************************************
	public void setOrderPaymentMethod(PayMethod orderPaymentMethod) {
		this.orderPaymentMethod = orderPaymentMethod;
	}
	
	//*************************************************************************************************
    /**
     * Returns the Order Payment Method
  	*  @return the Order Payment Method
  	*/
    //*************************************************************************************************
	public PayMethod getOrderPaymentMethod() {
		return orderPaymentMethod;
	}

    //*************************************************************************************************
    /**
     * Sets the order Origin Store
  	*  @param orderOriginStore the order Origin Store to be set
  	*/
    //*************************************************************************************************
	public void setOrderOriginStore(long orderOriginStore) {
		this.orderOriginStore = orderOriginStore;
	}
	
	//*************************************************************************************************
    /**
     * Returns the Order Origin Store
  	*  @return the Order Origin Store
  	*/
    //*************************************************************************************************
	public long getOrderOriginStore() {
		return orderOriginStore;
	}

	//*************************************************************************************************
    /**
     * Returns the items In Order
  	*  @return the items In Order
  	*/
    //*************************************************************************************************
	public ArrayList<ItemInOrder> getItemsInOrder()
	{
		return itemsInOrder;
	}
	
    //*************************************************************************************************
    /**
     * add a new ItemInOrder with the given parameters
  	*  @param productID the product ID
  	*  @param greetingCard The greeting card for that product
  	*/
    //*************************************************************************************************
	public void addItemToOrder(long productID, String greetingCard)
	{
		this.itemsInOrder.add(new ItemInOrder(productID, greetingCard));
	}
	
	//*************************************************************************************************
    /**
     * Returns the order Delivery Address
  	*  @return the order Delivery Address
  	*/
    //*************************************************************************************************
	public String getDeliveryAddress()
	{
		return this.getDeliveryInfo().getDeliveryAddress();
	}
	
	//*************************************************************************************************
    /**
     * Returns the custom Item In Order
  	*  @return the custom Item In Order
  	*/
    //*************************************************************************************************
	public ArrayList<CustomItemInOrder> getCustomItemInOrder() {
		return customItemInOrder;
	}

    //*************************************************************************************************
    /**
     * add a new CustomItemInOrder with the given parameters
  	*  @param customItem the custom item to be added
  	*  @param greetingCard The greeting card for that product
  	*/
    //*************************************************************************************************
	public void addCustomItemToOrder(CustomItemView customItem, String greetingCard)
	{
		ArrayList<Product> itemComp = new ArrayList<Product>(); 
		
		for (Product p : customItem.getCustomProducts())
			itemComp.add(p);
		
		this.customItemInOrder.add(new CustomItemInOrder(customItem.getType(), customItem.getPrice(), 
				customItem.getColor(), greetingCard, itemComp));
	}

    //*************************************************************************************************
    /**
     * Sets the order customer ID
  	*  @param customerID the order customer ID
  	*/
    //*************************************************************************************************
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	
	//*************************************************************************************************
    /**
     * Returns the customer ID
  	*  @return the customer ID
  	*/
    //*************************************************************************************************
	public long getCustomerID() {
		return customerID;
	}

	//*************************************************************************************************
    /**
     * Returns the order Creation Date Time
  	*  @return the order Creation Date Time
  	*/
    //*************************************************************************************************
	public Calendar getOrderCreationDateTime() {
		return orderCreationDateTime;
	}

	//*************************************************************************************************
    /**
     * Returns the order Creation Date Time as a string
  	*  @return the order Creation Date Time as a string
  	*/
    //*************************************************************************************************
	public String getCreationDateTime() {
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(orderCreationDateTime.getTime());
	}
	
    //*************************************************************************************************
    /**
     * Sets the order Creation Date Time
  	*  @param orderCreationDateTime the order Creation Date Time
  	*/
    //*************************************************************************************************
	public void setOrderCreationDateTime(Calendar orderCreationDateTime) {
		this.orderCreationDateTime = orderCreationDateTime;
	}

	//*************************************************************************************************
    /**
     * Returns the order Required Date Time
  	*  @return the order Required Date Time
  	*/
    //*************************************************************************************************
	public Calendar getOrderRequiredDateTime() {
		return orderRequiredDateTime;
	}

	//*************************************************************************************************
    /**
     * Returns the order Required Date Time as a string
  	*  @return the order Required Date Time as a string
  	*/
    //*************************************************************************************************
	public String getRequiredDateTime() {
		SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(orderRequiredDateTime.getTime());
	}
	
    //*************************************************************************************************
    /**
     * Sets the order Required Date Time
  	*  @param orderDate the order Required Date
  	*  @param orderTime the order Required time
  	*/
    //*************************************************************************************************
	public void setOrderRequiredDateTime(LocalDate orderDate, String orderTime) 
	{
		String[] time = orderTime.trim().split(":");
		orderRequiredDateTime = new GregorianCalendar(orderDate.getYear(), orderDate.getMonth().getValue() - 1, orderDate.getDayOfMonth(),
				Integer.parseInt(time[0]), Integer.parseInt(time[1]));
	}
	
    //*************************************************************************************************
    /**
     * Sets the order Required Date Time
  	*  @param orderRequiredDateTime the order Required Date and time
  	*/
    //*************************************************************************************************
	public void setOrderRequiredDateTime(Calendar orderRequiredDateTime) 
	{
		this.orderRequiredDateTime = orderRequiredDateTime;
	}
	
	//*************************************************************************************************
    /**
     * Returns the order Deliver Address
  	*  @return the order Deliver Address
  	*/
    //*************************************************************************************************
	public String getDeliverAddress()
	{
		return this.getDeliveryInfo().getDeliveryAddress();
	}
	
	//*************************************************************************************************
    /**
     * Returns the order Refund
  	*  @return the order Refund
  	*/
    //*************************************************************************************************
	public float getRefund() {
		return refund;
	}

    //*************************************************************************************************
    /**
     * Sets the order refund
  	*  @param refund the order refund value
  	*/
    //*************************************************************************************************
	public void setRefund(float refund) {
		this.refund = refund;
	}

}
