package networkGUI;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import order.ComplaintCreationGUI;
import order.ComplaintManageGUI;
import prototype.FormController;
import user.LoginGUI;
import user.User;
import user.UserController;
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
		client.setUI(complaintCreationGUI);
		complaintCreationGUI.doInit();
		complaintCreationGUI.setClinet(client);
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
		client.setUI(complaintManageGUI);
		complaintManageGUI.setClinet(client);
		complaintManageGUI.setParent(this);
		complaintManageGUI.doInit();
		if(complaintManageGUI.getComplaintFound())
		{
			FormController.primaryStage.setScene(complaintManageGUI.getScene());
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