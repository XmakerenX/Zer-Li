package catalog;

import client.Client;
import client.ClientInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import prototype.FormController;

public class CreateOrderGUI extends FormController implements ClientInterface {

    @FXML
    private TableView<?> orderTable;

    @FXML
    private TableColumn<?, ?> imageCol;

    @FXML
    private TableColumn<?, ?> nameCol;

    @FXML
    private TableColumn<?, ?> typeCol;

    @FXML
    private TableColumn<?, ?> colorCol;

    @FXML
    private TableColumn<?, ?> priceCol;

    @FXML
    private TableColumn<?, ?> buttonCol;

    @FXML
    private Label totalPrice;

    @FXML
    private DatePicker date;

    @FXML
    private ToggleGroup pickupMethod;

    @FXML
    private TextField addressTxt;

    @FXML
    private ToggleGroup payMethod;

    @FXML
    private TextField hourTxt;

    @FXML
    private TextField minsTxt;

    @FXML
    private Button confirmOrderBtn;

    @FXML
    private Button cancelBtn;
	
    @FXML
    void OnCancel(ActionEvent event) {

    }

    @FXML
    void onConfirmOrder(ActionEvent event) {

    }
    
	@Override
	public void display(Object message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

}
