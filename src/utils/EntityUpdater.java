package utils;

import java.sql.SQLException;
import java.time.LocalDate;

import Server.DBConnector;
import customer.Customer;
import customer.Customer.PayType;
import order.OrderComplaint;
import product.Product;
import survey.CustomerSatisfactionSurvey;
import survey.CustomerSatisfactionSurveyResults;
import user.User;

public class EntityUpdater {

	public static Boolean setEntity(String table, String oldKey, Object entity, DBConnector db)
	{
		try
		{
			switch (table)
			{
			case "Product":
				setProdcut(oldKey, (Product)entity, db);
				break;
				
			case "User":
				setUser(oldKey, (User)entity, db);
				break;
				
			case "Customers":
				setCustomer(oldKey, (Customer)entity, db);
				break;
				
			case "CustomerSatisfactionSurveyResults":
				setCustomerSatisfactionSurveyResult(oldKey, (CustomerSatisfactionSurveyResults)entity, db);
				break;
				
			case "orderComplaint":
				setOrderComplaint(oldKey, (OrderComplaint)entity, db);
			}
			
			return true;
		}
		catch(SQLException ex)
		{
		return false;
		}
	}
	
	private static void setProdcut(String oldKey, Product product, DBConnector db) throws SQLException
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
	
	private static void setUser(String oldKey, User user, DBConnector db) throws SQLException
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
	
	private static void setCustomer(String oldKey, Customer customer, DBConnector db) throws SQLException
	{
		String personID = "personID="+customer.getID();
		String fullName = "fullName='"+customer.getName()+"'";
		String phoneNumber = "phoneNumber='"+customer.getPhoneNumber()+"'";
		String payMethod = "payMethod='"+customer.getPayMethod()+"'";
		String accountBalance = "accountBalance="+customer.getAccountBalance();
		String creditCardNumber = "creditCardNumber='"+customer.getCreditCardNumber()+"'";
		String accountStatus = "AccountStatus="+customer.getAccountStatus();
		String storeID = "StoreID="+customer.getStoreID();
		String condition = "personID="+oldKey+""+" AND "+"StoreID="+customer.getStoreID();
		  
		db.executeUpdate("Customers", personID + "," + fullName + "," + phoneNumber + "," 
				  +payMethod+", " + accountBalance + ", " + creditCardNumber + "," + accountStatus + "," + storeID, condition);
	}
	
	private static void setCustomerSatisfactionSurveyResult(String oldKey, CustomerSatisfactionSurveyResults result, DBConnector db) throws SQLException
	{
		String analysis = "analysis='"+result.getAnalysis()+"'";
		String condition = "id=" + result.getID();
		db.executeUpdate("customersatisfactionsurveyresults", analysis, condition);
	}
	
	private static void setOrderComplaint(String oldKey, OrderComplaint order, DBConnector db) throws SQLException
	{
		String compensationValue = "givenCompensationAmount= "+order.getComplaintCompensation();
		String status = "status = 'Closed'";
		String condition = "id=" + order.getComplaintID();
		db.executeUpdate("orderComplaint", compensationValue + "," + status, condition);
	}
}
