package timed;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;

//import com.sun.jdi.connect.Connector;

import Server.DBConnector;
import client.Client;
import client.ClientInterface;
import product.Product;
import report.IncomeReport;
import report.ReportController;
import serverAPI.GetJoinedTablesRequest;
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
		   if(date.equals("01-01") || date.equals("01-03") || date.equals("01-07") || date.equals("01-10")
				   /* || date.equals(formatter.format(cal.getTime())) */)
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
					 String quarterStr;
					 if(quarter==1) quarterStr = "FIRST";
					 else if(quarter==2) quarterStr = "SECOND";
					 else if(quarter==3) quarterStr = "THIRD";
					 else quarterStr = "FOURTH";
					 System.out.println("=====================================\nCreating REPORTS for store :"+stores.get(i));
								   try {
									   //Works
//									   System.out.println("Creating complaint report for store :"+stores.get(i));
//									   reportData = calculateComplaintReportData(yearForReports, incomeQ, stores.get(i));
//									   System.out.println(reportData);
//									   conn.insertData("complaintreport(Quarterly, Year, StoreID, FirstMonthHandledComplaintsAmount, "
//										   		+ "FirstMonthPendingComplaintsAmount, SecondMonthHandledComplaintsAmount, SecondMonthPendingComplaintsAmount, "
//										   		+ "ThirdMonthHandledComplaintsAmount, ThirdMonthPendingComplaintsAmount)", "'"+quarterStr+"','"+
//										   		yearForReports+"',"+stores.get(i)+","+reportData.get(0)+"," +reportData.get(1)+
//										   		","+reportData.get(2)+","+reportData.get(3)+","+reportData.get(4)+","+
//										   		reportData.get(5));

									   //Works
//									   System.out.println("Creating income report for store :"+stores.get(i));
//									   conn.insertData("prototype.incomereport(Quarterly, Year, StoreID, IncomeAmount)", "'"+quarterStr+"','"+yearForReports+"',"+stores.get(i)+","+calculateIncomeAmount(yearForReports, incomeQ, stores.get(i)));
									   
//									   System.out.println("Creating order report for store :"+stores.get(i));
//									   orderData = calculateOrderData(yearForReports, orderQ);
//									   ReportController.createNewOrderReport(orderQ, yearForReports, stores.get(i), orderData.get(0), 
//											   orderData.get(1), orderData.get(2), orderData.get(3), orderData.get(4), 
//											   orderData.get(5), Client.client);

									   //Works
//									   System.out.println("Creating satisfaction report for store :"+stores.get(i));
//									   surveyResultData = calculateSurveyResultData(yearForReports, surveyQ, stores.get(i));
//									   System.out.println(surveyResultData);
//									   conn.insertData("surveyreport(Quarterly, Year, StoreID, FirstSurveyAverageResult, "
//									   		+ "SecondSurveyAverageResult, ThirdSurveyAverageResult, FourthSurveyAverageResult, "
//									   		+ "FifthSurveyAverageResult, SixthSurveyAverageResult)", "'"+quarterStr+"','"+
//									   		yearForReports+"',"+stores.get(i)+","+surveyResultData.get(0)+"," +surveyResultData.get(1)+
//									   		","+surveyResultData.get(2)+","+surveyResultData.get(3)+","+surveyResultData.get(4)+","+
//									   		surveyResultData.get(5));

								   
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
	   ArrayList<Integer> calculateComplaintReportData(String year, report.IncomeReport.Quarterly incomeQ, int store)
	   {
		   ArrayList<Integer> reportData =  new ArrayList<Integer>();
		   reportData.add(0);
		   reportData.add(0);
		   reportData.add(0);
		   reportData.add(0);
		   reportData.add(0);
		   reportData.add(0);
		   String condition1;
		   String condition2;
		   String condition3;
		   if(incomeQ == report.IncomeReport.Quarterly.FIRST) 
		   {
			   	condition1 = " and date >= CAST('"+year+"-01-01' AS DATE) and date<= CAST('"+year+"-01-31' AS DATE)";
		   		condition2 = " and date >= CAST('"+year+"-02-01' AS DATE) and date<= CAST('"+year+"-02-31' AS DATE)";
		   		condition3 = " and date >= CAST('"+year+"-03-01' AS DATE) and date<= CAST('"+year+"-03-31' AS DATE)";
		   }
		   else if(incomeQ == report.IncomeReport.Quarterly.SECOND)
		   {
			   condition1 = " and date >= CAST('"+year+"-04-01' AS DATE) and date<= CAST('"+year+"-04-31' AS DATE)";
			   condition2 = " and date >= CAST('"+year+"-05-01' AS DATE) and date<= CAST('"+year+"-05-31' AS DATE)";
		   		condition3 = " and date >= CAST('"+year+"-06-01' AS DATE) and date<= CAST('"+year+"-06-31' AS DATE)";
		   }
		   else if(incomeQ == report.IncomeReport.Quarterly.THIRD)
		   {
			   condition1 = " and date >= CAST('"+year+"-07-01' AS DATE) and date<= CAST('"+year+"-07-31' AS DATE)";
			   condition2 = " and date >= CAST('"+year+"-08-01' AS DATE) and date<= CAST('"+year+"-08-31' AS DATE)";
		   		condition3 = " and date >= CAST('"+year+"-09-01' AS DATE) and date<= CAST('"+year+"-09-31' AS DATE)";
		   }
		   else
		   {
			   condition1 = " and date >= CAST('"+year+"-10-01' AS DATE) and date<= CAST('"+year+"-10-31' AS DATE)";
			   condition2 = " and date >= CAST('"+year+"-11-01' AS DATE) and date<= CAST('"+year+"-11-30' AS DATE)";
			   condition3 = " and date >= CAST('"+year+"-12-01' AS DATE) and date<= CAST('"+year+"-12-31' AS DATE)";
		   }
		   //first month
		   ResultSet rs1 = conn.selectTableData("*", "ordercomplaint", "storeID="+store+" "+condition1);
		   try
			  {
				  while (rs1.next())		//update user's balance
				  {
					  String status = rs1.getString("Status");
					  if(status.equals("CLOSED"))
						  reportData.set(0, reportData.get(0)+1);
					  if(status.equals("NEW"))
						  reportData.set(1, reportData.get(1)+1);
				  }
			  }
		   catch (SQLException e) {e.printStackTrace();}
		   //second month
		   ResultSet rs2 = conn.selectTableData("*", "ordercomplaint", "storeID="+store+" "+condition2);
		   try
			  {
				  while (rs2.next())		//update user's balance
				  {
					  String status = rs2.getString("Status");
					  if(status.equals("CLOSED"))
						  reportData.set(2, reportData.get(2)+1);
					  if(status.equals("NEW"))
						  reportData.set(3, reportData.get(3)+1);
				  }
			  }
		   catch (SQLException e) {e.printStackTrace();}
		   //third month
		   ResultSet rs3 = conn.selectTableData("*", "ordercomplaint", "storeID="+store+" "+condition3);
		   try
			  {
				  while (rs3.next())		//update user's balance
				  {
					  String status = rs3.getString("Status");
					  if(status.equals("CLOSED"))
						  reportData.set(4, reportData.get(4)+1);
					  if(status.equals("NEW"))
						  reportData.set(5, reportData.get(5)+1);
				  }
			  }
		   catch (SQLException e) {e.printStackTrace();}
		   return reportData;
	   }
	 //===============================================================================================================
	   long calculateIncomeAmount(String year, report.IncomeReport.Quarterly quarter, Integer store)
	   {
		   long income = 0;
		   String condition;
		   if(quarter == report.IncomeReport.Quarterly.FIRST) 
			   condition = " and OrderCreationDateTime >= CAST('"+year+"-01-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-03-31' AS DATE)";
		   else if(quarter == report.IncomeReport.Quarterly.SECOND)
			   condition = " and OrderCreationDateTime >= CAST('"+year+"-04-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-06-31' AS DATE)";
		   else if(quarter == report.IncomeReport.Quarterly.THIRD)
			   condition = " and OrderCreationDateTime >= CAST('"+year+"-07-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-09-31' AS DATE)";
		   else
			   condition = " and OrderCreationDateTime >= CAST('"+year+"-10-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-12-31' AS DATE)";
		  
		   ResultSet rs = conn.selectTableData("*", "order", "OrderOriginStore ="+store + condition);
		   try
			  {
				  while (rs.next())		//update user's balance
				  {
					  income+=rs.getFloat("OrderPrice");
					  income-=rs.getFloat("OrderRefund");
				  }
			  }
		   catch (SQLException e) {e.printStackTrace();}
		   System.out.println("Store income is: "+income);
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
	   ArrayList<Long> calculateSurveyResultData(String year, report.SurveyReport.Quarterly quarter, int store)
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
			   condition = "date >= CAST('"+year+"-01-01' AS DATE) and date<= CAST('"+year+"-03-31' AS DATE)";
		   else if(quarter == report.SurveyReport.Quarterly.SECOND)
			   condition = "date >= CAST('"+year+"-04-01' AS DATE) and date<= CAST('"+year+"-06-31' AS DATE)";
		   else if(quarter == report.SurveyReport.Quarterly.THIRD)
			   condition = "date >= CAST('"+year+"-07-01' AS DATE) and date<= CAST('"+year+"-09-31' AS DATE)";
		   else
			   condition = "date >= CAST('"+year+"-10-01' AS DATE) and date<= CAST('"+year+"-12-31' AS DATE)";
		  
		   ResultSet rs = conn.selectTableData("*", "customersatisfactionsurveyresults", "storeID="+store+" and "+condition);
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
	    * 						orderData(5) - custom ordered
	    */
	   ArrayList<Integer> calculateOrderData(String year, report.OrderReport.Quarterly quarter, int storeID)
	   {
		   ArrayList<Integer> orderData =  new ArrayList<Integer>();
		   ArrayList<Integer> orderInStore = new ArrayList<Integer>();
		   int totalOrdersNum = 0;
		   int numBouquetsOrdered  = 0;
		   int numBridalOrdered  = 0;
		   int numFlowerPotsOrdered = 0;
		   int numFlowersOrdered = 0;
		   int numCustomOrdered = 0;
		   
		   String condition;
		   if(quarter == report.OrderReport.Quarterly.FIRST) 
			   condition = "date >= CAST('"+year+"-01-01' AS DATE) AND date<= CAST('"+year+"-03-31' AS DATE)";
		   else if(quarter == report.OrderReport.Quarterly.SECOND)
			   condition = "date >= CAST('"+year+"-04-01' AS DATE) AND date<= CAST('"+year+"-06-31' AS DATE)";
		   else if(quarter == report.OrderReport.Quarterly.THIRD)
			   condition = "date >= CAST('"+year+"-07-01' AS DATE) AND date<= CAST('"+year+"-09-31' AS DATE)";
		   else
			   condition = "date >= CAST('"+year+"-10-01' AS DATE) AND date<= CAST('"+year+"-12-31' AS DATE)";
		  
		   
		   ResultSet orderRS = conn.selectTableData("OrderID", "Order", "OrderOriginStore="+storeID+" AND "+condition);
		   try 
		   {
			   while (orderRS.next())
			   {
				   orderInStore.add(orderRS.getInt("OrderID"));
			   }
			   orderRS.close();
		   }catch (SQLException e) 
		   {
			   System.out.println("Failed to get orders for store "+storeID);
			   e.printStackTrace();
		   }
		   
		   for (Integer orderID : orderInStore)
		   {
			   totalOrdersNum++;
			   condition = "OrderID" + " = " + orderID;

			   ArrayList<String> tableKeyName = conn.getTableKeyName("Product");
			   ArrayList<String> joinedTableKeyName = conn.getTableKeyName("ProdcutInOrder");
			   // make the join on the primary key between the tables who should be the same for this to work
			   // condition  = <table>.<tableKey> = <joinedTable>.<joinedTableKey>;
			   condition = "Product"+"."+tableKeyName.get(0)+"="
					   +"ProdcutInOrder"+"."+joinedTableKeyName.get(0) +" AND " + condition;
			   ResultSet productRS = conn.selectJoinTablesData("*", "Product", "ProdcutInOrder", condition);
			   
			   try 
			   {
				   while (productRS.next())
				   {
					   String productType = productRS.getString("ProductType".toLowerCase());
					   
					   switch(productType)
					   {
					   
					   case "bonquet":
						   numBouquetsOrdered++;
						   break;
						   
					   case "bridal bouquet":
						   numBridalOrdered++;
						   break;
						   
					   case "pot":
					   		numFlowerPotsOrdered++;
					   		break;
					   }
				   }
				   productRS.close();
				   
				   int ret = conn.countSelectTableData("CustomItemID", "CustomItem", "CustomItemOrderID="+orderID);
				   if (ret != -1)
					   numCustomOrdered += ret;
				   
			   } catch (SQLException e) 
			   {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
		   }

		   orderData.add(totalOrdersNum);
		   orderData.add(numBouquetsOrdered);
		   orderData.add(numBridalOrdered);
		   orderData.add(numFlowerPotsOrdered);
		   orderData.add(numFlowersOrdered);
		   orderData.add(numCustomOrdered);
		   
		   return orderData;
	   }
}
