package survey;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.GetRequestWhere;
import serverAPI.UpdateRequest;
/**
 * A class that holds the functionality that's related to customer satisfaction survey results
 * @author dk198
 *
 */
public class CustomerSatisfactionSurveyResultsController 
{
	//===============================================================================================================
	/**
	 * adds a new result to the data base
	 * @param answers the answers to add
	 * @param storeID the store id to add the answers to
	 * @param client the client to use to send the request
	 */
	public static void addResults(int[] answers, int storeID, Client client)
	{		
		CustomerSatisfactionSurveyResults newSurveyResults = new CustomerSatisfactionSurveyResults(answers, storeID);
		client.handleMessageFromClientUI(new AddRequest("CustomerSatisfactionSurveyResults", newSurveyResults));
	}
	//===============================================================================================================
	/**
	 * lets us receive the results which haven't been analyzed yet
	 * @param client the client to use to send the request
	 * @param client the client to use to send the request
	 */
	public static void getResultsWithoutAnalysis(Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("customersatisfactionsurveyresults", "analysis", ""));
	}
	//===============================================================================================================
	/**
	 * lets us receive a specific customer satisfation survey result
	 * @param id		id of the survey result we are looking for
	 * @param client the client to use to send the request
	 */
	public static void getSpecificResults(String id, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("customersatisfactionsurveyresults", "id", id));
	}
	//===============================================================================================================
	/**
	 * a method that lets us add a new result analysis to the data base
	 * @param resultID 		id of the survey result we want to add an analysis to
	 * @param survey		an instance of survey, making the updating of the entry easier
	 * @param client		the current client
	 */
	public static void addResultAnalysis(String resultID, CustomerSatisfactionSurveyResults survey, Client client)
	{
		System.out.println(survey);
		Client.getInstance().handleMessageFromClientUI(
				new UpdateRequest("CustomerSatisfactionSurveyResults", resultID, survey));
	}
}
