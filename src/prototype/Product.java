package prototype;

import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;

public class Product implements Serializable {

	public static final long serialVersionUID = 55L;
	
    private final SimpleStringProperty ID;
    private final SimpleStringProperty name;
    private final SimpleStringProperty type;
	
	public Product(int newID, String newName, String newType)
	{
		ID = new SimpleStringProperty(""+newID);
		name = new SimpleStringProperty(newName);
		type = new SimpleStringProperty(newType);
	}
	
	public String getID()
	{
		return ID.get();
	}
	
	public void setID(int newID)
	{
		ID.set(""+ID);
	}
	
	public String getName()
	{
		return name.get();
	}
	
	public void setName(String newName)
	{
		name.set(newName);
	}
	
	public String getType()
	{
		return type.get();
	}
	
	public void setType(String newType)
	{
		type.set(newType);
	}
}