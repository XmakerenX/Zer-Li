package prototype;

import java.io.Serializable;

public class Product implements Serializable {

	public static final long serialVersionUID = 55L;
	
    private long    ID;
    private String name;
    private String type;
    
	public Product(long newID, String newName, String newType)
	{
		ID = newID;
		name = newName;
		type = newType;
	}
	
	public long getID()
	{
		return ID;
	}
	
	public void setID(long newID)
	{
		ID = newID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String newName)
	{
		name = newName;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String newType)
	{
		type = newType;
	}
	
	public String toString()
	{
		return ID+", "+name+", "+type;
	}
	
}