package product;

import java.io.Serializable;

import javafx.scene.control.Button;

public class EditableProductView extends Product implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public class EditableProductViewButton extends Button implements Serializable
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		EditableProductView origin;
		public EditableProductViewButton(EditableProductView origin,String name)
		{
			super(name);
			this.origin = origin;
		}
		public EditableProductView getOrigin()
		{
			return this.origin;
		}
		
	}
	EditableProductViewButton addToCatalogBtn;
	EditableProductViewButton editBtn;
	EditableProductViewButton removeBtn;
	
	public EditableProductView(Product prod) 
	{
		super(prod.getID(), prod.getName(),prod.getType(),prod.getPrice(),prod.getAmount(),prod.getColor());
		this.addToCatalogBtn = new EditableProductViewButton(this,"Add to catalog");
		this.editBtn = new EditableProductViewButton(this,"Edit");
		this.removeBtn = new EditableProductViewButton(this,"Remove");
	}
	
	
	public EditableProductViewButton getEditBtn() {
		return editBtn;
	}


	public void setEditBtn(EditableProductViewButton editBtn) {
		this.editBtn = editBtn;
	}


	public Button getRemoveBtn() {
		return removeBtn;
	}
	public void setRemoveBtn(EditableProductViewButton removeBtn) {
		this.removeBtn = removeBtn;
	}
	
	public Button getAddToCatalogBtn() {
		return addToCatalogBtn;
	}

	public void setAddToCatalogBtn(EditableProductViewButton addToCatalogBtn) {
		this.addToCatalogBtn = addToCatalogBtn;
	}

}
