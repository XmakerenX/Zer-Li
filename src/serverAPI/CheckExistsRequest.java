package serverAPI;

public class CheckExistsRequest extends Request
{
	private static final long serialVersionUID = 6135571085283599438L;
	String table;
	String primaryKey;
	public CheckExistsRequest(String tbl, String key) 
	{
		super("CheckExistsRequest");
		this.table = tbl;
		this.primaryKey = key;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
  
}
