package serverAPI;

import java.io.Serializable;

public class Response implements Serializable{
	public enum Type {SUCCESS, ERROR};
	
	Type type;
	// on sucess holds the requested objects 
	// on error holds the error message
	Object message;
	
	public Response(Type type, Object message)
	{
		this.type = type;
		this.message = message;
	}
	
	public Type getType()
	{
		return type;
	}
	
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
