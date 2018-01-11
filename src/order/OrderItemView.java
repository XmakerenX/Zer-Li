package order;

import java.io.File;
import java.util.Observable;

import catalog.CatalogItemView;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import product.CatalogItem;

public class OrderItemView extends CatalogItem {

	//private final String 
	private ImageView image;
	private OrderItemViewButton removeBtn;
	private TextArea greetingCard;
	
	public class OrderItemViewButton extends Observable
	{
		public Button button;
		public OrderItemView orderItem;
		
		OrderItemViewButton(OrderItemView orderItem, String buttonText)
		{
			this.button = new Button(buttonText);
			this.button.setUserData(this);
			this.orderItem = orderItem;
		}
		
		public void change()
		{
			this.setChanged();
		}
	}
	
	public OrderItemView(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName , byte[] imageCheckSum)
	{
		super(productID, productName, productType, productPrice, productAmount, productColor, salesPrice, imageName, imageCheckSum,0);
		
		greetingCard = new TextArea();
		greetingCard.setWrapText(true);
		removeBtn = new OrderItemViewButton(this, "remove");
		
		if (imageName != null)
		{
			File file = new File(imageName);
			image = new ImageView(file.toURI().toString());
			image.setFitWidth(64);
			image.setFitHeight(64);
		}
	}
	
	public OrderItemView(CatalogItemView catalogItemView)
	{
		super(catalogItemView.getID(), catalogItemView.getName(), catalogItemView.getType(), catalogItemView.getPrice(),
				catalogItemView.getAmount(), catalogItemView.getColor(), catalogItemView.getSalePrice(), catalogItemView.getImageName(), catalogItemView.getImageChecksum(),0);
		
		greetingCard = new TextArea();
		greetingCard.setWrapText(true);
		greetingCard.setPrefHeight(64);
		removeBtn = new OrderItemViewButton(this, "remove");
		
		if (catalogItemView.getImage() != null)
			image = catalogItemView.getImage();
	}
	
	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public Button getRemoveBtn() {
		return removeBtn.button;
	}

	public void setRemoveBtn(Button button) {
		this.removeBtn.button = button;
	}
	
	public OrderItemViewButton getObservableRemoveButton() {
		return removeBtn;
	}
	
	public TextArea getGreetingCard() {
		return greetingCard;
	}

	public void setGreetingCard(TextArea greetingCard) {
		this.greetingCard = greetingCard;
	}
	
	
	
}
