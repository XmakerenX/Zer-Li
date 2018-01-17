package order;

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

import java.time.LocalDate;
import java.util.ArrayList;

import client.Client;
import prototype.FormController;

public class ViewProductInOrder extends FormController {

	private Stage windowStage;
	
    @FXML
    private TableView<OrderItemView> productsTable;

    @FXML
    private TableColumn<OrderItemView, TextArea> nameCol;

    @FXML
    private TableColumn<OrderItemView, String> typeCol;

    @FXML
    private TableColumn<OrderItemView, String> colorCol;

    @FXML
    private TableColumn<OrderItemView, TextArea> greetingCardCol;

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
    private void InitTableView()
    {
    	nameCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,TextArea>("nameArea"));
    	typeCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,String>("Type"));
    	colorCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,String>("Color"));
    	greetingCardCol.setCellValueFactory( new PropertyValueFactory<OrderItemView,TextArea>("greetingCard"));
    }
    
    @FXML
    void onClose(ActionEvent event) {
    	windowStage.hide();
    }
	
    public void loadProducts(ArrayList<ProductInOrder> productsInOrder)
    {
    	final ObservableList<OrderItemView> observableProducts = FXCollections.observableArrayList();
    	for (ProductInOrder pInOrder : productsInOrder)
    	{
    		OrderItemView o = new OrderItemView( (Product)pInOrder, null, null );
    		o.setGreetingCardText(pInOrder.getGreetingCard());
    		observableProducts.add(o);
    	}
    	
    	this.productsTable.setItems(observableProducts);
    }
    
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}
	
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}

}
