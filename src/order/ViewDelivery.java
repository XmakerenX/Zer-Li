package order;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.Client;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  Provides a GUI that allow to see the order delivery information
	*/
//*************************************************************************************************
public class ViewDelivery extends FormController {

	private Stage windowStage;
		
    @FXML
    private TextField addressTxt;

    @FXML
    private TextField reciverNameTxt;

    @FXML
    private TextField reciverPhoneNumberTxt;

    @FXML
    private Button closeBtn;

	//*************************************************************************************************
    /**
  	*  Closes this window
  	*  @param e the event that triggered this function
  	*/
	//*************************************************************************************************
    @FXML
    void onClose(ActionEvent event) {
    	windowStage.hide();
    }
    
    //*************************************************************************************************
    /**
     *  loads the given deliveryInfo to the text Fields
     *  @param deliveryInfo the delivery information to present
     */
    //*************************************************************************************************
    public void loadDeliveryInfo(Order.DeliveryInfo deliveryInfo)
    {
    	this.addressTxt.setText(deliveryInfo.deliveryAddress);
    	this.reciverNameTxt.setText(deliveryInfo.getReceiverName());
    	this.reciverPhoneNumberTxt.setText(deliveryInfo.getReceiverPhoneNumber());
    }
    
    //*************************************************************************************************
    /**
     *  Sets the associated window Stage with this form
     *  @param windowStage the windowStage for this window
     */
    //*************************************************************************************************
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}
	
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

}
