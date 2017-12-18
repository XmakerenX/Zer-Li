package prototype;

import java.io.IOException;
import java.util.ArrayList;
import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MainFormController extends FormController implements ClientInterface {

	//private Client client;
	ProductInfoFormController productFromController;
	private ArrayList<Product> products = new ArrayList<Product>();
	
    @FXML
    private ComboBox<String> productCbx;

    @FXML
    private Button showProductBtn;

    @FXML
    private Button exitBtn;

    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	try
    	{
    		productFromController = FormController.<ProductInfoFormController, BorderPane>loadFXML(getClass().getResource("ProductInfoForm.fxml"), this);
    		productFromController.setClinet(client);
    	} catch(IOException e)
    	{
    		System.out.println("Failed to load ProductInfoForm.fxml");
    		productFromController = null;
    	}
    }
    
    @FXML
    void onExit(ActionEvent event) {
    	System.out.println("onExit was perssed");
    	client.quit();
    	System.exit(0);
    }

    @FXML
    void onShowProductInfo(ActionEvent event) {
    	
    	// get the index of the product selected
    	int productIndex = productCbx.getSelectionModel().getSelectedIndex();
    	// if no product was selected than abort
		if (productIndex == -1)
			return;
		
		if (productFromController != null)
		{
			productFromController.loadProduct(products.get(productCbx.getSelectionModel().getSelectedIndex()));
			productFromController.setClinet(client);
			FormController.primaryStage.setScene(productFromController.getScene());
		}
    }

    public void display(Object message)
    {
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	ArrayList<String> comboboxProductStrings = new ArrayList<String>();
    	products = (ArrayList<Product>)message; 	
    	
    	for (int i = 0; i < products.size(); i++)
    		comboboxProductStrings.add(products.get(i).getName());
    	
    	System.out.println(comboboxProductStrings);
    	ObservableList<String> comboBoxList = FXCollections.observableArrayList(comboboxProductStrings);
    	productCbx.setItems(comboBoxList);
    }
        
    public void initData(Client newClient)
    {
    	System.out.println("initData");
    	client = newClient;
    	ProdcutController.requestProducts(client);
    	productCbx.getSelectionModel().select(-1);
    }
    
    public void onSwitch(Client newClient)
    {
    	client = newClient;
    	ProdcutController.requestProducts(client);
    	productCbx.getSelectionModel().select(-1);   	
    }
    
}
