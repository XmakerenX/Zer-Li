package survey;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerSatisfactionSurveyResults {
	private String date;
	private int answers[] = new int[8];
	
	
	public CustomerSatisfactionSurveyResults(int[] answers) {
		setDate();
		setAnswers(answers);
		
	}


	public String getDate() {
		return date;
	}


	public void setDate() 
	{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		this.date = timeStamp;
	}


	public int[] getAnswers() {
		return answers;
	}


	public void setAnswers(int[] answers) {
		this.answers = answers;
	}

}
