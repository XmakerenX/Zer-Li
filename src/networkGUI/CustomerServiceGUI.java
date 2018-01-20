package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import survey.AddSurveyAnalysisToExistingSurveyGUI;
import survey.ResultInputGUI;
import survey.SurveyCreationGUI;
import survey.SurveyExplorerGUI;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;

//*************************************************************************************************
	/**
	*  Provides a gui to handle  customer service actions: (input survey analysis) 
	*/
//*************************************************************************************************
public class CustomerServiceGUI extends FormController implements ClientInterface{

	private AddSurveyAnalysisToExistingSurveyGUI addSurveyAnalysisToExistingSurveyGUI;
	
	//Current user's name
	private User user;
	
	  @FXML
	    private Button inputSurveyAnalysisButton;

	    @FXML
	    private Button logOutButton;

  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	addSurveyAnalysisToExistingSurveyGUI = FormController.<AddSurveyAnalysisToExistingSurveyGUI, AnchorPane>loadFXML(getClass().getResource("/survey/AddSurveyAnalysisToExistingSurveyGUI.fxml"), this);
    }
  //===============================================================================================================
 
    @FXML
    void onInputSurveyAnalysisButton(ActionEvent event) 
    {
    	if ( addSurveyAnalysisToExistingSurveyGUI != null)
		{
	    	addSurveyAnalysisToExistingSurveyGUI.setClinet(client);
			client.setUI(addSurveyAnalysisToExistingSurveyGUI);
			addSurveyAnalysisToExistingSurveyGUI.setUser(user);
			FormController.primaryStage.setScene(addSurveyAnalysisToExistingSurveyGUI.getScene());
		}
    }
  //===============================================================================================================
    @FXML
    void onLogOutButton(ActionEvent event) 
    {
    	
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
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
	//===============================================================================================================
		public void setUser(User user)
		{
			this.user = user;
		}

}