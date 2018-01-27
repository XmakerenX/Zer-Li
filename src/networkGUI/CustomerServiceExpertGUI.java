package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import survey.SurveyAnalysisGUI;
import user.LoginGUI;
import user.User;
import user.UserController;
import utils.FormController;

//*************************************************************************************************
	/**
	*  Provides a GUI to handle customer service expert actions : Analyzing the surveys results
	*/
//*************************************************************************************************
public class CustomerServiceExpertGUI extends FormController implements ClientInterface{

	SurveyAnalysisGUI surveyAnalysisGUI;	
	
	//Current user's name
	private User user;
	
	
    @FXML // fx:id="LogOutBtn"
    private Button LogOutBtn; 

    @FXML // fx:id="analyzeBtn"
    private Button analyzeBtn;
  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	surveyAnalysisGUI = FormController.<SurveyAnalysisGUI, AnchorPane>loadFXML(getClass().getResource("/survey/SurveyAnalysisGUI.fxml"), this);
    }
  //===============================================================================================================
    /**
     * Logs current user out and returns to LogInGUI
     * @param event - "Log out" button is clicked
     */
    @FXML
    void onLogOut(ActionEvent event) {

    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }
  //===============================================================================================================
    /**
     * Opens window which allows to view surveys' results
     * @param event - "Review survey results" button is clicked
     */
    @FXML
    void onAnalyze(ActionEvent event) {

		client.setUI(surveyAnalysisGUI);
		surveyAnalysisGUI.setClinet(client);
		surveyAnalysisGUI.initResults();
		FormController.primaryStage.setScene(surveyAnalysisGUI.getScene());
    }
    
    //===============================================================================================================
	public void setUser(User user)
	{
		this.user = user;
	}
	//===============================================================================================================
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}
	//===============================================================================================================
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}

}
