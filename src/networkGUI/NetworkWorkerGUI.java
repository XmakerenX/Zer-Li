package networkGUI;

import catalog.ManageCatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import user.LoginGUI;
import user.User;
import user.UserController;
//*************************************************************************************************
	/**
	*  Provides a GUI to handle network worker GUI (handle base catalog and products)
	*/
//*************************************************************************************************
public class NetworkWorkerGUI extends  FormController  implements ClientInterface
{
    @FXML
    private Button backBtn;

    @FXML
    private Button manageCatalogBTN;

    @FXML
    private Label welcomeLbl;

    User user;
    int storeID;
    
    public int getStoreID() 
    {
		return storeID;
	}

	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	/**
	 * Displays menu which allows to manage catalogs
	 * @param event - "Manage catalog" button is clicked
	 */
	@FXML
    void manageCatalog(ActionEvent event)
    {
    	ManageCatalogGUI manCatGui = FormController.<ManageCatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/ManageCatalogGUI.fxml"), this);
    	client.setUI(manCatGui);
    	manCatGui.setClinet(client);
    	manCatGui.setEmployeeStoreID(storeID);
    	manCatGui.doInit(user);  
    	FormController.primaryStage.setScene(manCatGui.getScene());
    	
    }
       
    public void setUser(User user)
	{
		this.user = user;
	}
    
    /**
     * Logs the user out of the system
     * @param event - "Log out" button is pressed
     */
    @FXML
    void onLogOut(ActionEvent event) 
    {
    	user.setUserStatus(User.Status.valueOf("REGULAR"));
    	UserController.requestLogout(user, client);
    	
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
    
	@Override
	public void onSwitch(Client newClient) 
	{
		
		
	}

	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub
		
	}
}