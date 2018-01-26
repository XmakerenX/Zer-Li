package order;

import java.io.File;
import java.text.DecimalFormat;
import catalog.CatalogItemView;
import client.Client;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import product.EditProductGUI;
import product.CatalogItem;
import product.Product;
import prototype.FormController;

//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the items to order TableView  
	*/
//*************************************************************************************************
public class OrderItemView extends CatalogItem {

	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private static final long serialVersionUID = 759292362526572518L;
	private ImageView image;
	private TextArea nameArea;
	protected Button removeBtn;
	protected Button viewBtn;
	private TextArea greetingCard;
	private WebView salePriceView;
	
	//*************************************************************************************************
	/**
	*  Handles the button press of the view button inside the orderItemView  
	*/
	//*************************************************************************************************
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
	    	editProdGUI.initWindowReadOnly(prod);
	    	editProdGUI.setWindowStage(newWindow);
	    	newWindow.requestFocus();     
	    	newWindow.showAndWait();
	    }
	};
	
	//*************************************************************************************************
	/**
	*  Creates a new OrderItemView with the following parameters
	*  @param productID The product ID
	*  @param productName The product name
	*  @param productType The product Type
	*  @param productPrice The product price
	*  @param productAmount The product amount
	*  @param productColor The product dominate color
	*  @param salesPrice The product sale price
	*  @param imageName The product Image name
	*  @param imageCheckSum The image checksum
	*/
	//*************************************************************************************************
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

		greetingCard.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, 
					String newValue) 
			{
				int inputLength = newValue.length();
				if(inputLength <= 100) 
					greetingCard.setText(newValue);
				else
					greetingCard.setText(oldValue);
			}
		});
		
		removeBtn = new Button("remove");
		removeBtn.setUserData(this);
		//removeBtn = new OrderItemViewButton(this, "remove");
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
			float precntage = ((productPrice - getSalePrice()) / productPrice)*100;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			engine.loadContent("<font size=\"2\"><del>"+productPrice+"€</del></font>" 
			+ " <font size=\"4\">" + getSalePrice() + "€</font>"
			+ " <font color=\"green\" ,size=\"4\">"+ "(-" + df.format(precntage)+"%)" + "</font>");
		}
		else
		{
			WebEngine engine = salePriceView.getEngine();
			engine.loadContent("<font size=\"4\">"+productPrice+"€</font>");
		}
	}
	
	//*************************************************************************************************
	/**
	*  Creates a new OrderItemView with the following parameters
	*  @param catalogItemView the item from whom to build the OrderItemView
	*/
	//*************************************************************************************************
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

	//*************************************************************************************************
	/**
	*  Creates a new OrderItemView with the following parameters
	*  @param customItem the item from whom to build the OrderItemView
	*  @param imageName The product Image name
	*  @param imageCheckSum The image checksum
	*/
	//*************************************************************************************************
	public OrderItemView(Product customItem, String imageName , byte[] imageCheckSum)
	{
		this(customItem.getID(), customItem.getName(), customItem.getType(), customItem.getPrice(),
				customItem.getAmount(), customItem.getColor(), 0, imageName, imageCheckSum);
	}
	
	//*************************************************************************************************
	/**
	*  Return this Item image  
	*  @return the catalog item image 
	*/
	//*************************************************************************************************
	public ImageView getImage() {
		return image;
	}

	//*************************************************************************************************
	/**
	*  sets This item Image
	*  @param the new image to set  
	*/
	//*************************************************************************************************
	public void setImage(ImageView image) {
		this.image = image;
	}

	//*************************************************************************************************
	/**
	*  Return this item remove button 
	*  @return this item remove button 
	*/
	//*************************************************************************************************
	public Button getRemoveBtn() {
		//return removeBtn.getButton();
		return removeBtn;
	}

	//*************************************************************************************************
	/**
	*  sets This item remove button
	*  @param the remove button to set  
	*/
	//*************************************************************************************************
	public void setRemoveBtn(Button button) {
		//this.removeBtn.setButton(button);
		this.removeBtn = button;
	}
	
	//*************************************************************************************************
	/**
	*  Return this item remove button 
	*  @return this item remove button 
	*/
	//*************************************************************************************************
//	public OrderItemViewButton getObservableRemoveButton() {
//		return null;
//	}
	
	//*************************************************************************************************
	/**
	*  Return this item Greeting Card textArea
	*  @return this item Greeting Card textArea 
	*/
	//*************************************************************************************************	
	public TextArea getGreetingCard() {
		return greetingCard;
	}

	//*************************************************************************************************
	/**
	*  sets This item Greeting Card text
	*  @param the Greeting Card text to set  
	*/
	//*************************************************************************************************
	public void setGreetingCardText(String text)
	{
		this.greetingCard.setText(text);
	}
	
	//*************************************************************************************************
	/**
	*  sets This item Greeting Card textArea
	*  @param the Greeting Card textArea to set  
	*/
	//*************************************************************************************************
	public void setGreetingCard(TextArea greetingCard) {
		this.greetingCard = greetingCard;
	}

	//*************************************************************************************************
	/**
	*  Return this item view button
	*  @return this item view button 
	*/
	//*************************************************************************************************
	public Button getViewBtn() {
		return viewBtn;
	}

	//*************************************************************************************************
	/**
	*  sets This item View button
	*  @param the item View button to set
	*/
	//*************************************************************************************************
	public void setViewBtn(Button viewBtn) {
		this.viewBtn = viewBtn;
	}

	//*************************************************************************************************
	/**
	*  Return this item name textArea
	*  @return this item name textArea 
	*/
	//*************************************************************************************************
	public TextArea getNameArea() {
		return nameArea;
	}

	//*************************************************************************************************
	/**
	*  sets This item View button
	*  @param the item View button to set
	*/
	//*************************************************************************************************
	public void setNameArea(TextArea nameArea) {
		this.nameArea = nameArea;
	}

	//*************************************************************************************************
	/**
	*  Return this Item webView for the sale price  
	*  @return this Item webView for the sale price
	*/
	//*************************************************************************************************
	public WebView getSalePriceView() {
		return salePriceView;
	}

	//*************************************************************************************************
	/**
	*  sets This item webView for the sale price
	*  @param the new webView for the sale price to set  
	*/
	//*************************************************************************************************
	public void setSalePriceView(WebView salePriceView) {
		this.salePriceView = salePriceView;
	}
	
	
	
}
