package timed;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimerTask;
import Server.DBConnector;
import Server.DBInterface;
import report.Report.Quarterly;

/**
 * this is a task that takes care of creating the quarterly reports for the zer li system
 *
 */
public class QuarterlyReportCreation extends TimerTask
{
	//variables:
	DBInterface conn = null;
	private Quarterly quarter;
	
	//constructor
	public QuarterlyReportCreation(DBInterface conn)
	{
		this.conn = conn;
	}
	
/**
 * this is what actually happens when the task is run
 * we check if it's time to create the reports and if so, we create them for each one of our stores
 */
	public void run() 
	{
		System.out.println("Checking if to create reports");
		Calendar currentTime = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM");
		String date = formatter.format(currentTime.getTime());
		
		System.out.println("date = " + date);
		//Checks if the date is right for creating new reports
		if(!date.equals("01-01") && !date.equals("01-03") && !date.equals("01-07") && !date.equals("01-10")  )
		{
			System.out.println("No need to create reports yet.");
			return;
		}

		//getting current year:
		int year = currentTime.get(Calendar.YEAR);
		String yearForReports=""+year;

		int month = currentTime.get(Calendar.MONTH);
		// calc in what quarter the current time is
		if (month == 0 || month ==1 || month== 2)
		{
			quarter = Quarterly.FOURTH;
			yearForReports = "" + (Integer.valueOf(year)-1);
		}
		else if (month == 3 || month == 4 || month== 5)
		{
			quarter = Quarterly.FIRST;
		}
		else if (month == 6 || month == 7 || month== 8)
		{
			quarter = Quarterly.SECOND;
		}
		else
			quarter = Quarterly.THIRD;
		
		//checks whether the reports have already been created, for the first run of the server.
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(""+quarter);
		keys.add(yearForReports);
		keys.add(""+1);

		if(conn.doesExists("ComplaintReport", keys))
		{
			System.out.println("Reports already created");
			return;
		}

		System.out.println("Creating reports!");

		//load all the stores in the network
		ResultSet storeRS = conn.selectTableData("StoreID", "Store", "");
		ArrayList<Integer> stores = new ArrayList<Integer>();
		try
		{
			while (storeRS.next())
			{
				// ignore base store
				if (storeRS.getInt(1) != 0)
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
			String quarterStr = ""+quarter;

			System.out.println("=====================================\nCreating REPORTS for store :"+stores.get(i));
			try {
				//--------------------------------------------------------------
				// Create Complaint report
				//--------------------------------------------------------------
				System.out.println("Creating complaint report for store :"+stores.get(i));
				reportData = calculateComplaintReportData(yearForReports, quarter, stores.get(i));
				System.out.println(reportData);
				conn.insertData("ComplaintReport(Quarterly, Year, StoreID, FirstMonthHandledComplaintsAmount, "
						+ "FirstMonthPendingComplaintsAmount, SecondMonthHandledComplaintsAmount, SecondMonthPendingComplaintsAmount, "
						+ "ThirdMonthHandledComplaintsAmount, ThirdMonthPendingComplaintsAmount)", "'"+quarterStr+"','"+
								yearForReports+"',"+stores.get(i)+","+reportData.get(0)+"," +reportData.get(1)+
								","+reportData.get(2)+","+reportData.get(3)+","+reportData.get(4)+","+
								reportData.get(5));

				//--------------------------------------------------------------
				// Create Income report
				//--------------------------------------------------------------
				System.out.println("Creating income report for store :"+stores.get(i));
				conn.insertData("prototype.incomereport(Quarterly, Year, StoreID, IncomeAmount)", "'"+quarterStr+"','"+yearForReports+"',"+stores.get(i)+","+calculateIncomeAmount(yearForReports, quarter, stores.get(i)));

				//--------------------------------------------------------------
				// Create Order report
				//--------------------------------------------------------------
				System.out.println("Creating order report for store :"+stores.get(i));
				orderData = calculateOrderData(yearForReports, quarter, stores.get(i));
				conn.insertData("orderreport","'" +quarterStr+ "'" + "," + "'" + yearForReports+"',"+stores.get(i)+"," + 
						orderData.get(0) + "," + orderData.get(1) + "," + orderData.get(2) + "," +
						orderData.get(3) + "," + orderData.get(4) + "," + orderData.get(5));

				//--------------------------------------------------------------
				// Create Satisfaction report
				//--------------------------------------------------------------
				System.out.println("Creating satisfaction report for store :"+stores.get(i));
				surveyResultData = calculateSurveyResultData(yearForReports, quarter, stores.get(i));
				System.out.println(surveyResultData);
				conn.insertData("surveyreport(Quarterly, Year, StoreID, FirstSurveyAverageResult, "
						+ "SecondSurveyAverageResult, ThirdSurveyAverageResult, FourthSurveyAverageResult, "
						+ "FifthSurveyAverageResult, SixthSurveyAverageResult)", "'"+quarterStr+"','"+
								yearForReports+"',"+stores.get(i)+","+surveyResultData.get(0)+"," +surveyResultData.get(1)+
								","+surveyResultData.get(2)+","+surveyResultData.get(3)+","+surveyResultData.get(4)+","+
								surveyResultData.get(5));
			} 
			catch (Exception ex)
			{
				System.out.println("error running thread " + ex.getMessage());
			}
		}
	}
	//===============================================================================================================
	/**
	 * 						calculates all the needed data for the complaint report for a specific store, year and quarter
	 * @param year			for what year to generate the report
	 * @param incomeQ		the year quarter
	 * @param store 		for what store to generate the report
	 * @return				reportData(0) - first month's handled complaints
	 * 						reportData(1) - first month's not handled complaints
	 *						reportData(2) - second month's handled complaints
	 * 						reportData(3) - second month's not handled complaints
	 * 						reportData(4) - third month's handled complaints
	 * 						reportData(5) - third month's not handled complaints
	 */
	public ArrayList<Integer> calculateComplaintReportData(String year, Quarterly incomeQ, int store)
	{
		ArrayList<Integer> reportData =  new ArrayList<Integer>();
		//initialization:
		reportData.add(0);
		reportData.add(0);
		reportData.add(0);
		reportData.add(0);
		reportData.add(0);
		reportData.add(0);
		String condition1;
		String condition2;
		String condition3;
		//setting the dates based on the needed quarter:
		if(incomeQ == report.IncomeReport.Quarterly.FIRST) 
		{
			//calculating each month's dates separately
			condition1 = " and date >= CAST('"+year+"-01-01' AS DATE) and date<= CAST('"+year+"-01-31' AS DATE)";
			condition2 = " and date >= CAST('"+year+"-02-01' AS DATE) and date<= CAST('"+year+"-02-31' AS DATE)";
			condition3 = " and date >= CAST('"+year+"-03-01' AS DATE) and date<= CAST('"+year+"-03-31' AS DATE)";
		}
		else if(incomeQ == report.IncomeReport.Quarterly.SECOND)
		{
			//calculating each month's dates separately
			condition1 = " and date >= CAST('"+year+"-04-01' AS DATE) and date<= CAST('"+year+"-04-31' AS DATE)";
			condition2 = " and date >= CAST('"+year+"-05-01' AS DATE) and date<= CAST('"+year+"-05-31' AS DATE)";
			condition3 = " and date >= CAST('"+year+"-06-01' AS DATE) and date<= CAST('"+year+"-06-31' AS DATE)";
		}
		else if(incomeQ == report.IncomeReport.Quarterly.THIRD)
		{
			//calculating each month's dates separately
			condition1 = " and date >= CAST('"+year+"-07-01' AS DATE) and date<= CAST('"+year+"-07-31' AS DATE)";
			condition2 = " and date >= CAST('"+year+"-08-01' AS DATE) and date<= CAST('"+year+"-08-31' AS DATE)";
			condition3 = " and date >= CAST('"+year+"-09-01' AS DATE) and date<= CAST('"+year+"-09-31' AS DATE)";
		}
		else
		{
			//calculating each month's dates separately
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
	/**
	 * calculates store's income for a specific quarter in a given year
	 * @param year for what year to generate the report
	 * @param quarter the year quarter  
	 * @param store year for what year to generate the report
	 * @return	the income for said store, year and quarter
	 */
	public long calculateIncomeAmount(String year, Quarterly quarter, Integer store)
	{
		long income = 0;
		String condition;
		//setting the dates based on the needed quarter:
		if(quarter == report.IncomeReport.Quarterly.FIRST) 
			condition = " and OrderCreationDateTime >= CAST('"+year+"-01-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-03-31' AS DATE)";
		else if(quarter == report.IncomeReport.Quarterly.SECOND)
			condition = " and OrderCreationDateTime >= CAST('"+year+"-04-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-06-31' AS DATE)";
		else if(quarter == report.IncomeReport.Quarterly.THIRD)
			condition = " and OrderCreationDateTime >= CAST('"+year+"-07-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-09-31' AS DATE)";
		else
			condition = " and OrderCreationDateTime >= CAST('"+year+"-10-01' AS DATE) and OrderCreationDateTime<= CAST('"+year+"-12-31' AS DATE)";

		ResultSet rs = conn.selectTableData("*", "Order", "OrderOriginStore ="+store + condition);
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
	 * 						calculates the report data for a specific year, store and quarter
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
		//setting the dates based on the needed quarter:
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
			//saving all of the results in our data base to later divide them by the number of results
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
		//calculating averages for each answer:
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
	 * 						calculates the order report data for a specific store, year and quarter
	 * @param year			for what year to generate the report
	 * @param quarter		the year quarter 
	 * @param storeID		for what store to generate the report
	 * @return				orderData(0) - total amount of orders in said quarter
	 * 						orderData(1) - bouquets ordered
	 * 						orderData(2) - bridal bouquets ordered
	 * 						orderData(3) - flower pots ordered
	 * 						orderData(4) - flowers ordered
	 * 						orderData(5) - custom ordered
	 */
	public ArrayList<Integer> calculateOrderData(String year, Quarterly quarter, int storeID)
	{
		ArrayList<Integer> orderData =  new ArrayList<Integer>();
		ArrayList<Integer> orderInStore = new ArrayList<Integer>();
		//initialization:
		int totalOrdersNum = 0;
		int numBouquetsOrdered  = 0;
		int numBridalOrdered  = 0;
		int numFlowerPotsOrdered = 0;
		int numFlowersClusterOrdered = 0;
		int numCustomOrdered = 0;

		String condition;
		//setting the dates based on the needed quarter:
		if(quarter == report.OrderReport.Quarterly.FIRST) 
			condition = "OrderCreationDateTime >= CAST('"+year+"-01-01' AS DATE) AND OrderCreationDateTime<= CAST('"+year+"-03-31' AS DATE)";
		else if(quarter == report.OrderReport.Quarterly.SECOND)
			condition = "OrderCreationDateTime >= CAST('"+year+"-04-01' AS DATE) AND OrderCreationDateTime<= CAST('"+year+"-06-31' AS DATE)";
		else if(quarter == report.OrderReport.Quarterly.THIRD)
			condition = "OrderCreationDateTime >= CAST('"+year+"-07-01' AS DATE) AND OrderCreationDateTime<= CAST('"+year+"-09-31' AS DATE)";
		else
			condition = "OrderCreationDateTime >= CAST('"+year+"-10-01' AS DATE) AND OrderCreationDateTime<= CAST('"+year+"-12-31' AS DATE)";

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
			ArrayList<String> joinedTableKeyName = conn.getTableKeyName("ProductInOrder");
			// make the join on the primary key between the tables who should be the same for this to work
			// condition  = <table>.<tableKey> = <joinedTable>.<joinedTableKey>;
			condition = "Product"+"."+tableKeyName.get(0)+"="
					+"ProductInOrder"+"."+joinedTableKeyName.get(0) +" AND " + condition;
			ResultSet productRS = conn.selectJoinTablesData("*", "Product", "ProductInOrder", condition);

			try 
			{
				while (productRS.next())
				{
					String productType = productRS.getString("ProductType").toLowerCase();

					System.out.println(productType);
					switch(productType)
					{

					case "bouquet":
						numBouquetsOrdered++;
						break;

					case "bridal bouquet":
						numBridalOrdered++;
						break;

					case "flower pot":
						numFlowerPotsOrdered++;
						break;
						
					case "flowers cluster":
						numFlowersClusterOrdered++;
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
		//getting the data ready for return
		orderData.add(totalOrdersNum);
		orderData.add(numBouquetsOrdered);
		orderData.add(numBridalOrdered);
		orderData.add(numFlowerPotsOrdered);
		orderData.add(numCustomOrdered);
		orderData.add(numFlowersClusterOrdered);

		return orderData;
	}
}
