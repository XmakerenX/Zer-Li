package utils;

import java.util.ArrayList;

import Server.DBConnector;


public class EntityChecker 
{
	public static Boolean doesExists(String table, ArrayList<String> keys, DBConnector db)
	{
		return db.doesExists(table, keys);
	}
}
