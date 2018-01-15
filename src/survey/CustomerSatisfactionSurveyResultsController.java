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
	 * @param surveyName
	 * @param client
	 */
	public static void addResults(int[] answers, int storeID, Client client)
	{		
		CustomerSatisfactionSurveyResults newSurveyResults = new CustomerSatisfactionSurveyResults(answers, storeID);
		client.handleMessageFromClientUI(new AddRequest("CustomerSatisfactionSurveyResults", newSurveyResults));
	}
	//===============================================================================================================
	public static void getResultsWithoutAnalysis(Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("customersatisfactionsurveyresults", "analysis", ""));
	}
	//===============================================================================================================
	public static void getSpecificResults(String id, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("customersatisfactionsurveyresults", "id", id));
	}
	//===============================================================================================================
	public static void addResultAnalysis(String resultID, CustomerSatisfactionSurveyResults survey, Client client)
	{
		System.out.println(survey);
		client.handleMessageFromClientUI(new UpdateRequest("CustomerSatisfactionSurveyResults", resultID, survey));
	}
}
