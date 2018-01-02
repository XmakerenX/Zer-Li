package utils;

import Server.DBConnector;
import product.Product;
import survey.CustomerSatisfactionSurvey;
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
			        
		case"surveys":
					return addSurvey((CustomerSatisfactionSurvey)entity, db);
		
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
}
