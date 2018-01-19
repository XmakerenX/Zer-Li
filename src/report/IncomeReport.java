package report;

import java.io.Serializable;

/**
 * This class includes attributes that are needed for income report at the end
 * of quarterly
 */

public class IncomeReport implements Serializable {

	public class ReportException extends Exception {

		public ReportException(String message) {
			super(message);
		}
	}

	public enum Quarterly {
		FIRST/* Months: 1-3 */, SECOND/* Months: 4-6 */, THIRD/* Months: 7-9 */, FOURTH
		/* Months: 10-12 */};

	private Quarterly quarterly;
	private String year;
	private long storeID;
	private float incomeAmount;

	public IncomeReport(Quarterly quarterly, String year, long storeID, float incomeAmount) throws ReportException {
		
		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
		this.incomeAmount = incomeAmount;
		
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

	public void setYear(String year) throws ReportException {
		if (Integer.parseInt(year) > 0)
			this.year = year;
		else
			throw new ReportException("Entered year is invalid!");
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) throws ReportException {
		if(storeID > 0)
			this.storeID = storeID;
		else
			throw new ReportException("Entered store's ID is invalid!");
	}

	public float getIncomeAmount() {
		return incomeAmount;
	}

	public void setIncomeAmount(float incomeAmount) throws ReportException{
		if(incomeAmount > 0)
			this.incomeAmount = incomeAmount;
		else
			throw new ReportException("Entered income's amount is invalid!");
	}

}
