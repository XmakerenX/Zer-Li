package customer;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import prototype.FormController;
import user.LoginGUI;

public class CustomerGUI extends FormController {

    @FXML
    private Label welcomeLbl;
    
    @FXML
    private Button backBtn;

    @FXML
    void onBack(ActionEvent event) {
    	LoginGUI loginGUi = (LoginGUI)parent;
    	client.setUI(loginGUi);
    	FormController.primaryStage.setScene(parent.getScene());
    }
	
	public void onSwitch(Client newClient)
	{
		
	}
}
