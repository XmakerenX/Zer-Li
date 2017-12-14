package prototype;

import java.util.ArrayList;

import client.Client;

public class ProdcutController {

	public static void requestProducts(Client client)
	{
    	ArrayList<String> message = new ArrayList<String>();
    	
    	message.add("GET");
    	message.add("Product");
    	client.handleMessageFromClientUI(message);
	}
	
	public static void updateProduct(long ProductID, Product updatedProduct, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		message.add("SET"); 						// 0
		message.add("Product"); 					// 1
		message.add(""+ProductID);					    // oldID
		message.add(""+updatedProduct.getID());		// newID
		message.add(updatedProduct.getName().trim());	// 4
		message.add(updatedProduct.getType().trim());	// 5
    	
		client.handleMessageFromClientUI(message);
	}
	
}
