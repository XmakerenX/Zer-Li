package order;

/*
 * An Exception class to help us see which exception occurred in case of an order exception
 */
public class OrderException extends Exception 
{
	public OrderException(String message)
	{
		super(message);
	}
}
