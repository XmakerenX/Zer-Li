package unittests;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Server.ProtoTypeServer;
import serverAPI.RemoveOrderRequest;

public class TestGetFieldValue {

	private DBMock mockDB;
	
	private Method getColumnValue;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		mockDB = new DBMock();
		
		getColumnValue = DBMock.class.getDeclaredMethod("getFieldValue", String.class, String.class);
		getColumnValue.setAccessible(true);
	}

	@Test
	public void testFieldValue_OneField_AND() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "Select '*' FROM table WHERE storeID=5";
		String output = (String)getColumnValue.invoke(mockDB, "storeID", input);
		assertTrue(output.equals("5"));
	}
	
	@Test
	public void testFieldValue_TwoFields_AND() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "Select '*' FROM table WHERE storeID=5 AND Name='name'";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID", input);
		String Name = (String)getColumnValue.invoke(mockDB, "Name", input);
		assertTrue(storeID.equals("5"));
		assertTrue(Name.equals("'name'"));
	}
	
	@Test
	public void testFieldValue_ThreeFields_AND() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "Select '*' FROM table WHERE storeID=5 AND Name='name' AND Fullname='full name'";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID", input);
		String Name = (String)getColumnValue.invoke(mockDB, "Name", input);
		String Fullname = (String)getColumnValue.invoke(mockDB, "Fullname", input);
		assertTrue(storeID.equals("5"));
		assertTrue(Name.equals("'name'"));
		assertTrue(Fullname.equals("'full name'"));
	}
	
	@Test
	public void testFieldValue_OneField_Comma() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID=4";
		String output = (String)getColumnValue.invoke(mockDB, "storeID", input);
		assertTrue(output.equals("4"));
	}
	
	@Test
	public void testFieldValue_TwoFields_Comma() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID=4,Name='new Name'";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID", input);
		String Name = (String)getColumnValue.invoke(mockDB, "Name", input);
		assertTrue(storeID.equals("4"));
		assertTrue(Name.equals("'new Name'"));
	}
	
	@Test
	public void testFieldValue_ThreeFields_Comma() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID=4,Name='new Name', FullName = \"Full name\"";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID", input);
		String Name = (String)getColumnValue.invoke(mockDB, "Name", input);
		String Fullname = (String)getColumnValue.invoke(mockDB, "FullName", input);
		assertTrue(storeID.equals("4"));
		assertTrue(Name.equals("'new Name'"));
		assertTrue(Fullname.equals("\"Full name\""));
	}
	
	@Test
	public void testFieldValue_NoEqual() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID 4,Name='new Name', FullName = \"Full name\"";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID", input);
		assertTrue(storeID == null);
	}
	
	@Test
	public void testFieldValue_NoField() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID=4,Name='new Name', FullName = \"Full name\"";
		String storeID = (String)getColumnValue.invoke(mockDB, "storeID2", input);
		assertTrue(storeID == null);
	}
	
	@Test
	public void testFieldValue_NoComma() throws InvocationTargetException, IllegalAccessException 
	{
		String input = "storeID=4 Name='new Name  FullName = \"Full name\"";
		String Name = (String)getColumnValue.invoke(mockDB, "Name", input);
		assertTrue(Name == null);
	}
	

}
