package utils;

import Server.DBConnector;
import product.Product;
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
}
