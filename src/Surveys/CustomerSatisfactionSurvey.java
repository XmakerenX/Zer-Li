package Surveys;

import java.util.ArrayList;

public class CustomerSatisfactionSurvey {
	private String surveyName;
	private String[] surveyQuestions;
	private ArrayList<CustomerSatisfactionSurveyResults> surveyResults;
	private String surveyAnalysis;
	
	public CustomerSatisfactionSurvey(String name, String[] questions) throws SurveyException
	{
		try 
		{
			setSurveyName(name);
			setSurveyQuestions(questions);
			setSurveyResults(null);
		}
		catch(SurveyException exception) 
		{
			throw exception;
		}
		
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		if()				//------------------------------check whether there's one in the data base already
		{
			this.surveyName = surveyName;
		}
		else
		{
			throw new SurveyException("Survey with the same name already exists");
		}
	}

	public String[] getSurveyQuestions() {
		return surveyQuestions;
	}

	public void setSurveyQuestions(String[] surveyQuestions) throws SurveyException {
		if(surveyQuestions.length!=6)
		{
			for(int i=0; i<6; i++)
			{
				this.surveyQuestions[i]=surveyQuestions[i];
			}
		}
		else
		{
			throw new SurveyException("You must input 6 questions to create a survey.");
		}
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
