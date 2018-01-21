package order;

import client.Client;
import javafx.collections.ObservableList;
import serverAPI.AddRequest;
import serverAPI.GetCustomItemRequest;
import serverAPI.GetJoinedTablesWhereRequest;
import serverAPI.GetRequest;
import serverAPI.GetRequestWhere;
import serverAPI.RemoveOrderRequest;

//*************************************************************************************************
	/**
	*  Provides an API to to the order table in the database
	*/
//*************************************************************************************************
public class OrderController
{
    /*
     * A function to add new order entry in the database
     */
	public static void CreateNewOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addItemToOrder(item.getID(), item.getGreetingCard().getText());
    		}

    		Client.client.handleMessageFromClientUI(new AddRequest("Order", order));
	}
	//==============================================================================================================
	
	/*
     * A function to add new custom order entry in the database
     */
	public static void CreateNewCustomOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addCustomItemToOrder((CustomItemView)item, item.getGreetingCard().getText());
    			//order.addItemToOrder(item.getID(), item.getGreetingCard().getText());
    		}

    		Client.client.handleMessageFromClientUI(new AddRequest("Order", order));
	}
	//==============================================================================================================
	/* This function gets all orders of a given customer
     * input: String customerID
     * output: server sends all of the orders that belong to this user
     */
	public static void getOrdersOfaUser(String customerID)
	{
		Client.client.handleMessageFromClientUI((new GetRequestWhere("Order", "OrderCustomerID", customerID)));
	}
	//==============================================================================================================
	/*
	 * This function removes given order entry from database
	 *  input: String orderID
     *   output: server responses with either success or error as for if he succeed to remove the entry
	 */
	public static void cancelOrder(long orderID)
	{
		Client.client.handleMessageFromClientUI(new RemoveOrderRequest(orderID));
	}
	//==============================================================================================================
	/* This function gets all orders of a given customer
     * input: long customerID
     * output: server sends all of the orders that belong to this user
     */
	public static void requestCustomerOrders(long customerID)
	{
		Client.client.handleMessageFromClientUI(new GetRequestWhere("Order", "OrderCustomerID", ""+customerID) );
	}
	//==============================================================================================================
	/*This functions return all the products that were orderd in the order
	 * input: long orderID
     * output: server sends all of the products that exists in the given order 
	 */
	public static void getOrderProducts(long orderID)
	{
		Client.client.handleMessageFromClientUI(new GetJoinedTablesWhereRequest("Product", "ProductInOrder", 0,"OrderID", ""+orderID));
	}
	//==============================================================================================================
	/*This functions return all the custom products that were orderd in the order
	 * input: long orderID
     * output: server sends all of the custom products that exists in the given order 
	 */
	public static void getOrderCustomProducts(long orderID)
	{
		Client.client.handleMessageFromClientUI(new GetCustomItemRequest(orderID));
	}
}
