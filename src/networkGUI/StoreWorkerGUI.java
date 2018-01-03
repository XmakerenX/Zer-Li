package networkGUI;

import client.Client;
import client.ClientInterface;
import prototype.FormController;
import survey.ResultInputGUI;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class StoreWorkerGUI extends FormController implements ClientInterface{
	
	//Current user's name
	private User user;
	
	private ResultInputGUI resultInputGUI;
	
	
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
			FormController.primaryStage.setScene(resultInputGUI.getScene());
		}

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
	public void setUser(User user)
	{
		this.user = user;
	}

}
