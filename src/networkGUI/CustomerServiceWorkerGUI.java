package networkGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import client.Client;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import order.ComplaintCreationGUI;
import order.ComplaintManageGUI;
import user.LoginGUI;
import user.User;
import user.UserController;
import utils.FormController;
//*************************************************************************************************
	/**
	*  Provides a GUI to handle customer service worker actions: adding new complaint, managing complaints
	*/
//*************************************************************************************************
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
    /**
     * Opens menu which allows to add new customer's complaint
     * @param event - "Add new complaint" button is clicked
     */
    @FXML
    void OnAddNewComplaintButton(ActionEvent event) {
    	complaintCreationGUI.setUser(user);
    	Client.getInstance().setUI(complaintCreationGUI);
		complaintCreationGUI.doInit();
		FormController.primaryStage.setScene(complaintCreationGUI.getScene());
    }
  //===============================================================================================================
    /**
     * Opens menu which allows to manager existing customers' complaints
     * @param event - "Manage complaints" button is clicked
     */
    @FXML
    void onManageComplaintsButton(ActionEvent event)
    {
    	Client.getInstance().setUI(complaintManageGUI);
		complaintManageGUI.setParent(this);
		complaintManageGUI.setUser(user);
		complaintManageGUI.doInit();
		if(complaintManageGUI.getComplaintFound())
		{
			FormController.primaryStage.setScene(complaintManageGUI.getScene());
		}
		else {
			this.showInformationMessage("No complaints you've added need to be taken care of.");
		}
    }
  //===============================================================================================================
    /**
     * Logs current user out and returns to LogInGUI
     * @param event - "Log out" button is clicked
     */
    @FXML
    void onLogOutButton(ActionEvent event) {
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, Client.getInstance());
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	Client.getInstance().setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());

    }
  //===============================================================================================================
  	public void setUser(User user)
  	{
  		this.user = user;
  	}
  	//===============================================================================================================

}