package serverAPI;

//*************************************************************************************************
/**
*  a Request for the server to update table data
*  Stores the data for the UpdateRequest that will be sent to the server from the client 
*/
//*************************************************************************************************
public class UpdateRequest extends Request 
{
	private static final long serialVersionUID = -8697566778951697267L;
	private String table;
	private String entityKey;
	private Object entity;
	
	//*************************************************************************************************
	/**
	 *  Creates an UpdateRequest with the following parameters
	 *  @param table  the table to update 
	 *  @param entityKey the entity primary key
	 *  @param entity the entity data 
	 */
	//*************************************************************************************************
	public UpdateRequest(String table,String entityKey, Object entity)
	{
		super("UpdateRequest");
		this.table = table;
		this.entityKey = entityKey;
		this.entity = entity;
	}

	//*************************************************************************************************
	/**
	 *  Returns the UpdateRequest table
	 *  @return the UpdateRequest table 
	 */
	//*************************************************************************************************
	public String getTable() {
		return table;
	}

	//*************************************************************************************************
	/**
	 *  Returns the UpdateRequest entityKey
	 *  @return the UpdateRequest entityKey 
	 */
	//*************************************************************************************************
	public String getEntityKey() {
		return entityKey;
	}

	//*************************************************************************************************
	/**
	 *  Returns the GetRequestByKey entity
	 *  @return the GetRequestByKey entity 
	 */
	//*************************************************************************************************
	public Object getEntity() {
		return entity;
	}
	
	
}
