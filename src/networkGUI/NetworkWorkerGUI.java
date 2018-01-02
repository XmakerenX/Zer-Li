package networkGUI;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import prototype.FormController;
import user.NewUserCreationGUI;

public class NetworkWorkerGUI extends  FormController  implements ClientInterface
{
    @FXML
    private Button backBtn;
    
    @FXML
    private Button addNewProduct;

    @FXML
    private Button manageCatalogBTN;

    @FXML
    private Label welcomeLbl;

    @FXML
    void manageCatalog(ActionEvent event)
    {
       
    }
    
    @FXML
    void AddnewProduct(ActionEvent event) 
    {
    	
    	NewProductCreationGUI createProductGUI = FormController.<NewProductCreationGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NewProductCreation.fxml"), this);
		if ( createProductGUI != null)
		{
			client.setUI(createProductGUI);
			createProductGUI.setClinet(client);
			FormController.primaryStage.setScene(createProductGUI.getScene());
		}
    }
    
    @FXML
    void onBack(ActionEvent event) 
    {

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
