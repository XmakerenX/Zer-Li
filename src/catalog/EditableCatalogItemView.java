package catalog;

import java.io.File;
import java.io.Serializable;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import product.CatalogItem;



//*************************************************************************************************
	/**
	*  The class that holds the data to be shown in the editable catalog TableView  
	*/
//*************************************************************************************************
public class EditableCatalogItemView extends CatalogItem implements Serializable
{
	public class editableCatalogItemViewButton extends Button implements Serializable
	{

		private static final long serialVersionUID = 1L;

		CatalogItem origin;
		public editableCatalogItemViewButton(String buttonName,CatalogItem Origin)
		{
			super(buttonName);
			this.origin = Origin;
		}
	}
												 
	private static final long serialVersionUID = -4645756625966051562L;

	public ImageView getImageView() 
     {
		return imageView;
	}
	
    String stringSalePrice;
    
    public String getStringSalePrice()
    {
    	float salePrice = super.getSalePrice();
    	if(salePrice == -1)
    	{
    		return "none";
    	}
    	else
    	{
    		return Float.toString(salePrice);
    	}
    }
    
    public void setStringSalePrice(String stringSalePrice)
    {
    	this.stringSalePrice = stringSalePrice;
    }
    
	public void setImageView(ImageView imageView) 
	{
		this.imageView = imageView;
	}

	ImageView imageView;
     
	public EditableCatalogItemView(CatalogItem catItem)
	{
		super(catItem.getBaseProduct(),catItem.getSalePrice(), catItem.getImageName(),
				catItem.getImageChecksum(),catItem.getStoreID());
		
		  System.out.println(catItem.getImageName());
		  File file = new File("Cache//"+catItem.getImageName());
		  Image image = new Image(file.toURI().toString());
		  imageView = new ImageView(image);
		  imageView.setFitHeight(64);
		  imageView.setFitWidth(64);
		 
	}
	
	ImageView image;
	
	public ImageView getImage() {
		return image;
	}

	public void setImage(ImageView image) {
		this.image = image;
	}

	public editableCatalogItemViewButton getRemoveButton() {
		return removeButton;
	}

	public void setRemoveButton(editableCatalogItemViewButton removeButton) {
		this.removeButton = removeButton;
	}

	public editableCatalogItemViewButton getEditButton() {
		return editButton;
	}

	public void setEditButton(editableCatalogItemViewButton editButton) {
		this.editButton = editButton;
	}

	editableCatalogItemViewButton removeButton = new editableCatalogItemViewButton("Remove",this);
	editableCatalogItemViewButton editButton = new editableCatalogItemViewButton("Edit",this);

}
