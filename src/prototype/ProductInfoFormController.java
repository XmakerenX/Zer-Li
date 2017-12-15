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

public class ProductInfoFormController extends FormController{

	private Product p;
	private Client client;
	
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

    @FXML
    void OnSave(ActionEvent event) {
    	long oldID = p.getID();
    	p.setID(Integer.parseInt(productIDtxt.getText()));
    	p.setName(productNametxt.getText());
    	p.setType(productTypetxt.getText());

    	// send update to DB
    	ProdcutController.updateProduct(oldID, p, client);
    }

    @FXML
    void onClose(ActionEvent event) {
    	
    	MainFormController mainFormController = (MainFormController)parent;
    	client.setUI(mainFormController);
    	mainFormController.initData(client);
    	FormController.primaryStage.setScene(parent.getScene());
    	//((Node)event.getSource()).getScene().getWindow().hide(); //hiding primary window
		//Stage primaryStage = new Stage();
		//FXMLLoader loader = new FXMLLoader();
//		try
//		{
//			Pane root = loader.load(getClass().getResource("MainForm.fxml").openStream());
//			
//			MainFormController mainFormController = loader.getController();
//			mainFormController.initData(client);
//			client.setUI(mainFormController);
//			
//			Scene scene = new Scene(root);			
//			//scene.getStylesheets().add(getClass().getResource("/gui/StudentForm.css").toExternalForm());
//			
//			primaryStage.setScene(scene);	
//			primaryStage.setTitle("Prototype");
//			primaryStage.show();
//		}
//		catch(IOException e)
//		{
//			System.out.println("Failed to open MainForm");
//		}
    }

	public void loadProduct(Product p1){
		this.p=p1;
		this.productIDtxt.setText(""+p.getID());
		this.productNametxt.setText(p.getName());
		this.productTypetxt.setText(p.getType());
	}
	
	public void setClinet(Client client)
	{
		this.client = client;
	}
    
}
