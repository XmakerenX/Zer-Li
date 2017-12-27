package utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

import Server.DBConnector;
import product.Product;
import user.User;

public class EntityRemover 
{
	public static Boolean removeEntity(String table, String key, DBConnector db)
	{
		   ResultSet rs;
		   String primaryKeyCol;
		   java.sql.PreparedStatement getPrimaryKey = db.createPreparedStatement("SHOW KEYS FROM "+table+" WHERE Key_name = 'PRIMARY'");
		   java.sql.PreparedStatement ps = db.createPreparedStatement("delete from (?) where (?) = (?)");
		  
		 
		try {
			rs = getPrimaryKey.executeQuery();//First query to get primary key string: 
			primaryKeyCol = rs.getString(1);
			
			ps.setString(1,table);
			ps.setString(2,primaryKeyCol);
			ps.setString(3,key);
		    ps.execute();
		    return true;
		}
		catch (SQLException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		   
	}
	
}