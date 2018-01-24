package customer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import client.Client;
import customer.Customer.CustomerException;
import customer.Customer.PayType;
import serverAPI.AddRequest;
import serverAPI.GetRequestByKey;
import serverAPI.GetRequestWhere;
import serverAPI.UpdateRequest;

//*************************************************************************************************
	/**
	* Provides functions that Manages the Customer Entity 
	*/
//*************************************************************************************************
public class CustomerController {
	
	//*************************************************************************************************
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
	//*************************************************************************************************
	public static void createNewCustomer(long personID, long storeID ,String fullName, String phoneNumber, PayType payMethod, float accountBalance,
									String creditCardNumber,boolean accountStatus, Calendar expirationDate, Client client)
	{
		Customer newCustomer;
		try {
			newCustomer = new Customer(personID, storeID, fullName, phoneNumber, payMethod, accountBalance,
					creditCardNumber, accountStatus, expirationDate);
			client.handleMessageFromClientUI(new AddRequest("Customers", newCustomer));
		} catch (CustomerException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//*************************************************************************************************
	/**
	 * Applies changes in customer's info
	 * @param updatedCustomer - updated Customer entity
	 * @param formerPersonID - old person ID (in case it has been changed)
	 * @param client - currently running client
	 */
	//*************************************************************************************************
	public static void updateCustomerDetails(Customer updatedCustomer, String formerPersonID, Client client)
	{		
		client.handleMessageFromClientUI(new UpdateRequest("Customers", formerPersonID, updatedCustomer));
	}
	
	//*************************************************************************************************
	/**
	 * Gets customer from data base
	 * @param personID - customer's person ID (is the key)
	 * @param client - currently running client
	 */
	//*************************************************************************************************
	public static void getCustomer(String personID, String storeID ,Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(personID);
		
		if(storeID != null)
			keys.add(storeID);
		
		client.handleMessageFromClientUI(new GetRequestByKey("Customers", keys));
	}
	
	//*************************************************************************************************
	/**
	 * Returns the customers who meet mentioned condition
	 * @param column	the column that will be checked
	 * @param condition	the condition that will be met
	 * @param client	client to send the answer back to
	 */
	//*************************************************************************************************
	public static void getCertainCustomers(String column, String condition, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("Customers", column, condition));
	}

	public static boolean isSubscriptonisValid(Customer Customer)
	{
		Calendar currentTime = Calendar.getInstance();
		return currentTime.before(Customer.getExpirationDate());
	}
	
	//*************************************************************************************************
	/**
	 * Calculates how much to refund the customer for his canceled order
	 * @param requiredDate	The order required Date
	 * @return how much of the original price to refund the customer (0.0f, 0.5f , 1.0f)
	 */
	//*************************************************************************************************
	public static float calcCustomerRefund(Calendar requiredDate)
	{
		  Calendar currentTime = Calendar.getInstance();
		  
		  float refundRate = 0.0f;
		  long requiredDateTimestamp = requiredDate.getTimeInMillis();
		  long currentTimestamp = currentTime.getTimeInMillis();
		  long timediff = requiredDateTimestamp - currentTimestamp;
		  if (timediff >= 10800000)
			  refundRate = 1.0f;
		  else
			  if (timediff < 10800000 && timediff > 3600000)
				  refundRate = 0.5f;
		  
		  return refundRate;
	}

}
