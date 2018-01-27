package catalog;

import java.util.ArrayList;
import java.util.Optional;
import catalog.EditableCatalogItemView.editableCatalogItemViewButton;
import client.Client;
import client.ClientInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.EditableProductView.EditableProductViewButton;
import product.*;
import prototype.FormController;
import serverAPI.Response;
import user.User;
import utils.ImageData;

//*************************************************************************************************
	/**
	*  Provides a GUI to manage a store's catalog, network products and base catalog
	*/
//*************************************************************************************************
public class ManageCatalogGUI extends FormController implements ClientInterface
{
	
	    ClientInterface ManageCatInterface = this;
		Response response;
		//Client myClient;
		User myUser;
		int storeID;
		int employeeStoreID;
		
		public int getEmployeeStoreID() {
			return employeeStoreID;
		}
		public void setEmployeeStoreID(int storeID) {
			this.employeeStoreID = storeID;
		}

		//GUI:
		EditProductGUI editProdGUI;
		AddToCatalogGUI addToCatGUI;
		EditCatalogItemGUI editCatItemGui;

		
		
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
		    
		    
			ObservableList<EditableCatalogItemView> eCatalogProducts; //table's data

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
    
   ObservableList<EditableProductView> eProducts; //table's data

		
					
			    
//action events for product buttons:
			    
			
   /**
    * Returns to the previous window
    * @param event - "Back" button is clicked
    */
	   @FXML
	    void onBackBTN(ActionEvent event) 
	    {
		    this.client.setUI((ClientInterface)this.parent);
	    	FormController.primaryStage.setScene(this.parent.getScene());
	    	
	    }
		/**
		 * Opens a new product creation window								    
		 * @param event - "New product" button is clicked
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
				createProductGUI.setWindowStage(newWindow);
				newWindow.showAndWait();
				getClient().setUI(ManageCatInterface);
			}
			   initProductsTableContent();
	    }	  
	    
	 
		/**
		 * function to allow access of client even in event handlers
		 */
		private Client getClient()
		{
			return Client.client;
		}
		/**
		 * function to allow setting of user
		 */
		public void setUser(User user)
		{
			this.myUser = user;
		}
		
		//proodTable button handlers:
		
		//edit button:
		EventHandler<ActionEvent> prodEditAction  = new EventHandler<ActionEvent>() 
		{
			void createAndInitNewEditWIndow(ActionEvent e)
			{
				EditableProductViewButton src = (EditableProductViewButton)e.getSource();
				Product prod = src.getOrigin();
				Stage newWindow = new Stage();
		    	
		    	getClient().setUI(editProdGUI);
		    	
		    	editProdGUI.setClinet( Client.client );
		    	newWindow.initOwner(FormController.primaryStage);
		    	newWindow.initModality(Modality.WINDOW_MODAL);  
		    	newWindow.setScene(editProdGUI.getScene());
		    	editProdGUI.initWindow(prod);
		    	newWindow.requestFocus();     
		    	newWindow.showAndWait();
		    	
		    	getClient().setUI(ManageCatInterface);
		    	
		    	//if result from edition was success(Meaning we managed to edit it in the database):
		    	//update table
		    	if(editProdGUI.response.getType().name().equals("SUCCESS"))
		    	{
		    		ArrayList<EditableProductView> currProdTbl = getArrayListOfCurrentProdTable();
		    		initProductsTableContent();
	    			 
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
		    	Boolean itemDoesAlreadyExists = false;
		    	EditableProductViewButton src = (EditableProductViewButton)e.getSource();
    			Product prod = src.getOrigin();
    			
    			// search for the item in catalog:
    			for(EditableCatalogItemView element : eCatalogProducts)
    			{
    				if(element.getID() == prod.getID())
    				{
    					itemDoesAlreadyExists = true;
    				}
    			}
    			if(itemDoesAlreadyExists)
    				showErrorMessage("Selected product already appears in the catalog!");
    			
    			else
    			{
					if (addToCatGUI != null)
					{
						Stage newWindow = new Stage();
						getClient().setUI(addToCatGUI);
						addToCatGUI.setClinet(client);
						addToCatGUI.setProd(prod);
						addToCatGUI.setStoreID(storeID);
						addToCatGUI.doInit();
						newWindow.initOwner(FormController.primaryStage);
				    	newWindow.initModality(Modality.WINDOW_MODAL);  
						newWindow.setScene(addToCatGUI.getScene());
						newWindow.showAndWait();
						getClient().setUI(ManageCatInterface);
						CatalogItem newCatalogItem = addToCatGUI.getCatItem();
					}
					initCatalogProductsTableContent();
					
    			}
		    }
		};		
		//remove product button:
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
			    			EditableProductViewButton src = (EditableProductViewButton)e.getSource();
			    			String id =Long.toString(src.getOrigin().getID());
		    				ProdcutController.removeProductFromDataBase(id, Client.client);
		    				
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
			    			   eCatalogProducts = getEditableCatalogProducts();
			    			   
			    			   for(EditableCatalogItemView eci: eCatalogProducts)
			    			   {
			    				   if(Long.toString(eci.getID()).equals(id))
			    				   {
			    				    CatalogController.removeCatalogProductFromDataBase(eci.getID(), eci.getStoreID(), Client.client);
			    				   }	    				   
			    			   }
			    			   initProductsTableContent();
			    			   initCatalogProductsTableContent();
		    			   }
			    			  
			    		} else return;
				    }
				};
				
		
		//Catalog buttons handler:
		
				EventHandler<ActionEvent> catRemoveFromCatalog  = new EventHandler<ActionEvent>() 
				{
				    @Override public void handle(ActionEvent e) 
				    {
				    	
				    	//get selected Item as object:
				    	editableCatalogItemViewButton buttonPressed =(editableCatalogItemViewButton) e.getSource();
				    	CatalogItem selectedItem = buttonPressed.origin;
				    	
				    	Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
				    	alert.setContentText("About to remove the selected from the catalog\n Are you sure?");
			    		Optional<ButtonType> result = alert.showAndWait();
			    		
			    		if (result.get() == ButtonType.OK)
						{
			    			long prodID = selectedItem.getID();
			    			int storeID = selectedItem.getStoreID();
							CatalogController.removeCatalogProductFromDataBase(prodID, storeID, Client.client);
							
							//wait for server Response:
							waitForResponse();
							
							//Display server result to user
							String outputMsg;
			    			if(response.getType().name().equals("SUCCESS")) {outputMsg = "catalog product was removed";}
			    			else											{outputMsg = "Could not remove catalog product";}
			    			
		    			   Alert resultAlert = new Alert(AlertType.INFORMATION);
		    			   resultAlert.setTitle("Action result");
		    			   resultAlert.setHeaderText(outputMsg);
		    			   resultAlert.showAndWait();
		    			   

		    			   //update table:
		    			   initCatalogProductsTableContent();
						}
			    		
				    }
				};
				
				EventHandler<ActionEvent> editCatalogItem  = new EventHandler<ActionEvent>() 
				{
				    @Override public void handle(ActionEvent e) 
				    {
				        	editableCatalogItemViewButton button = (editableCatalogItemViewButton)e.getSource();
				        	EditableCatalogItemView baseCatalogProduct = button.origin;
				        
							if (editCatItemGui != null)
							{
								Stage newWindow = new Stage();
								getClient().setUI(editCatItemGui);
								editCatItemGui.setClinet(client);
								editCatItemGui.setProd(baseCatalogProduct.getBaseProduct());
								editCatItemGui.setStoreID(storeID);
								
								editCatItemGui.getCatalogItemImage().setImage(baseCatalogProduct.getImageView().getImage());
								editCatItemGui.setImageField("Cache:\\"+baseCatalogProduct.getImageName());
								editCatItemGui.doInit();
								editCatItemGui.initWindow(baseCatalogProduct);
								editCatItemGui.setStage(newWindow);
								newWindow.initOwner(FormController.primaryStage);
						    	newWindow.initModality(Modality.WINDOW_MODAL);  
								newWindow.setScene(editCatItemGui.getScene());
								newWindow.showAndWait();
								getClient().setUI(ManageCatInterface);
							}
							initCatalogProductsTableContent();
									
		    			}
				    
				};		
		//---------------------------------------------------------------------------------------
		private ArrayList<EditableProductView> getArrayListOfCurrentProdTable()
		{
			 ArrayList<EditableProductView> output =new ArrayList<EditableProductView>();
			   for(Object ev: editProductTable.getItems().toArray())
			   {
				   EditableProductView eProd = (EditableProductView) ev;
				   output.add(eProd);
			   }
			   return output;
		}
//---------------------------------------------------------------------------------------
		/**
		 * Initiates window's fields and tables
		 * @param user - current logged in user
		 */
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
	    	editCatItemGui= FormController.<EditCatalogItemGUI, AnchorPane>loadFXML(getClass().getResource("/catalog/EditCatalogItem.fxml"), this);
	    	
	    	
	    	//init catalog table:
	    	cat_imageCol.setCellValueFactory(new PropertyValueFactory<>("ImageView"));
		    cat_nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		    cat_priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		    cat_salesPriceCol.setCellValueFactory(new PropertyValueFactory<>("StringSalePrice"));
		    cat_editCol.setCellValueFactory(new PropertyValueFactory<>("EditButton"));
		    cat_removeCol.setCellValueFactory(new PropertyValueFactory<>("RemoveButton"));
		 
		    
			editCatalogView.getColumns().addAll(cat_imageCol,cat_nameCol,cat_priceCol,cat_salesPriceCol,
					cat_editCol,cat_removeCol);

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
			
			initCatalogProductsTableContent();
			
				    	
	    	
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * this adds data to the table from the database
	     * and match buttons with action event handlers
	     */
	    public void initProductsTableContent()
	    {
	    	eProducts = getEditableProducts();
	    	editProductTable.getItems().clear();
	    	editProductTable.getItems().addAll(eProducts);
	    	
	    }
//---------------------------------------------------------------------------------------
	   /**
	     * this adds data to the catalog table from the database
	     * and match buttons with action event handlers
	    */
	    public void initCatalogProductsTableContent()
	    {
	    	eCatalogProducts = getEditableCatalogProducts();
	    	
	    	ArrayList<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
	    	catalogItems.addAll(eCatalogProducts);
	    	downloadMissingCatalogImages(catalogItems);
	    	
	    	eCatalogProducts = getEditableCatalogProducts();

	    	
	    	editCatalogView.getItems().clear();
	    	editCatalogView.getItems().addAll(eCatalogProducts);
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * this sends a request to get Products and then wait for server's response
	     */
	    private void requestProductsAndWait()
	    {
	    	//this.client.handleMessageFromClientUI(new GetRequest("Product"));
	    	ProdcutController.requestProducts(Client.client);
	    	waitForResponse();
	    }
//---------------------------------------------------------------------------------------
	    /**
	     * get all catalogProducts in database and transform them into "editable catalogProducts"
	     * @return list of editable products(same as normal, but includes buttons for tableview)
	     */
	    private ObservableList<EditableCatalogItemView> getEditableCatalogProducts()
	    {
	    	CatalogController.requestCatalogItems(this.client);
	    	waitForResponse();
	    	
	    	ArrayList<EditableCatalogItemView> res = new ArrayList<EditableCatalogItemView>();
	    	ArrayList<CatalogItem> catalogProducts = (ArrayList<CatalogItem>)response.getMessage();
	    	for(CatalogItem catItem : catalogProducts)
	    	{
	    		int catalogProductStoreID = catItem.getStoreID();
	    		if(( catalogProductStoreID == 0 ) || (catalogProductStoreID==this.employeeStoreID))
	    		{
	    			EditableCatalogItemView eCatProd =new EditableCatalogItemView(catItem);
		    		
	    			//add functions for buttons:
	    			eCatProd.getRemoveButton().setOnAction(catRemoveFromCatalog);
	    			eCatProd.getEditButton().setOnAction(editCatalogItem);
		    		res.add(eCatProd);
	    		}	
	    	}
	    	return FXCollections.observableArrayList(res);
	    	
	    }
	    /**
	     * get all products in database and transform them into "editable products"
	     * @return list of editable products(same as normal, but includes buttons for tableview)
	     */
	    private ObservableList<EditableProductView> getEditableProducts()
	    {
	    	requestProductsAndWait();

	    	ArrayList<EditableProductView> res = new ArrayList<EditableProductView>();
	    	ArrayList<Product> products = (ArrayList<Product>)response.getMessage();
	    	for(Product prod : products)
	    	{
	    		EditableProductView eProd =new EditableProductView(prod);
	    		
	    		eProd.getRemoveBtn().setOnAction(prodRemoveAction);
	    		eProd.getEditBtn().setOnAction(prodEditAction);
	    		eProd.getAddToCatalogBtn().setOnAction(prodAddToCatalog);
	    		res.add(eProd);
	    	}
	    	return FXCollections.observableArrayList(res);
	    }
//---------------------------------------------------------------------------------------		
	    /**
	     * Downloads and fills the missing item's images for client
	     * @param catalogItemsSet - set of catalog items 
	     */
	    @SuppressWarnings("unchecked")
		private void downloadMissingCatalogImages(ArrayList<CatalogItem> catalogItemsSet)
	    {
	    	ArrayList<String> missingImages = CatalogController.scanForMissingCachedImages(catalogItemsSet);
			if (missingImages.size() > 0)
			{
				System.out.println("Missing images "+ missingImages);
				response = null;
				CatalogController.requestCatalogImages(missingImages, Client.client);

				waitForResponse();
				
	    		if (response != null)
	    			CatalogController.saveCatalogImages((ArrayList<ImageData>)response.getMessage());
			}
	    }
//----------------------------------------------------------------------
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

	