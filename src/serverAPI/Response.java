package serverAPI;

import java.io.Serializable;

//*************************************************************************************************
/**
*  The Response data from the server , Holds a type which represent SUCCESS or ERROR
*  on SUCCESS the desired data is in message
*  on ERROR the error message is in message
*/
//*************************************************************************************************
public class Response implements Serializable{

	private static final long serialVersionUID = -7513804699439435928L;

	public enum Type {SUCCESS, ERROR};
	
	Type type;
	// on sucess holds the requested objects 
	// on error holds the error message
	Object message;
	
	//*************************************************************************************************
	/**
	 * Creates an Response with the following parameters
	 * @param type the Response type
	 * @param message the Response data
	 */
	//*************************************************************************************************
	public Response(Type type, Object message)
	{
		this.type = type;
		this.message = message;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the Response type
	 *  @returns the Response type 
	 */
	//*************************************************************************************************
	public Type getType()
	{
		return type;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the Response message
	 *  @returns the Response message 
	 */
	//*************************************************************************************************
	public Object getMessage()
	{
		return message;
	}
	
	//*************************************************************************************************
	/**
	 * Returns a string representation of the Replay
	 *  @return a string representation of the Replay
	 */
	//*************************************************************************************************
	public String toString()
	{
		return type+", "+message.toString();
	}
}
