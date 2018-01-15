package utils;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import customer.Customer;
import user.User;
import product.CatalogItem;
import product.Product;
import store.Store;
import survey.CustomerSatisfactionSurvey;
import survey.CustomerSatisfactionSurveyResults;

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
			  
		  case "customersatisfactionsurveyresults":
			  return loadCustomerSatisfactionSurveyResults(rs);
		  
		  case "Customers":
			  return loadCustomers(rs);
			  
		  case "Store":
			  return loadStores(rs);
			  
			  
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
				  ImageData image = null;

				  String imageName = rs.getString("Image");
				  try {
					  if (imageName != null)
						  image = new ImageData(ImageData.ServerImagesDirectory+imageName);
					  else
						  image = null;
				  } 
				  catch (IOException e) {
					  System.out.println("Failed to read file "+ImageData.ServerImagesDirectory+imageName);
					  e.printStackTrace();
				  }
				  // check if image was found
				  if (image != null)
				  {
					  //System.out.println(imageName+" was found");
					  catalogItems.add(new CatalogItem(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("ProductType"),
							  rs.getFloat("ProductPrice"), rs.getInt("ProductAmount"), rs.getString("ProductColor"),
							  rs.getFloat("salesPrice"), image.getFileName(), image.getSha256(),rs.getInt("storeID")));
				  }
				  else
				  {
					  // image path of "" means there is no image for this item
					  catalogItems.add(new CatalogItem(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("ProductType"),
							  rs.getFloat("ProductPrice"), rs.getInt("ProductAmount"), rs.getString("ProductColor"),
							  rs.getFloat("salesPrice"), "",null,rs.getInt("storeID")));
				  }

			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  
		  return catalogItems;
	  }
	  
	//==========================================================================================================================
	  /**
	   * parse a ResultSet and returns an ArrayList of CustomerSatisfactionSurveyResults from it
	   * @param rs ResultSet of the query to get the CustomerSatisfactionSurveyResults table
	   * @return an arrayList of CustomerSatisfactionSurveyResults made from the given ResultSet
	   */
	  
	  public static ArrayList<CustomerSatisfactionSurveyResults> loadCustomerSatisfactionSurveyResults(ResultSet rs)
	  {
		  ArrayList<CustomerSatisfactionSurveyResults> surveyResults = new ArrayList<CustomerSatisfactionSurveyResults>();
		  try
		  {
			  while (rs.next())
			  {
				  int resultID = rs.getInt("id");
				  int[] results = new int[6];
				  results[0]=rs.getInt("answer1");
				  results[1]=rs.getInt("answer2");
				  results[2]=rs.getInt("answer3");
				  results[3]=rs.getInt("answer4");
				  results[4]=rs.getInt("answer5");
				  results[5]=rs.getInt("answer6");
				  java.sql.Date sqlDate = rs.getDate("date");
				  LocalDate date = sqlDate.toLocalDate();
				  int storeID = rs.getInt("storeID");
				  String analysis = rs.getString("analysis");
			  
			      surveyResults.add(new CustomerSatisfactionSurveyResults(resultID, results, date, storeID, analysis));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  
		  return surveyResults;
	  }
	//==========================================================================================================================
	  
	  /**
	   * parse a ResultSet and returns an ArrayList of customers from it
	   * @param rs ResultSet of the query to get the users table
	   * @return an arrayList of customers made from the given ResultSet
	   */
	  public static ArrayList<Customer> loadCustomers(ResultSet rs)
	  {
		  ArrayList<Customer> customers = new ArrayList<Customer>();
		  try
		  {
			  while (rs.next())
			  {
				  customers.add(new Customer(rs.getInt("personID") , rs.getInt("StoreID"), rs.getString("fullname"),
						  rs.getString("phoneNumber"), Customer.PayType.valueOf(rs.getString("payMethod")),
						  rs.getFloat("accountBalance"), rs.getString("creditCardNumber"), rs.getBoolean("AccountStatus")));
//				  customers.add(new Customer(rs.getInt(1), rs.getString(2), rs.getString(3),
//						  Customer.PayType.valueOf(rs.getString(4)), rs.getFloat(5), rs.getString(6), rs.getBoolean(7)));
				  
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (Customer.CustomerException ce ) {
			  System.out.println("Invalid customer data received from database");
			  ce.printStackTrace();
		  }
		  
		  return customers;
	  }
	  
	  /**
	   * parse a ResultSet and returns an ArrayList of Stores from it
	   * @param rs ResultSet of the query to get the users table
	   * @return an arrayList of Stores made from the given ResultSet
	   */
	  public static ArrayList<Store> loadStores(ResultSet rs)
	  {
		  ArrayList<Store> stores = new ArrayList<Store>();
		  try
		  {
			  while (rs.next())
			  {
				  stores.add(new Store(rs.getInt("StoreID"), rs.getString("StoreAddress")));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  return stores;
	  }
	  
}
