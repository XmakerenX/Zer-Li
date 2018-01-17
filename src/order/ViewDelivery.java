package order;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.Client;
import prototype.FormController;

public class ViewDelivery extends FormController {

	private Stage windowStage;
	
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
    @FXML
    private TextField addressTxt;

    @FXML
    private TextField reciverNameTxt;

    @FXML
    private TextField reciverPhoneNumberTxt;

    @FXML
    private Button closeBtn;

    @FXML
    void onClose(ActionEvent event) {
    	windowStage.hide();
    }
    
    public void loadDeliveryInfo(Order.DelivaryInfo delivaryInfo)
    {
    	this.addressTxt.setText(delivaryInfo.delivaryAddress);
    	this.reciverNameTxt.setText(delivaryInfo.getReceiverName());
    	this.reciverPhoneNumberTxt.setText(delivaryInfo.getReceiverPhoneNumber());
    }
    
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}

}
