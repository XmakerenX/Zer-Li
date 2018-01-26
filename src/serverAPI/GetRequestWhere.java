package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to get table data with the given condition
*  	example: get all products entry in which their color is purple
*            table = "Product"
*            checkColomn = "Color"
*            condition = "purple"
*  Stores the data for the GetRequestWhere that will be sent to the server from the client 
*/
//*************************************************************************************************
public class GetRequestWhere extends Request 
{
	private static final long serialVersionUID = 2544020321528348359L;
	private String table;
	private String checkColomn;
	private String condition;
	
	//*************************************************************************************************
	/**
	 * Creates an GetRequestWhere with the following parameters
	 * @param table name of the table we are looking at
	 * @param checkColomn the Column name to do the condition on
	 * @param condition the value for the column
	 */
	//*************************************************************************************************
	public GetRequestWhere(String table, String checkColomn, String condition)
	{
		super("GetRequestWhere");
		this.table = table;
		this.checkColomn = checkColomn;
		this.condition = condition;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the GetRequestWhere table
	 *  @returns the GetRequestWhere table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}
	
	//*************************************************************************************************
	/**
	 *  Returns the GetRequestWhere checkColomn
	 *  @returns the GetRequestWhere checkColomn 
	 */
	//*************************************************************************************************
	public String getCheckColomn() {
		return checkColomn;
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetRequestWhere condition
	 *  @returns the GetRequestWhere condition 
	 */
	//*************************************************************************************************
	public String getCondition() {
		return condition;
	}


}
