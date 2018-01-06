package survey;

import client.Client;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import product.Product;
import serverAPI.AddRequest;
import serverAPI.CheckExistsRequest;
import serverAPI.GetRequest;
import serverAPI.GetRequestByKey;
import serverAPI.LoginRequest;
import serverAPI.Response;
import serverAPI.UpdateRequest;
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
		CustomerSatisfactionSurvey newSurvey = new CustomerSatisfactionSurvey(surveyName, questions);
		client.handleMessageFromClientUI(new AddRequest("surveys", newSurvey));
	}
	
	//===============================================================================================================
	public static void doesSurveyExist(String surveyName, Client client)
	{
		client.handleMessageFromClientUI(new CheckExistsRequest("surveys", surveyName));
	}
	//===============================================================================================================
	/**
	*  Sends a request to the DB for a list of the products
	*  @param client The client connection to use to send the message to the server 
	*/
	public static void requestSurveys(Client client)
	{
	    client.handleMessageFromClientUI(new GetRequest("surveys"));
	    	
	}
	//===============================================================================================================
	/**
	 * updates the survey in the data base
	 * @param surveyName the survey we are looking for(old key)
	 * @param survey the the data we need to update
	 * @param client
	 */
	public static void updateCustomerSatisfactionSurvey(String surveyName, CustomerSatisfactionSurvey survey, Client client)
	{	
		client.handleMessageFromClientUI(new UpdateRequest("surveys", ""+surveyName, survey));
	}
	//===============================================================================================================

}
