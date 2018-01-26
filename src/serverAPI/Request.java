package serverAPI;

import java.io.Serializable;

//*************************************************************************************************
/**
*  The base class for all Request types 
*/
//*************************************************************************************************
public abstract class Request implements Serializable{

	private static final long serialVersionUID = -6708971349416159936L;
	protected String type;
	
	//*************************************************************************************************
	/**
	 * Creates an Request with the following parameters
	 * @param type the Request type
	 */
	//*************************************************************************************************
	Request(String type)
	{
		this.type = type;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the Request type
	 *  @returns the Request type 
	 */
	//*************************************************************************************************
	public String getType() {
		return type;
	}
}
