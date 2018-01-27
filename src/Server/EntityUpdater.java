package Server;

import java.sql.SQLException;

import customer.Customer;
import order.Order;
import order.OrderComplaint;
import product.Product;
import survey.CustomerSatisfactionSurveyResults;
import user.User;

//*************************************************************************************************
/**
* updates Entities in database
*/
//*************************************************************************************************
public class EntityUpdater {

	//*************************************************************************************************
	/**
	 * Updates entity in database
	 * call the appropriate sub setXXX function based on the table
	 * @param table the table load the entity from
	 * @param oldKey the select query result set
	 * @param entity the entity data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
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
				
			case "ordercomplaint":
				setOrderComplaint(oldKey, (OrderComplaint)entity, db);
				break;
				
			case "Order":
				setOrder(oldKey, (Order)entity, db);
				break;
			}		
			return true;
		}
		catch(SQLException ex)
		{
		return false;
		}
	}
	
	//*************************************************************************************************
	/**
	 * Updates order in database
	 * @param oldKey the select query result set
	 * @param order the order data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
	private static void setOrder(String oldKey, Order order, DBConnector db) throws SQLException
	{         
		  String OrderStatus = "OrderStatus=\""+order.getStatus().toString()+"\"";

		  String OrderRefund = "OrderRefund="+order.getRefund();
		  System.out.println(OrderStatus);
		  System.out.println(OrderRefund);
		  String condition = "OrderID="+oldKey; 
		  db.executeUpdate("prototype.Order", OrderRefund + "," + OrderStatus , condition);
	}
	
	//*************************************************************************************************
	/**
	 * Updates product in database
	 * @param oldKey the select query result set
	 * @param product the product data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
	private static void setProdcut(String oldKey, Product product, DBConnector db) throws SQLException
	{
		  String productID = "ProductID="+product.getID();
		  String productName = "ProductName=\""+product.getName()+"\"";
		  String productType = "ProductType=\""+product.getType()+"\"";
		  String productPrice = "productPrice=\""+product.getPrice()+"\"";
		  String productAmount= "productAmount=\""+product.getAmount()+"\"";
		  String productColor = "productColor=\""+product.getColor()+"\"";

		  String condition = "ProductID="+oldKey; 
		  db.executeUpdate("Product", productID + "," + productName + "," + productType+ ","+productPrice+ "," +
				  			productAmount + "," +productColor, condition);
	}
	
	//*************************************************************************************************
	/**
	 * Updates User in database
	 * @param oldKey the select query result set
	 * @param user the user data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
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

	//*************************************************************************************************
	/**
	 * Updates Customer in database
	 * @param oldKey the select query result set
	 * @param customer the customer data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
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
	
	//*************************************************************************************************
	/**
	 * Updates Customer Satisfaction Survey Results in database
	 * @param oldKey the select query result set
	 * @param result the Customer Satisfaction Survey Results data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
	private static void setCustomerSatisfactionSurveyResult(String oldKey, CustomerSatisfactionSurveyResults result, DBConnector db) throws SQLException
	{
		String analysis = "analysis='"+result.getAnalysis()+"'";
		String condition = "id=" + result.getID();
		db.executeUpdate("customersatisfactionsurveyresults", analysis, condition);
	}
	
	//*************************************************************************************************
	/**
	 * Updates Order Complaint in database
	 * @param oldKey the select query result set
	 * @param order the Order Complaint data to update
	 * @param db the database connector
	 * @return true on entity updated successfully otherwise returns false
	 */
	//*************************************************************************************************
	private static void setOrderComplaint(String oldKey, OrderComplaint order, DBConnector db) throws SQLException
	{
		String compensationValue = "givenCompensationAmount= "+order.getComplaintCompensation();
		String status = "status = 'CLOSED'";
		String condition = "id=" + order.getComplaintID();
		db.executeUpdate("ordercomplaint", compensationValue + "," + status, condition);
	}
}
