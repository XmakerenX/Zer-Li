package customer;

import java.util.ArrayList;

import client.Client;
import customer.Customer.CustomerException;
import customer.Customer.PayType;
import serverAPI.AddRequest;
import serverAPI.GetRequestByKey;
import serverAPI.GetRequestWhere;
import serverAPI.UpdateRequest;

public class CustomerController {
	
	/**
	 * Creates new customer and adds to database
	 * @param personID - customer's person ID
	 * @param storeID - the store the customer belongs to 
	 * @param fullName - customer's first and last names
	 * @param phoneNumber - customer's active phone number
	 * @param payMethod - payment method
	 * @param accountBalance - customer's account balance in the store
	 * @param creditCardNumber - customer's credit card number
	 * @param client - currently running client
	 */
	public static void createNewCustomer(long personID, long storeID ,String fullName, String phoneNumber, PayType payMethod, float accountBalance,
									String creditCardNumber,boolean accountStatus, Client client)
	{
		
		Customer newCustomer;
		try {
			newCustomer = new Customer(personID, storeID,fullName, phoneNumber, payMethod, accountBalance, creditCardNumber, accountStatus);
			client.handleMessageFromClientUI(new AddRequest("Customers", newCustomer));
		} catch (CustomerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Applies changes in customer's info
	 * @param updatedCustomer - updated Customer entity
	 * @param formerPersonID - old person ID (in case it has been changed)
	 * @param client - currently running client
	 */
	public static void updateCustomerDetails(Customer updatedCustomer, String formerPersonID, Client client)
	{		
		client.handleMessageFromClientUI(new UpdateRequest("Customers", formerPersonID, updatedCustomer));
	}
	
	/**
	 * Gets customer from data base
	 * @param personID - customer's person ID (is the key)
	 * @param client - currently running client
	 */
	public static void getCustomer(String personID, String storeID,Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(personID);
		keys.add(storeID);
		client.handleMessageFromClientUI(new GetRequestByKey("Customers", keys));
	}
	//===========================================================================================================
	/**
	 * returns the customers who meet said condition
	 * @param column	the column we are checking
	 * @param condition	the condition we are looking for
	 * @param client	client to send the answer back to
	 */
	public static void getSertainCustomers(String column, String condition, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("Customers", column, condition));
	}

}
