package order;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Observable;

import catalog.CatalogItemView;
import client.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.EditProductGUI;
import product.CatalogItem;
import product.Product;
import product.CatalogItem.ImageInfo;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the items to order TableView  
	*/
//*************************************************************************************************
public class OrderItemView extends CatalogItem {

	//private final String 
	private ImageView image;
	private TextArea nameArea;
	protected OrderItemViewButton removeBtn;
	protected Button viewBtn;
	private TextArea greetingCard;
	private WebView salePriceView;
		
	EventHandler<ActionEvent> viewProductAction  = new EventHandler<ActionEvent>() 
	{
	    @Override public void handle(ActionEvent e) 
	    {
	    	Button b = (Button)e.getSource();
	    	
	    	Product prod = (Product)b.getUserData();
	    	Stage newWindow = new Stage();
	    	EditProductGUI editProdGUI = FormController.<EditProductGUI, AnchorPane>loadFXML(getClass().getResource("/product/EditProductGUI.fxml"), null);
	    	Client.client.setUI(editProdGUI);
	    	    	
	    	newWindow.initOwner(FormController.getPrimaryStage());
	    	newWindow.initModality(Modality.WINDOW_MODAL);  
	    	newWindow.setScene(editProdGUI.getScene());
	    	editProdGUI.initWindow(prod);
	    	newWindow.requestFocus();     
	    	newWindow.showAndWait();
	    }
	};
	
	public OrderItemView(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName , byte[] imageCheckSum)
	{
		super(productID, productName, productType, productPrice, productAmount, productColor, salesPrice, imageName, imageCheckSum,0);
		
		nameArea = new TextArea();
		nameArea.setText(productName);
		nameArea.setWrapText(true);
		nameArea.setPrefHeight(64);
		nameArea.setEditable(false);
		greetingCard = new TextArea();
		greetingCard.setWrapText(true);
		greetingCard.setPrefHeight(64);
		removeBtn = new OrderItemViewButton(this, "remove");
		viewBtn = new Button("view");
		viewBtn.setUserData(this);
		viewBtn.setOnAction(viewProductAction);
		
		if (imageName != null)
		{
			File file = new File(imageName);
			image = new ImageView(file.toURI().toString());
			image.setFitWidth(64);
			image.setFitHeight(64);
		}
		
		salePriceView = new WebView();
		salePriceView.setPrefSize(75, 75);
		if (getSalePrice() > 0)
		{
			WebEngine engine = salePriceView.getEngine();
			//  <font size="6">This is some text!</font> 
			// <font color="red">This is some text!</font> getSalePrice()
			float precntage = ((productPrice - getSalePrice()) / productPrice)*100;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			engine.loadContent("<font size=\"2\"><del>"+productPrice+"₪</del></font>" 
			+ " <font size=\"4\">" + getSalePrice() + "₪</font>"
			+ " <font color=\"green\" ,size=\"4\">"+ "(-" + df.format(precntage)+"%)" + "</font>");
		}
		else
		{
			WebEngine engine = salePriceView.getEngine();
			engine.loadContent("<font size=\"4\">"+productPrice+"₪</font>");
		}
	}
	
	public OrderItemView(CatalogItemView catalogItemView)
	{
		this(catalogItemView.getID(), catalogItemView.getName(), catalogItemView.getType(), catalogItemView.getPrice(),
				catalogItemView.getAmount(), catalogItemView.getColor(), catalogItemView.getSalePrice(), null, null);
		
		this.setImageInfo(new ImageInfo(catalogItemView.getImageName(), catalogItemView.getImageChecksum()));
		
		if (catalogItemView.getImage() != null)
		{
			image = catalogItemView.getImage();
		}
	}
	
	public OrderItemView(Product customItem, String imageName , byte[] imageCheckSum)
	{
		this(customItem.getID(), customItem.getName(), customItem.getType(), customItem.getPrice(),
				customItem.getAmount(), customItem.getColor(), 0, imageName, imageCheckSum);
	}
	
	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public Button getRemoveBtn() {
		return removeBtn.getButton();
	}

	public void setRemoveBtn(Button button) {
		this.removeBtn.setButton(button);
	}
	
	public OrderItemViewButton getObservableRemoveButton() {
		return removeBtn;
	}
	
	public TextArea getGreetingCard() {
		return greetingCard;
	}

	public void setGreetingCardText(String text)
	{
		this.greetingCard.setText(text);
	}
	
	public void setGreetingCard(TextArea greetingCard) {
		this.greetingCard = greetingCard;
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

	public WebView getSalePriceView() {
		return salePriceView;
	}

	public void setSalePriceView(WebView salePriceView) {
		this.salePriceView = salePriceView;
	}
	
	
	
}
