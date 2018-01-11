package networkGUI;

import catalog.AddToCatalogGUI;
import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import prototype.FormController;
import user.LoginGUI;
import user.NewUserCreationGUI;
import user.User;
import user.UserController;

public class NetworkWorkerGUI extends  FormController  implements ClientInterface
{
    @FXML
    private Button backBtn;

    @FXML
    private Button manageCatalogBTN;

    @FXML
    private Label welcomeLbl;

    User user;
    
    @FXML
    void manageCatalog(ActionEvent event)
    {
    	ManageCatalogGUI manCatGui = FormController.<ManageCatalogGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/ManageCatalogGUI.fxml"), this);
    	client.setUI(manCatGui);
    	manCatGui.setClinet(client);
    	FormController.primaryStage.setScene(manCatGui.getScene());
    	manCatGui.doInit();  
    }
       
    public void setUser(User user)
	{
		this.user = user;
	}

    
    @FXML
    void onBack(ActionEvent event) 
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