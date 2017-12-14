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
	
	public static void updateProduct(int ProductID, Product updatedProduct, Client client)
	{
		ArrayList<String> message = new ArrayList<String>();
		message.add("SET"); 				// 0
		message.add("Product"); 			// 1
		message.add(updatedProduct.getID().trim());		// 2
		message.add(updatedProduct.getID().trim());		// 3
		message.add(updatedProduct.getName().trim());	// 4
		message.add(updatedProduct.getType().trim());	// 5
    	
		client.handleMessageFromClientUI(message);
	}
	
}
