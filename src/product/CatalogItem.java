package product;

import javafx.scene.control.CheckBox;

public class CatalogItem extends Product
{
	
	private float salePrice;
	private String imagePath;
	//Image
	
	
	public CatalogItem(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imagePath) 
	{
		super(productID, productName, productType, productPrice, productAmount, productColor);
		this.salePrice = salesPrice;
		this.imagePath = imagePath;
	}

	public float getSalePrice() {
		return salePrice;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}
	
}
