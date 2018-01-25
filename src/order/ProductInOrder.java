package order;

import product.Product;

//*************************************************************************************************
	/**
	*  This Class holds the data for a product in a order
	*  Holds the same data as product just with an added greetingCard string
	*/
//*************************************************************************************************
public class ProductInOrder extends Product {

	private static final long serialVersionUID = -6384119123589491223L;
	private String greetingCard;
	
	//*************************************************************************************************
	/**
	*  Creates a new ProductInOrder with the following parameters
	*  @param newID the product ID
	*  @param newName the product Name
	*  @param newType the product type
	*  @param productPrice the product price 
	*  @param productAmount the product amount
	*  @param productColor the product color
	*  @param greetingCard the product greeting Card
	*/
	//*************************************************************************************************
	public ProductInOrder(long newID, String newName, String newType,
			float productPrice, int productAmount,String productColor, String greetingCard)
	{
		super(newID, newName, newType, productPrice, productAmount, productColor);
		
		this.greetingCard = greetingCard;
	}

	//*************************************************************************************************
	/**
	*  Return the product greeting card 
	*  @return the product greeting card 
	*/
	//*************************************************************************************************
	public String getGreetingCard() {
		return greetingCard;
	}

    //*************************************************************************************************
    /**
    *  Sets the product greeting card
  	*  @param greetingCard  the product greeting card to be set
  	*/
    //*************************************************************************************************
	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}
}
