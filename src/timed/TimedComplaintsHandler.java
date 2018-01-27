package timed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import Server.DBConnector;
import customer.Customer;
import order.OrderComplaint;
import serverAPI.GetRequestByKey;
import utils.EntityFactory;

/**
 * A task that takes care of whether or not an active complaint had been taken care of within 24 hours from its sumbission. 
 * if not, it automatically refunds the complainer.
 * @author ariel
 *
 */
public class TimedComplaintsHandler extends TimerTask
{
	DBConnector db;
	public TimedComplaintsHandler(DBConnector dateBaseConnector)
	{
		this.db = dateBaseConnector;
	}
	
	
	@Override
	public void run() 
	{
		//Get result set of unhandled complaints
		ResultSet rs = db.selectTableData("*", "ordercomplaint", "status = \"NEW\"");
		
		//Convert result set to arrayList of OrderComplaints
		ArrayList<OrderComplaint> unhandledComplaints = EntityFactory.loadOrderComplaints(rs);
				
		//handle all unhandled complaints
		iterateAndHandle(unhandledComplaints);		
	}
//===========================================================================================================
	private void iterateAndHandle(ArrayList<OrderComplaint> unhandledComplaints)
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("DD-MM-YY");
		int currHours = cal.getTime().getHours();
		int currMinutes = cal.getTime().getMinutes();
		
		String currentTime = currHours+":"+currMinutes;

		for(OrderComplaint complaint : unhandledComplaints)
		{
			if (complaint.getComplaintCompensation() != 0.0)
			{
				continue;
			}
			LocalDate complaintDate = complaint.getComplaintDate();
			LocalDate currentDate = LocalDate.now();
			
			String complaintTime = complaint.getComplaintTime();
			
			boolean timePassed = currentDate.getDayOfMonth()>complaintDate.getDayOfMonth()&&
								 currentDate.getMonthValue() >=complaintDate.getMonthValue();
			
			 timePassed = timePassed ||  ((currentDate.isAfter(complaintDate)) &&					           
					  						(currentTime.compareTo( complaintTime) !=-1)); 
	
			System.out.println(timePassed);
			if(timePassed) 
			{
				ArrayList<String> keys = new ArrayList<String>();
				keys.add(Long.toString(complaint.getCustomerID()));
				keys.add(Integer.toString(complaint.getStoreID()));
				try 
				{
					refundCustomer(keys,30);
					db.executeUpdate("ordercomplaint", "givenCompensationAmount ="+30,"id = "+complaint.getComplaintID());
				}
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
//===========================================================================================================
	private void refundCustomer(ArrayList<String> customerKeys, float refundAmount) throws SQLException
	  {
		 String condition = "";
		 condition = db.generateConditionForPrimayKey("Customers",customerKeys, condition);
		 System.out.println(condition);
		 ResultSet rs = db.selectTableData("*", "Customers", condition);
		 
		ArrayList<Customer> result = EntityFactory.loadCustomers(rs);
		
		Customer customer = result.get(0);
		
		float  newBalance = customer.getAccountBalance() +refundAmount;
		db.executeUpdate("Customers", "accountBalance="+newBalance, 
					  "PersonID="+customer.getID()+" AND StoreID="+customer.getStoreID() );
		
	
	  }



}
