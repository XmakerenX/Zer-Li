package serverAPI;

public class RemoveRequest extends Request
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String table;
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	String key;
	
	public RemoveRequest(String table,String key)
	{
	   super("RemoveRequest");
	   this.table = table;
	   this.key = key;
	}
}
