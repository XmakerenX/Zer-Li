package report;

import java.io.Serializable;
import java.util.HashMap;

import report.OrderReport.Quarterly;
import report.OrderReport.ReportException;

/**
 * This class includes attributes that are needed for complaints' report at the end of quarterly
 */

public class ComplaintReport implements Serializable{
	
	public class ReportException extends Exception {

		public ReportException(String message) {
			super(message);
		}
	}
	
	public enum Quarterly {FIRST/* Months: 1-3 */, SECOND/* Months: 4-6 */, THIRD/* Months: 7-9 */, FOURTH/* Months: 10-12 */};
	
	private Quarterly quarterly;
	private String year;
	private long storeID;
	private long firstMonthHandledComplaintsAmount;
	private long firstMonthPendingComplaintsAmount;
	private long secondMonthHandledComplaintsAmount;
	private long secondMonthPendingComplaintsAmount;
	private long thirdMonthHandledComplaintsAmount;
	private long thirdMonthPendingComplaintsAmount;

	public ComplaintReport(Quarterly quarterly, String year, long storeID, long firstMonthHandled, long firstMonthPending,
			long secondMonthHandled, long secondMonthPending, long thirdMonthHandled, long thirdMonthPending) {
		
		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
		this.firstMonthHandledComplaintsAmount = firstMonthHandled;
		this.firstMonthPendingComplaintsAmount = firstMonthPending;
		this.secondMonthHandledComplaintsAmount = secondMonthHandled;
		this.secondMonthPendingComplaintsAmount = secondMonthPending;
		this.thirdMonthHandledComplaintsAmount = thirdMonthHandled;
		this.thirdMonthPendingComplaintsAmount = thirdMonthPending;
		
	}
	
	public Quarterly getQuarterly() {
		return quarterly;
	}
	
	public void setQuarterly(Quarterly quarterly) {
		this.quarterly = quarterly;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) throws ReportException{
		if (Integer.parseInt(year) > 0)
			this.year = year;
		else
			throw new ReportException("Entered year is invalid!");
	}
	
	public long getStoreID() {
		return storeID;
	}
	
	public void setStoreID(long storeID) throws ReportException{
		if(storeID > 0)
			this.storeID = storeID;
		else
			throw new ReportException("Entered store's ID is invalid!");
	}
	
	public long getFirstMonthHandledComplaintsAmount() {
		return firstMonthHandledComplaintsAmount;
	}
	
	public void setFirstMonthHandledComplaintsAmount(long firstMonthHandledComplaintsAmount) throws ReportException{
		if(firstMonthHandledComplaintsAmount > 0)
			this.firstMonthHandledComplaintsAmount = firstMonthHandledComplaintsAmount;
		else
			throw new ReportException("Entered handled complaints' amount is invalid!");
	}
	
	public long getFirstMonthPendingComplaintsAmount() {
		return firstMonthPendingComplaintsAmount;
	}
	
	public void setFirstMonthPendingComplaintsAmount(long firstMonthPendingComplaintsAmount) throws ReportException{
		if(firstMonthPendingComplaintsAmount > 0)
			this.firstMonthPendingComplaintsAmount = firstMonthPendingComplaintsAmount;
		else
			throw new ReportException("Entered pending complaints' amount is invalid!");
	}
	
	public long getSecondMonthHandledComplaintsAmount() {
		return secondMonthHandledComplaintsAmount;
	}
	
	public void setSecondMonthHandledComplaintsAmount(long secondMonthHandledComplaintsAmount) throws ReportException{
		if(secondMonthHandledComplaintsAmount > 0)
			this.secondMonthHandledComplaintsAmount = secondMonthHandledComplaintsAmount;
		else
			throw new ReportException("Entered handled complaints' amount is invalid!");
	}
	
	public long getSecondMonthPendingComplaintsAmount() {
		return secondMonthPendingComplaintsAmount;
	}
	
	public void setSecondMonthPendingComplaintsAmount(long secondMonthPendingComplaintsAmount) throws ReportException{
		if(secondMonthPendingComplaintsAmount > 0)
			this.secondMonthPendingComplaintsAmount = secondMonthPendingComplaintsAmount;
		else
			throw new ReportException("Entered pending complaints' amount is invalid!");
	}
	
	public long getThirdMonthHandledComplaintsAmount() {
		return thirdMonthHandledComplaintsAmount;
	}
	
	public void setThirdMonthHandledComplaintsAmount(long thirdMonthHandledComplaintsAmount) throws ReportException{
		if(thirdMonthHandledComplaintsAmount > 0)
			this.thirdMonthHandledComplaintsAmount = thirdMonthHandledComplaintsAmount;
		else
			throw new ReportException("Entered handled complaints' amount is invalid!");
	}
	
	public long getThirdMonthPendingComplaintsAmount() {
		return thirdMonthPendingComplaintsAmount;
	}
	
	public void setThirdMonthPendingComplaintsAmount(long thirdMonthPendingComplaintsAmount) throws ReportException{
		if(thirdMonthPendingComplaintsAmount > 0)
			this.thirdMonthPendingComplaintsAmount = thirdMonthPendingComplaintsAmount;
		else
			throw new ReportException("Entered pending complaints' amount is invalid!");
	}
}
