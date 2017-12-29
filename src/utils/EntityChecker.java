package utils;

import Server.DBConnector;

public class EntityChecker 
{
	public static Boolean doesExists(String table, String key, DBConnector db)
	{
		return db.doesExists(table, key);
	}
}
