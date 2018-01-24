package timed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

import com.sun.jdi.connect.Connector;

import Server.DBConnector;
import client.Client;
import client.ClientInterface;
import product.Product;
import report.IncomeReport;
import report.ReportController;
import serverAPI.Response;

public class QuarterlyReportCreation extends TimerTask
{
	DBConnector conn = null;
	private int quarter;
	private String year;
	
	public QuarterlyReportCreation(int quarter, String year, DBConnector conn)
	{
		this.quarter = quarter;
		this.year = year;
		this.conn = conn;
	   }

	   public void run() 
	   {
		   Calendar cal = Calendar.getInstance();
		   SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
		   String date = formatter.format(cal.getTime());
		   //Checks if the date is right for creating new reports
		   if(date.equals("01-01") || date.equals("01-03") || date.equals("01-07") || date.equals("01-10") /* || date.equals(formatter.format(cal.getTime()))*/ )
		   {
			   String yearForReports=""+year;;
			   report.IncomeReport.Quarterly incomeQ = report.IncomeReport.Quarterly.FIRST;
			   report.OrderReport.Quarterly orderQ = report.OrderReport.Quarterly.FIRST;
			   report.SurveyReport.Quarterly surveyQ = report.SurveyReport.Quarterly.FIRST;
			   report.ComplaintReport.Quarterly complaintQ = report.ComplaintReport.Quarterly.FIRST;
			   //inits the quarters
			   switch(quarter) {
				   case 1: 
					   break;
				   case 2:
					   incomeQ = report.IncomeReport.Quarterly.SECOND;
					   orderQ = report.OrderReport.Quarterly.SECOND;
					   surveyQ = report.SurveyReport.Quarterly.SECOND;
					   complaintQ = report.ComplaintReport.Quarterly.SECOND;
					   break; 
				   case 3:
					   incomeQ = report.IncomeReport.Quarterly.THIRD;
					   orderQ = report.OrderReport.Quarterly.THIRD;
					   surveyQ = report.SurveyReport.Quarterly.THIRD;
					   complaintQ = report.ComplaintReport.Quarterly.THIRD;
					   break;
				   case 4:
					   incomeQ = report.IncomeReport.Quarterly.FOURTH;
					   orderQ = report.OrderReport.Quarterly.FOURTH;
					   surveyQ = report.SurveyReport.Quarterly.FOURTH;
					   complaintQ = report.ComplaintReport.Quarterly.FOURTH;
					   yearForReports = "" + (Integer.valueOf(year)-1);		//means it's last years'
					   break;
			   }
			   //checks whether the reports have already been created, for the first run of the server.
			   ArrayList<String> keys = new ArrayList<String>();
			   keys.add(""+complaintQ);
			   keys.add(yearForReports);
			   keys.add(""+1);
			   if(conn.doesExists("ComplaintReport", keys))
			   {
				   System.out.println("Reports already created");
			   }
			   
			   else
			   {
				   System.out.println("Creating reports!");
			
			   ResultSet storeRS = conn.selectTableData("StoreID", "store", "");
			   ArrayList<Integer> stores = new ArrayList<Integer>();
				  try
				  {
					  while (storeRS.next())
					  {
						  stores.add(storeRS.getInt(1));
					  }
				  }catch (SQLException e) {e.printStackTrace();}
				  
				  
				  
				  ArrayList<Integer> reportData;
				  ArrayList<Long> surveyResultData;
				  ArrayList<Integer> orderData;
				 System.out.println("stores are: " + stores);
				 int numberOfStores = stores.size();
				 //creating the reports for each store
				 for(int i=0; i<numberOfStores; i++)
				 {
					 reportData = calculateComplaintReportData(yearForReports, incomeQ);
					 surveyResultData = calculateSurveyResultData(yearForReports, surveyQ);
					 orderData = calculateOrderData(yearForReports, orderQ);
					 
								   try {
									   ReportController.createNewComplaintReport(complaintQ, yearForReports, stores.get(i), 
											   reportData.get(0), reportData.get(1), reportData.get(2), reportData.get(3), 
											   reportData.get(4), reportData.get(5), Client.client);
									   
									   ReportController.createNewIncomeReport(incomeQ, yearForReports, stores.get(i), 
											   calculateIncomeAmount(yearForReports, incomeQ), Client.client);	
									   
									   ReportController.createNewOrderReport(orderQ, yearForReports, stores.get(i), orderData.get(0), 
											   orderData.get(1), orderData.get(2), orderData.get(3), orderData.get(4), 
											   orderData.get(5), Client.client);
									   
									   ReportController.createNewSurveyReport(surveyQ, yearForReports, stores.get(i), 
											   surveyResultData.get(0), surveyResultData.get(1), surveyResultData.get(2), 
											   surveyResultData.get(3), surveyResultData.get(4), surveyResultData.get(5), Client.client);
								   		} 
								   catch (Exception ex)
								   		{
									   		System.out.println("error running thread " + ex.getMessage());
								   		}
				 }
		   }
		   }
		   else
		   {
			   System.out.println("We dont need to create reports right now!");
		   }
	   }
	 //===============================================================================================================
	   /**
	    * 
	    * @param year			year
	    * @param quarter		quarter
	    * @return				reportData(0) - first month's handled complaints
	    * 						reportData(1) - first month's not handled complaints
	    *						reportData(2) - second month's handled complaints
	    * 						reportData(3) - second month's not handled complaints
	    * 						reportData(4) - third month's handled complaints
	    * 						reportData(5) - third month's not handled complaints
	    */
	   ArrayList<Integer> calculateComplaintReportData(String year, report.IncomeReport.Quarterly incomeQ)
	   {
		   ArrayList<Integer> reportData =  new ArrayList<Integer>();
		   
		   
		   return reportData;
	   }
	 //===============================================================================================================
	   long calculateIncomeAmount(String year, report.IncomeReport.Quarterly quarter)
	   {
		   long income = 0;
		   ResultSet storeRS = conn.selectTableData("StoreID", "store", "");
		   
		   return income;
	   }
	 //===============================================================================================================
	   /**
	    * 
	    * @param year			year
	    * @param quarter		quarter
	    * @return				reportData(0) - first question's average result
	    * 						reportData(1) - second question's average result
	    *						reportData(2) - third question's average result
	    * 						reportData(3) - fourth question's average result
	    * 						reportData(4) - fifth question's average result
	    * 						reportData(5) - sixth question's average result
	    */
	   ArrayList<Long> calculateSurveyResultData(String year, report.SurveyReport.Quarterly quarter)
	   {
		   ArrayList<Long> surveyResultData =  new ArrayList<Long>();
		   surveyResultData.add((long) 0);
		   surveyResultData.add((long) 0);
		   surveyResultData.add((long) 0);
		   surveyResultData.add((long) 0);
		   surveyResultData.add((long) 0);
		   surveyResultData.add((long) 0);
		   String condition;
		   if(quarter == report.SurveyReport.Quarterly.FIRST) 
			   condition = "date >= CAST('"+year+"-01-01' AS DATE) and date<= CAST('"+year+"-13-31' AS DATE)";
		   else if(quarter == report.SurveyReport.Quarterly.SECOND)
			   condition = "date >= CAST('"+year+"-04-01' AS DATE) and date<= CAST('"+year+"-06-31' AS DATE)";
		   else if(quarter == report.SurveyReport.Quarterly.THIRD)
			   condition = "date >= CAST('"+year+"-07-01' AS DATE) and date<= CAST('"+year+"-09-31' AS DATE)";
		   else
			   condition = "date >= CAST('"+year+"-10-01' AS DATE) and date<= CAST('"+year+"-12-31' AS DATE)";
		  
		   ResultSet rs = conn.selectTableData("*", "customersatisfactionsurveyresults", condition);
		   int numberOfResults=0;
		   try
			  {
				  while (rs.next())
				  {
					  surveyResultData.set(0, surveyResultData.get(0) + rs.getLong("answer1"));
					  surveyResultData.set(1, surveyResultData.get(1) + rs.getLong("answer2"));
					  surveyResultData.set(2, surveyResultData.get(2) + rs.getLong("answer3"));
					  surveyResultData.set(3, surveyResultData.get(3) + rs.getLong("answer4"));
					  surveyResultData.set(4, surveyResultData.get(4) + rs.getLong("answer5"));
					  surveyResultData.set(5, surveyResultData.get(5) + rs.getLong("answer6"));
					  numberOfResults++;
				  }
			  }catch (SQLException e) {e.printStackTrace();}
		   if(numberOfResults!=0)
		   {
			   surveyResultData.set(0, surveyResultData.get(0)/numberOfResults);
			   surveyResultData.set(1, surveyResultData.get(1)/numberOfResults);
			   surveyResultData.set(2, surveyResultData.get(2)/numberOfResults);
			   surveyResultData.set(3, surveyResultData.get(3)/numberOfResults);
			   surveyResultData.set(4, surveyResultData.get(4)/numberOfResults);
			   surveyResultData.set(5, surveyResultData.get(5)/numberOfResults);
		   }
		   
		   return surveyResultData;
	   }
	 //===============================================================================================================
	   /**
	    * 
	    * @param year			year
	    * @param quarter		quarter
	    * @return				orderData(0) - total amount of orders in said quarter
	    * 						orderData(1) - bouquets ordered
	    * 						orderData(2) - bridal bouquets ordered
	    * 						orderData(3) - flower pots ordered
	    * 						orderData(4) - flowers ordered
	    * 						orderData(5) - plants ordered
	    */
	   ArrayList<Integer> calculateOrderData(String year, report.OrderReport.Quarterly quarter)
	   {
		   ArrayList<Integer> orderData =  new ArrayList<Integer>();
		   orderData.add(0);
		   orderData.add(0);
		   orderData.add(0);
		   orderData.add(0);
		   orderData.add(0);
		   orderData.add(0);
		   
		   
		   return orderData;
	   }
}
