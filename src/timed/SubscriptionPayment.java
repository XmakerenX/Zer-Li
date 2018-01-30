package timed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import Server.DBConnector;
import Server.DBInterface;
/**
 * a task that takes care of the customer's need to pay for the subscription they've made
 * at the beginning of each month we take care of the last months orders
 * @author dk198
 *
 */
public class SubscriptionPayment extends TimerTask
{
	DBInterface conn = null;
	//String year;
	public SubscriptionPayment(DBInterface db)
	{
		this.conn = db;
		//this.year = year;
	}
	
	@Override
	/**
	 * the actual method that is run, it checks whether it's the first of a month and reduces the money from their balance
	 */
	public void run() {
		Calendar cal = Calendar.getInstance();
		//cal.add(Calendar.MONTH, 1);
		   SimpleDateFormat formatter = new SimpleDateFormat("dd");
		   String date = formatter.format(cal.getTime());
		   //Checks if the date is right for creating new reports
		   if(date.equals("01")) //checks whether the it's the first of a month
		   //if (date.equals("27"))
		   {
		        //getting current year:
		        String year = String.valueOf(cal.get(Calendar.YEAR));
			   formatter = new SimpleDateFormat("MM");
			   String monthOfPayment = formatter.format(cal.getTime());
			   if(monthOfPayment.equals("01"))		//if we are at the beginning of the year
			   {
				   System.out.println("it's January now!");
				   year="" + (Integer.valueOf(year)-1);
				   System.out.println("Year for payment is: "+year);
			   }
			   cal = Calendar.getInstance();
			   formatter = new SimpleDateFormat("MM");
			   String stringMonth = formatter.format(cal.getTime());
			   int month = Integer.valueOf(formatter.format(cal.getTime()))-1;
			   System.out.println("It's time to PAY! MUHAHA");
			   
			   SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
			   
			   Calendar from = Calendar.getInstance();
			   //from.add(Calendar.MONTH, 1);
			   from.add(Calendar.MONTH, -1);
			   from.set(Calendar.MINUTE, 00);
			   from.set(Calendar.HOUR_OF_DAY, 00);
			   from.set(Calendar.SECOND, 00);
			   from.set(Calendar.DAY_OF_MONTH, 01);
			   from.set(Calendar.YEAR, Integer.valueOf(year));
			   
			   Calendar to = Calendar.getInstance();
			   //to.add(Calendar.MONTH, 1);
			   to.add(Calendar.MONTH, -1);
			   to.set(Calendar.MINUTE, 59);
			   to.set(Calendar.HOUR_OF_DAY, 23);
			   to.set(Calendar.SECOND, 59);
			   to.set(Calendar.DAY_OF_MONTH, 31);
			   to.set(Calendar.YEAR, Integer.valueOf(year));
			   ArrayList<String> customerIDs = new ArrayList<String>();
			   ArrayList<Float> spendings = new ArrayList<Float>();
			   ArrayList<Integer> orderIDs = new ArrayList<Integer>(); //this way we save all of the orders we need to update to 'SUBSCRIPTION_PAID'
			   
			   //all the customers who have bought anything during this month
			   ResultSet rs = conn.selectTableData("*", "Order", "OrderPaymentMethod = 'SUBSCRIPTION' AND "
				   		+ "STR_TO_DATE(OrderCreationDateTime, '%Y-%m-%d %k:%i:%s') between '"+format.format(from.getTime())+"' and '"+format.format(to.getTime())+"' ");
			   try
				  {
					  while (rs.next())
					  {
						  String toAdd = rs.getInt("OrderCustomerID")+"-"+ rs.getInt("OrderOriginStore");
						  if(!customerIDs.contains(toAdd))
						  {
							  customerIDs.add(toAdd);
							  orderIDs.add(rs.getInt("ORderID"));
						  }
						  
					  }
				  }catch (SQLException e) {e.printStackTrace();}
			   
			   int customerNumber = customerIDs.size();
			   if(customerNumber==0) System.out.println("No subscription payments to update!");
			   //for each customer who used subscription, we are updating how much they have spent
			   if(customerNumber!=0) {
				   System.out.println(customerIDs);
				   System.out.println("we have "+ customerNumber + " customers.");	
				   for(int i=0; i<customerNumber; i++)
				   {
					   String customerANdStore = customerIDs.get(i);
					   String[] parts = customerANdStore.split("-");
					   String customerID = parts[0];
					   String storeID = parts[1];
					   System.out.println(customerID+" store: "+ storeID);
					   spendings.add((float) 0);
					   rs = conn.selectTableData("*", "Order", "OrderCustomerID = "+customerID+ " AND OrderOriginStore = "+storeID+" AND OrderPaymentMethod = 'SUBSCRIPTION' AND "
						   		+ "STR_TO_DATE(OrderCreationDateTime, '%Y-%m-%d %k:%i:%s') between '"+format.format(from.getTime())+"' and '"+format.format(to.getTime())+"' ");
					   try
						  {
							  while (rs.next())
							  {
								  spendings.set(i, spendings.get(i) + rs.getFloat("OrderPrice"));
								  
							  }
						  }
					   catch (SQLException e) {e.printStackTrace();}
					   System.out.println(spendings);
				   }
			   }
			   
			   //now customerIDs(i) holds the 'customerID+storeID'
			   //and spendings(i) holds that customer's spendings
			   for(int i=0; i< customerNumber; i++) 
			   {
				   String customerANdStore = customerIDs.get(i);
				   String[] parts = customerANdStore.split("-");
				   String customerID = parts[0];
				   String storeID = parts[1];
				   rs = conn.selectTableData("accountBalance", "Customers", "personID ="+customerID+" and StoreID="+storeID);
				   try
					  {
						  while (rs.next())		//update user's balance
						  {
							  Float updateTo = rs.getFloat("accountBalance") - spendings.get(i);
							  System.out.println("Updating customer Balance! from "+rs.getFloat("accountBalance")+" to: "+updateTo);
							  conn.executeUpdate("Customers", "accountBalance="+updateTo, "personID="+customerID+" and StoreID ="+storeID);
						  }
					  }
				   catch (SQLException e) {e.printStackTrace();}
			   }
			   int numberOfOrdersToUpdate = orderIDs.size();
			   for(int i=0; i<numberOfOrdersToUpdate; i++)				//updating the status of each order to 'SUBSCRIPTION_PAID'
			   {
				   try {
					conn.executeUpdate("prototype.Order", "OrderPaymentMethod = 'SUBSCRIPTION_PAID'", "OrderID = "+orderIDs.get(i));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			   
		   }
		   else
		   {
			   System.out.println("Dont have to update account balances!");
		   }
		
	}

}
