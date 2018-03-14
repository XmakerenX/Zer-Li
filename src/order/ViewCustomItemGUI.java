package order;


import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import product.Product;
import utils.FormController;
import client.Client;

//*************************************************************************************************
	/**
	*  Provides a GUI that allow to see the components of a custom item
	*/
//*************************************************************************************************
public class ViewCustomItemGUI extends FormController {

	private Stage windowStage;
	
    @FXML
    private TableView<Product> customTable;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, Number> priceCol;

    @FXML
    private TableColumn<Product, String> colorCol;

    @FXML
    private TableColumn<Product, Number> amountCol;
	
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
    	windowStage.close();
    }
    
    //*************************************************************************************************
    /**
     *  Called by FXMLLoader on class initialization 
     */
    //*************************************************************************************************
    @FXML
    //Will be called by FXMLLoader
    public void initialize()
    {
    	InitTableView();
    } 
    
    //*************************************************************************************************
    /**
     *  Initializes the Table View to be compatible with the product class (get and set class values)
     */
    //*************************************************************************************************
    private void InitTableView()
    {   	
    	nameCol.setCellValueFactory( new PropertyValueFactory<Product,String>("Name"));
    	nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    			
    	priceCol.setCellValueFactory( new PropertyValueFactory<Product,Number>("Price"));
    	priceCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
    	
    	colorCol.setCellValueFactory( new PropertyValueFactory<Product,String>("Color"));
    	colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	
    	amountCol.setCellValueFactory( new PropertyValueFactory<Product,Number>("Amount"));
    	amountCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
    }
    
    //*************************************************************************************************
    /**
     *  loads the given customProducts to the Table View
     *  @param customProducts the custom product components to show in the table View
     */
    //*************************************************************************************************
    public void loadCustomItem(ObservableList<Product> customProducts)
    {    	
    	customTable.setItems(customProducts);
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

}
