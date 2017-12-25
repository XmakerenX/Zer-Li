package product;

import java.util.ArrayList;

import client.Client;
import serverAPI.GetRequest;
import serverAPI.UpdateRequest;

public class ProdcutController {

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
	public static void updateProduct(long ProductID, Product updatedProduct, Client client)
	{	
		client.handleMessageFromClientUI(new UpdateRequest("Product", ""+ProductID, updatedProduct));
	}
	
}
