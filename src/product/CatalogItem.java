package product;

import java.io.Serializable;

//*************************************************************************************************
/**
 * This class stores the data of a catalogItem - a product with image to appear in catalog
 */
//*************************************************************************************************
public class CatalogItem extends Product implements Comparable<CatalogItem>
{
	//*************************************************************************************************
	/**
	 * This class stores the image information that helps compare image without actually sending the 
	 * images
	 */
	//*************************************************************************************************
	public class ImageInfo implements Serializable
	{
		private static final long serialVersionUID = -5507121718779841287L;
		public String imageName;
		public byte[] imageCheckSum;
		
		//*****************************************************************************************
		/**
		* Creates a new ImageInfo with the following parameters
		* @param imageName The image name
		* @param imageCheckSum The image checksum
		*/
		//*****************************************************************************************
		public ImageInfo(String imageName, byte[] imageCheckSum)
		{
			this.imageName = imageName;
			this.imageCheckSum = imageCheckSum;
		}
	}
	
	private static final long serialVersionUID = -7422283710477942269L;
	private float salePrice;
	private ImageInfo imageInfo;
	private int storeID;
	
	//*****************************************************************************************
	/**
	* Creates a new ImageInfo with the following parameters
	* @param prod the product to copy 
	* @param salesPrice the CatalogItem sale price
	* @param imageName the CatalogItem image name
	* @param imageCheckSum the CatalogItem image checksum
	* @param storeID the CatalogItem storeID
	*/
	//*****************************************************************************************
	public CatalogItem(Product prod,float salesPrice, String imageName, byte[] imageCheckSum,int storeID)
	{
		super(prod.getID(), prod.getName(), prod.getType(), prod.getPrice(), prod.getAmount(), prod.getColor());
		this.salePrice = salesPrice;
		this.imageInfo = new ImageInfo(imageName, imageCheckSum);
		this.storeID = storeID;
	}
	
	//*****************************************************************************************
	/**
	* Creates a new ImageInfo with the following parameters
	* @param productID  the product ID
	* @param productName the product Name
	* @param productType  the product Type
	* @param productPrice the product price
	* @param productAmount  the product amount 
	* @param productColor the product color
	* @param salesPrice the CatalogItem sale price
	* @param imageName CatalogItem image name 
	* @param imageCheckSum CatalogItem check sum
	* @param storeID CatalogItem store ID
	*/
	//*****************************************************************************************
	public CatalogItem(long productID, String productName, String productType, float productPrice, int productAmount,
			String productColor,float salesPrice, String imageName, byte[] imageCheckSum,int storeID) 
	{
		super(productID, productName, productType, productPrice, productAmount, productColor);
		this.salePrice = salesPrice;
		this.imageInfo = new ImageInfo(imageName, imageCheckSum);
		this.storeID = storeID;
	}

	//*************************************************************************************************
    /**
     * Returns the CatalogItem store ID
  	*  @return the CatalogItem store ID
  	*/
    //*************************************************************************************************
	public int getStoreID() {
		return storeID;
	}
	
    //*************************************************************************************************
    /**
     * Sets the CatalogItem store ID
  	*  @param storeID the CatalogItem store ID to be set
  	*/
    //*************************************************************************************************
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	
	//*************************************************************************************************
    /**
     * Returns the CatalogItem cast to product
  	*  @return the CatalogItem cast to product
  	*/
    //*************************************************************************************************
	public Product getBaseProduct()
	{
		return (Product)this;
	}
	
	//*************************************************************************************************
    /**
     * Returns the CatalogItem sale price
  	*  @return the CatalogItem sale price
  	*/
    //*************************************************************************************************
	public float getSalePrice() 
	{
		return salePrice;
	}

    //*************************************************************************************************
    /**
     * Sets the CatalogItem sale Price
  	*  @param salePrice the CatalogItem sale Price to be set
  	*/
    //*************************************************************************************************
	public void setSalePrice(float salePrice) {
		this.salePrice = salePrice;
	}

	//*************************************************************************************************
    /**
     * Returns the CatalogItem Image information (name and checksum)
  	*  @return the CatalogItem Image information (name and checksum)
  	*/
    //*************************************************************************************************
	public ImageInfo getImageInfo() {
		return imageInfo;
	}

    //*************************************************************************************************
    /**
     * Sets the CatalogItem image information(name and checksum)
  	*  @param imageInfo the image information(name and checksum) to be set
  	*/
    //*************************************************************************************************
	public void setImageInfo(ImageInfo imageInfo) {
		this.imageInfo = imageInfo;
	}
	
	//*************************************************************************************************
    /**
     * Returns the CatalogItem image name
  	*  @return the CatalogItem image name
  	*/
    //*************************************************************************************************
	public String getImageName()
	{
		return this.imageInfo.imageName;
	}
	
	//*************************************************************************************************
    /**
     * Returns the CatalogItem image CheckSum
  	*  @return the CatalogItem image CheckSum
  	*/
    //*************************************************************************************************
	public byte[] getImageChecksum()
	{
		return this.imageInfo.imageCheckSum;
	}
	
	//*************************************************************************************************
    /**
     * Compare the catalog Items by their ID 
     * @param o the catalog item to compare with
  	*  @return -1 , 0 , 1
  	*/
    //*************************************************************************************************
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