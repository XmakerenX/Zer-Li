package product;

import java.io.Serializable;

import javafx.scene.control.Button;
/**
 *  Entity to enable storing products in tableview objects in order to show them in gui
 */
public class EditableProductView extends Product implements Serializable
{
	/**
	 * A button for the entity EditableProductView to be shown in the tableview and to point at its origin EditableProductView object whom he belong to.
	 */
	public class EditableProductViewButton extends Button implements Serializable
	{
		private static final long serialVersionUID = 1L;
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
	
	private static final long serialVersionUID = 1L;

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
