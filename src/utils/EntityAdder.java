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
					addProdcut((Product)entity, db);
					break;
		
		case "User":
			        addUser((User)entity, db);  
			        break;
		
		default:break;
		
		}
	}
	
	private static void addProdcut(Product product, DBConnector db)
	{
		  String productID = "ProductID="+product.getID();
		  String productName = "ProductName=\""+product.getName()+"\"";
		  String productType = "ProductType=\""+product.getType()+"\"";
		  String productPrice = "ProductType=\""+product.getPrice()+"\"";
		  String productAmount= "ProductType=\""+product.getAmount()+"\"";
		  String productColor = "ProductType=\""+product.getColor()+"\"";
		  
		  db.insertData("Prodcut",  productID + "," + productName + "," + productType+ ","+productPrice+ "," +
		  			productAmount + "," +productColor);
	}
	
	private static void addUser(User user, DBConnector db)
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
