package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import survey.SurveyAnalysisGUI;
import survey.SurveyCreationGUI;
import survey.SurveyExplorerGUI;
import user.LoginGUI;
import user.User;
import user.UserController;

public class CustomerServiceExpertGUI extends FormController implements ClientInterface{

	SurveyAnalysisGUI surveyAnalysisGUI;	
	
	//Current user's name
	private User user;
	
	
    @FXML // fx:id="LogOutBtn"
    private Button LogOutBtn; // Value injected by FXMLLoader

    @FXML // fx:id="analyzeBtn"
    private Button analyzeBtn; // Value injected by FXMLLoader
  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	surveyAnalysisGUI = FormController.<SurveyAnalysisGUI, AnchorPane>loadFXML(getClass().getResource("/survey/SurveyAnalysisGUI.fxml"), this);
    }
  //===============================================================================================================
    @FXML
    void onLogOut(ActionEvent event) {

    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }
  //===============================================================================================================
    @FXML
    void onAnalyze(ActionEvent event) {

		client.setUI(surveyAnalysisGUI);
		surveyAnalysisGUI.setClinet(client);
		//---------------------....................................................
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
