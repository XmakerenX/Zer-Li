package survey;

import java.util.ArrayList;

import javafx.event.ActionEvent;

/**
 * Sample Skeleton for 'SurveyCreationGUI.fxml' Controller Class
 */

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SurveyCreationGUI {

    @FXML // fx:id="surveyNameTxtFld"
    private TextField surveyNameTxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question3TxtFld"
    private TextField question3TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question6TxtFld"
    private TextField question6TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question4TxtFld"
    private TextField question4TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question1TxtFld"
    private TextField question1TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question5TxtFld"
    private TextField question5TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question8TxtFld"
    private TextField question8TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="createBtn"
    private Button createBtn; // Value injected by FXMLLoader

    @FXML // fx:id="question2TxtFld"
    private TextField question2TxtFld; // Value injected by FXMLLoader

    @FXML // fx:id="question7TxtFld"
    private TextField question7TxtFld; // Value injected by FXMLLoader

    @FXML
    void closeWindow(ActionEvent event) {

    }

    @FXML
    void createSurvey(ActionEvent event) {
    	ArrayList<String> questions = null;
    	String surveyName = surveyNameTxtFld.getText();
    	questions.add(question1TxtFld.getText());
    	questions.add(question2TxtFld.getText());
    	questions.add(question3TxtFld.getText());
    	questions.add(question4TxtFld.getText());
    	questions.add(question5TxtFld.getText());
    	questions.add(question6TxtFld.getText());
    	questions.add(question7TxtFld.getText());
    	questions.add(question8TxtFld.getText());
    	if(surveyName==null)
    	{
    		
    	}
    	if((questions.get(0) || questions.get(1) || questions.get(2)||questions.get(3) )==null)
    	{
    		
    		
    	}

    }

}
