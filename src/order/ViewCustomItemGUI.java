package order;


import javafx.collections.FXCollections;
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

import java.util.ArrayList;

import client.Client;
import prototype.FormController;

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
    
    public void loadCustomItem(ObservableList<Product> customProducts)
    {    	
    	customTable.setItems(customProducts);
    }
    
    
    
	public void setWindowStage(Stage windowStage) {
		this.windowStage = windowStage;
	}

	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

}
