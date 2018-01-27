package serverAPI;

//*************************************************************************************************
/**
 *  a Request for the server to add entity data to table
 *  Stores the data for the AddRequest that will be sent to the server from the client 
 */
//*************************************************************************************************
public class AddRequest extends Request 
{
	private static final long serialVersionUID = 5403443428892955361L;
	private String table;
	private Object entity;

	//*************************************************************************************************
	/**
	 *  Creates an AddRequest with the following parameters
	 *  @param table the table to add to 
	 *  @param entity holds an entity class with the data to add to table
	 */
	//*************************************************************************************************
	public AddRequest(String table, Object entity)
	{
		super("AddRequest");
		this.table = table;
		this.entity = entity;
	}

	//*************************************************************************************************
	/**
	 *  Returns the AddRequest table
	 *  @return the AddRequest table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}

	//*************************************************************************************************
	/**
	 *  Returns the AddRequest entity
	 *  @return the AddRequest entity 
	 */
	//*************************************************************************************************
	public Object getEntity() {
		return entity;
	}
}

