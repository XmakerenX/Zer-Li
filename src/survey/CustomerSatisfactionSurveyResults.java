package survey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class CustomerSatisfactionSurveyResults implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	private String ofSurvey;
	private LocalDate date;
	private int answers[] = new int[6];
	
	/**
	 * a constructor without local date, used for creating new results BEFORE sending them to the data base
	 * @param resultsOfSurvey name of the survey the results are for
	 * @param answers array with answers to the survey
	 */
	public CustomerSatisfactionSurveyResults(String resultsOfSurvey, int[] answers) {
		setOfSurvey(resultsOfSurvey);
		setDate();
		setAnswers(answers);
		
	}
	/**
	 * a constructor with local date, used for getting the data from the data base
	 * @param resultsOfSurvey name of the survey the results are for
	 * @param answers answers array with answers to the survey
	 * @param date date of the survey
	 */
	public CustomerSatisfactionSurveyResults(String resultsOfSurvey, int[] answers, LocalDate date) {
		setOfSurvey(resultsOfSurvey);
		setDateGivenDate(date);
		setAnswers(answers);
		
	}


	public LocalDate getDate() {
		return date;
	}


	public void setDate() 
	{
		LocalDate todayLocalDate = LocalDate.now();
		this.date = todayLocalDate;
	}
	
	public void setDateGivenDate(LocalDate date) 
	{
		this.date = date;
	}


	public int[] getAnswers() {
		return answers;
	}


	public void setAnswers(int[] answers) {
		this.answers = answers;
	}


	public String getOfSurvey() {
		return ofSurvey;
	}


	public void setOfSurvey(String ofSurvey) {
		this.ofSurvey = ofSurvey;
	}
	
	public String toString()
	{
		return "Results of survey: " + this.getOfSurvey()+" results: "+this.getAnswers()[0]+","+this.getAnswers()[1]+","+this.getAnswers()[2]+","+this.getAnswers()[3]+","+this.getAnswers()[4]+","+this.getAnswers()[5]+ "," +this.getDate();
	}

}
