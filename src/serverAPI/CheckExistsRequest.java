package serverAPI;

import java.util.ArrayList;

public class CheckExistsRequest extends Request
{
	private static final long serialVersionUID = 6135571085283599438L;
	String table;
	ArrayList<String> primaryKeys;
	public CheckExistsRequest(String tbl, ArrayList<String> keys) 
	{
		super("CheckExistsRequest");
		this.table = tbl;
		this.primaryKeys = keys;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public ArrayList<String> getPrimaryKey() {
		return primaryKeys;
	}
	public void setPrimaryKey(ArrayList<String> primaryKey) {
		this.primaryKeys = primaryKey;
	}
  
}
