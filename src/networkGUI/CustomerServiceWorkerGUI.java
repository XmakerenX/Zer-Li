package networkGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import order.ComplaintCreationGUI;
import order.ComplaintManageGUI;
import prototype.FormController;
import survey.SurveyAnalysisGUI;
import survey.SurveyCreationGUI;
import survey.SurveyExplorerGUI;
import user.LoginGUI;
import user.User;
import user.UserController;

public class CustomerServiceWorkerGUI extends CustomerServiceGUI{

	ComplaintCreationGUI complaintCreationGUI;
	ComplaintManageGUI complaintManageGUI;
	private User user;
	
	
    @FXML // fx:id="logOutButton"
    private Button logOutButton; // Value injected by FXMLLoader

    @FXML // fx:id="addNewComplaintButton"
    private Button addNewComplaintButton; // Value injected by FXMLLoader

    @FXML // fx:id="manageComplaintsButton"
    private Button manageComplaintsButton; // Value injected by FXMLLoader

  //===============================================================================================================
    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	complaintCreationGUI = FormController.<ComplaintCreationGUI, AnchorPane>loadFXML(getClass().getResource("/order/ComplaintCreationGUI.fxml"), this);
    	complaintManageGUI = FormController.<ComplaintManageGUI, AnchorPane>loadFXML(getClass().getResource("/order/ComplaintManageGUI.fxml"), this);
    }
  //===============================================================================================================
    @FXML
    void OnAddNewComplaintButton(ActionEvent event) {
		client.setUI(complaintCreationGUI);
		complaintCreationGUI.setClinet(client);
		FormController.primaryStage.setScene(complaintCreationGUI.getScene());
    }
  //===============================================================================================================
    @FXML
    void onManageComplaintsButton(ActionEvent event) {
		client.setUI(complaintManageGUI);
		complaintManageGUI.setClinet(client);
		FormController.primaryStage.setScene(complaintManageGUI.getScene());
    }
  //===============================================================================================================
    @FXML
    void onLogOutButton(ActionEvent event) {
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
  	//===============================================================================================================

}