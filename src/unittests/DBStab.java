package unittests;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Server.DBInterface;
import customer.Customer;
import order.Order;

public class DBStab implements DBInterface {

	//private Order order;
	private ResultSetOrderStub resultSetOrder;
	private ResultSetCustomerStub resultSetCustomer;
	
	private Order.Status updatedOrderStatus;
	private long updatedOrderID;
	private float updatedOrderRefund;
	
	private boolean customerUpdated = false;
	private long updatedCustomerID;
	private long updatedCustomerStore;
	private float updatedCustomerBalance;
		
	public DBStab(Order order)
	{
		resultSetOrder = new ResultSetOrderStub();
		resultSetCustomer = new ResultSetCustomerStub();
		setOrder(order);
	}
	
	public void setOrder(Order order)
	{
		//this.order = order;
		resultSetOrder.setOrder(order);
	}
	
	public void setCustomer(Customer customer)
	{
		//this.customer = customer;
		customerUpdated = false;
		resultSetCustomer.setCustomer(customer);
	}
		
	public boolean isCustomerUpdated() {
		return customerUpdated;
	}

	private String getColumnValue(String columnName, String updateString)
	{
		// updateString looks like this: ..... columnName=value, columnNameX=valueX, .......
		// OR like this : ..... AND columnName=value AND columnNameX=valueX AND....
		// cut to the start of the columnName
		String sub = updateString.substring(updateString.indexOf(columnName));
		// split around the = sign
		String[] split = sub.split("=");
		// take the right of the = sign and cut at the ','
		String value;
		int index = split[1].indexOf(",");
		if (index == -1)
		{
			// split around space
			split = split[1].split(" ");			
			value = split[0];
		}
		else
			value = split[1].substring(0, index);
		
		return value;
	}
	
	@Override
	public ResultSet selectTableData(String fields, String table, String condition) {
		if (table.equals("Order"))
		{
			resultSetOrder.setDataSet(true);
			return resultSetOrder;
		}
	
		if (table.equals("Customers"))
		{
			resultSetCustomer.setDataSet(true);
			return resultSetCustomer;
		}
			
		return null;
	}

	@Override
	public void executeUpdate(String table, String fieldsToUpdate, String condition) throws SQLException {
		
		if (table.equals("prototype.Order"))
		{
			String orderStatus = getColumnValue("OrderStatus", fieldsToUpdate);
			orderStatus= orderStatus.substring(1, orderStatus.length() - 1);
			updatedOrderStatus = Order.Status.valueOf(orderStatus);
			updatedOrderRefund = Float.parseFloat(getColumnValue("OrderRefund", fieldsToUpdate));
			updatedOrderID = Long.parseLong(getColumnValue("OrderID", condition));
		}
		
		if (table.equals("Customers"))
		{
			customerUpdated = true;
			updatedCustomerID = Long.parseLong(getColumnValue("PersonID", condition));
			updatedCustomerStore = Long.parseLong(getColumnValue("StoreID", condition));
			updatedCustomerBalance = Float.parseFloat(getColumnValue("accountBalance", fieldsToUpdate));
		}

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

	@Override
	public String generateConditionForPrimayKey(String table, ArrayList<String> keys, String condition) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ArrayList<String> getTableKeyName(String table)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
