package utils;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import customer.Customer;
import order.CustomItemInOrder;
import order.Order;
import order.OrderComplaint;
import order.OrderException;
import order.ProductInOrder;
import user.User;
import product.CatalogItem;
import product.Product;
import report.ComplaintReport;
import report.IncomeReport;
import report.OrderReport;
import report.SurveyReport;
import store.Store;
import survey.CustomerSatisfactionSurveyResults;

//*************************************************************************************************
/**
* load Entities From database
*/
//*************************************************************************************************
public class EntityFactory {
	
	//*************************************************************************************************
	/**
	 * loads a entity from database
	 * call the appropriate sub loadXXX function based on the table
	 * @param table the table load the entity from
	 * @param rs the select query result set
	 * @returns An ArrayList of the desired entity data loaded form the database
	 */
	//*************************************************************************************************
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
			  
		  case "Order":
			  return loadOrders(rs);
			  
		  case "ProductInOrder":
			  return loadProductInOrder(rs);
			  
		  case "ordercomplaint":
			  return loadOrderComplaints(rs);
			  
		  case "ComplaintReport":
			  return loadComplaintReports(rs);
			
		  case "incomereport":
			  return loadIncomeReports(rs);
		
		  case "orderreport":
			  return loadOrderReports(rs);
			  
		  case "surveyreport":
			  return loadSurveyReports(rs);
			  
		  case "CustomItem":
			  return loadCustomItem(rs);
			  
		  case "CustomItemProduct":
			  return loadCustomItemInOrder(rs);
			  
		  default:
			  return null;
		  }  
	  }
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of products from it
	   * @param rs ResultSet of the query to get the products table
	   * @return an arrayList of products made from the given ResultSet
	   */
	  //*************************************************************************************************
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
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of users from it
	   * @param rs ResultSet of the query to get the users table
	   * @return an arrayList of users made from the given ResultSet
	   */
	  //*************************************************************************************************
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
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of CatalogItem from it
	   * @param rs ResultSet of the query to get the CatalogItem table
	   * @return an arrayList of CatalogItems made from the given ResultSet
	   */
	  //*************************************************************************************************
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
	  

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of CustomerSatisfactionSurveyResults from it
	   * @param rs ResultSet of the query to get the CustomerSatisfactionSurveyResults table
	   * @return an arrayList of CustomerSatisfactionSurveyResults made from the given ResultSet
	   */
	  //*************************************************************************************************
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
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of customers from it
	   * @param rs ResultSet of the query to get the customers table
	   * @return an arrayList of customers made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<Customer> loadCustomers(ResultSet rs)
	  {
		  ArrayList<Customer> customers = new ArrayList<Customer>();
		  try
		  {
			  while (rs.next())
			  {
				  SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				  Calendar expirationDate;
				  String expirationString = rs.getString("ExpirationDate");
				  if (expirationString != null && !expirationString.trim().equals(""))
				  {
					  expirationDate = new GregorianCalendar();
					  expirationDate.setTime(formatter.parse(expirationString));
				  }
				  else
					  expirationDate = null;
				  
				  customers.add(new Customer(rs.getInt("personID") , rs.getInt("StoreID"), rs.getString("fullname"),
						  rs.getString("phoneNumber"), Customer.PayType.valueOf(rs.getString("payMethod")),
						  rs.getFloat("accountBalance"), rs.getString("creditCardNumber"), rs.getBoolean("AccountStatus"),
						  expirationDate));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (Customer.CustomerException | ParseException ce) {
			  System.out.println("Invalid customer data received from database");
			  ce.printStackTrace();
		  }
		  
		  return customers;
	  }

	  //*************************************************************************************************
	  /**
	   * 
	   * parse a ResultSet and returns an ArrayList of order complaints from it
	   * @param rs ResultSet of the query to get the order complaints table
	   * @return an arrayList of order complaints made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<OrderComplaint> loadOrderComplaints(ResultSet rs)
	  {
		  ArrayList<OrderComplaint> complaints = new ArrayList<OrderComplaint>();
		  try
		  {
			  while (rs.next())
			  {
				  java.sql.Date sqlDate = rs.getDate("date");
				  LocalDate date = sqlDate.toLocalDate();
				  complaints.add(new OrderComplaint(rs.getInt("id") ,rs.getInt("customerID"),  rs.getString("customerName"), rs.getString("customerPhoneNumber"),
						  rs.getInt("storeID"), rs.getString("complaintDescription"),
						  date, rs.getString("time"), rs.getFloat("givenCompensationAmount"), rs.getFloat("maxCompensationAmount"), 
						  rs.getString("status"),Integer.parseInt(rs.getString("orderID"))));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (Exception ce ) {
			  System.out.println("Invalid customer data received from database");
			  ce.printStackTrace();
		  }
		  
		  return complaints;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of Stores from it
	   * @param rs ResultSet of the query to get the stores table
	   * @return an arrayList of Stores made from the given ResultSet
	   */
	  //*************************************************************************************************
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
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of orders from it
	   * @param rs ResultSet of the query to get the order table
	   * @return an arrayList of Orders made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<Order> loadOrders(ResultSet rs)
	  {
		  ArrayList<Order> orders = new ArrayList<Order>();
		  try
		  {
			  while (rs.next())
			  {				  
				  Calendar creationTime = new GregorianCalendar();
				  Timestamp t = rs.getTimestamp("OrderCreationDateTime");
				  creationTime.setTimeInMillis(t.getTime());
				  
				  Calendar requiredTime = new GregorianCalendar();
				  t = rs.getTimestamp("OrderRequiredDate");
				  requiredTime.setTimeInMillis(t.getTime());
				  
				  orders.add(new Order(rs.getInt("OrderID"), Order.Status.valueOf(rs.getString("OrderStatus")), rs.getFloat("OrderPrice"),
						  creationTime, requiredTime,
						  rs.getString("OrderShipmentAddress"), rs.getString("OrderReceiverName"), rs.getString("OrderReceiverPhoneNUmber"),
						  Order.PayMethod.valueOf(rs.getString("OrderPaymentMethod")), rs.getInt("OrderOriginStore"), rs.getInt("OrderCustomerID")));
				  
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (OrderException e) {
			  System.out.println("Invalid order data recived from database");
			  e.printStackTrace();
			  }
		  
		  return orders;
	  }
	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of ProductInORder from it
	   * @param rs ResultSet of the query to get the ProductInORder table
	   * @return an arrayList of ProductInORder made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<ProductInOrder> loadProductInOrder(ResultSet rs)
	  {
		  ArrayList<ProductInOrder> productsInOrder= new ArrayList<ProductInOrder>();
		  
		  try
		  {
			  while (rs.next())
			  {
				  productsInOrder.add(new ProductInOrder(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("ProductType"),
						  rs.getFloat("productPrice"), 0, rs.getString("ProductColor"), rs.getString("ProductGrettingCard")));
			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  
		  return productsInOrder;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of complaint reports from it
	   * @param rs ResultSet of the query to get the users table
	   * @return an arrayList of complaint reports made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<ComplaintReport> loadComplaintReports(ResultSet rs)
	  {
		  ArrayList<ComplaintReport> reports = new ArrayList<ComplaintReport>();
		  try
		  {
			  while (rs.next())
			  {
				  reports.add(new ComplaintReport(ComplaintReport.Quarterly.valueOf(rs.getString("Quarterly")), rs.getString("Year"), rs.getLong("StoreID"),
						  rs.getLong("FirstMonthHandledComplaintsAmount"), rs.getLong("FirstMonthPendingComplaintsAmount"),
						  rs.getLong("SecondMonthHandledComplaintsAmount"), rs.getLong("SecondMonthPendingComplaintsAmount"),
						  rs.getLong("ThirdMonthHandledComplaintsAmount"), rs.getLong("ThirdMonthPendingComplaintsAmount")));

			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (ComplaintReport.ReportException re ) {
			  System.out.println("Invalid complaint reports' data received from database");
			  re.printStackTrace();
		  }

		  return reports;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of income reports from it
	   * @param rs ResultSet of the query to get the income reports table
	   * @return an arrayList of income reports made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<IncomeReport> loadIncomeReports(ResultSet rs)
	  {
		  ArrayList<IncomeReport> reports = new ArrayList<IncomeReport>();
		  try
		  {
			  while (rs.next())
			  {
				  reports.add(new IncomeReport(IncomeReport.Quarterly.valueOf(rs.getString("Quarterly")), rs.getString("Year"), rs.getLong("StoreID"), 
						  rs.getFloat("IncomeAmount")));

			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (IncomeReport.ReportException re ) {
			  System.out.println("Invalid income reports' data received from database");
			  re.printStackTrace();
		  }

		  return reports;
	  } 

	  
	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of order reports from it
	   * @param rs ResultSet of the query to get the order reports  table
	   * @return an arrayList of order reports made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<OrderReport> loadOrderReports(ResultSet rs)
	  {
		  ArrayList<OrderReport> reports = new ArrayList<OrderReport>();
		  try
		  {
			  while (rs.next())
			  {
				  reports.add(new OrderReport(OrderReport.Quarterly.valueOf(rs.getString("Quarterly")), rs.getString("Year"), rs.getLong("StoreID"), 
						  rs.getLong("TotalOrdersAmount"), rs.getLong("BouquetAmount"), rs.getLong("BrideBouquetAmount"),
						  rs.getLong("FlowerPotAmount"), rs.getLong("CustomAmount"), rs.getLong("FlowerClusterAmount")));

			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (OrderReport.ReportException re ) {
			  System.out.println("Invalid order reports' data received from database");
			  re.printStackTrace();
		  }

		  return reports;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of survey reports from it
	   * @param rs ResultSet of the query to get the survey reports table
	   * @return an arrayList of survey reports made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<SurveyReport> loadSurveyReports(ResultSet rs)
	  {
		  ArrayList<SurveyReport> reports = new ArrayList<SurveyReport>();
		  try
		  {
			  while (rs.next())
			  {
				  reports.add(new SurveyReport(SurveyReport.Quarterly.valueOf(rs.getString("Quarterly")),
						  rs.getString("Year"), rs.getLong("StoreID"), 
						  rs.getLong("FirstSurveyAverageResult"), rs.getLong("SecondSurveyAverageResult"),
						  rs.getLong("ThirdSurveyAverageResult"),
						  rs.getLong("FourthSurveyAverageResult"), rs.getLong("FifthSurveyAverageResult"),
						  rs.getLong("SixthSurveyAverageResult")));

			  }
		  }catch (SQLException e) {e.printStackTrace();}
		  catch (SurveyReport.ReportException re ) {
			  System.out.println("Invalid survey reports' data received from database");
			  re.printStackTrace();
		  }

		  return reports;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of Custom Items from it
	   * @param rs ResultSet of the query to get the Custom Items table
	   * @return an arrayList of Custom Items made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<CustomItemInOrder> loadCustomItem(ResultSet rs)
	  {
		  ArrayList<CustomItemInOrder> customItems = new ArrayList<CustomItemInOrder>();

		  try
		  {
			  while (rs.next())
			  {	  
				  customItems.add(new CustomItemInOrder(rs.getInt("CustomItemID") ,rs.getString("CustomItemType"),
						  rs.getFloat("CustomItemPrice"), rs.getString("CustomItemColor"), rs.getString("CustomItemGreetingCard"), null));
			  }
		  }catch (SQLException e) {e.printStackTrace();}

		  return customItems;
	  }

	  //*************************************************************************************************
	  /**
	   * parse a ResultSet and returns an ArrayList of Custom Item In Order from it
	   * @param rs ResultSet of the query to get the Custom Item In Order table
	   * @return an arrayList of Custom Item In Order made from the given ResultSet
	   */
	  //*************************************************************************************************
	  public static ArrayList<Product> loadCustomItemInOrder(ResultSet rs)
	  {
		  ArrayList<Product> components = new ArrayList<Product>();

		  try
		  {
			  while (rs.next())
			  {
				  components.add(new Product(rs.getInt("ProductID"), rs.getString("ProductName"), rs.getString("ProductType"),
						  rs.getFloat("Price"), rs.getInt("Amount"), rs.getString("ProductColor") ) );
			  }
		  }catch (SQLException e) {e.printStackTrace();}

		  return components;
	  }

}
