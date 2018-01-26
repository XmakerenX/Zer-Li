package survey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
/**
 * a class that we use for customer satisfaction survey results
 * @author dk198
 *
 */
public class CustomerSatisfactionSurveyResults implements Serializable {

	//class variables:
	private static final long serialVersionUID = 11L;
	private int ID;
	private LocalDate date;
	private int answers[] = new int[6];
	private int storeID;
	private String analysis = null;
	
	/**
	 * a constructor without local date, used for creating new results BEFORE sending them to the data base
	 * @param answer	array with answers to the survey
	 * @param storeID	the id of the store the answers are for 
	 */
	public CustomerSatisfactionSurveyResults(int[] answers, int storeID) {
		setDate();
		setAnswers(answers);
		setStoreID(storeID);
		
	}
	/**
	 * a constructor with local date, used for getting the data from the data base
	 * @param answers 	answers array with answers to the survey
	 * @param date		date of the survey
	 * @param storeID	the id of the store the answers are for 
	 */
	public CustomerSatisfactionSurveyResults(int id, int[] answers, LocalDate date, int storeID, String analysis) {
		setID(id);
		setDateGivenDate(date);
		setAnswers(answers);
		setStoreID(storeID);
		setAnalysis(analysis);
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

	public String getAnalysis()
	{
		return this.analysis;
	}
	
	public void setAnalysis(String analysis)
	{
		this.analysis = analysis;
	}
	
	public int getID()
	{
		return this.ID;
	}
	
	public void setID(int id)
	{
		this.ID = id;
	}

	public void setStoreID(int id)
	{
		this.storeID=id;
	}
	
	public int getStoreID()
	{
		return this.storeID;
	}
	
	public String toString()
	{
		return "Result num: " + this.getID() + ", " +this.getAnswers()[0]+","+this.getAnswers()[1]+","+this.getAnswers()[2]+","+this.getAnswers()[3]+","+this.getAnswers()[4]+","+this.getAnswers()[5]+ "," +this.getDate();
	}

}
