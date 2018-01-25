package report;

import java.io.Serializable;

public class Report implements Serializable {

	public class ReportException extends Exception {

		public ReportException(String message) {
			super(message);
		}
	}
	
	public enum Quarterly {FIRST/* Months: 1-3 */, SECOND/* Months: 4-6 */, THIRD/* Months: 7-9 */, FOURTH/* Months: 10-12 */};
	
	private static final long serialVersionUID = 3605408565986533948L;
	private Quarterly quarterly;
	private String year;
	private long storeID;
	
	public Report(Quarterly quarterly, String year, long storeID)
	{
		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
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
	
	
}
