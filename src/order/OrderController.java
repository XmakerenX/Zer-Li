package order;

import client.Client;
import javafx.collections.ObservableList;
import serverAPI.AddRequest;
import serverAPI.GetRequestWhere;
import serverAPI.RemoveOrderRequest;


public class OrderController
{
	public static void CreateNewOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addItemToOrder(item.getID(), item.getGreetingCard().getText());
    		}

    		Client.client.handleMessageFromClientUI(new AddRequest("Order", order));
	}
	
	public static void CreateNewCustomOrder(Order order, ObservableList<OrderItemView> orderItems)
	{
    		for (OrderItemView item : orderItems)
    		{
    			order.addCustomItemToOrder((CustomItemView)item, item.getGreetingCard().getText());
    			//order.addItemToOrder(item.getID(), item.getGreetingCard().getText());
    		}

    		Client.client.handleMessageFromClientUI(new AddRequest("Order", order));
	}
	
	public static void requestCustomerOrders(long customerID)
	{
		Client.client.handleMessageFromClientUI(new GetRequestWhere("Order", "OrderCustomerID", ""+customerID) );
	}
	
	public static void cancelOrder(long orderID)
	{
		Client.client.handleMessageFromClientUI(new RemoveOrderRequest(orderID));
	}

}
