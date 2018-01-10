package serverAPI;

import java.util.ArrayList;

public class RemoveRequest extends Request
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String table;
	ArrayList<String> keys;
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public ArrayList<String> getKey() {
		return keys;
	}

	public void setKey(ArrayList<String> keys) {
		this.keys = keys;
	}
	
	public RemoveRequest(String table, ArrayList<String> keys)
	{
	   super("RemoveRequest");
	   this.table = table;
	   this.keys = keys;
	}
}
