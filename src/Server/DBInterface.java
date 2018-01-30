package Server;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public interface DBInterface {

	public void connectToDB(String username, String password);
	public void closeConnection();
	public ResultSet selectTableData(String fields, String table, String condition);
	public ResultSet selectJoinTablesData(String fields, String table, String joinTable, String condition);
	public int countSelectTableData(String field, String table, String condition);
	public ResultSet selectLastInsertID();
	public void executeUpdate(String table, String fieldsToUpdate, String condition) throws SQLException;
	public void insertData(String table, String fieldToInsert) throws SQLException;
	public String getColumnType(String table , String columnName);
	public Boolean removeEntry(String table,ArrayList<String> keys);
	public Boolean doesExists(String table, ArrayList<String> keys);
	public String generateConditionForPrimayKey(String table, ArrayList<String> keys, String condition);
	
}

