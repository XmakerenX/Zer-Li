package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import survey.AddSurveyAnalysisToExistingSurveyGUI;
import user.LoginGUI;
import user.User;
import user.UserController;
import utils.FormController;

//*************************************************************************************************
	/**
	*  Provides a GUI to handle customer service actions: (input survey analysis) 
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
 
    /**
     * Opens window which allows to enter surveys' analysis
     * @param event - "Input survey analysis" button is clicked
     */
    @FXML
    void onInputSurveyAnalysisButton(ActionEvent event) 
    {
    	if ( addSurveyAnalysisToExistingSurveyGUI != null)
		{
    		Client.getInstance().setUI(addSurveyAnalysisToExistingSurveyGUI);
			addSurveyAnalysisToExistingSurveyGUI.setUser(user);
			FormController.primaryStage.setScene(addSurveyAnalysisToExistingSurveyGUI.getScene());
		}
    }
  //===============================================================================================================
    /**
     * Logs current user out and returns to LogInGUI
     * @param event - "Log out" button is clicked
     */
    @FXML
    void onLogOutButton(ActionEvent event) 
    {
    	
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, Client.getInstance());
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	Client.getInstance().setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    	
    }
  //===============================================================================================================
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}
	//===============================================================================================================
		public void setUser(User user)
		{
			this.user = user;
		}

}