package prototype;

import java.io.IOException;
import java.util.ArrayList;

import client.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import product.ProdcutController;
import product.Product;

public class ProductInfoFormController extends FormController{

	private Product p;
	//private Client client;
	
    @FXML
    private TextField productIDtxt;

    @FXML
    private TextField productNametxt;

    @FXML
    private TextField productTypetxt;

    @FXML
    private Button saveBtn;

    @FXML
    private Button closeBtn;

//*************************************************************************************************
    /**
  	*  Called when the save button is pressed
  	*  @param event The button press event
  	*/
//*************************************************************************************************
    @FXML
    void OnSave(ActionEvent event) {
    	long oldID = p.getID();
    	p.setID(Integer.parseInt(productIDtxt.getText()));
    	p.setName(productNametxt.getText());
    	p.setType(productTypetxt.getText());

    	// send update to DB
    	ProdcutController.updateProduct(oldID, p, client);
    }

//*************************************************************************************************
    /**
  	*  Called when the close button is pressed
  	*  @param event The button press event
  	*/
//*************************************************************************************************
    @FXML
    void onClose(ActionEvent event) {
    	
    	MainFormController mainFormController = (MainFormController)parent;
    	client.setUI(mainFormController);
    	mainFormController.initData(client);
    	FormController.primaryStage.setScene(parent.getScene());
    }

//*************************************************************************************************
    /**
  	*  sets the GUI text boxes with the given product data
  	*  @param p1 The product data
  	*/
//*************************************************************************************************
	public void loadProduct(Product p1){
		this.p=p1;
		this.productIDtxt.setText(""+p.getID());
		this.productNametxt.setText(p.getName());
		this.productTypetxt.setText(p.getType());
	}

//*************************************************************************************************
    /**
  	*  Sets the client var
  	*  @param client
  	*/
//*************************************************************************************************
	public void setClinet(Client client)
	{
		this.client = client;
	}
	
	public void onSwitch(Client newClient)
	{
		
	}
	
    
}
