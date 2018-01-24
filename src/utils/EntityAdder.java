package utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

import Server.DBConnector;
import customer.Customer;
import customer.Customer.PayType;
import order.CustomItemInOrder;
import order.Order;
import order.OrderComplaint;
import product.CatalogItem;
import product.Product;
import report.ComplaintReport;
import report.IncomeReport;
import report.OrderReport;
import report.SurveyReport;
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
					
		case "CustomerSatisfactionSurveyResults":
			return addSurveyResults((CustomerSatisfactionSurveyResults)entity, db);
					
		case "Order":
			return addOrder((Order)entity, db);
			
		case "Customers":
			return addCustomer((Customer)entity, db);
			
		case "orderComplaint":
			return addOrderComplaint((OrderComplaint)entity, db);
		
	    case "ComplaintReport":
			return addComplaintReports((ComplaintReport)entity, db);
			
	    case "IncomeReport":
			return addIncomeReports((IncomeReport)entity, db);
		
		case "OrderReport":
			return addOrderReports((OrderReport)entity, db);
			  
		case "SurveyReport":
			return addSurveyReports((SurveyReport)entity, db);
			
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

	private static Boolean addSurveyResults(CustomerSatisfactionSurveyResults surveyResults, DBConnector db)
	{		  
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
		db.insertData("customersatisfactionsurveyresults", null + "," + "'" + sqlDate + "'" + "," + answer1 + "," + answer2 + "," + answer3 + "," + answer4 + "," + answer5 + 
				"," + answer6 + "," + storeID + "," + "''");
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
}
		
	@SuppressWarnings("deprecation")
	private static Boolean addOrder(Order order, DBConnector db)
	{
		String orderStatus = "'"+order.getStatus()+"'";
		float orderPrice = order.getPrice();

		String orderAddress = null;
		String receiverName = null;
		String receiverPhoneNumber = null;
		String orderRefund = Float.toString(order.getRefund());
		
		if (order.getDeliveryInfo() != null)
		{
			orderAddress = "'" + order.getDeliveryInfo().getDeliveryAddress() + "'";
			receiverName = "'" + order.getDeliveryInfo().getReceiverName() + "'";
			receiverPhoneNumber = "'" + order.getDeliveryInfo().getReceiverPhoneNumber() + "'";
		}
		
		
		String paymentMethod = "'"+order.getOrderPaymentMethod() + "'";
		long originStore = order.getOrderOriginStore();
		long customerID = order.getCustomerID();
		
		try {
			Calendar orderTimeAndDate = order.getOrderRequiredDateTime();
			Calendar currentTime = Calendar.getInstance();
					
			
			Timestamp orderMinTimestamp = new Timestamp(currentTime.get(Calendar.YEAR) - 1900, currentTime.get(Calendar.MONTH),
					currentTime.get(Calendar.DAY_OF_MONTH), currentTime.get(Calendar.HOUR),
					currentTime.get(Calendar.MINUTE), currentTime.get(Calendar.SECOND), 0);
			;
			if (currentTime.get(Calendar.HOUR_OF_DAY) < 12)
				orderMinTimestamp.setTime(orderMinTimestamp.getTime() + 10800000);
			else
				orderMinTimestamp.setTime(orderMinTimestamp.getTime() + 54000000);

			
			if (orderTimeAndDate.before(currentTime))
				throw new Exception("Bad Date and Time was Given");

			String orderRequiredDate;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//if (orderTimeAndDate.before(orderMinTimestamp))
			if (orderMinTimestamp.after(orderTimeAndDate.getTime()))
			{
				Calendar orderMinTime = new GregorianCalendar();
				orderMinTime.setTimeInMillis(orderMinTimestamp.getTime());

				orderRequiredDate = sdf.format(orderMinTime.getTime());
			}
			else
			{
				orderRequiredDate = sdf.format(orderTimeAndDate.getTime());
			}

			String currentTimeString = sdf.format(currentTime.getTime());
			
			db.insertData("prototype.Order", "null" + "," + orderStatus + "," + orderPrice + "," + 
					"'" + currentTimeString + "'" + "," + "'" + orderRequiredDate + "'" +
					"," + "''" + "," + orderAddress + "," + receiverName + "," + receiverPhoneNumber + ","
					+ paymentMethod + "," + originStore + "," + customerID+","+orderRefund);
							
			// get the orderID from database
			ResultSet rs = db.selectLastInsertID();
			rs.next();
			int orderID = rs.getInt(1);
			rs.close();
			for (Order.ItemInOrder item : order.getItemsInOrder())
			{ 
				db.insertData("ProductInOrder", item.getProductID() + "," + orderID + "," + "'" +item.getGreetingCard() + "'" );
			}
			
			for (CustomItemInOrder item : order.getCustomItemInOrder())
			{
				String itemType = "'" + item.getType() + "'";
				String itemColor = "'" + item.getColor() + "'";
				String greetingCrad = "'" + item.getGreetingCard() + "'"; 
				db.insertData("CustomItem", "null" + "," + itemType + "," + item.getPrice() + "," + itemColor +
						"," + greetingCrad + "," + orderID);

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
			
			if (order.getOrderPaymentMethod() == Order.PayMethod.STORE_ACCOUNT)
			{
				String personID = "personID="+order.getCustomerID();
				String storeID = "StoreID="+order.getOrderOriginStore();
				db.executeUpdate("Customers", "accountBalance=accountBalance - "+order.getPrice(), personID + " AND " + storeID);
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
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			String expirationDate = "'" + formatter.format(customer.getExpirationDate().getTime()) +"'";
			//String expirationDate = "'" + customer.getExpirationDate()+"'";
			
			try
			{
			db.insertData("Customers", personID + "," + fullName + "," + phoneNumber + "," + payMethod + "," + accountBalance + ","
					+ creditCardNumber + "," + accountStatus + "," + storeID + "," + expirationDate);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		private static Boolean addOrderComplaint(OrderComplaint complaint, DBConnector db)
		{
			String personID = ""+complaint.getCustomerID();
			String complaintTxt = "'"+complaint.getComplaintDescription() + "'";
			String name = "'" + complaint.getCustomerName() +"'";
			String phone = "'" + complaint.getCustomerPhoneNum() +"'";
			java.sql.Date sqlDate = java.sql.Date.valueOf( complaint.getComplaintDate() );
			String time = "'"+ complaint.getComplaintTime()+"'";
			float maxConpensation = complaint.getMaxCompensationAmount();
			String complaintStatus = "'"+complaint.getComplaintStatus()+"'";
			String storeID = "" + complaint.getStoreID();
			
			try
			{
			db.insertData("orderComplaint", null + "," + personID + "," + name +"," + phone + "," + storeID + "," + complaintTxt + "," + "'" + sqlDate + "'" + ","
					+ time + "," + null + "," + maxConpensation  + "," +  complaintStatus);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		/**
		 * Defines fields to insert into "ComplaintReport" table
		 * @param complaintReport - a complaint report's entity with info which will be added to data base
		 * @param db - a connector to data base
		 * @return - a boolean value of success or failure
		 */
		private static Boolean addComplaintReports(ComplaintReport complaintReport, DBConnector db)
		{
			String quarterly = "'"+complaintReport.getQuarterly()+"'";
			String year = "'"+complaintReport.getYear()+"'";
			String storeID = ""+complaintReport.getStoreID();
			String firstMonthHandledComplaintsAmount = ""+complaintReport.getFirstMonthHandledComplaintsAmount();
			String firstMonthPendingComplaintsAmount = ""+complaintReport.getFirstMonthPendingComplaintsAmount();
			String secondMonthHandledComplaintsAmount = ""+complaintReport.getSecondMonthHandledComplaintsAmount();
			String secondMonthPendingComplaintsAmount = ""+complaintReport.getSecondMonthPendingComplaintsAmount();
			String thirdMonthHandledComplaintsAmount = ""+complaintReport.getThirdMonthHandledComplaintsAmount();
			String thirdMonthPendingComplaintsAmount = ""+complaintReport.getThirdMonthPendingComplaintsAmount();
			
			try
			{
			db.insertData("ComplaintReport", quarterly + "," + year + "," + storeID + "," + firstMonthHandledComplaintsAmount + "," +
					firstMonthPendingComplaintsAmount + "," + secondMonthHandledComplaintsAmount + "," + secondMonthPendingComplaintsAmount + "," +
					thirdMonthHandledComplaintsAmount + "," + thirdMonthPendingComplaintsAmount);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		/**
		 * Defines fields to insert into "IncomeReport" table
		 * @param incomeReport - a income report's entity with info which will be added to data base
		 * @param db - a connector to data base
		 * @return - a boolean value of success or failure
		 */
		private static Boolean addIncomeReports(IncomeReport incomeReport, DBConnector db)
		{
			String quarterly = "'"+incomeReport.getQuarterly()+"'";
			String year = "'"+incomeReport.getYear()+"'";
			String storeID = ""+incomeReport.getStoreID();
			String incomeAmount = ""+incomeReport.getIncomeAmount();
			
			try
			{
			db.insertData("IncomeReport", quarterly + "," + year + "," + storeID + "," + incomeAmount);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		/**
		 * Defines fields to insert into "OrderReport" table
		 * @param orderReport - a order report's entity with info which will be added to data base
		 * @param db - a connector to data base
		 * @return - a boolean value of success or failure
		 */
		private static Boolean addOrderReports(OrderReport orderReport, DBConnector db)
		{
			String quarterly = "'"+orderReport.getQuarterly()+"'";
			String year = "'"+orderReport.getYear()+"'";
			String storeID = ""+orderReport.getStoreID();
			String totalOrdersAmount = ""+orderReport.getTotalOrdersAmount();
			String bouquetAmount = ""+orderReport.getBouquetAmount();
			String brideBouquetAmount = ""+orderReport.getBrideBouquetAmount();
			String flowerPotAmount = ""+orderReport.getFlowerPotAmount();
			String flowerAmount = ""+orderReport.getFlowerAmount();
			String plantAmount = ""+orderReport.getPlantAmount();
			
			try
			{
			db.insertData("OrderReport", quarterly + "," + year + "," + storeID + "," + totalOrdersAmount + "," + bouquetAmount + ","
						+ brideBouquetAmount + "," + flowerPotAmount + "," + flowerAmount + "," + plantAmount);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
		
		/**
		 * Defines fields to insert into "SurveyReport" table
		 * @param surveyReport - a survey report's entity with info which will be added to data base
		 * @param db - a connector to data base
		 * @return - a boolean value of success or failure
		 */
		private static Boolean addSurveyReports(SurveyReport surveyReport, DBConnector db)
		{
			String quarterly = "'"+surveyReport.getQuarterly()+"'";
			String year = "'"+surveyReport.getYear()+"'";
			String storeID = ""+surveyReport.getStoreID();
			String firstSurveyAverageResult = ""+surveyReport.getFirstSurveyAverageResult();
			String secondSurveyAverageResult = ""+surveyReport.getSecondSurveyAverageResult();
			String thirdSurveyAverageResult = ""+surveyReport.getThirdSurveyAverageResult();
			String fourthSurveyAverageResult = ""+surveyReport.getFourthSurveyAverageResult();
			String fifthSurveyAverageResult = ""+surveyReport.getFifthSurveyAverageResult();
			String sixthSurveyAverageResult = ""+surveyReport.getSixthSurveyAverageResult();
			
			try
			{
			db.insertData("SurveyReport", quarterly + "," + year + "," + storeID + "," + firstSurveyAverageResult + "," + 
					secondSurveyAverageResult + "," + thirdSurveyAverageResult + "," + fourthSurveyAverageResult + "," +
					fifthSurveyAverageResult + "," + sixthSurveyAverageResult);
				return true;
			}
			catch(Exception e)
			{
				return false;
			}
		}
}
