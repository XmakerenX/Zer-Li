package Order;

public class Order 
{
  public enum Status{}
  String orderID ="";
  String orderStatus ="";
  Float  orderPrice;
  String orderRequiredDate="";
  String orderShipmentAdress="";
  String orderPaymentMethod="";
  String orderOriginStore="";
  
  //todo:
  //ArrayList<ItemInOrder> = newArrayList<itemInOrder>();
  
  public Order(String id,String status,String price,String date,
		  	   String shipphingAdr,String payMethod,String originShop)
  {
	  
  }
  
	//---------------------------------------------------  
	  public void setID()
	  {
	  
	  }
	//---------------------------------------------------
	  public void getID()
	  {
		  
	  }
	//---------------------------------------------------
	  public void setStatus()
	  {
		  
	  }
	//---------------------------------------------------
	  public String getStatus()
	  {
	      return this.orderStatus;
	  }
	//---------------------------------------------------
	  public float getPrice()
	  {
		  return this.orderPrice;
	  }
	//---------------------------------------------------
	  public void setPrice(float price) throws Exception
	  {
		  if(price>=0)
		  this.orderPrice = price;
		  else
		  {
			  throw new OrderException("price must be above or equal zero");
		  }
	  }
	  
  
}
