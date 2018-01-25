package report;

import java.io.Serializable;

/**
 * This class includes attributes that are needed for surveys' report at the end
 * of quarterly
 */

public class SurveyReport extends Report {

	private long firstSurveyAverageResult;
	private long secondSurveyAverageResult;
	private long thirdSurveyAverageResult;
	private long fourthSurveyAverageResult;
	private long fifthSurveyAverageResult;
	private long sixthSurveyAverageResult;

	
	public SurveyReport(Quarterly quarterly, String year, long storeID, long firstSurveyAverageResult,
			long secondSurveyAverageResult, long thirdSurveyAverageResult, long fourthSurveyAverageResult,
			long fifthSurveyAverageResult, long sixthSurveyAverageResult) throws ReportException
	{
		super(quarterly, year, storeID);
		this.firstSurveyAverageResult = firstSurveyAverageResult;
		this.secondSurveyAverageResult = secondSurveyAverageResult;
		this.thirdSurveyAverageResult = thirdSurveyAverageResult;
		this.fourthSurveyAverageResult = fourthSurveyAverageResult;
		this.fifthSurveyAverageResult = fifthSurveyAverageResult;
		this.sixthSurveyAverageResult = sixthSurveyAverageResult;
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
