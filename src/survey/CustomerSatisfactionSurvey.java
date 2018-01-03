package survey;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomerSatisfactionSurvey implements Serializable{


	private static final long serialVersionUID = 12L;
	
	
	private String surveyName;
	private String[] surveyQuestions;
	private ArrayList<CustomerSatisfactionSurveyResults> surveyResults;
	private String surveyAnalysis;
	
	public CustomerSatisfactionSurvey(String name, String[] questions)
	{

		setSurveyName(name);
		setSurveyQuestions(questions);
		setSurveyResults(null);
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {

			this.surveyName = surveyName;

	}

	public String[] getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(String[] surveyQuestions)   {
		this.surveyQuestions = surveyQuestions;
	}

	public ArrayList<CustomerSatisfactionSurveyResults> getResults() {
		return surveyResults;
	}

	public void setSurveyResults(ArrayList<CustomerSatisfactionSurveyResults> results) {
		this.surveyResults = results;
	}

	public String getSurveyAnalysis() {
		return surveyAnalysis;
	}

	public void setSurveyAnalysis(String surveyAnalysis) {
		this.surveyAnalysis = surveyAnalysis;
	}

}
