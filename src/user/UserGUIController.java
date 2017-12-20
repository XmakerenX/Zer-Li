package user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import client.Client;
import prototype.FormController;

public class UserGUIController extends FormController {

    @FXML
    private Button loginBtn;

    @FXML
    private TextField usernameTxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private Button exitBtn;

    @FXML
    void onExit(ActionEvent event) {

    }

    @FXML
    void onLogin(ActionEvent event) {

    }
	
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

}
