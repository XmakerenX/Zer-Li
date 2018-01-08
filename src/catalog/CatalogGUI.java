package catalog;

import java.util.ArrayList;
import java.util.Collections;

import client.Client;
import client.ClientInterface;
import customer.CustomerGUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.converter.NumberStringConverter;
import order.CreateOrderGUI;
import product.CatalogItem;
import prototype.FormController;
import serverAPI.Response;
import user.LoginGUI;
import utils.ImageData;

public class CatalogGUI extends FormController implements ClientInterface {

	private CreateOrderGUI createOrderGUI;
	
    @FXML
    private TableView<CatalogItemView> catalogTable;

    @FXML
    private TableColumn<CatalogItemView, ImageView> imageCol;

    @FXML
    private TableColumn<CatalogItemView, String> nameCol;

    @FXML
    private TableColumn<CatalogItemView, String> typeCol;

    @FXML
    private TableColumn<CatalogItemView, String> colorCol;

    @FXML
    private TableColumn<CatalogItemView, Number> priceCol;
	
    @FXML
    private TableColumn<CatalogItemView, Boolean> checkboxCol;
    
    @FXML
    private Button backBtn;
    
    @FXML
    private Button printBtn;
    
    @FXML
    private Button createOrderBtn;
    
 // holds the last replay we got from server
 	private Response replay = null;
    
//*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*/
//*************************************************************************************************
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    	
    	createOrderGUI = FormController.<CreateOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CreateOrderGUI.fxml"), this);
    }
    
//*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
//*************************************************************************************************
    private void InitTableView()
    {
    	imageCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView, ImageView>("image"));
    	
    	nameCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,String>("Name"));
    	nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	nameCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItemView, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItemView, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setName(t.getNewValue());
    				}
    			}
    			);
    	
    	typeCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView,String>("Type"));
    	typeCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	typeCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItemView, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItemView, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setType(t.getNewValue());
    				}
    			}
    			);
    	
    	colorCol.setCellValueFactory(new PropertyValueFactory<CatalogItemView,String>("Color"));
    	colorCol.setCellFactory(TextFieldTableCell.forTableColumn());
    	colorCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItemView, String>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItemView, String> t) {
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setColor(t.getNewValue());
    				}
    			}
    			);
    	
    	priceCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,Number>("Price"));
    	priceCol.setCellFactory(TextFieldTableCell.<CatalogItemView, Number>forTableColumn(new NumberStringConverter()));
    	priceCol.setOnEditCommit(
    			new EventHandler<CellEditEvent<CatalogItemView, Number>>() {
    				@Override
    				public void handle(CellEditEvent<CatalogItemView, Number> t) {   					
    					((CatalogItem) t.getTableView().getItems().get(
    							t.getTablePosition().getRow())
    							).setPrice((float)t.getNewValue());
    				}
    			}
    			);
    	
    	checkboxCol.setCellValueFactory( new PropertyValueFactory<CatalogItemView,Boolean>("CheckBox"));
    	checkboxCol.setCellValueFactory(
    			new Callback<CellDataFeatures<CatalogItemView, Boolean>, ObservableValue<Boolean>>()
    	{
    		@Override
    		public ObservableValue<Boolean> call(CellDataFeatures<CatalogItemView, Boolean> param)
    		{
    			return param.getValue().selectedProperty();
    		}
    	}
    	);
    	checkboxCol.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxCol));
    	

    	
    	catalogTable.setEditable(true);
    	checkboxCol.setEditable(true);
    }
    
    public void onRefresh(ActionEvent event) {
    	System.out.println("request catalog items");
    	CatalogController.requestCatalogItems(Client.client);
    	// wait for response
		synchronized(this)
		{
			// wait for server response
			try {
				this.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
		if (replay == null)
			return;
		
		System.out.println("process replay");
		
    	if (replay.getType() == Response.Type.SUCCESS)
    	{
    		final ObservableList<CatalogItemView> itemData = FXCollections.observableArrayList();
    		
    		ArrayList<CatalogItem> catalogItems = (ArrayList<CatalogItem>)replay.getMessage();
    		
    		ArrayList<String> missingImages = CatalogController.scanForMissingCachedImages(catalogItems);
    		replay = null;
    		if (missingImages.size() > 0)
    		{
    			System.out.println("Missing images "+ missingImages);
    			CatalogController.requestCatalogImages(missingImages, Client.client);

    			// wait for response 
    			synchronized(this)
    			{
    				try {
    					this.wait();
    				} catch (InterruptedException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    			}
    			
        		if (replay != null)
        			CatalogController.saveCatalogImages((ArrayList<ImageData>)replay.getMessage());
    		}
    		
    			
    		for (int i = 0; i < catalogItems.size(); i++)
    		{
    			
    			itemData.add(new CatalogItemView(catalogItems.get(i), "Cache//"));
    		}

    		//SortedList<CatalogItemView> sortList = new SortedList<CatalogItemView>(itemData);
    		//sortList.comparatorProperty().bind(catalogTable.comparatorProperty());
    		//Collections.sort(itemData);
    		catalogTable.setItems(itemData);
    		Collections.sort(catalogTable.getItems());
    	}
    	
    	replay = null;
    }
    
    @FXML
    void onBack(ActionEvent event) {
    	FormController.primaryStage.setScene(parent.getScene());
    }
    
    @FXML
    void onPrint(ActionEvent event) {
    	final ObservableList<CatalogItemView> itemData = catalogTable.getItems();
    	
    	for (CatalogItemView item : itemData)
    	{
    		System.out.println(item.getName()+ " "+ item.isSelected());
    	}
    }
    
    @FXML
    void onCreateOrder(ActionEvent event) 
    {
    	final ObservableList<CatalogItemView> itemData = catalogTable.getItems();
    	final ObservableList<CatalogItemView> itemsSelected = FXCollections.observableArrayList();
    	
    	for (CatalogItemView item : itemData)
    	{
    		if (item.isSelected())
    			itemsSelected.add(item);
    	}
    	
    	if (itemsSelected.size() > 0)
    	{        	
        	if (createOrderGUI != null)
        	{
        		Client.client.setUI(createOrderGUI);
        		createOrderGUI.loadItemsInOrder(itemsSelected);
        		FormController.primaryStage.setScene(createOrderGUI.getScene());
        	}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("No item selected!");
			alert.setContentText("please select an item from the catalog");
			alert.showAndWait();
    	}
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
    	
    	replay = (Response)message;

		synchronized(this)
		{
			this.notify();
		}
    	    	
    }
    
    @Override
	public void setClinet(Client client)
	{
    	//super.setClinet(client);
    	onRefresh(null);
	}
    
	@Override
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

	
	
}
