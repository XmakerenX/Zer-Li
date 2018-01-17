package report;

import java.util.ArrayList;

import client.Client;
import report.ComplaintReport.Quarterly;
import serverAPI.GetRequestByKey;

/**
 * This class includes functionality of four entities: ComplaintReport, IncomeReport, OrderReport and SurveyReport
 */

public class ReportController {
	
	/**
	 * Create new complaints' report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param firstMonthHandled - amount of handled complaints in first month
	 * @param firstMonthPending - amount of pending complaints in first month
	 * @param secondMonthHandled - amount of handled complaints in second month
	 * @param secondMonthPending - amount of pending complaints in second month
	 * @param thirdMonthHandled - amount of handled complaints in third month
	 * @param thirdMonthPending - amount of pending complaints in third month
	 * @param client - currently running client
	 */
	public static void createNewComplaintReport(Quarterly quarterly, String year, long storeID, long firstMonthHandled, long firstMonthPending,
			long secondMonthHandled, long secondMonthPending, long thirdMonthHandled, long thirdMonthPending, Client client)
	{
		
	}
	
	/**
	 * Create new income's report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param incomeAmount - amount of income in specific quarterly
	 * @param client - currently running client
	 */
	public static void createNewIncomeReport(Quarterly quarterly, String year, long storeID, float incomeAmount, Client client)
	{
		
	}
	
	/**
	 * Create new orders' report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param totalOrdersAmount - total amount of ordered items of all types
	 * @param bouquetAmount - amount of ordered bouquets 
	 * @param brideBouquetAmount - amount of ordered bride bouquets 
	 * @param flowerPotAmount - amount of ordered flower pots 
	 * @param flowerAmount - amount of ordered flowers
	 * @param plantAmount - amount of ordered plants 
	 * @param client - currently running client
	 */
	public static void createNewOrderReport(Quarterly quarterly, String year, long storeID, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long flowerAmount, long plantAmount, Client client)
	{
		
	}
	
	/**
	 * Create new surveys' report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param surveyAverageResults - average results of every survey's question
	 * @param client - currently running client
	 */
	public static void createNewSurveyReport(Quarterly quarterly, String year, long storeID, float[] surveyAverageResults, Client client)
	{
		
	}
	
	/**
	 * Get specific report from data base
	 * @param specificReport - specific report's name to access correct table
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param client - currently running client
	 */
	public static void getReport(String specificReport, Quarterly quarterly, String year, long storeID, Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(""+quarterly);
		keys.add(""+year);
		keys.add(""+storeID);
		
		client.handleMessageFromClientUI(new GetRequestByKey(specificReport, keys));
	}
}
