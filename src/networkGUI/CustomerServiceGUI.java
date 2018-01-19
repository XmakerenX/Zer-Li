package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import survey.ResultInputGUI;
import survey.SurveyCreationGUI;
import survey.SurveyExplorerGUI;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;

//*************************************************************************************************
	/**
	*  Provides a gui to handle  customer service actions: (create new survey) 
	*/
//*************************************************************************************************
public class CustomerServiceGUI extends FormController implements ClientInterface{

	SurveyCreationGUI surveyCreationGUI;
	SurveyExplorerGUI surveyExplorer;
	
	//Current user's name
	private User user;
	
    @FXML // fx:id="surveylistBtn"
    private Button surveylistBtn; // Value injected by FXMLLoader

    @FXML // fx:id="newSurveyBtn"
    private Button newSurveyBtn; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	surveyCreationGUI = FormController.<SurveyCreationGUI, AnchorPane>loadFXML(getClass().getResource("/survey/SurveyCreationGUI.fxml"), this);
    	surveyExplorer = FormController.<SurveyExplorerGUI, AnchorPane>loadFXML(getClass().getResource("/survey/SurveyExplorerGUI.fxml"), this);
    }
  //===============================================================================================================
    @FXML
    void onNewSurvey(ActionEvent event) {
    	if ( surveyCreationGUI != null)
		{
    		client.setUI(surveyCreationGUI);
			surveyCreationGUI.setClinet(client);
			FormController.primaryStage.setScene(surveyCreationGUI.getScene());
		}
    }
  //===============================================================================================================
 
    @FXML
    void onSurveyList(ActionEvent event) {
    	if ( surveyExplorer != null)
		{
    		client.setUI(surveyExplorer);
    		surveyExplorer.setClinet(client);
    		surveyExplorer.initComboBox();
			FormController.primaryStage.setScene(surveyExplorer.getScene());
		}
    }
  //===============================================================================================================
    @FXML
    void onBack(ActionEvent event) {
    	
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