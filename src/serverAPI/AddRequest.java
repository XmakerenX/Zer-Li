package serverAPI;

public class AddRequest extends Request 
{
	String table;
	Object entity;
	
	public AddRequest(String table, Object entity)
	{
		super("AddRequest");
		this.table = table;
		this.entity = entity;
	}

	public String getTable() {
		return table;
	}

	public Object getEntity() {
		return entity;
	}
}

