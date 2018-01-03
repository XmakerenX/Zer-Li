package utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import Server.DBConnector;
import product.Product;
import survey.CustomerSatisfactionSurvey;
import survey.CustomerSatisfactionSurveyResults;
import user.User;

public class EntityAdder {
	public static Boolean addEntity(String table, Object entity, DBConnector db)
	{
		switch (table)
		{
		case "Product":
					return addProdcut((Product)entity, db);
		
		case "User":
			        return addUser((User)entity, db);  
			        
		case "surveys":
					return addSurvey((CustomerSatisfactionSurvey)entity, db);
					
		case "CustomerSatisfactionSurveyResults":
					return addSurveyResults((CustomerSatisfactionSurveyResults)entity, db);
		
		default:return false;
		
		}
	}
	
	private static Boolean addProdcut(Product product, DBConnector db)
	{
		  String productID = Long.toString(product.getID());
		  String productName = "'"+product.getName()+"'";
		  String productType = "'"+product.getType()+"'";
		  String productPrice = Float.toString(product.getPrice());
		  String productAmount= Integer.toString(product.getAmount());
		  String productColor = "'"+product.getColor()+"'";
		  
		  try
		  {
			  db.insertData("Product", productID + "," + productName + "," + productType+ ","+productPrice+ "," +
		  			productAmount + "," +productColor);
			  return true;
		  }
		  catch(Exception e)
		  {
			  return false;
		  }
	}
	
	private static Boolean addUser(User user, DBConnector db)
	{		  
		String userName = "'"+user.getUserName()+"'";
		String userPassword = "'"+user.getUserPassword()+"'";
		String userPermission = "'"+user.getUserPermission()+"'";
		String personID = ""+user.getPersonID();
		String userStatus = "'"+user.getUserStatus()+"'";
		String userUnsuccessfulTries = ""+user.getUnsuccessfulTries();
		try
		{
		db.insertData("User", userName + "," + userPassword + "," + userPermission + "," + personID + "," + userStatus + "," + userUnsuccessfulTries);
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
		
		private static Boolean addSurvey(CustomerSatisfactionSurvey survey, DBConnector db)
		{		  
			String surveyName = "'"+survey.getSurveyName()+"'";
			String question1 = "'"+survey.getSurveyQuestions()[0]+"'";
			String question2 = "'"+survey.getSurveyQuestions()[1]+"'";
			String question3 = "'"+survey.getSurveyQuestions()[2]+"'";
			String question4 = "'"+survey.getSurveyQuestions()[3]+"'";
			String question5 = "'"+survey.getSurveyQuestions()[4]+"'";
			String question6 = "'"+survey.getSurveyQuestions()[5]+"'";
			try
			{
			db.insertData("surveys", surveyName + "," + question1 + "," + question2 + "," + question3 + "," + question4 + "," + question5 + 
					"," + question6 + "," + "Analysis");
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
	}
		
		
		private static Boolean addSurveyResults(CustomerSatisfactionSurveyResults surveyResults, DBConnector db)
		{		  
			String surveyName = "'"+surveyResults.getOfSurvey()+"'";
			String answer1 = "'"+surveyResults.getAnswers()[0]+"'";
			String answer2 = "'"+surveyResults.getAnswers()[1]+"'";
			String answer3 = "'"+surveyResults.getAnswers()[2]+"'";
			String answer4 = "'"+surveyResults.getAnswers()[3]+"'";
			String answer5 = "'"+surveyResults.getAnswers()[4]+"'";
			String answer6 = "'"+surveyResults.getAnswers()[5]+"'";
			java.sql.Date sqlDate = java.sql.Date.valueOf( surveyResults.getDate() );

			try
			{
			db.insertData("customersatisfactionsurveyresults", null + "," + surveyName + "," + "'" + sqlDate + "'" + "," + answer1 + "," + answer2 + "," + answer3 + "," + answer4 + "," + answer5 + 
					"," + answer6);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
	}
}
