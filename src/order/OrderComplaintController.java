package order;

import java.time.LocalDate;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.GetRequestWhere;
import serverAPI.UpdateRequest;
import survey.CustomerSatisfactionSurveyResults;

/**
 * a class that holds the functionality related to order complaints
 * @author dk198
 *
 */
public class OrderComplaintController {
/**
 * creating a new complaint based on the inputs below
 * @param customerID
 * @param name
 * @param phone
 * @param complaint
 * @param date
 * @param time
 * @param storeID
 * @param maxCompensationAmount
 */
	public static void addNewComplaint(long customerID, String name, String phone, String complaint, LocalDate date, String time, 
			int storeID, float maxCompensationAmount,int orderID)
	{		
		OrderComplaint newComplaint = new OrderComplaint(customerID, name, phone, complaint, date, time, storeID, maxCompensationAmount,orderID);
		Client.client.handleMessageFromClientUI(new AddRequest("ordercomplaint", newComplaint));
	}
	//===============================================================================================================
	/**
	 * gives us all of the active(not handled) complaints
	 */
	public static void getActiveComplaints()
	{
		Client.client.handleMessageFromClientUI(new GetRequestWhere("ordercomplaint", "status", "NEW"));
	}
	//===============================================================================================================
	/**
	 * updates the compensation value(if compensatoin was issued) and the status of a complaint(closing it)
	 * @param key		complaint id
	 * @param orderComplaint	holds the info to update
	 */
	public static void handleOrderComplaint(String key, OrderComplaint orderComplaint)
	{
		Client.client.handleMessageFromClientUI(new UpdateRequest("ordercomplaint", key, orderComplaint));
	}
}
