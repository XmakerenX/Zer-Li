package survey;

import client.Client;
import serverAPI.AddRequest;
import serverAPI.GetRequestWhere;

public class CustomerSatisfactionSurveyResultsController 
{
	
	
	//===============================================================================================================
	/**
	 * adds a new result to the data base
	 * @param surveyName
	 * @param client
	 */
	public static void addResults(String surveyName, int[] answers, int storeID, Client client)
	{		
		CustomerSatisfactionSurveyResults newSurveyResults = new CustomerSatisfactionSurveyResults(surveyName, answers, storeID);
		client.handleMessageFromClientUI(new AddRequest("CustomerSatisfactionSurveyResults", newSurveyResults));
	}
	//===============================================================================================================
	/**
	 * get results of a specific survey
	 * @param surveyName name of the survey we want the results of
	 * @param client
	 */
	public static void getResultsOfSurvey(String surveyName, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestWhere("customersatisfactionsurveyresults", "surveyName", surveyName));
	}
	//===============================================================================================================
}
