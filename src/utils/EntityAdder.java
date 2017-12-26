package utils;

import Server.DBConnector;
import product.Product;
import user.User;

public class EntityAdder {
	public static void addEntity(String table, Object entity, DBConnector db)
	{
		switch (table)
		{
		case "Product":
		{
			addProdcut((Product)entity, db);
		}break;
		
		case "User":
		{
			setUser((User)entity, db);
		}break;
		
		}
	}
	
	private static void addProdcut(Product product, DBConnector db)
	{
		  String productID = ""+product.getID();
		  String productName = product.getName();
		  String productType = product.getType();
		  db.insertData("Prodcut", productID + "," + productName + "," + productType);
	}
	
	private static void setUser(User user, DBConnector db)
	{		  
		String userName = user.getUserName();
		String userPassword = user.getUserPassword();
		String userPermission = ""+user.getUserPermission();
		String personID = ""+user.getPersonID();
		String userStatus = ""+user.getUserStatus();
		String userUnsuccessfulTries = ""+user.getUnsuccessfulTries();

		db.insertData("User", userName + "," + userPassword + "," + userPermission + "," + personID + "," + userStatus + "," + userUnsuccessfulTries);
	}
}
