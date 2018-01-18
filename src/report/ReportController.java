package report;

import java.util.ArrayList;

import client.Client;
import report.ComplaintReport.Quarterly;
import report.ComplaintReport.ReportException;
import serverAPI.AddRequest;
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
		ComplaintReport complaintReport;
		try {
			complaintReport = new ComplaintReport(quarterly, year, storeID, firstMonthHandled, firstMonthPending, secondMonthHandled,
					secondMonthPending, thirdMonthHandled, thirdMonthPending);
			client.handleMessageFromClientUI(new AddRequest("ComplaintReport", complaintReport));
		} catch (ComplaintReport.ReportException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Create new income's report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param incomeAmount - amount of income in specific quarterly
	 * @param client - currently running client
	 */
	public static void createNewIncomeReport(report.IncomeReport.Quarterly quarterly, String year, long storeID, float incomeAmount, Client client)
	{
		IncomeReport incomeReport;
		try {
			incomeReport = new IncomeReport(quarterly, year, storeID, incomeAmount);
			client.handleMessageFromClientUI(new AddRequest("IncomeReport", incomeReport));
		} catch (IncomeReport.ReportException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
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
	public static void createNewOrderReport(report.OrderReport.Quarterly quarterly, String year, long storeID, long totalOrdersAmount, long bouquetAmount,
			long brideBouquetAmount, long flowerPotAmount, long flowerAmount, long plantAmount, Client client)
	{
		OrderReport orderReport;
		try {
			orderReport = new OrderReport(quarterly, year, storeID, totalOrdersAmount, bouquetAmount, brideBouquetAmount, flowerPotAmount,
					flowerAmount, plantAmount);
			client.handleMessageFromClientUI(new AddRequest("OrderReport", orderReport));
		} catch (OrderReport.ReportException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Create new surveys' report and adds to data base
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param firstSurveyAverageResult - average result of the survey's first question
	 * @param secondSurveyAverageResult - average result of the survey's second question
	 * @param thirdSurveyAverageResult - average result of the survey's  third question
	 * @param fourthSurveyAverageResult - average result of the survey's fourth question
	 * @param fifthSurveyAverageResult - average result of the survey's fifth question
	 * @param sixthSurveyAverageResult - average result of the survey's sixth question
	 * @param client - currently running client
	 */
	public static void createNewSurveyReport(report.SurveyReport.Quarterly quarterly, String year, long storeID, long firstSurveyAverageResult, long secondSurveyAverageResult,
												long thirdSurveyAverageResult, long fourthSurveyAverageResult, long fifthSurveyAverageResult, 
												long sixthSurveyAverageResult, Client client)
	{
		SurveyReport surveyReport;
		try {
			surveyReport = new SurveyReport(quarterly, year, storeID, firstSurveyAverageResult, secondSurveyAverageResult, thirdSurveyAverageResult,
											 fourthSurveyAverageResult, fifthSurveyAverageResult, sixthSurveyAverageResult);
			client.handleMessageFromClientUI(new AddRequest("SurveyReport", surveyReport));
		} catch (SurveyReport.ReportException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Get specific report from data base
	 * @param specificReport - specific report's name to access correct table
	 * @param quarterly - specific quarterly
	 * @param year - specific year
	 * @param storeID - specific store's ID
	 * @param client - currently running client
	 */
	public static void getReport(String specificReport, IncomeReport.Quarterly quarterly, String year, long storeID, Client client)
	{
		ArrayList<String> keys = new ArrayList<String>();
		keys.add(""+quarterly);
		keys.add(""+year);
		keys.add(""+storeID);
		
		client.handleMessageFromClientUI(new GetRequestByKey(specificReport, keys));
	}
}
