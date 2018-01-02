package customer;

import catalog.CatalogGUI;
import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import networkGUI.SystemManagerGUI;
import prototype.FormController;
import user.LoginGUI;

public class CustomerGUI extends FormController {

	CatalogGUI catalogGui;
	
    @FXML
    private Label welcomeLbl;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button viewCatalogBtn;

    @FXML
    //Will be called by FXMLLoader
    public void initialize(){
    	catalogGui = FormController.<CatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/CatalogGUI.fxml"), this);
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
	
    @FXML
    void onViewCatalog(ActionEvent event) {
    	if (catalogGui != null)
    	{
    		client.setUI(catalogGui);
    		catalogGui.setClinet(client);
    		FormController.primaryStage.setScene(catalogGui.getScene());
    	}
    }
    
	public void onSwitch(Client newClient)
	{
		
	}
}
