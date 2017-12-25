package prototype;

import java.util.ArrayList;

import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.NumberStringConverter;
import product.ProdcutController;
import product.Product;
import serverAPI.Replay;

public class ShowProductController extends FormController implements ClientInterface{

	private class TableID
	{
		TableID(long oldID, long newID)
		{
			this.oldID = oldID;
			this.newID = newID;
		}
		
		public long oldID;
		public long newID;
	}
	
	private Client client;
	private ArrayList<TableID> productsToUpdate = new ArrayList<TableID>();
	
    @FXML
    private TableView<Product> productView;

    @FXML
    private TableColumn<Product, Number> ProductIDCol;

    @FXML
    private TableColumn<Product, String> ProductNameCol;

    @FXML
    private TableColumn<Product, String> ProductTypeCol;
    
    @FXML
    private Label productLabel;

    @FXML
    private Button buttonUpdate;

    @FXML
    private Button buttonRefresh;
    
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
  	*  Sets the client var and sends a request for a list of products from the server
  	*  @param newClient The client
  	*/
//*************************************************************************************************
    public void initData(Client newClient)
    {
    	client = newClient;
    	
    	ProdcutController.requestProducts(client);
    }

//*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
//*************************************************************************************************
    private void InitTableView()
    {
    	ProductIDCol.setCellValueFactory( new PropertyValueFactory<Product,Number>("ID"));
    	ProductIDCol.setCellFactory(TextFieldTableCell.<Product, Number>forTableColumn(new NumberStringConverter()));
    	// Set onEditCommmit for ID field
    	ProductIDCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, Number>>() {
    				@Override
    				public void handle(CellEditEvent<Product, Number> t) {   					
    					addProductToUpdate((Long)t.getOldValue(), (Long)t.getNewValue());
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setID((Long)t.getNewValue());
    				}
    			}
    			);

    	ProductNameCol.setCellValueFactory( new PropertyValueFactory<Product,String>("name"));
    	ProductNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	// Set onEditCommmit for Name field
    	ProductNameCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {
    					long ID = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
    					addProductToUpdate(ID,ID);
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setName(t.getNewValue());
    				}
    			}
    			);

    	ProductTypeCol.setCellValueFactory( new PropertyValueFactory<Product,String>("type"));
    	ProductTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	// Set onEditCommmit for Type field
    	ProductTypeCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {
    					long ID = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
    					addProductToUpdate(ID,ID);
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setType(t.getNewValue());
    				}
    			}
    			);
    	
    	productView.setEditable(true);
    }
    
    private void addProductToUpdate(long oldID, long newID)
    {
		boolean found = false;
		for (int i = 0; i < productsToUpdate.size(); i++)
		{
			if (productsToUpdate.get(i).newID == oldID)
			{
				productsToUpdate.get(i).newID = newID;
				found = true;
			}
		}
		if (!found)
			productsToUpdate.add(new TableID(oldID, newID));
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
    	
    	Replay replay = (Replay)message;
    	
    	if (replay.getType() == Replay.Type.SUCCESS)
    	{
    		final ObservableList<Product> itemData = FXCollections.observableArrayList();
    		
    		ArrayList<Product> products = (ArrayList<Product>)replay.getMessage();
    		
    		for (int i = 0; i < products.size(); i++)
        		itemData.add(products.get(i));
        	    	   	
        	productView.setItems(itemData);
    	}
    }
    
//*************************************************************************************************
    /**
  	*  Called when the update button is pressed
  	*  @param event The button press event
  	*/
 //*************************************************************************************************
    @FXML
    void OnUpdate(ActionEvent event) {  	
    	int i,j;
    	
    	for (i = 0; i < productsToUpdate.size(); i++)
    	{
    		ArrayList<String> message = new ArrayList<String>();
    		System.out.println("sending items to update");
    		    		
    		j = 0;
    		boolean found = false;
    		for ( j = 0; j < productView.getItems().size(); j++)
    		{
    			System.out.println(productsToUpdate.get(i).newID+"||"+productView.getItems().get(j).getID());
    			if (productsToUpdate.get(i).newID == (productView.getItems().get(j).getID()))
    			{
    				found = true;
    				break;
    			}
    		}
    		
    		if (found)
    		{
    			Product updatedProduct = new Product(productView.getItems().get(j).getID(),
    					productView.getItems().get(j).getName().trim(),
    					productView.getItems().get(j).getType().trim());
    			
    			ProdcutController.updateProduct(productsToUpdate.get(i).oldID, updatedProduct, client);
    		}
    		else
    		{
    			System.out.println("Error didn't find matching row despite being impossible...");
    		}
        	found = false;
    	}
    	
    	productsToUpdate.clear();
    	// refresh products list from database
    	ProdcutController.requestProducts(client);
    }

//*************************************************************************************************
    /**
  	*  Called when the refresh button is pressed
  	*  @param event The button press event
  	*/
 //*************************************************************************************************
    @FXML
    void onRefresh(ActionEvent event) {
    	productsToUpdate.clear();
    	ProdcutController.requestProducts(client);
    }
    
    public void onSwitch(Client newClient)
    {
    	
    }

}