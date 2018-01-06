package utils;

import Server.DBConnector;
import product.Product;
import survey.CustomerSatisfactionSurvey;
import user.User;

public class EntityUpdater {

	public static void setEntity(String table, String oldKey, Object entity, DBConnector db)
	{
		switch (table)
		{
		case "Product":
			setProdcut(oldKey, (Product)entity, db);
			break;
			
		case "User":
			setUser(oldKey, (User)entity, db);
			break;
			
		case "surveys":
			setSurvey(oldKey, (CustomerSatisfactionSurvey)entity, db);
			break;
		}
	}
	
	private static void setProdcut(String oldKey, Product product, DBConnector db)
	{
		  String productID = "ProductID="+product.getID();
		  String productName = "ProductName=\""+product.getName()+"\"";
		  String productType = "ProductType=\""+product.getType()+"\"";
		  String productPrice = "ProductType=\""+product.getPrice()+"\"";
		  String productAmount= "ProductType=\""+product.getAmount()+"\"";
		  String productColor = "ProductType=\""+product.getColor()+"\"";

		  String condition = "ProductID="+oldKey; 
		  db.executeUpdate("Product", productID + "," + productName + "," + productType+ ","+productPrice+ "," +
				  			productAmount + "," +productColor, condition);
	}
	
	private static void setUser(String oldKey, User user, DBConnector db)
	{		  
		  String userName = "userName=\""+user.getUserName()+"\"";
		  String userPassword = "userPassword=\""+user.getUserPassword()+"\"";
		  String userPermission = "userPermission=\""+user.getUserPermission()+"\"";
		  String personID = "personID="+user.getPersonID();
		  String userStatus = "userStatus=\""+user.getUserStatus()+"\"";
		  String unsuccessfulTries = "unsuccessfulTries="+user.getUnsuccessfulTries();
		  String condition = "userName=\""+oldKey+"\""; 
		  
		  db.executeUpdate("User", userName + "," + userPassword + "," + userPermission + "," 
				  +personID+", " + userStatus + ", " + unsuccessfulTries , condition);
	}
	
	private static void setSurvey(String oldKey, CustomerSatisfactionSurvey survey, DBConnector db)
	{		  
		  String surveyName = "surveyName=\""+survey.getSurveyName()+"\"";
		  String question1 = "question1=\""+survey.getSurveyQuestions()[0]+"\"";
		  String question2 = "question2=\""+survey.getSurveyQuestions()[1]+"\"";
		  String question3 = "question3=\""+survey.getSurveyQuestions()[2]+"\"";
		  String question4 = "question4=\""+survey.getSurveyQuestions()[3]+"\"";
		  String question5 = "question5=\""+survey.getSurveyQuestions()[4]+"\"";
		  String question6 = "question6=\""+survey.getSurveyQuestions()[5]+"\"";
		  String analysis = "analysis=\""+survey.getSurveyAnalysis() + "\"";
		  String condition = "surveyName=\""+oldKey+"\""; 
		  
		  db.executeUpdate("surveys", surveyName + "," + question1 + "," + question2 + "," 
				  + question3 + "," + question4 + "," + question5 + "," + question6 + "," + analysis , condition);
	}
}
