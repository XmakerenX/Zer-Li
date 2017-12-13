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

public class ShowProductController implements ClientInterface{

	private class TableID
	{
		TableID(String oldID, String newID)
		{
			this.oldID = oldID;
			this.newID = newID;
		}
		
		public String oldID;
		public String newID;
	}
	
	private Client client;
	private ArrayList<TableID> productsToUpdate = new ArrayList<TableID>();
	
    @FXML
    private TableView<Product> productView;

    @FXML
    private TableColumn<Product, String> ProductIDCol;

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
    
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    }

    public void initData(Client newClient)
    {
    	client = newClient;
    	
    	getProductInfo();
    }
    
    private void InitTableView()
    {
    	ProductIDCol.setCellValueFactory( new PropertyValueFactory<Product,String>("ID"));
    	ProductIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	// Set onEditCommmit for ID field
    	ProductIDCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {   					
    					addProductToUpdate(t.getOldValue(), t.getNewValue());
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setID(Integer.parseInt(t.getNewValue()));
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
    					String ID = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
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
    					String ID = t.getTableView().getItems().get(t.getTablePosition().getRow()).getID();
    					addProductToUpdate(ID,ID);
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setType(t.getNewValue());
    				}
    			}
    			);
    	
    	productView.setEditable(true);
    }
    
    private void addProductToUpdate(String oldID, String newID)
    {
		boolean found = false;
		for (int i = 0; i < productsToUpdate.size(); i++)
		{
			if (productsToUpdate.get(i).newID.equals(oldID))
			{
				productsToUpdate.get(i).newID = newID;
				found = true;
			}
		}
		if (!found)
			productsToUpdate.add(new TableID(oldID, newID));
    }
    
    public void getProductInfo()
    {
    	ArrayList<String> message = new ArrayList<String>();
    	
    	message.add("GET");
    	message.add("Product");
    	client.handleMessageFromClientUI(message);
    }
    
    public void display(Object message)
    {
    	System.out.println(message.toString());
    	System.out.println(message.getClass().toString());
    	String data = (String)message;
    	final ObservableList<Product> itemData = FXCollections.observableArrayList();
    	
    	// get the rows data from the data string by splitting around '['
    	String[] rows = data.split("\\[");
    	
    	// read fields data from each row
    	// starting from 2 since 0,1 are empty
    	for (int i = 2; i < rows.length; i++)
    	{
    		String[] field = rows[i].split("\\,");	
    		
    		// remove the ending ] from the third field string
    		field[0] = field[0].trim();
    		field[1] = field[1].trim();
    		field[2] = field[2].replaceAll("\\]", "");
    		field[2] = field[2].trim();
    		itemData.add(new Product(Integer.parseInt(field[0]), field[1], field[2]));
    	}
    	   	
    	productView.setItems(itemData);	
    }
    
    @FXML
    void OnUpdate(ActionEvent event) {
    	//ArrayList<String> message = new ArrayList<String>();
    	
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
    			if (productsToUpdate.get(i).newID.equals(productView.getItems().get(j).getID()))
    			{
    				found = true;
    				break;
    			}
    		}
    		
    		if (found)
    		{
    			message.add("SET"); 											// 0
    			message.add("Product"); 										// 1
    			message.add(productsToUpdate.get(i).oldID.trim());				// 2
    			message.add(productView.getItems().get(j).getID().trim());		// 3
    			message.add(productView.getItems().get(j).getName().trim());	// 4
    			message.add(productView.getItems().get(j).getType().trim());	// 5
    		}
    		else
    		{
    			System.out.println("Error didn't find matching row despite being impossible...");
    		}
        	
        	client.handleMessageFromClientUI(message);
        	found = false;
    	}
    	
    	productsToUpdate.clear();
    	// refresh products list from database
    	getProductInfo();
    }

    @FXML
    void onRefresh(ActionEvent event) {
    	productsToUpdate.clear();
    	getProductInfo();
    }
    
    // TODO: find why it won't let me remove this....
    @FXML
    void onEditCommit(ActionEvent event) {

    }
    
}