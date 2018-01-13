package product;

import java.io.Serializable;

public class CatalogItem extends Product implements Comparable<CatalogItem>
{
	
	public class ImageInfo implements Serializable
	{
		public String imageName;
		public byte[] imageCheckSum;
		
		public ImageInfo(String imageName, byte[] imageCheckSum)
		{
			this.imageName = imageName;
			this.imageCheckSum = imageCheckSum;
		}
	}
	
	private float salePrice;
	private ImageInfo imageInfo;
	private int storeID;
	
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	public CatalogItem(Product product, float salesPrice, String imageName, byte[] imageCheckSum,int storeID)
	{
		super(product.getID(), product.getName(), product.getType(), product.getPrice(), product.getAmount(), product.getColor());
		this.salePrice = salesPrice;
		this.imageInfo = new ImageInfo(imageName, imageCheckSum);
		this.storeID = storeID;
	}
	public CatalogItem(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName, byte[] imageCheckSum,int storeID) 
	{
		super(productID, productName, productType, productPrice, productAmount, productColor);
		this.salePrice = salesPrice;
		this.imageInfo = new ImageInfo(imageName, imageCheckSum);
		this.storeID = storeID;
	}
	
	public float getSalePrice() 
	{
		return salePrice;
	}

	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	public ImageInfo getImageInfo() {
		return imageInfo;
	}

	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	
	public String getImageName()
	{
		return this.imageInfo.imageName;
	}
	
	public byte[] getImageChecksum()
	{
		return this.imageInfo.imageCheckSum;
	}
	
	
	public int compareTo(CatalogItem o)
	{
		if (this.getID() == o.getID())
			return 0;
		
		if (this.getID() > o.getID())
			return -1;
		
		if (this.getID() < o.getID())
			return 1;
		
		return -1; 
	}
	
	
}
