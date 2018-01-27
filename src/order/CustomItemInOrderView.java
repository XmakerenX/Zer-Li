package order;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.Product;
import prototype.FormController;


//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the customItems TableView  
	*/
//*************************************************************************************************
public class CustomItemInOrderView extends CustomItemInOrder 
{

	private static final long serialVersionUID = -4482492688342154169L;
	private Button viewBtn;
	private TextArea nameArea;
	private TextArea greetingCard;
	
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
    		viewCustomItemGUI.loadCustomItem(FXCollections.observableArrayList(getComponents()));
	    	newWindow.requestFocus();     
	    	newWindow.showAndWait();
	    }
	};
	/**
	 * this is how a custom item will look in an order
	 * @param customItemID	it's id
	 * @param type			it's type
	 * @param price			the custom item price
	 * @param color			the custom item dominate color
	 * @param greetingCard	the custom item greeting card
	 * @param components    the products that makes this custom item
	 */
	public CustomItemInOrderView(long customItemID, String type, float price, String color, String greetingCard,
			ArrayList<Product> components) 
	{
		super(customItemID, type, price, color, greetingCard, components);
		viewBtn = new Button("view");
		viewBtn.setUserData(viewBtn);
		viewBtn.setOnAction(viewCustomProductAction);
		
		nameArea = new TextArea();
		nameArea.setText("Custom");
		nameArea.setWrapText(true);
		nameArea.setPrefHeight(64);
		nameArea.setEditable(false);
		this.greetingCard = new TextArea();
		this.greetingCard.setWrapText(true);
		this.greetingCard.setPrefHeight(64);
		this.greetingCard.setText(greetingCard);
		this.greetingCard.setEditable(false);
	}
	
	public CustomItemInOrderView(CustomItemInOrder customItem)
	{
		this(customItem.getCustomItemID(), ""+customItem.getType(), customItem.getPrice(),customItem.getColor(),
				customItem.getGreetingCard(),customItem.getComponents());
	}

	public Button getViewBtn() {
		return viewBtn;
	}

	public void setViewBtn(Button viewBtn) {
		this.viewBtn = viewBtn;
	}

	public TextArea getNameArea() {
		return nameArea;
	}

	public void setNameArea(TextArea nameArea) {
		this.nameArea = nameArea;
	}

	public TextArea getGreetingCardText() {
		return greetingCard;
	}

	public void setGreetingCardText(TextArea greetingCard) {
		this.greetingCard = greetingCard;
	}
	
	
	
}
