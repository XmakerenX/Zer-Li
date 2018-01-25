package order;

/*
 * An Exception class to help us see which exception occurred in case of an order exception
 */
public class OrderException extends Exception 
{
	private static final long serialVersionUID = -529768004639113854L;

	public OrderException(String message)
	{
		super(message);
	}
}
