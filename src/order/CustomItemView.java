package order;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import order.OrderItemViewButton;
import product.Product;
import prototype.FormController;

public class CustomItemView extends OrderItemView {

	private ObservableList<Product> customProducts;
	
	public CustomItemView(Product customItem, String imageName , byte[] imageCheckSum, ObservableList<Product> customProducts)
	{
		super(customItem, imageName, imageCheckSum);
		this.customProducts = customProducts;
		this.viewBtn.setOnAction(viewCustomProductAction);
	}
	
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

	public ObservableList<Product> getCustomProducts() {
		return customProducts;
	}
	
}
