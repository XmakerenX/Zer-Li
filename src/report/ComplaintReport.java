package report;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class includes attributes that are needed for complaints' report at the end of quarterly
 */

public class ComplaintReport extends Report{
		
	private long firstMonthHandledComplaintsAmount;
	private long firstMonthPendingComplaintsAmount;
	private long secondMonthHandledComplaintsAmount;
	private long secondMonthPendingComplaintsAmount;
	private long thirdMonthHandledComplaintsAmount;
	private long thirdMonthPendingComplaintsAmount;

	public ComplaintReport(Quarterly quarterly, String year, long storeID, long firstMonthHandled, long firstMonthPending,
			long secondMonthHandled, long secondMonthPending, long thirdMonthHandled, long thirdMonthPending) throws ReportException
	{
		super(quarterly, year, storeID);
		this.firstMonthHandledComplaintsAmount = firstMonthHandled;
		this.firstMonthPendingComplaintsAmount = firstMonthPending;
		this.secondMonthHandledComplaintsAmount = secondMonthHandled;
		this.secondMonthPendingComplaintsAmount = secondMonthPending;
		this.thirdMonthHandledComplaintsAmount = thirdMonthHandled;
		this.thirdMonthPendingComplaintsAmount = thirdMonthPending;
		
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
