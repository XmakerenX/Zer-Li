package utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;

import Server.DBConnector;
import customer.Customer;
import customer.Customer.PayType;
import order.Order;
import product.CatalogItem;
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
		case "CatalogProduct":
					return addCatalogProduct((CatalogItem)entity, db);
		case "User":
			        return addUser((User)entity, db);  
			        
		case "surveys":
					return addSurvey((CustomerSatisfactionSurvey)entity, db);
					
		case "CustomerSatisfactionSurveyResults":
					return addSurveyResults((CustomerSatisfactionSurveyResults)entity, db);
					
		case "Order":
			return addOrder((Order)entity, db);
			
		case "Customers":
			return addCustomer((Customer)entity, db);
		
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
	private static Boolean addCatalogProduct(CatalogItem catalogItem, DBConnector db)
	{
		 String productID = Long.toString(catalogItem.getID());
		  String salesPrice = Float.toString(catalogItem.getSalePrice());
		  String imageName = "'"+catalogItem.getImageName()+"'";
		  String storeID = Integer.toString( catalogItem.getStoreID());
		 
		  
		  try
		  {
			  db.insertData("CatalogProduct", productID + "," + salesPrice + "," + imageName+ ","+storeID);
			  return true;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
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
			int storeID = surveyResults.getStoreID();
			try
			{
			db.insertData("customersatisfactionsurveyresults", null + "," + surveyName + "," + "'" + sqlDate + "'" + "," + answer1 + "," + answer2 + "," + answer3 + "," + answer4 + "," + answer5 + 
					"," + answer6 + "," + storeID);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
	}
		
		private static Boolean addOrder(Order order, DBConnector db)
		{
			String orderStatus = "'"+order.getStatus()+"'";
			float orderPrice = order.getPrice();
			Date sqlDate = Date.valueOf(order.getOrderDate());
			String orderTime = "'" + order.getOrderTime() + "'";

			String orderAddress = null;
			String receiverName = null;
			String receiverPhoneNumber = null;
			
			if (order.getDelivaryInfo() != null)
			{
				orderAddress = "'" + order.getDelivaryInfo().getDelivaryAddress() + "'";
				receiverName = "'" + order.getDelivaryInfo().getReceiverName() + "'";
				receiverPhoneNumber = "'" + order.getDelivaryInfo().getReceiverPhoneNumber() + "'";
			}
			String paymentMethod = "'"+order.getOrderPaymentMethod() + "'";
			long originStore = order.getOrderOriginStore();
			long customerID = order.getCustomerID();
			
			try {
				Calendar orderTimeAndDate = order.getOrderDateAndTime();
				Calendar currentTime = Calendar.getInstance();
				if (!orderTimeAndDate.after(currentTime))
					throw new Exception("Bad Date and Time was Given");
				
				db.insertData("prototype.Order", "null" + "," + orderStatus + "," + orderPrice + "," + "'" + sqlDate + "'" +
						"," + orderTime + "," + orderAddress + "," + receiverName + "," + receiverPhoneNumber + ","
						+ paymentMethod + "," + originStore + "," + customerID);
				
				// get the orderID from database
				ResultSet rs = db.selectLastInsertID();
				rs.next();
				int orderID = rs.getInt(1);
				rs.close();
				for (Order.ItemInOrder item : order.getItemsInOrder())
				{ 
					db.insertData("ProductInOrder", item.getProductID() + "," + orderID + "," + "'" +item.getGreetingCard() + "'" );
				}
				
				for (Order.CustomItemInOrder item : order.getCustomItemInOrder())
				{
					String itemType = "'" + item.getType() + "'";
					String itemColor = "'" + item.getColor() + "'";
					String greetingCrad = "'" + item.getGreetingCard() + "'"; 
					db.insertData("CustomItem", "null" + "," + itemType + "," + item.getPrice() + "," + itemColor +
							"," + greetingCrad);

					ResultSet rss = db.selectLastInsertID();
					rss.next();
					int CustomItemID = rss.getInt(1);
					rss.close();
					
					for (Product component : item.getComponents())
					{
						db.insertData("CustomItemProduct", CustomItemID + "," + component.getID() + "," + 
								component.getAmount() + "," + component.getPrice());
					}										
				}
				
				return true;
			} catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				return false;
			}
		}
		
		private static Boolean addCustomer(Customer customer, DBConnector db)
		{
			String personID = ""+customer.getID();
			String fullName = "'"+customer.getName()+"'";
			String phoneNumber = "'"+customer.getPhoneNumber()+"'";
			String payMethod = "'"+customer.getPayMethod()+"'";
			String accountBalance = ""+customer.getAccountBalance();
			String creditCardNumber = "'"+customer.getCreditCardNumber()+"'";
			String accountStatus = ""+customer.getAccountStatus();
			String storeID = "" + customer.getStoreID();
			
			try
			{
			db.insertData("Customers", personID + "," + fullName + "," + phoneNumber + "," + payMethod + "," + accountBalance + ","
					+ creditCardNumber + "," + accountStatus + "," + storeID);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
}
