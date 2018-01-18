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
	private long firstSurveyAverageResult;
	private long secondSurveyAverageResult;
	private long thirdSurveyAverageResult;
	private long fourthSurveyAverageResult;
	private long fifthSurveyAverageResult;
	private long sixthSurveyAverageResult;

	
	public SurveyReport(Quarterly quarterly, String year, long storeID, long firstSurveyAverageResult,
			long secondSurveyAverageResult, long thirdSurveyAverageResult, long fourthSurveyAverageResult,
			long fifthSurveyAverageResult, long sixthSurveyAverageResult) throws ReportException{
		super();
		this.quarterly = quarterly;
		this.year = year;
		this.storeID = storeID;
		this.firstSurveyAverageResult = firstSurveyAverageResult;
		this.secondSurveyAverageResult = secondSurveyAverageResult;
		this.thirdSurveyAverageResult = thirdSurveyAverageResult;
		this.fourthSurveyAverageResult = fourthSurveyAverageResult;
		this.fifthSurveyAverageResult = fifthSurveyAverageResult;
		this.sixthSurveyAverageResult = sixthSurveyAverageResult;
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


	public void setYear(String year) {
		this.year = year;
	}


	public long getStoreID() {
		return storeID;
	}


	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}


	public long getFirstSurveyAverageResult() {
		return firstSurveyAverageResult;
	}


	public void setFirstSurveyAverageResult(long firstSurveyAverageResult) {
		this.firstSurveyAverageResult = firstSurveyAverageResult;
	}


	public long getSecondSurveyAverageResult() {
		return secondSurveyAverageResult;
	}


	public void setSecondSurveyAverageResult(long secondSurveyAverageResult) {
		this.secondSurveyAverageResult = secondSurveyAverageResult;
	}


	public long getThirdSurveyAverageResult() {
		return thirdSurveyAverageResult;
	}


	public void setThirdSurveyAverageResult(long thirdSurveyAverageResult) {
		this.thirdSurveyAverageResult = thirdSurveyAverageResult;
	}


	public long getFourthSurveyAverageResult() {
		return fourthSurveyAverageResult;
	}


	public void setFourthSurveyAverageResult(long fourthSurveyAverageResult) {
		this.fourthSurveyAverageResult = fourthSurveyAverageResult;
	}


	public long getFifthSurveyAverageResult() {
		return fifthSurveyAverageResult;
	}


	public void setFifthSurveyAverageResult(long fifthSurveyAverageResult) {
		this.fifthSurveyAverageResult = fifthSurveyAverageResult;
	}


	public long getSixthSurveyAverageResult() {
		return sixthSurveyAverageResult;
	}


	public void setSixthSurveyAverageResult(long sixthSurveyAverageResult) {
		this.sixthSurveyAverageResult = sixthSurveyAverageResult;
	}

}
