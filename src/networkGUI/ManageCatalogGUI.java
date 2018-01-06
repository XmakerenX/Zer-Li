package networkGUI;

import java.util.ArrayList;
import java.util.Optional;

import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.EditableProductVIew.EditableProductVIewButton;
import product.*;
import prototype.FormController;
import serverAPI.GetRequest;
import serverAPI.RemoveRequest;
import serverAPI.Response;


public class ManageCatalogGUI extends FormController implements ClientInterface
{
		Response response;
		Client myClient;
		EditProductGUI editProdGUI;
		
		
		
	//editable catalog view:
	   @FXML
	    private TableView editCatalogView;
	   
			    TableColumn cat_imageCol = new TableColumn("Image");
			    TableColumn cat_nameCol = new TableColumn("Name");
			    TableColumn cat_priceCol = new TableColumn("Price");
			    TableColumn cat_salesPriceCol = new TableColumn("sale price");
			    TableColumn cat_editCol = new TableColumn("");
			    TableColumn cat_removeCol = new TableColumn("");

    //editable product view
	    @FXML
	    private TableView editProductTable;//define table

	    
			    TableColumn prod_idCol = new TableColumn("id");
			    TableColumn prod_nameCol = new TableColumn("Name");
			    TableColumn prod_typeCol = new TableColumn("Type");
			    TableColumn prod_priceCol = new TableColumn("Price");
			    TableColumn prod_amountCol = new TableColumn("Amount");
			    TableColumn prod_addToCatalogCol = new TableColumn("");
			    TableColumn prod_editCol = new TableColumn("");
			    TableColumn prod_removeCol = new TableColumn("");
	    
			    ObservableList<EditableProductVIew> eProducts; //table's data
		
			    
			 		//action events for product buttons:
			    
			    @FXML
			    private Button newProdBtn;

			    @FXML
			    void newProdBtn(ActionEvent event) 
			    {
			    	NewProductCreationGUI createProductGUI = FormController.<NewProductCreationGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/NewProductCreation.fxml"), this);
					if (createProductGUI != null)
					{
						Stage newWindow = new Stage();
						client.setUI(createProductGUI);
						createProductGUI.setClinet(client);
						newWindow.initOwner(FormController.primaryStage);
				    	newWindow.initModality(Modality.WINDOW_MODAL);  
						newWindow.setScene(createProductGUI.getScene());
						newWindow.showAndWait();
					}
	    			//initProductsTableContent();
					
			    }	  
			    
	    //remove button:
		EventHandler<ActionEvent> prodRemoveAction  = new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		    	Alert alert = new Alert(AlertType.CONFIRMATION);
	    		alert.setHeaderText("About to remove product");
	    		alert.setContentText("Are you sure ?");

	    		Optional<ButtonType> result = alert.showAndWait();
	    		if (result.get() == ButtonType.OK)
	    		{
	    			EditableProductVIewButton src = (EditableProductVIewButton)e.getSource();
	    			String id =Long.toString(src.getOrigin().getID());
    				ProdcutController.removeProductFromDataBase(id,myClient);
    				waitForResponse();
	    			
    				//show result:
	    			String outputMsg;
	    			if(response.getType().name().equals("SUCCESS")) {outputMsg = "product was removed";}
	    			else											{outputMsg = "Could not remove product";}
	    			
    			   Alert resultAlert = new Alert(AlertType.INFORMATION);
    			   resultAlert.setTitle("Action result");
    			   resultAlert.setHeaderText(outputMsg);
    			   resultAlert.showAndWait();
    			   
    			   //update table if indeed succeded:
    			   if(response.getType().name().equals("SUCCESS"))
    			   {
	    			   ArrayList<EditableProductVIew> currProdTbl = getArrayListOfCurrentProdTable();
	    			   for(EditableProductVIew epv :currProdTbl)
	    			   {
	    				   if(epv.getID() == src.getOrigin().getID())
	    				   {
	    					   currProdTbl.remove(epv);
	    					   break;
	    				   }
	    			   }
	    			   editProductTable.getItems().clear();
	    			   editProductTable.getItems().addAll(currProdTbl);	    			   
    			   }
	    			  
	    		} else return;
		    }
		};
		
		
		//edit button:
		EventHandler<ActionEvent> prodEditAction  = new EventHandler<ActionEvent>() 
		{
			void createAndInitNewEditWIndow(ActionEvent e)
			{
				EditableProductVIewButton src = (EditableProductVIewButton)e.getSource();
				Product prod = src.getOrigin();
		    	Stage newWindow = new Stage();
		    	myClient.setUI(editProdGUI);
		    	editProdGUI.setClinet( myClient);
		    	
		    	
		    	newWindow.initOwner(FormController.primaryStage);
		    	newWindow.initModality(Modality.WINDOW_MODAL);  
		    	newWindow.setScene(editProdGUI.getScene());
		    	editProdGUI.initWindow(prod);
		    	newWindow.requestFocus();     
		    	newWindow.showAndWait();
		    	//if result from edition was success(Meaning we manged to edit it in the database):
		    	//update table
		    	if(editProdGUI.response.getType().name().equals("SUCCESS"))
		    	{
		    		ArrayList<EditableProductVIew> currProdTbl = getArrayListOfCurrentProdTable();
	    			   for(EditableProductVIew epv :currProdTbl)
	    			   {
	    				   if(epv.getID() == src.getOrigin().getID())
	    				   {
	    					   
	    					   currProdTbl.remove(epv);
	    					   epv.setName(editProdGUI.nameFIeld.getText());
	    					   epv.setType(editProdGUI.typeComboBox.getValue());
	    					   epv.setPrice(Float.parseFloat(editProdGUI.priceField.getText()));
	    					   epv.setAmount(Integer.parseInt(editProdGUI.amountFIeld.getText()));
	    					   epv.setColor(editProdGUI.colorComboBox.getValue());
	    					   currProdTbl.add(epv);
	    					   break;
	    				   }
	    			   }
	    			   editProductTable.getItems().clear();
	    			   editProductTable.getItems().addAll(currProdTbl);	
		    	}
			       
			}
		    @Override public void handle(ActionEvent e) 
		    {
		    	createAndInitNewEditWIndow(e);
		    	
		    }
		};
	    
		
		private ArrayList<EditableProductVIew> getArrayListOfCurrentProdTable()
		{
			 ArrayList<EditableProductVIew> output =new ArrayList<EditableProductVIew>();
			   for(Object ev: editProductTable.getItems().toArray())
			   {
				   EditableProductVIew eProd = (EditableProductVIew) ev;
				   output.add(eProd);
			   }
			   return output;
		}
		
//---------------------------------------------------------------------------------------
	    public void doInit()
	    {
	    	//init product table:
	    	
	    	prod_idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	    	prod_nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
	    	prod_typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
	    	prod_priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
	    	prod_amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
	    	prod_addToCatalogCol.setCellValueFactory(new PropertyValueFactory<>("AddToCatalogBtn"));
	    	prod_editCol.setCellValueFactory(new PropertyValueFactory<>("EditBtn"));
	    	prod_removeCol.setCellValueFactory(new PropertyValueFactory<>("RemoveBtn"));
	  
	    	editProductTable.getColumns().addAll(prod_idCol,prod_nameCol,prod_typeCol,prod_priceCol,prod_amountCol
	    			,prod_addToCatalogCol,prod_editCol,prod_removeCol);
	    	
	    	initProductsTableContent();
	    	editProdGUI = FormController.<EditProductGUI, AnchorPane>loadFXML(getClass().getResource("/networkGUI/EditProductGUI.fxml"), this);
            
	    	
	    	//todo: init catalog table:
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * this adds data to the table from the database
	     * and match buttons with actionevent handlers
	     */
	    public void initProductsTableContent()
	    {
	    	eProducts = getEditableProducts();
	    	editProductTable.getItems().clear();
	    	editProductTable.getItems().addAll(eProducts);
	    	
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * this sends a request to get Products and then wait for server's response
	     */
	    private void requestProductsAndWait()
	    {
	    	//this.client.handleMessageFromClientUI(new GetRequest("Product"));
	    	myClient = this.client;
	    	ProdcutController.requestProducts(this.client);
	    	waitForResponse();
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * get all products in database and transform them into "editable products"
	     * @return list of editable products(same as normal, but includes buttons for tableview)
	     */
	    private ObservableList<EditableProductVIew> getEditableProducts()
	    {
	    	requestProductsAndWait();

	    	ArrayList<EditableProductVIew> res = new ArrayList<EditableProductVIew>();
	    	ArrayList<Product> products = (ArrayList<Product>)response.getMessage();
	    	for(Product prod : products)
	    	{
	    		EditableProductVIew eProd =new EditableProductVIew(prod);
	    		
	    		eProd.getRemoveBtn().setOnAction(prodRemoveAction);
	    		eProd.getEditBtn().setOnAction(prodEditAction);
	    		res.add(eProd);
	    	}
	    	return FXCollections.observableArrayList(res);
	    }
//---------------------------------------------------------------------------------------		
	 /**
	  * As said, this functions wait for the server's reply to continue.
	  */
	    public void waitForResponse()
	 {
		 synchronized(this) 
	    	{
	    		try 
	    		{
	    			this.wait();
	    		}
	    		
	    		catch (InterruptedException e) 
	    		{
	    			e.printStackTrace();
	    		}
	    	} 
	 }
//---------------------------------------------------------------------------------------		
	    @Override
	public void display(Object message) 
	{
		this.response = (Response)message;
		synchronized(this)
		{
			this.notifyAll();
		}
		

	}

	@Override
	public void onSwitch(Client newClient) 
	{
		// TODO Auto-generated method stub
		
	}


}

	