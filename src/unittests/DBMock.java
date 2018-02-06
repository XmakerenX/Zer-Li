package unittests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.DBInterface;
import customer.Customer;
import order.Order;

//*************************************************************************************************
/**
* Provides a Mock implementation of DBInterface
* used to test CancelOrder
*/
//*************************************************************************************************
public class DBMock implements DBInterface {

	private Order order;
	private Customer customer;
	private ResultSetOrderStub resultSetOrder;
	private ResultSetCustomerStub resultSetCustomer;
	
	private boolean orderUpdated = false;
	private Order.Status updatedOrderStatus;
	private long updatedOrderID;
	private float updatedOrderRefund;
	
	private boolean customerUpdated = false;
	private long updatedCustomerID;
	private long updatedCustomerStore;
	private float updatedCustomerBalance;
		
	public DBMock()
	{
		resultSetOrder = new ResultSetOrderStub();
		resultSetCustomer = new ResultSetCustomerStub();
	}
	
	public void setOrder(Order order)
	{
		this.order = order;
		resultSetOrder.setOrder(order);
	}

	public boolean isOrderUpdated() {
		return orderUpdated;
	}

	public void setCustomer(Customer customer)
	{
		this.customer = customer;
		customerUpdated = false;
		resultSetCustomer.setCustomer(customer);
	}
		
	public boolean isCustomerUpdated() {
		return customerUpdated;
	}

	//*************************************************************************************************
	/**
	* Returns the column value from the given string
	* @param fieldnName the column name to get
	* @param updateString the string from which to get the column value
	*/
	//*************************************************************************************************
	private String getFieldValue(String fieldnName, String updateString)
	{
		// updateString looks like this: ..... columnName=value, columnNameX=valueX, .......
		// OR like this : ..... AND columnName=value AND columnNameX=valueX AND ....

		if (fieldnName == null || updateString == null)
			return null;

		// cut to the start of the columnName
		int fieldIndex = updateString.indexOf(fieldnName);
		if (fieldIndex == -1)
			return null;

		String sub = updateString.substring(fieldIndex);
		
		//verify that there is an equal sign
		if (updateString.substring(fieldIndex + fieldnName.length()).trim().charAt(0) != '=')
			return null;

		// split around the = sign
		String[] split = sub.split("=");
		// take the right of the = sign and cut at the ','
		String value;
		int index = split[1].indexOf(",");
		if (index == -1)
		{
			split[1] = split[1].trim();
			if (split[1].indexOf("'") == 0 || split[1].indexOf("\"") == 0)
			{
				char endingChar = split[1].charAt(0);
				index = split[1].substring(1, split[1].length()).indexOf(""+endingChar);
				if (index == -1)
					return null;
				value = split[1].substring(0, index + 2);
			}
			else
			{
				// split around space
				split = split[1].split(" ");			
				value = split[0];
			}
		}
		else
			value = split[1].substring(0, index);
		
		return value;
	}
	
	@Override
	public ResultSet selectTableData(String fields, String table, String condition) {
		if (table.equals("Order"))
		{
			long orderRequestedID = Long.parseLong(getFieldValue("OrderID", condition));
			// verify the correct order was requested
			if (orderRequestedID == order.getID())
			{
				// mark resultSet to have one order row in it
				resultSetOrder.setDataSet(true);
				return resultSetOrder;
			}
			else
			{
				//return empty resultSet
				return resultSetOrder;
			}
		}
	
		if (table.equals("Customers"))
		{
			long customerID = Long.parseLong(getFieldValue("personID", condition));
			long storeID = Long.parseLong(getFieldValue("StoreID", condition));
			// verify the correct customer was requested
			if (customerID == customer.getID() && storeID == customer.getStoreID())
			{
				// mark resultSet to have one customer row in it
				resultSetCustomer.setDataSet(true);
				return resultSetCustomer;
			}
			else
			{
				//return empty resultSet
				return resultSetCustomer;
			}
		}
			
		return null;
	}

	@Override
	public void executeUpdate(String table, String fieldsToUpdate, String condition) throws SQLException {
		
		if (table.equals("prototype.Order"))
		{
			orderUpdated = true;
			String orderStatus = getFieldValue("OrderStatus", fieldsToUpdate);
			orderStatus= orderStatus.substring(1, orderStatus.length() - 1);
			updatedOrderStatus = Order.Status.valueOf(orderStatus);
			updatedOrderRefund = Float.parseFloat(getFieldValue("OrderRefund", fieldsToUpdate));
			updatedOrderID = Long.parseLong(getFieldValue("OrderID", condition));
		}
		
		if (table.equals("Customers"))
		{
			customerUpdated = true;
			updatedCustomerID = Long.parseLong(getFieldValue("PersonID", condition));
			updatedCustomerStore = Long.parseLong(getFieldValue("StoreID", condition));
			updatedCustomerBalance = Float.parseFloat(getFieldValue("accountBalance", fieldsToUpdate));
		}

	}
	
	@Override
	public String generateConditionForPrimayKey(String table, ArrayList<String> keys, String condition) {
		
		if (table.equals("Order"))
		{
			return "OrderID="+keys.get(0);
		}
		
		if (table.equals("Customers"))
		{
			return "personID="+keys.get(0) + " AND " + "StoreID="+keys.get(1);
		}
		
		return "";
	}
	
	//******************************************************
	// Un-needed and unImplemented functions
	//******************************************************
	
	public Order.Status getUpdatedOrderStatus() {
		return updatedOrderStatus;
	}

	public long getUpdatedOrderID() {
		return updatedOrderID;
	}

	public float getUpdatedOrderRefund() {
		return updatedOrderRefund;
	}

	public long getUpdatedCustomerID() {
		return updatedCustomerID;
	}

	public long getUpdatedCustomerStore() {
		return updatedCustomerStore;
	}

	public float getUpdatedCustomerBalance() {
		return updatedCustomerBalance;
	}

	@Override
	public void connectToDB(String username, String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeConnection() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public ResultSet selectJoinTablesData(String fields, String table, String joinTable, String condition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countSelectTableData(String field, String table, String condition) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ResultSet selectLastInsertID() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void insertData(String table, String fieldToInsert) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getColumnType(String table, String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean removeEntry(String table, ArrayList<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doesExists(String table, ArrayList<String> keys) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<String> getTableKeyName(String table)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
