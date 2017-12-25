package serverAPI;

import java.io.Serializable;

public abstract class Request implements Serializable{

	String type;
	
	Request(String type)
	{
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
