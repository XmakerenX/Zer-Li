package order;

import client.Client;
import javafx.collections.ObservableList;
import serverAPI.AddRequest;
import serverAPI.GetCustomItemRequest;
import serverAPI.GetJoinedTablesWhereRequest;
import serverAPI.GetRequestWhere;
import serverAPI.RemoveOrderRequest;
import serverAPI.UpdateRequest;

//*************************************************************************************************
	/**
	*  Provides an API to to the order table in the database
	*/
//*************************************************************************************************
public class OrderController
{
	//*************************************************************************************************
	/**
	*  Adds the orderItems to the order and requests to add a new order entry in the database
	*  @param order the order to add to the database
	*  @param orderItems the Items to add to the order
	*/
	//*************************************************************************************************
	public static void CreateNewOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addItemToOrder(item.getID(), item.getGreetingCard().getText());
    		}

    		Client.getInstance().handleMessageFromClientUI(new AddRequest("Order", order));
	}

	//*************************************************************************************************
	/**
	 *  send an Update request to the server to update an order
	 *  @param orderID The ID of the order to update 
	 *  @param order The updated order
	 */
	//*************************************************************************************************
	public static void updateOrder(int orderID, Order order)
	{
		String key = Integer.toString(orderID);
		Client.getInstance().handleMessageFromClientUI(new UpdateRequest("Order", key, order));
	}
	
	//*************************************************************************************************
	/**
	*  Adds custom orderItems to the order and requests to add a new order entry in the database
	*  @param order the order to add to the database
	*  @param orderItems the custom Items to add to the order
	*/
	//*************************************************************************************************
	public static void CreateNewCustomOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addCustomItemToOrder((CustomItemView)item, item.getGreetingCard().getText());
    		}

    		Client.getInstance().handleMessageFromClientUI(new AddRequest("Order", order));
	}
	
	//*************************************************************************************************
	/**
	*  This function requests from the server to get all the orders of a given customer
	*  @param customerID  The customer ID to return his orders
	*/
	//*************************************************************************************************
	public static void getOrdersOfaUser(String customerID)
	{
		Client.getInstance().handleMessageFromClientUI((new GetRequestWhere("Order", "OrderCustomerID", customerID)));
	}
	
	//*************************************************************************************************
	/**
	*  This function requests from the server to cancel a Order with a given orderID 
	*  @param order the order to cancel
	*/
	//*************************************************************************************************
	public static void cancelOrder(Order order)
	{	
		Client.getInstance().handleMessageFromClientUI(new RemoveOrderRequest(order.getID()));
	}
	
	//*************************************************************************************************
	/**
	*  This function requests from the server to get all the orders of a given customer
	*  @param customerID  The customer ID to return his orders
	*/
	//*************************************************************************************************
	public static void requestCustomerOrders(long customerID)
	{
		Client.getInstance().handleMessageFromClientUI(new GetRequestWhere("Order", "OrderCustomerID", ""+customerID) );
	}
	
	//*************************************************************************************************
	/**
	*  This function requests from the server to get all the products that were ordered in an order
	*  @param orderID  The order ID to the order required order
	*/
	//*************************************************************************************************
	public static void getOrderProducts(long orderID)
	{
		Client.getInstance().handleMessageFromClientUI(new GetJoinedTablesWhereRequest("Product", "ProductInOrder", 0,"OrderID", ""+orderID));
	}
	
	//*************************************************************************************************
	/**
	*  This function requests from the server to get all the custom products that were ordered in an order
	*  @param orderID  The order ID to the order required order
	*/
	//*************************************************************************************************
	public static void getOrderCustomProducts(long orderID)
	{
		Client.getInstance().handleMessageFromClientUI(new GetCustomItemRequest(orderID));
	}
}
