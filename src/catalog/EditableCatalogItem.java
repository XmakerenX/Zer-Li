package catalog;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import product.CatalogItem;
import product.Product;

public class EditableCatalogItem extends CatalogItem 
{
     public ImageView getImageView() 
     {
		return imageView;
	}


	public void setImageView(ImageView imageView) 
	{
		this.imageView = imageView;
	}


	ImageView imageView;
     
	public EditableCatalogItem(CatalogItem catItem)
	{
		super((Product)catItem, catItem.getSalePrice(), catItem.getImageName(),
				catItem.getImageChecksum(),catItem.getStoreID());
		
		  System.out.println(catItem.getImageName());
		  File file = new File("Cache//"+catItem.getImageName());
		  Image image = new Image(file.toURI().toString());
		  imageView = new ImageView(image);
		  imageView.setFitHeight(64);
		  imageView.setFitWidth(64);
		  
				 
	}
	public class editableCatalogItemButton extends Button
	{
		EditableCatalogItem origin;
		public editableCatalogItemButton(String buttonName,EditableCatalogItem Origin)
		{
			super(buttonName);
			this.origin = origin;
		}
	}
	ImageView image;
	public ImageView getImage() {
		return image;
	}


	public void setImage(ImageView image) {
		this.image = image;
	}


	public editableCatalogItemButton getRemoveButton() {
		return removeButton;
	}


	public void setRemoveButton(editableCatalogItemButton removeButton) {
		this.removeButton = removeButton;
	}


	public editableCatalogItemButton getEditButton() {
		return editButton;
	}


	public void setEditButton(editableCatalogItemButton editButton) {
		this.editButton = editButton;
	}


	public editableCatalogItemButton getAddSale() {
		return addSale;
	}


	public void setAddSale(editableCatalogItemButton addSale) {
		this.addSale = addSale;
	}


	public editableCatalogItemButton getRemoveFromSale() {
		return removeFromSale;
	}


	public void setRemoveFromSale(editableCatalogItemButton removeFromSale) {
		this.removeFromSale = removeFromSale;
	}


	editableCatalogItemButton removeButton = new editableCatalogItemButton("Remove",this);
	editableCatalogItemButton editButton = new editableCatalogItemButton("Edit",this);
	editableCatalogItemButton addSale = new editableCatalogItemButton("Add sale",this);
	editableCatalogItemButton removeFromSale = new editableCatalogItemButton("Remove from sale",this);


	

}
