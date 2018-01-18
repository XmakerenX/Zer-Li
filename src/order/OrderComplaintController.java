package order;

import java.time.LocalDate;

import client.Client;
import serverAPI.AddRequest;
import survey.CustomerSatisfactionSurveyResults;

/**
 * a class that holds the functionality related to order complaints
 * @author dk198
 *
 */
public class OrderComplaintController {

	public static void addNewComplaint(long customerID, String name, String phone, String complaint, LocalDate date, String time, 
			int storeID, float maxCompensationAmount)
	{		
		OrderComplaint newComplaint = new OrderComplaint(customerID, name, phone, complaint, date, time, storeID, maxCompensationAmount);
		Client.client.handleMessageFromClientUI(new AddRequest("orderComplaint", newComplaint));
	}
	//===============================================================================================================
}
