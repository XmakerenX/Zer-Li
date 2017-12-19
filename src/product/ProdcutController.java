package product;

import java.util.ArrayList;

import client.Client;

public class ProdcutController {

//*************************************************************************************************
	/**
	*  Sends a request to the DB for a list of the products
	*  @param client The client connection to use to send the message to the server 
	*/
//*************************************************************************************************
	public static void requestProducts(Client client)
	{
    	ArrayList<String> message = new ArrayList<String>();
    	
    	message.add("GET");
    	message.add("Product");
    	client.handleMessageFromClientUI(message);
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
		ArrayList<String> message = new ArrayList<String>();
		message.add("SET"); 						
		message.add("Product"); 					
		message.add(""+ProductID);					
		message.add(""+updatedProduct.getID());		
		message.add(updatedProduct.getName().trim());
		message.add(updatedProduct.getType().trim());
    	
		client.handleMessageFromClientUI(message);
	}
	
}
