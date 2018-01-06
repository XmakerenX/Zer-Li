package product;

import javafx.scene.control.Button;

public class EditableProductVIew extends Product 
{
	public class EditableProductVIewButton extends Button
	{
		EditableProductVIew origin;
		public EditableProductVIewButton(EditableProductVIew origin,String name)
		{
			super(name);
			this.origin = origin;
		}
		public EditableProductVIew getOrigin()
		{
			return this.origin;
		}
		
	}
	EditableProductVIewButton addToCatalogBtn;
	EditableProductVIewButton editBtn;
	EditableProductVIewButton removeBtn;
	
	public EditableProductVIew(Product prod) 
	{
		super(prod.getID(), prod.getName(),prod.getType(),prod.getPrice(),prod.getAmount(),prod.getColor());
		this.addToCatalogBtn = new EditableProductVIewButton(this,"Add to catalog");
		this.editBtn = new EditableProductVIewButton(this,"Edit");
		this.removeBtn = new EditableProductVIewButton(this,"Remove");
	}
	
	
	public EditableProductVIewButton getEditBtn() {
		return editBtn;
	}


	public void setEditBtn(EditableProductVIewButton editBtn) {
		this.editBtn = editBtn;
	}


	public Button getRemoveBtn() {
		return removeBtn;
	}
	public void setRemoveBtn(EditableProductVIewButton removeBtn) {
		this.removeBtn = removeBtn;
	}
	
	public Button getAddToCatalogBtn() {
		return addToCatalogBtn;
	}

	public void setAddToCatalogBtn(EditableProductVIewButton addToCatalogBtn) {
		this.addToCatalogBtn = addToCatalogBtn;
	}

}
