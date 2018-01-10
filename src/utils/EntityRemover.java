package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import Server.DBConnector;
import product.Product;
import user.User;

public class EntityRemover 
{
	public static Boolean removeEntity(String table, ArrayList<String> keys, DBConnector db)
	{
		return db.removeEntry(table, keys);
	}
		   
}
	
