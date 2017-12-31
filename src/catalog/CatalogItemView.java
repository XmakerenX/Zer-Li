package catalog;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import product.CatalogItem;

public class CatalogItemView extends CatalogItem {

	private BooleanProperty selected;
	private ImageView image;
	
	public CatalogItemView(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imagePath)
	{
		super(productID, productName, productType, productPrice, productAmount, productColor, salesPrice, imagePath);
		selected = new SimpleBooleanProperty();
		image = new ImageView(imagePath);
	}
	
	public CatalogItemView(CatalogItem catalogItem)
	{
		super(catalogItem.getID(), catalogItem.getName(), catalogItem.getType(), catalogItem.getPrice(),
				catalogItem.getAmount(), catalogItem.getColor(), catalogItem.getSalePrice(), catalogItem.getImagePath());
		
		selected = new SimpleBooleanProperty();
		System.out.println(catalogItem.getImagePath());
		if (catalogItem.getImagePath() != null)
		{
			File file = new File(catalogItem.getImagePath());
			image = new ImageView(file.toURI().toString());
			image.setFitWidth(64);
			image.setFitHeight(64);
		}
	}

	public boolean isSelected() {
		return selected.get();
	}

	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}
	
	public BooleanProperty selectedProperty()
	{
		return selected;
	}

	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}
	
	
	
	
}
