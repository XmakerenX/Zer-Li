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

	private Client client;
	
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
    	//    	final ObservableList<Product> data = FXCollections.observableArrayList(
    	//	    new Product(1, "item1", "type1"),
    	//	    new Product(2, "item2", "tpye2"),
    	//	    new Product(3, "item3", "type1"),
    	//	    new Product(4, "item4", "type2"),
    	//	    new Product(5, "item5", "typ3")
    	//	);

    	ProductIDCol.setCellValueFactory( new PropertyValueFactory<Product,String>("ID"));
    	ProductIDCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	ProductIDCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setID(Integer.parseInt(t.getNewValue()));
    				}
    			}
    			);

    	ProductNameCol.setCellValueFactory( new PropertyValueFactory<Product,String>("name"));
    	ProductNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	ProductNameCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setName(t.getNewValue());
    				}
    			}
    			);

    	ProductTypeCol.setCellValueFactory( new PropertyValueFactory<Product,String>("type"));
    	ProductTypeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	ProductTypeCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<Product, String>>() {
    				@Override
    				public void handle(CellEditEvent<Product, String> t) {
    					((Product) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setType(t.getNewValue());
    				}
    			}
    			);

    	productView.setEditable(true);
    	//productView.setItems(data);
    	//productView.getItems().add(new Product(6,"item66","type66"));
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
    	//final ObservableList<Product> data = (ObservableList<Product>)message;
    	final ObservableList<Product> itemData = FXCollections.observableArrayList();
    	
    	// get the rows data from the data string by splitting around '['
    	String[] rows = data.split("\\[");
    	
    	// read fields data from each row
    	// starting from 2 since 0,1 are empty
    	for (int i = 2; i < rows.length; i++)
    	{
    		String[] field = rows[i].split("\\,");	
    		
    		// remove the ending ] from the third field string
    		field[2] = field[2].replaceAll("\\]", "");
    		itemData.add(new Product(Integer.parseInt(field[0]), field[1], field[2]));
    	}
    	
//    	System.out.println(data);
//    	String[] parse = data.split("\\[");
//    	for (int i = 0; i < parse.length; i++)
//    		System.out.println(parse[i]);
//    	
//    	String[] parse2 = parse[2].split("\\,");
//    	for (int i = 0; i < parse2.length; i++)
//    		System.out.println(parse2[i]);
    	
    	productView.setItems(itemData);	
    }
    
    @FXML
    //TODO: get rid of this
    // does nothing usefull
    void onEditCommit(ActionEvent event) {
    	System.out.println("Eevent");
    	//System.out.println("row pressed is "+ event.getRowValue());
    	//productLabel.setText("row pressed is "+ event.getRowValue());
    }
    
    @FXML
    void OnUpdate(ActionEvent event) {
    	ArrayList<String> message = new ArrayList<String>();
    	message.add("SET");
    	message.add("Product");
    	message.add(ProductIDCol.getCellData(0));
    	message.add(ProductNameCol.getCellData(0));
    	message.add(ProductTypeCol.getCellData(0));
    	
    	client.handleMessageFromClientUI(message);
    	
    	//String ID = ProductIDCol.getCellData(0);
    	//String name = ProductNameCol.getCellData(0);
    	//String type = ProductTypeCol.getCellData(0);
    	
    	//System.out.println(ID+" "+name+" "+type);
    }

    @FXML
    void onRefresh(ActionEvent event) {
    	getProductInfo();
    }
    
}