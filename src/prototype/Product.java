package prototype;

import java.io.Serializable;

public class Product implements Serializable {

	public static final long serialVersionUID = 55L;
	
    private long    ID;
    private String name;
    private String type;
    
//*************************************************************************************************
    /**
  	*  Constructs a new Product
  	*  @param newID
  	*  @param newName
  	*  @param newType
  	*/
//*************************************************************************************************
	public Product(long newID, String newName, String newType)
	{
		ID = newID;
		name = newName;
		type = newType;
	}

//*************************************************************************************************
    /**
  	*  Get the value of the ID property
  	*  @return the ID property
  	*/
//*************************************************************************************************
	public long getID()
	{
		return ID;
	}

//*************************************************************************************************
    /**
  	*  Set the value of the ID property
  	*  @return the ID property
  	*/
//*************************************************************************************************
	public void setID(long newID)
	{
		ID = newID;
	}

//*************************************************************************************************
    /**
  	*  Get the value of the Name property
  	*  @return the Name property
  	*/
//*************************************************************************************************
	public String getName()
	{
		return name;
	}

//*************************************************************************************************
    /**
  	*  Set the value of the Name property
  	*  @return the Name property
  	*/
//*************************************************************************************************
	public void setName(String newName)
	{
		name = newName;
	}

//*************************************************************************************************
    /**
  	*  Get the value of the Type property
  	*  @return the Type property
  	*/
//*************************************************************************************************
	public String getType()
	{
		return type;
	}

//*************************************************************************************************
    /**
  	*  Set the value of the Type property
  	*  @return the Type property
  	*/
//*************************************************************************************************
	public void setType(String newType)
	{
		type = newType;
	}

//*************************************************************************************************
    /**
     * Returns a string representation of the product
  	*  @return a string representation of the product
  	*/
//*************************************************************************************************
	public String toString()
	{
		return ID+", "+name+", "+type;
	}
	
}