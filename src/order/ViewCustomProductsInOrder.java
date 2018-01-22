package order;

import java.util.ArrayList;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import product.Product;
import prototype.FormController;

public class ViewCustomProductsInOrder extends FormController {

	private Stage windowStage;
	
    @FXML
    private TableView<CustomItemInOrderView> productsTable;

    @FXML
    private TableColumn<CustomItemInOrderView, TextArea> nameCol;

    @FXML
    private TableColumn<CustomItemInOrderView, String> typeCol;

    @FXML
    private TableColumn<CustomItemInOrderView, String> colorCol;

    @FXML
    private TableColumn<CustomItemInOrderView, TextArea> greetingCardCol;

    @FXML
    private TableColumn<CustomItemInOrderView, Button> viewCol;
    
    @FXML
    private Button closeBtn;
    

//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*/
//*************************************************************************************************
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    }
    
  //*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
//*************************************************************************************************
    protected void InitTableView()
    {
    	nameCol.setCellValueFactory( new PropertyValueFactory<CustomItemInOrderView,TextArea>("nameArea"));
    	typeCol.setCellValueFactory( new PropertyValueFactory<CustomItemInOrderView,String>("Type"));
    	colorCol.setCellValueFactory( new PropertyValueFactory<CustomItemInOrderView,String>("Color"));
    	greetingCardCol.setCellValueFactory( new PropertyValueFactory<CustomItemInOrderView,TextArea>("GreetingCardText"));
    	viewCol.setCellValueFactory( new PropertyValueFactory<CustomItemInOrderView,Button>("viewBtn"));
    }
    
    @FXML
    void onClose(ActionEvent event) {
    	windowStage.hide();
    }
	
    public void loadCustomProducts(ArrayList<CustomItemInOrder> customInOrder)
    {
    	final ObservableList<CustomItemInOrderView> observableProducts = FXCollections.observableArrayList();
    	for (CustomItemInOrder cInOrder : customInOrder)
    	{
    		observableProducts.add(new CustomItemInOrderView(cInOrder));
    	}
    	
    	this.productsTable.setItems(observableProducts);
    }
	    
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}
    
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub
		
	}
}
