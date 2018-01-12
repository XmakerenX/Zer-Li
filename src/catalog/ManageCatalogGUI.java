package catalog;

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
import networkGUI.NetworkWorkerGUI;
import product.EditableProductVIew.EditableProductVIewButton;
import product.*;
import prototype.FormController;
import serverAPI.GetRequest;
import serverAPI.RemoveRequest;
import serverAPI.Response;
import user.LoginGUI;
import user.User;
import user.UserController;
import user.User.Permissions;


public class ManageCatalogGUI extends FormController implements ClientInterface
{
	
	    ClientInterface ManageCatInterface = this;
		Response response;
		Client myClient;
		User myUser;
		int storeID;
		
		//Gui:
		EditProductGUI editProdGUI;
		AddToCatalogGUI addToCatGUI;
		
		
		
		 @FXML
		  private Button newProdBtn;
		
		    @FXML
		    private Button backBTN;

		   

		   
		 
		//---------------Tables:--------------------------
									
									
								//editable catalog view:
								   @FXML
								    private TableView editCatalogView;
								   
										    TableColumn cat_imageCol = new TableColumn("Image");
										    TableColumn cat_nameCol = new TableColumn("Name");
										    TableColumn cat_priceCol = new TableColumn("Price");
										    TableColumn cat_salesPriceCol = new TableColumn("sale price");
										    TableColumn cat_editCol = new TableColumn("");
										    TableColumn cat_removeCol = new TableColumn("");
										    TableColumn cat_addSaleCol = new TableColumn("");
										    TableColumn cat_removeSaleCol = new TableColumn("");
										    
										    
											ObservableList<EditableCatalogItem> eCatalogProducts; //table's data

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
			    
			
	   /*
	    * return to the previous window			   
	    */
	   @FXML
	    void onBackBTN(ActionEvent event) 
	    {
		    this.client.setUI((NetworkWorkerGUI)this.parent);
	    	FormController.primaryStage.setScene(this.parent.getScene());
	    }
		/**
		 * Opens a new product creation window								    
		 * @param event
		 */
	    @FXML
	    void newProdBtn(ActionEvent event) 
	    {
	    	NewProductCreationGUI createProductGUI = FormController.<NewProductCreationGUI, AnchorPane>loadFXML(getClass().getResource("/product/NewProductCreation.fxml"), this);
			if (createProductGUI != null)
			{
				Stage newWindow = new Stage();
				getClient().setUI(createProductGUI);
				createProductGUI.setClinet(client);
				newWindow.initOwner(FormController.primaryStage);
		    	newWindow.initModality(Modality.WINDOW_MODAL);  
				newWindow.setScene(createProductGUI.getScene());
				newWindow.showAndWait();
				getClient().setUI(ManageCatInterface);
			}
			Product newProd = new Product(0, null, null, 0, 0, null);
			
			newProd.setID(Long.parseLong(createProductGUI.idField.getText()));
			newProd.setName(createProductGUI.nameFIeld.getText());
			newProd.setType(createProductGUI.typeComboBox.getValue());
			newProd.setPrice(Float.parseFloat(createProductGUI.priceField.getText()));
			newProd.setAmount(Integer.parseInt(createProductGUI.amountFIeld.getText()));
			newProd.setColor(createProductGUI.colorComboBox.getValue());
			EditableProductVIew epv = new EditableProductVIew(newProd);
			epv.getRemoveBtn().setOnAction(prodRemoveAction);
			epv.getEditBtn().setOnAction(prodEditAction);
			epv.getAddToCatalogBtn().setOnAction(prodAddToCatalog);
            
			
			ArrayList<EditableProductVIew> currProdTbl = getArrayListOfCurrentProdTable();
			currProdTbl.add(epv);
		   editProductTable.getItems().clear();
		   editProductTable.getItems().addAll(currProdTbl);	
	    }	  
	    
	    //Add to catalog button:
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
		
		/*
		 * function to allow access of client even in evene handlers
		 */
		private Client getClient()
		{
			return this.client;
		}
		//edit button:
		EventHandler<ActionEvent> prodEditAction  = new EventHandler<ActionEvent>() 
		{
			void createAndInitNewEditWIndow(ActionEvent e)
			{
				EditableProductVIewButton src = (EditableProductVIewButton)e.getSource();
				Product prod = src.getOrigin();
				Stage newWindow = new Stage();
		    	
		    	getClient().setUI(editProdGUI);
		    	
		    	editProdGUI.setClinet( myClient);
		    	newWindow.initOwner(FormController.primaryStage);
		    	newWindow.initModality(Modality.WINDOW_MODAL);  
		    	newWindow.setScene(editProdGUI.getScene());
		    	editProdGUI.initWindow(prod);
		    	newWindow.requestFocus();     
		    	newWindow.showAndWait();
		    	
		    	getClient().setUI(ManageCatInterface);
		    	
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
		
		 //add to catalog button:
		EventHandler<ActionEvent> prodAddToCatalog  = new EventHandler<ActionEvent>() 
		{
		    @Override public void handle(ActionEvent e) 
		    {
		    	
		    	EditableProductVIewButton src = (EditableProductVIewButton)e.getSource();
    			Product prod = src.getOrigin();
    			
				if (addToCatGUI != null)
				{
					Stage newWindow = new Stage();
					getClient().setUI(addToCatGUI);
					addToCatGUI.setClinet(client);
					addToCatGUI.setProd(prod);
					newWindow.initOwner(FormController.primaryStage);
			    	newWindow.initModality(Modality.WINDOW_MODAL);  
					newWindow.setScene(addToCatGUI.getScene());
					newWindow.showAndWait();
					getClient().setUI(ManageCatInterface);
				}
		    }
		};		
//---------------------------------------------------------------------------------------
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
		public void setUser(User user)
		{
			this.myUser = user;
		}
		
 private int getStoreIdOfWorker(User thisUser)
 {
		//get storeID;
		UserController.getStoreOfEmployee(thisUser.getUserName(), this.client);
		waitForResponse();
		
		if(response.getType().name().equals("SUCCESS"))
		{
			return(int)response.getMessage();
		}
		else
			return 0;
 }
//---------------------------------------------------------------------------------------
		public void doInit(User user)
	    {
		
			this.setUser(user);
			
			
			//basically, we init all compoments and we disabled some based on the type 
			//of user that uses the "manage catalog option"
			
        	//init product table coloumns:	
	    	prod_idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
	    	prod_nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
	    	prod_typeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
	    	prod_priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
	    	prod_amountCol.setCellValueFactory(new PropertyValueFactory<>("Amount"));
	    	prod_addToCatalogCol.setCellValueFactory(new PropertyValueFactory<>("AddToCatalogBtn"));
	    	prod_editCol.setCellValueFactory(new PropertyValueFactory<>("EditBtn"));
	    	prod_removeCol.setCellValueFactory(new PropertyValueFactory<>("RemoveBtn"));
	    	
	    	editProdGUI = FormController.<EditProductGUI, AnchorPane>loadFXML(getClass().getResource("/product/EditProductGUI.fxml"), this);
	    	addToCatGUI = FormController.<AddToCatalogGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/AddToCatalog.fxml"), this);
	    	
	    	//init catalog table:
	    	cat_imageCol.setCellValueFactory(new PropertyValueFactory<>("ImageView"));
		    cat_nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		    cat_priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		    cat_salesPriceCol.setCellValueFactory(new PropertyValueFactory<>("SalePrice"));
		    cat_editCol.setCellValueFactory(new PropertyValueFactory<>("EditButton"));
		    cat_removeCol.setCellValueFactory(new PropertyValueFactory<>("RemoveButton"));
		    cat_addSaleCol.setCellValueFactory(new PropertyValueFactory<>("AddSale"));
		    cat_removeSaleCol.setCellValueFactory(new PropertyValueFactory<>("RemoveFromSale"));
	    	
		    
			editCatalogView.getColumns().addAll(cat_imageCol,cat_nameCol,cat_priceCol,cat_salesPriceCol,
					cat_editCol,cat_removeCol,cat_addSaleCol,cat_removeSaleCol);

			switch(myUser.getUserPermission())
			{
		    case STORE_MANAGER:
				this.newProdBtn.setVisible(false);//only pure network worker can add a new product
				editProductTable.getColumns().addAll(prod_idCol,prod_nameCol,prod_typeCol,prod_priceCol,prod_amountCol
		    			,prod_addToCatalogCol);
				break;
				
		    	
		    case STORE_WORKER:
				this.newProdBtn.setVisible(false);//only pure network worker can add a new product
				editProductTable.getColumns().addAll(prod_idCol,prod_nameCol,prod_typeCol,prod_priceCol,prod_amountCol
		    			,prod_addToCatalogCol);
				
		    	
		    	break;
		    	
		    default:
		    	editProductTable.getColumns().addAll(prod_idCol,prod_nameCol,prod_typeCol,prod_priceCol,prod_amountCol
		    			,prod_addToCatalogCol,prod_editCol,prod_removeCol);
		    	break;
				
			}
	    	
			storeID = 0; 
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			initProductsTableContent();
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			initCatalogProductsTableContent();
			
			
	    	//addToCatGUI.doInit();rem
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
	    public void initCatalogProductsTableContent()
	    {
	    	eCatalogProducts = getEditableCatalogProducts();
	    	editCatalogView.getItems().clear();
	    	editCatalogView.getItems().addAll(eCatalogProducts);
	    }
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
	    private ObservableList<EditableCatalogItem> getEditableCatalogProducts()
	    {
	    	CatalogController.requestCatalogItems(this.client);
	    	waitForResponse();
	    	
	    	ArrayList<EditableCatalogItem> res = new ArrayList<EditableCatalogItem>();
	    	ArrayList<CatalogItem> catalogProducts = (ArrayList<CatalogItem>)response.getMessage();
	    	for(CatalogItem catItem : catalogProducts)
	    	{
	    		int catalogProductStoreID = catItem.getStoreID();
	    		if(( catalogProductStoreID == 0 ) || (catalogProductStoreID==this.storeID))
	    		{
	    			EditableCatalogItem eCatProd =new EditableCatalogItem(catItem);
		    		
	    			//add functions for buttons:
	    			eCatProd.getRemoveButton().setOnAction(null);
	    			eCatProd.getEditButton().setOnAction(null);
	    			eCatProd.getAddSale().setOnAction(null);
		    		res.add(eCatProd);
	    		}
	    		
	    	}
	    	return FXCollections.observableArrayList(res);
	    	
	    }
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
	    		eProd.getAddToCatalogBtn().setOnAction(prodAddToCatalog);
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
			this.notify();
		}
		

	}

	@Override
	public void onSwitch(Client newClient) 
	{
		// TODO Auto-generated method stub
		
	}


}

	