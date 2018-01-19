package order;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.Product;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  class that holds the data to be shown in the CreateOrderGUI TableView
	*  holds the info for a custom Item  
	*/
//*************************************************************************************************
public class CustomItemView extends OrderItemView {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private static final long serialVersionUID = 1155546752934372912L;
	private ObservableList<Product> customProducts;
	
	//*************************************************************************************************
	/**
	*  Creates a new CustomItemView with the following parameters
	*  @param customItem The custom Item product information
	*  @param imageName The product image name
	*  @param imageCheckSum The product image checksum
	*  @param customProducts The custom product sub products
	*/
	//*************************************************************************************************
	public CustomItemView(Product customItem, String imageName , byte[] imageCheckSum, ObservableList<Product> customProducts)
	{
		super(customItem, imageName, imageCheckSum);
		this.customProducts = customProducts;
		this.viewBtn.setOnAction(viewCustomProductAction);
	}
	
	//*************************************************************************************************
	/**
	*  the onAction for the view button
	*  opens a new window showing the custom item information
	*/
	//*************************************************************************************************
	EventHandler<ActionEvent> viewCustomProductAction  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {
	    	Stage newWindow = new Stage();
	    	ViewCustomItemGUI viewCustomItemGUI = FormController.<ViewCustomItemGUI, AnchorPane>loadFXML(getClass().getResource("/order/ViewCustomItemGUI.fxml"), null);
	    	    	
	    	newWindow.initOwner(FormController.getPrimaryStage());
	    	newWindow.initModality(Modality.WINDOW_MODAL);  
	    	viewCustomItemGUI.setWindowStage(newWindow);
	    	newWindow.setScene(viewCustomItemGUI.getScene());
	    	viewCustomItemGUI.loadCustomItem(customProducts);
	    	newWindow.requestFocus();     
	    	newWindow.showAndWait();
	    }
	};

	//*************************************************************************************************
	/**
	*  return the custom item sub products
	*  @return an ArrayList of the custom item sub products
	*/
	//*************************************************************************************************
	public ObservableList<Product> getCustomProducts() {
		return customProducts;
	}
	
}
