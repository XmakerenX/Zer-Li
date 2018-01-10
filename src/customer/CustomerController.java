package customer;

import client.Client;
import customer.Customer.CustomerException;
import customer.Customer.PayType;
import serverAPI.AddRequest;
import serverAPI.GetRequestByKey;
import serverAPI.UpdateRequest;

public class CustomerController {
	
	/**
	 * Creates new customer and adds to database
	 * @param personID - customer's person ID
	 * @param fullName - customer's first and last names
	 * @param phoneNumber - customer's active phone number
	 * @param payMethod - payment method
	 * @param accountBalance - customer's account balance in the store
	 * @param creditCardNumber - customer's credit card number
	 * @param client - currently running client
	 */
	public static void createNewCustomer(long personID, String fullName, String phoneNumber, PayType payMethod, float accountBalance,
									String creditCardNumber,boolean accountStatus, Client client)
	{
		
		Customer newCustomer;
		try {
			newCustomer = new Customer(personID, fullName, phoneNumber, payMethod, accountBalance, creditCardNumber, accountStatus);
			client.handleMessageFromClientUI(new AddRequest("Customers", newCustomer));
		} catch (CustomerException e) {
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
	public static void getCustomer(String personID, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestByKey("Customers", personID));
	}

}
