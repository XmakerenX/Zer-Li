package survey;

import client.Client;
import serverAPI.AddRequest;

public class CustomerSatisfactionSurveyResultsController 
{
	
	
	//===============================================================================================================
	/**
	 * adds a new result to the data base
	 * @param surveyName
	 * @param client
	 */
	public static void addResults(String surveyName, int[] answers, Client client)
	{		
		CustomerSatisfactionSurveyResults newSurveyResults = new CustomerSatisfactionSurveyResults(surveyName, answers);
		client.handleMessageFromClientUI(new AddRequest("CustomerSatisfactionSurveyResults", newSurveyResults));
	}
	//===============================================================================================================
}
