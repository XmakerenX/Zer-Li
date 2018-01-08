package product;

import java.io.Serializable;

public class CatalogItem extends Product implements Comparable<CatalogItem>
{
	
	class ImageInfo implements Serializable
	{
		public String imageName;
		public byte[] imageCheckSum;
		
		ImageInfo(String imageName, byte[] imageCheckSum)
		{
			this.imageName = imageName;
			this.imageCheckSum = imageCheckSum;
		}
	}
	
	private float salePrice;
	private ImageInfo imageInfo;
	
	public CatalogItem(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName, byte[] imageCheckSum) 
	{
		super(productID, productName, productType, productPrice, productAmount, productColor);
		this.salePrice = salesPrice;
		this.imageInfo = new ImageInfo(imageName, imageCheckSum);
	}

	public float getSalePrice() {
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
		System.out.println(this.getSalePrice() + " " + o.getSalePrice());
		if (this.salePrice == -1 && o.salePrice != -1)
			return 1;
		
		if ( (this.salePrice != -1 && o.salePrice != -1) || (this.salePrice == 0 && o.salePrice == 0))
			return 0;
		
		if (this.salePrice != -1 && o.salePrice == -1)
			return -1;
		
		return 0;
	}
	
	
}
