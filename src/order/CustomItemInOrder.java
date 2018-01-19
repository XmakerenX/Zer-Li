package order;

import java.io.Serializable;
import java.util.ArrayList;

import product.Product;

//*********************************************************************************************
/**
* Holds the custom Item data that is in the current Order
*/
//*********************************************************************************************
public class CustomItemInOrder implements Serializable {
	private static final long serialVersionUID = -5628523607173737142L;
	private long customItemID;
	private Product.Type type;
	private float price;
	private String color;
	private String greetingCard;
	private ArrayList<Product> components;
	
	//*****************************************************************************************
	/**
	* Creates a new CustomItemInOrder with the following parameters
	* @param customItemID the custom Item ID
	* @param type the custom item type
	* @param price the custom item total price
	* @param color the custom item dominate color
	* @param greetingCard the custom item greetingCard
	* @param components the sub products contained in the custom item 
	*/
	//*****************************************************************************************
	public CustomItemInOrder(long customItemID, String type, float price, String color, String greetingCard
			, ArrayList<Product> components)
	{
		this.customItemID = customItemID;
		this.type = Product.Type.valueOf(type);
		this.price = price;
		this.color = color;
		this.greetingCard = greetingCard;
		this.components = components;
	}
	
	//*****************************************************************************************
	/**
	* Creates a new CustomItemInOrder with the following parameters
	* @param type the custom item type
	* @param price the custom item total price
	* @param color the custom item dominate color
	* @param greetingCard the custom item greetingCard
	* @param components the sub products contained in the custom item 
	*/
	//*****************************************************************************************
	public CustomItemInOrder(String type, float price, String color, String greetingCard
			, ArrayList<Product> components)
	{
		this(0, type, price, color, greetingCard, components);
	}
	
	public long getCustomItemID() {
		return customItemID;
	}

	public String getGreetingCard() {
		return greetingCard;
	}

	public ArrayList<Product> getComponents() {
		return components;
	}

	public void setComponents(ArrayList<Product> components)
	{
		this.components = components;
	}
	
	public Product.Type getType() {
		return type;
	}

	public float getPrice() {
		return price;
	}

	public String getColor() {
		return color;
	}
}
