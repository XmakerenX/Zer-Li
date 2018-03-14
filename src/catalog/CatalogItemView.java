package catalog;

import java.io.File;
import java.text.DecimalFormat;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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
	private WebView salePriceView;
	
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
		
		salePriceView = new WebView();
		salePriceView.setPrefSize(75, 75);
		if (getSalePrice() > 0)
		{
			WebEngine engine = salePriceView.getEngine();
			float precntage = ((productPrice - getSalePrice()) / productPrice) * 100;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			engine.loadContent("<font size=\"2\"><del>"+productPrice+" ILS</del></font><br>" 
			+ " <font size=\"4\">" + getSalePrice() + " ILS</font><br>"
			+ " <font color=\"green\" ,size=\"4\">"+ "(-" + df.format(precntage)+"%)" + "</font>");
		}
		else
		{
			WebEngine engine = salePriceView.getEngine();
			engine.loadContent("<font size=\"4\">"+productPrice+" ILS</font>");
		}
		
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
	*  @param imagesDir The images directory
	*/
	//*************************************************************************************************
	public CatalogItemView(CatalogItem catalogItem, String imagesDir)
	{
		super(catalogItem.getID(), catalogItem.getName(), catalogItem.getType(), catalogItem.getPrice(),
				catalogItem.getAmount(), catalogItem.getColor(), catalogItem.getSalePrice(), catalogItem.getImageName(), catalogItem.getImageChecksum(),0);
		
		selected = new SimpleBooleanProperty();

		salePriceView = new WebView();
		salePriceView.setPrefSize(75, 75);
		if (getSalePrice() > 0)
		{
			WebEngine engine = salePriceView.getEngine();
			//  <font size="6">This is some text!</font> 
			// <font color="red">This is some text!</font> getSalePrice()
			float precntage = ((catalogItem.getPrice() - getSalePrice()) / catalogItem.getPrice())*100;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			engine.loadContent("<font size=\"2\"><del>"+catalogItem.getPrice()+" ILS</del></font><br>" 
			+ " <font size=\"4\">" + getSalePrice() + " ILS</font><br>"
			+ " <font color=\"green\" ,size=\"4\">"+ "(-" + df.format(precntage)+"%)" + "</font>");
		}
		else
		{
			WebEngine engine = salePriceView.getEngine();
			engine.loadContent("<font size=\"4\">"+catalogItem.getPrice()+" ILS</font>");
		}
		
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
	*  Return if this Item check box was selected  
	*  @return return the selected status of the checkbox inside this item
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
	*  @return The selected status of the checkbox inside this item
	*/
	//*************************************************************************************************
	public BooleanProperty selectedProperty()
	{
		return selected;
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
	*  @param image the new image to set  
	*/
	//*************************************************************************************************
	public void setImage(ImageView image) {
		this.image = image;
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
	*  @param salePriceView the new webView for the sale price to set  
	*/
	//*************************************************************************************************
	public void setSalePriceView(WebView salePriceView) {
		this.salePriceView = salePriceView;
	}

	//*************************************************************************************************
	/**
	*  compare items  based on if the item is on sale
	*  @param o the CatalogItem to compare to
	*  @see java.lang.Comparable
	*/
	//*************************************************************************************************
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
