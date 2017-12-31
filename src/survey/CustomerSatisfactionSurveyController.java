package survey;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import serverAPI.AddRequest;
import serverAPI.CheckExistsRequest;
import serverAPI.GetRequestByKey;
import serverAPI.LoginRequest;
import serverAPI.Response;
import user.LoginException;
import user.User;
import user.User.UserException;

public class CustomerSatisfactionSurveyController {
	
	//===============================================================================================================
	public static void getSurvey(String surveyName, Client client)
	{
		client.handleMessageFromClientUI(new GetRequestByKey("surveys", surveyName));
		
	}
	//===============================================================================================================
	/**
	 * checks whether the survey with such a name exists in the data base
	 * @param surveyName
	 * @param client
	 */
	public static void surveyCreation(String surveyName, String[] questions, Client client)
	{		
		try {
			CustomerSatisfactionSurvey newSurvey = new CustomerSatisfactionSurvey(surveyName, questions);
			client.handleMessageFromClientUI(new AddRequest("surveys", newSurvey));
		} catch (SurveyException e) {
			// TODO deal with error
			// shouldn't get here!
			e.printStackTrace();
		}	
	}
	//===============================================================================================================
//	public static void verifySurveyCreation (String surveyName, String[] questions) throws SurveyException
//	{
//		//checking if all the fields are alright and throwing exceptions if not so.
//	}
	
	//===============================================================================================================
	public static void doesSurveyExist(String surveyName, Client client)
	{
		client.handleMessageFromClientUI(new CheckExistsRequest("Surveys", surveyName));
	}


}
