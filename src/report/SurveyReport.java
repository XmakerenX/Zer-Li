package report;

import java.io.Serializable;

import report.ComplaintReport.Quarterly;
import report.ComplaintReport.ReportException;

/**
 * This class includes attributes that are needed for surveys' report at the end
 * of quarterly
 */

public class SurveyReport implements Serializable {


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
	private float[] surveyAverageResults;

	public SurveyReport(Quarterly quarterly, String year, long storeID, float[] surveyAverageResults) {

		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
		this.surveyAverageResults = surveyAverageResults;
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

	public float[] getSurveyAverageResults() {
		return surveyAverageResults;
	}

	public void setSurveyAverageResults(float[] surveyAverageResults) throws ReportException{
		if(surveyAverageResults.length == 6)
			this.surveyAverageResults = surveyAverageResults;
		else
			throw new ReportException("The amount of average results is invalid!");
	}

}
