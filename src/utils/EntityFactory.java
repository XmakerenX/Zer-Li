package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import user.User;
import product.CatalogItem;
import product.Product;
import survey.CustomerSatisfactionSurvey;

public class EntityFactory {
	
	  public static ArrayList<?> loadEntity(String table ,ResultSet rs)
	  {
	      switch (table)
		  {
		  case "Product":
			  return loadProducts(rs); 
			  
		  case "User":
			  return loadUsers(rs);
			  
		  case "CatalogProduct":
			  return loadCatalogItems(rs);
			  
		  case "CustomerSatisfactionSurvey":
			  return loadCustomerSatisfactionSurveys(rs);
			  
		  default:
			  return null;
		  }  
	  }
	  
	  /**
	   * parse a ResultSet and returns an ArrayList of products from it
	   * @param rs ResultSet of the query to get the products table
	   * @return an arrayList of products made from the given ResultSet
	   */
	  public static ArrayList<Product> loadProducts(ResultSet rs)
	  {
		  ArrayList<Product> products = new ArrayList<Product>();
		  try
		  {
			  while (rs.next())
			  {
				  products.add(new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
						  		rs.getFloat(4),rs.getInt(5),rs.getString(6)));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		 
		  return products;
	  }
	  
	  /**
	   * parse a ResultSet and returns an ArrayList of users from it
	   * @param rs ResultSet of the query to get the users table
	   * @return an arrayList of users made from the given ResultSet
	   */
	  public static ArrayList<User> loadUsers(ResultSet rs)
	  {
		  ArrayList<User> users = new ArrayList<User>();
		  try
		  {
			  while (rs.next())
			  {
				  users.add(new User(rs.getString(1), rs.getString(2), User.Permissions.valueOf(rs.getString(3)),
						  rs.getInt(4), User.Status.valueOf(rs.getString(5)), rs.getInt(6)));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (User.UserException ue ) {
			  System.out.println("Invalid User data received from database");
			  ue.printStackTrace();
		  }
		  
		  return users;
	  }
	  
	  /**
	   * parse a ResultSet and returns an ArrayList of CatalogItem from it
	   * @param rs ResultSet of the query to get the CatalogItem table
	   * @return an arrayList of CatalogItems made from the given ResultSet
	   */
	  public static ArrayList<CatalogItem> loadCatalogItems(ResultSet rs)
	  {
		  ArrayList<CatalogItem> catalogItems = new ArrayList<CatalogItem>();
		  try
		  {
			  while (rs.next())
			  {
				  catalogItems.add(new CatalogItem(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("ProductType"),
						  rs.getFloat("ProductPrice"), rs.getInt("ProductAmount"), rs.getString("ProductColor"),
						  rs.getFloat("salesPrice"), rs.getString("Image")));
				  
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  
		  return catalogItems;
	  }
	  
	  //==========================================================================================================================
	  /**
	   * parse a ResultSet and returns an ArrayList of CustomerSatisfactionSurvey from it
	   * @param rs ResultSet of the query to get the CustomerSatisfactionSurvey table
	   * @return an arrayList of CustomerSatisfactionSurvey made from the given ResultSet
	   */
	  
	  public static ArrayList<CustomerSatisfactionSurvey> loadCustomerSatisfactionSurveys(ResultSet rs)
	  {
		  ArrayList<CustomerSatisfactionSurvey> surveys = new ArrayList<CustomerSatisfactionSurvey>();
		  try
		  {
			  String[] questionlist = new String[6];
			  while (rs.next())
			  {
				  questionlist[0]=rs.getString("question1");
				  questionlist[1]=rs.getString("question2");
				  questionlist[2]=rs.getString("question3");
				  questionlist[3]=rs.getString("question4");
				  questionlist[4]=rs.getString("question5");
				  questionlist[5]=rs.getString("question6");

				  surveys.add(new CustomerSatisfactionSurvey(rs.getString("surveyName"), questionlist));
				  
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  
		  return surveys;
	  }
	  
	  
}
