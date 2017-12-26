package product;

public class CatalogItem extends Product
{
	
	float salePrice;
	//Image
	
	
	public CatalogItem(int productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice) 
	{
		super(productID, productName, productType, productPrice, productAmount, productColor);
		this.salePrice = salesPrice;
	}
}
