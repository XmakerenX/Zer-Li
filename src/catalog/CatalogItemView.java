package catalog;

import java.io.File;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import product.CatalogItem;

//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the catalog TableView  
	*/
//*************************************************************************************************
public class CatalogItemView extends CatalogItem 
{
	//*********************************************************************************************
	// class instance variables
	//*********************************************************************************************
	private static final long serialVersionUID = -9177300290774001001L;
	private BooleanProperty selected;
	private ImageView image;
	
	//*************************************************************************************************
		/**
		*  Creates a new CatalogItemView with the following parameters
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
	public CatalogItemView(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName , byte[] imageCheckSum)
	{
		super(productID, productName, productType, productPrice, productAmount, productColor, salesPrice, imageName, imageCheckSum,0);
		selected = new SimpleBooleanProperty();
		
		if (getSalePrice() > 0)
			this.setPrice(getSalePrice());
		
		if (imageName != null)
		{
			File file = new File(imageName);
			image = new ImageView(file.toURI().toString());
			image.setFitWidth(64);
			image.setFitHeight(64);
		}
	}
	
	//*************************************************************************************************
	/**
	*  Creates a new CatalogItemView with the following parameters
	*  @param catalogItem The catalogItem item to copy
	*  //TODO: remember wtf was this var
	*  @param imagesDir The images directory
	*/
	//*************************************************************************************************
	public CatalogItemView(CatalogItem catalogItem, String imagesDir)
	{
		super(catalogItem.getID(), catalogItem.getName(), catalogItem.getType(), catalogItem.getPrice(),
				catalogItem.getAmount(), catalogItem.getColor(), catalogItem.getSalePrice(), catalogItem.getImageName(), catalogItem.getImageChecksum(),0);
		
		selected = new SimpleBooleanProperty();

		if (getSalePrice() > 0)
			this.setPrice(getSalePrice());
		
		if (catalogItem.getImageName() != null)
		{
			File file = new File(imagesDir+catalogItem.getImageName());
			image = new ImageView(file.toURI().toString());
			image.setFitWidth(64);
			image.setFitHeight(64);
		}
	}

	//*************************************************************************************************
	/**
	*  Return if this Item checkbox was selected  
	*/
	//*************************************************************************************************
	public boolean isSelected() {
		return selected.get();
	}

	//*************************************************************************************************
	/**
	*  sets the selected property of this Item checkbox
	*  @param selected The new selected property status
	*/
	//*************************************************************************************************
	public void setSelected(boolean selected) {
		this.selected.set(selected);
	}
	
	//*************************************************************************************************
	/**
	*  Return this Item selectedProperty  
	*/
	//*************************************************************************************************
	public BooleanProperty selectedProperty()
	{
		return selected;
	}

	//*************************************************************************************************
	/**
	*  Return this Item image  
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
	*  compare items  based on if the item is on sale
	*  @param o the CatalogItem to compare to
	*  @see java.lang.Comparable.compareTo
	*/
	//*************************************************************************************************
	//TODO: do a smarter compareTO to sort by something...
	@Override
	public int compareTo(CatalogItem o)
	{
		if (this.getSalePrice() == -1 && o.getSalePrice() != -1)
			return 1;
		
		if ( (this.getSalePrice() != -1 && o.getSalePrice() != -1) || (this.getSalePrice() == 0 && o.getSalePrice() == 0))
			return 0;
		
		if (this.getSalePrice() != -1 && o.getSalePrice() == -1)
			return -1;
		
		return 0;
	}
	
	
	
}
