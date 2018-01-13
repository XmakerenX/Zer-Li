package serverAPI;

public class UpdateRequest extends Request 
{

	String table;
	String entityKey;
	Object entity;
	
	public UpdateRequest(String table,String entityKey, Object entity)
	{
		super("UpdateRequest");
		this.table = table;
		this.entityKey = entityKey;
		this.entity = entity;
	}

	public String getTable() {
		return table;
	}

	public String getEntityKey() {
		return entityKey;
	}

	public Object getEntity() {
		return entity;
	}
	
	
}
