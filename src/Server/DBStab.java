package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.scene.control.SplitPane;
import order.Order;

public class DBStab implements DBInterface {

	private Order order;
	private float refundValue;
	private ResultSetOrderStub resultSetOrder;
	private ResultSetCustomerStub resultSetCustomer;
	
	private Order.Status updatedOrderStatus;
	private long updatedOrderID;
	private float updatedOrderRefund;
	
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
		this.order = order;
		resultSetOrder.setOrder(order);
	}
	
	public void setCustomer(float accountBalance)
	{
		resultSetCustomer.setCustomerBalance(accountBalance);
	}
	
	private String getColumnValue(String columnName, String updateString)
	{
		// updateString looks like this: UPDATE ..... columnName=value, columnNameX=valueX, .......
		// cut to the start of the columnName
		String sub = updateString.substring(updateString.indexOf(columnName));
		// split around the = sign
		String[] split = sub.split("=");
		// take the right of the = sign and cut at the ','
		String value = split[1].substring(0, split[1].indexOf(","));
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
			
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void executeUpdate(String table, String fieldsToUpdate, String condition) throws SQLException {
		if (table.equals("Order"))
		{
			String orderStatus = getColumnValue("OrderStatus", fieldsToUpdate);
			orderStatus= orderStatus.substring(1, orderStatus.length() - 1);
			Order.Status.valueOf(orderStatus);
			updatedOrderRefund = Float.parseFloat(getColumnValue("OrderRefund", fieldsToUpdate));
			updatedOrderID = Long.parseLong(getColumnValue("OrderID", condition));
		}
		
		if (table.equals("Customers"))
		{
			updatedCustomerID = Long.parseLong(getColumnValue("personID", condition));
			updatedCustomerStore = Long.parseLong(getColumnValue("StoreID", condition));
			updatedCustomerBalance = Float.parseFloat(getColumnValue("accountBalance", condition));
		}

	}
	
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

}
