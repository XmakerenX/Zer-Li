package networkGUI;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import prototype.FormController;

public class baseGUI extends FormController 
{
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
	
    @FXML
    private Button logOutBtn;

    @FXML
    void logOut(ActionEvent event) {

    }

	
	
}
