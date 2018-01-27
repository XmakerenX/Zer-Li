package timed;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import Server.DBConnector;
import order.OrderComplaint;
import utils.EntityFactory;

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
			LocalDate complaintDate = complaint.getComplaintDate();
			LocalDate currentDate = LocalDate.now();
			
			String complaintTime = complaint.getComplaintTime();
			
			boolean timePassed = currentDate.getDayOfMonth()>complaintDate.getDayOfMonth()&&
								 currentDate.getMonthValue() >=complaintDate.getMonthValue();
			
			 timePassed = timePassed ||  ((currentDate.isAfter(complaintDate)) &&					           
					  						(currentTime.compareTo( complaintTime) !=-1)); 
			
			if(timePassed) 
			{
				
			}
			
		}
	}
//===========================================================================================================
}
