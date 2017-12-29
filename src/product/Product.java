package product;

import java.io.Serializable;

public class Product implements Serializable 
{

	public static final long serialVersionUID = 55L;
	
    private long     productID;
    private String  productName;
    private String  productType;
    private float   productPrice;
    private int     productAmount;
    private String  productColor;
    
    
//*************************************************************************************************
    /**
  	*  Constructs a new Product
  	*  @param newID
  	*  @param newName
  	*  @param newType
  	*/
//*************************************************************************************************
	public Product(int newID, String newName, String newType,
					float productPrice, int productAmount,String productColor)
	{
		this.productID = newID;
		this.productName = newName;
		this.productType = newType;
		this.productPrice = productPrice;
		this.productAmount = productAmount;
		this.productColor = productColor;
	}


	public long getID() {
		return productID;
	}


	public void setID(long productID) {
		this.productID = productID;
	}


	public String getName() {
		return productName;
	}


	public void setName(String productName) {
		this.productName = productName;
	}


	public String getType() {
		return productType;
	}


	public void setType(String productType) {
		this.productType = productType;
	}


	public float getPrice() {
		return productPrice;
	}


	public void setPrice(float productPrice) {
		this.productPrice = productPrice;
	}


	public int getAmount() {
		return productAmount;
	}


	public void setAmount(int productAmount) {
		this.productAmount = productAmount;
	}


	public String getColor() {
		return productColor;
	}


	public void setColor(String productColor) {
		this.productColor = productColor;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}