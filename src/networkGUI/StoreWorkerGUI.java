package networkGUI;

import client.Client;
import client.ClientInterface;
import prototype.FormController;
import survey.AddSurveyAnalysisToExistingSurveyGUI;
import survey.ResultInputGUI;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

//*************************************************************************************************
	/**
	*  Provides a gui to handle store worker actions : input surveys results
	*/
//*************************************************************************************************

public class StoreWorkerGUI extends FormController implements ClientInterface{
	
	//Current user's name
	private User user;
	
	private ResultInputGUI resultInputGUI;
	private AddSurveyAnalysisToExistingSurveyGUI addSurveyAnalysisToExistingSurveyGUI;
	
    @FXML
    private Button inputSurveyAnalysis;
    
    @FXML // fx:id="inputSurveyBtn"
    private Button inputSurveyBtn; // Value injected by FXMLLoader

    @FXML // fx:id="logOutBtn"
    private Button logOutBtn; // Value injected by FXMLLoader
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
	//===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	addSurveyAnalysisToExistingSurveyGUI = FormController.<AddSurveyAnalysisToExistingSurveyGUI, AnchorPane>loadFXML(getClass().getResource("/survey/AddSurveyAnalysisToExistingSurveyGUI.fxml"), this);
    	resultInputGUI = FormController.<ResultInputGUI, AnchorPane>loadFXML(getClass().getResource("/survey/ResultInputGUI.fxml"), this);
    }
  //===============================================================================================================
    @FXML
    void onInputSurvey(ActionEvent event) {
    	if ( resultInputGUI != null)
		{
    		resultInputGUI.setClinet(client);
			client.setUI(resultInputGUI);
			resultInputGUI.initComboBox();
			resultInputGUI.setUser(user);
			FormController.primaryStage.setScene(resultInputGUI.getScene());
		}

    }
    //===============================================================================================================
    @FXML
    void onInputSurveyAnalysis(ActionEvent event) {
    	if ( resultInputGUI != null)
		{
	    	addSurveyAnalysisToExistingSurveyGUI.setClinet(client);
			client.setUI(addSurveyAnalysisToExistingSurveyGUI);
			addSurveyAnalysisToExistingSurveyGUI.setUser(user);
			FormController.primaryStage.setScene(addSurveyAnalysisToExistingSurveyGUI.getScene());
		}
    }
    
  //===============================================================================================================
    /**
     * Logs the user out of the system
     * @param event - "Log out" button is pressed
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
	public void setUser(User user)
	{
		this.user = user;
	}

}
