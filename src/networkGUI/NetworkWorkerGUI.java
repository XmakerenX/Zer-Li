package networkGUI;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import prototype.FormController;

public class NetworkWorkerGUI extends  FormController 
{


    @FXML
    private Button backBtn;

    @FXML
    private Button manageCatalogBTN;

    @FXML
    private Label welcomeLbl;

    @FXML
    void manageCatalog(ActionEvent event)
    {

    }

    @FXML
    void onBack(ActionEvent event) {

    }
    
	@Override
	public void onSwitch(Client newClient) 
	{
		
		
	}

}
