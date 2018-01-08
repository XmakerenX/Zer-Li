package customer;

import client.Client;
import customer.Customer.PayType;
import serverAPI.AddRequest;
import serverAPI.UpdateRequest;
import user.User;
import user.User.Permissions;
import user.User.UserException;

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
									String creditCardNumber, Client client)
	{
		
		Customer newCustomer = new Customer(personID, fullName, phoneNumber, payMethod, accountBalance, creditCardNumber);
		client.handleMessageFromClientUI(new AddRequest("Customer", newCustomer));
			
	}
	
	public static void updateCustomerDetails(Customer updatedCustomer, String formerPersonID, Client client)
	{		
		client.handleMessageFromClientUI(new UpdateRequest("Customer", formerPersonID, updatedCustomer));
	}

}
