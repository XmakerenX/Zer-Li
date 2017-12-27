package product;

import java.util.ArrayList;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.GetRequest;
import serverAPI.RemoveRequest;
import serverAPI.UpdateRequest;

public class ProdcutController 
{

//*************************************************************************************************
	/**
	*  Sends a request to the DB for a list of the products
	*  @param client The client connection to use to send the message to the server 
	*/
//*************************************************************************************************
	public static void requestProducts(Client client)
	{
    	client.handleMessageFromClientUI(new GetRequest("Product"));
	}
//*************************************************************************************************
	/**
	*  Updates the given product in the DB
	*  @param ProductID The ID of the product to update
	*  @param updatedProduct The product data
	*  @param client The client connection to use to send the message to the server 
	*/
//*************************************************************************************************
	public static void updateProduct(int ProductID, Product updatedProduct, Client client)
	{	
		client.handleMessageFromClientUI(new UpdateRequest("Product", ""+ProductID, updatedProduct));
	}
//*************************************************************************************************
	/**
	 * 
	 * Request server to add a new Product to the Product table in the database
	 * @param newProd
	 * @param client
	 */
	public static void addProductToDataBase(Product newProd,Client client)
	{
		
		client.handleMessageFromClientUI(new AddRequest("Product",newProd));
	}
	
	/**
	 * Request server to add a Product whose key is prodKey
	 * @param prodKey
	 * @param client
	 */
	public static void removeProductFromDataBase(String prodKey,Client client)
	{
		client.handleMessageFromClientUI(new RemoveRequest("Product",prodKey));
	}
}
