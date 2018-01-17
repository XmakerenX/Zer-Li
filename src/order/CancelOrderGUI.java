package order;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import client.Client;
import client.ClientInterface;
import customer.Customer;
import prototype.FormController;
import serverAPI.Response;

public class CancelOrderGUI extends FormController implements ClientInterface, Observer {

	private Response replay;
	private Customer currentCustomer = null;
	
    @FXML
    private TableView<OrderView> orderTable;

    @FXML
    private TableColumn<OrderView, Number> IDCol;

    @FXML
    private TableColumn<OrderView, String> statusCol;

    @FXML
    private TableColumn<OrderView, Number> totalCol;

    @FXML
    private TableColumn<OrderView, String> creationDateCol;

    @FXML
    private TableColumn<OrderView, LocalDate> orderDateCol;

    @FXML
    private TableColumn<OrderView, Button> delivaryInfoCol;

    @FXML
    private TableColumn<OrderView, String> payMethodPol;

    @FXML
    private TableColumn<OrderView, Number> storeIDCol;

    @FXML
    private TableColumn<OrderView, Button> viewProductCol;

    @FXML
    private TableColumn<OrderView, Button> cancelCol;

    @FXML
    private Button backBtn;

    @FXML
    void onBack(ActionEvent event) {
    	Client.client.setUI((ClientInterface)parent);
    	FormController.primaryStage.setScene(parent.getScene());
    }

 //*************************************************************************************************
    /**
  	*  Called by FXMLLoader on class initialization 
  	*/
//*************************************************************************************************
    @FXML
    public void initialize(){
        //Will be called by FXMLLoader
    	InitTableView();
    	
    	//createOrderGUI = FormController.<CreateOrderGUI, AnchorPane>loadFXML(getClass().getResource("/order/CreateOrderGUI.fxml"), this);
    }
    
  //*************************************************************************************************
    /**
  	*  Initializes the Table View to be compatible with the product class (get and set class values)
  	*/
//*************************************************************************************************
    private void InitTableView()
    {
    	IDCol.setCellValueFactory( new PropertyValueFactory<OrderView,Number>("ID"));
    	statusCol.setCellValueFactory( new PropertyValueFactory<OrderView,String>("Status"));
    	totalCol.setCellValueFactory( new PropertyValueFactory<OrderView,Number>("Price"));
    	creationDateCol.setCellValueFactory( new PropertyValueFactory<OrderView,String>("CreationDateTime")); 
    	orderDateCol.setCellValueFactory( new PropertyValueFactory<OrderView,LocalDate>("RequiredDateTime")); 
    	delivaryInfoCol.setCellValueFactory( new PropertyValueFactory<OrderView,Button>("viewInfoButton"));
    	payMethodPol.setCellValueFactory( new PropertyValueFactory<OrderView,String>("orderPaymentMethod"));
    	storeIDCol.setCellValueFactory( new PropertyValueFactory<OrderView,Number>("orderOriginStore")); 
    	viewProductCol.setCellValueFactory( new PropertyValueFactory<OrderView,Button>("viewProductsButton"));
    	cancelCol.setCellValueFactory( new PropertyValueFactory<OrderView,Button>("cancelButton"));
    }
    
    public void onRefresh(ActionEvent event) {
    	OrderController.requestCustomerOrders(currentCustomer.getID());
    	
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
		
		if (replay != null)
		{
			if (replay.getType() == Response.Type.SUCCESS)
			{
				ArrayList<Order> orders = (ArrayList<Order>)replay.getMessage();
				final ObservableList<OrderView> observableOrders = FXCollections.observableArrayList();
				
				for (Order o : orders)
				{
					try {
						OrderView newO = new OrderView(o);
						observableOrders.add(newO);
						newO.getObservableCancelButton().addObserver(this);
						newO.getObservableViewProductsButton().addObserver(this);
					} catch (OrderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				orderTable.setItems(observableOrders);

				
			}
		}
    }
    
	@Override
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
	public void onSwitch(Client newClient) {
		// TODO Auto-generated method stub

	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
	}
	
	//*************************************************************************************************
    /**
  	*  Triggered by the observable cancel button in the table to indicate what order to cancel form
  	*  the table
  	*  @param o the Observable button triggering this method
  	*  @param arg the item to remove from the table
  	*/
//*************************************************************************************************
	@Override
	public void update(Observable o, Object arg)
	{
		OrderItemViewButton b = (OrderItemViewButton)o;
		if (b.getButtonText().equals("Cancel"))
		{
			Alert alert = new Alert(AlertType.CONFIRMATION, "",ButtonType.YES, ButtonType.NO);
			alert.setHeaderText("About to cancel order");
			alert.setContentText("Are you sure you want to cancel the order?");
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			ButtonType result = alert.showAndWait().get();
			if (result == ButtonType.YES)
			{
				OrderView orderItem = (OrderView)arg;
				OrderController.cancelOrder(orderItem.getID());

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

				if (replay != null)
				{
					if (replay.getType() == Response.Type.SUCCESS)
					{
						alert = new Alert(AlertType.INFORMATION);
						alert.setHeaderText("Sucesss");
						alert.setContentText("Order canceled successfully");
						alert.showAndWait();
						this.orderTable.getItems().remove(orderItem);
					}
					else
					{
						alert = new Alert(AlertType.ERROR);
						alert.setHeaderText("Failure");
						alert.setContentText("Order was not canceled");
						alert.showAndWait();
					}

				}

			}
		}
		
		if (b.getButtonText().equals("View Products"))
		{
			OrderView orderItem = (OrderView)arg;
			OrderController.getOrderProducts(orderItem.getID());
			
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
			
			if (replay != null)
			{
				if (replay.getType() == Response.Type.SUCCESS)
				{
					ArrayList<ProductInOrder> prodcutsInOrder = (ArrayList<ProductInOrder>)replay.getMessage();
					
					Stage newWindow = new Stage();
					ViewProductInOrder viewProductsInOrder = FormController.<ViewProductInOrder, AnchorPane>loadFXML(getClass().getResource("/order/ViewProductsInOrder.fxml"), null);

					newWindow.initOwner(FormController.getPrimaryStage());
					newWindow.initModality(Modality.WINDOW_MODAL);  
					newWindow.setScene(viewProductsInOrder.getScene());
					viewProductsInOrder.loadProducts(prodcutsInOrder);
					viewProductsInOrder.setWindowStage(newWindow);
					newWindow.requestFocus();     
					newWindow.showAndWait();
					
				}
			}

		}
	}
	

}
