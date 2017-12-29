package catalog;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.util.converter.NumberStringConverter;
import product.CatalogItem;
import prototype.FormController;
import serverAPI.Response;

public class CatalogGUI extends FormController implements ClientInterface {

    @FXML
    private TableView<CatalogItem> catalogTable;

    @FXML
    private TableColumn<CatalogItem, ImageView> imageCol;

    @FXML
    private TableColumn<CatalogItem, String> nameCol;

    @FXML
    private TableColumn<CatalogItem, String> typeCol;

    @FXML
    private TableColumn<CatalogItem, String> colorCol;

    @FXML
    private TableColumn<CatalogItem, Number> priceCol;
	
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
    	nameCol.setCellValueFactory( new PropertyValueFactory<CatalogItem,String>("Name"));
    	nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	nameCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItem, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItem, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setName(t.getNewValue());
    				}
    			}
    			);
    	
    	typeCol.setCellValueFactory(new PropertyValueFactory<CatalogItem,String>("Type"));
    	typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	typeCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItem, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItem, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setType(t.getNewValue());
    				}
    			}
    			);
    	
    	colorCol.setCellValueFactory(new PropertyValueFactory<CatalogItem,String>("Color"));
    	colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	colorCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItem, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItem, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setColor(t.getNewValue());
    				}
    			}
    			);
    	
    	priceCol.setCellValueFactory( new PropertyValueFactory<CatalogItem,Number>("Price"));
    	priceCol.setCellFactory(TextFieldTableCell.<CatalogItem, Number>forTableColumn(new NumberStringConverter()));
    	priceCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItem, Number>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItem, Number> t) {   					
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setPrice((float)t.getNewValue());
    				}
    			}
    			);
    	
    	catalogTable.setEditable(false);
    }
    
    void onRefresh(ActionEvent event) {
    	CatalogController.requestCatalogItems(client);
    }
    
    //*************************************************************************************************
    /**
  	*  Called from the client when the server sends a response
  	*  fills the TableView with the received products data
  	*  @param message The Server response , an ArrayList of products
  	*/
//*************************************************************************************************
    public void display(Object message)
    {
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	
    	Response replay = (Response)message;
    	
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		final ObservableList<CatalogItem> itemData = FXCollections.observableArrayList();
    		
    		ArrayList<CatalogItem> catalogitems = (ArrayList<CatalogItem>)replay.getMessage();
    		
    		for (int i = 0; i < catalogitems.size(); i++)
    		{
        		itemData.add(catalogitems.get(i));
    		}
    		
    		catalogTable.setItems(itemData);
    	}
    }
    
    @Override
	public void setClinet(Client client)
	{
    	super.setClinet(client);
    	onRefresh(null);
	}
    
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

	
	
}
