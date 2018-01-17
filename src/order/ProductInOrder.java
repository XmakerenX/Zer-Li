package order;

import product.Product;

public class ProductInOrder extends Product {

	private String greetingCard;
	
	public ProductInOrder(long newID, String newName, String newType,
			float productPrice, int productAmount,String productColor, String greetingCard)
	{
		super(newID, newName, newType, productPrice, productAmount, productColor);
		
		this.greetingCard = greetingCard;
	}

	public String getGreetingCard() {
		return greetingCard;
	}

	public void setGreetingCard(String greetingCard) {
		this.greetingCard = greetingCard;
	}
}
