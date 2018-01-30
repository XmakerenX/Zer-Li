package unittests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import Server.ProtoTypeServer;
import customer.Customer;
import order.Order;
import serverAPI.RemoveOrderRequest;

public class TestCancelOrder {

	private ProtoTypeServer server;
	private DBStab stabDB;
	private Customer customerStub;
	private Order moreThan3Hours;
	private Order lessThan3Hours;
	private Order lessThan1Hour;
	
	@Before
	public void setUp() throws Exception {
		// the  customer for the test
		customerStub = new Customer(10, 1, null, null, Customer.PayType.CREDIT_CARD, 50, null, false, null);
		// orders creation time
		Calendar currentTime = Calendar.getInstance();
		// the time for the more than 3 hours order
		Calendar morethan3 = new GregorianCalendar();
		morethan3.setTimeInMillis(currentTime.getTimeInMillis() + TimeUnit.HOURS.toMillis(4));
		// the time for the more less than 3 hours but more than 1 order
		Calendar lessThan3 = new GregorianCalendar();
		lessThan3.setTimeInMillis(currentTime.getTimeInMillis() + TimeUnit.HOURS.toMillis(2));
		// the time for the less than 1 hour order
		Calendar lessThan1 = new GregorianCalendar();
		lessThan1.setTimeInMillis(currentTime.getTimeInMillis() + TimeUnit.MINUTES.toMillis(45));
		// the test orders
		moreThan3Hours = new Order(5, Order.Status.NEW, 200, currentTime, morethan3, null, null, null, Order.PayMethod.CREDITCARD, 1, 10);
		lessThan3Hours = new Order(6, Order.Status.NEW, 200, currentTime, lessThan3, null, null, null, Order.PayMethod.CREDITCARD, 1, 10);
		lessThan1Hour = new Order(7, Order.Status.NEW, 200, currentTime, lessThan1, null, null, null, Order.PayMethod.CREDITCARD, 1, 10);
		// the stab database
		stabDB = new DBStab(moreThan3Hours);
		// the class under test injected with the stab database
		server = new ProtoTypeServer(stabDB);
	}

	@Test
	public void testCancelOrderMoreThan3Hour() 
	{
		// set customer and order data in the stab database
		stabDB.setCustomer(customerStub);
		stabDB.setOrder(moreThan3Hours);
		// refund the loaded order
		ProtoTypeServer.CancelInfo result = server.refundOrder(new RemoveOrderRequest(moreThan3Hours.getID()));
		// assert refund amount
		assertTrue("Assert refund amount",result.getRefundAmount() == moreThan3Hours.getPrice());
		// assert the updated customer data
		assertTrue("Assert updated customer ID",stabDB.getUpdatedCustomerID() == customerStub.getID());
		assertTrue("Assert updated customer store ID",stabDB.getUpdatedCustomerStore() == customerStub.getStoreID());
		assertTrue("Assert updated customer account balance",
				stabDB.getUpdatedCustomerBalance() == customerStub.getAccountBalance() + moreThan3Hours.getPrice());
		// assert the updated order data
		assertTrue("Assert updated order ID",stabDB.getUpdatedOrderID() == moreThan3Hours.getID());
		assertTrue("Assert updated order Refund",
				stabDB.getUpdatedOrderRefund() == moreThan3Hours.getRefund() + moreThan3Hours.getPrice());
		assertTrue("Assert updated order status",stabDB.getUpdatedOrderStatus() == Order.Status.CANCELED);
	}
	
	@Test
	public void testCancelOrderLessThan3Hour() 
	{
		// set customer and order data in the stab database
		stabDB.setCustomer(customerStub);
		stabDB.setOrder(lessThan3Hours);
		// refund the loaded order
		ProtoTypeServer.CancelInfo result = server.refundOrder(new RemoveOrderRequest(lessThan3Hours.getID()));
		// assert refund amount
		assertTrue("Assert refund amount",result.getRefundAmount() == (lessThan3Hours.getPrice() / 2));
		// assert the updated customer data
		assertTrue("Assert updated customer ID", stabDB.getUpdatedCustomerID() == customerStub.getID());
		assertTrue("Assert updated customer store ID", stabDB.getUpdatedCustomerStore() == customerStub.getStoreID());
		assertTrue("Assert updated customer account balance",
				stabDB.getUpdatedCustomerBalance() == customerStub.getAccountBalance() + lessThan3Hours.getPrice() / 2);
		// assert the updated order data
		assertTrue("Assert updated order ID", stabDB.getUpdatedOrderID() == lessThan3Hours.getID());
		assertTrue("Assert updated order Refund",
				stabDB.getUpdatedOrderRefund() == lessThan3Hours.getRefund() + lessThan3Hours.getPrice() /2);
		assertTrue("Assert updated order status", stabDB.getUpdatedOrderStatus() == Order.Status.CANCELED);
	}
	
	@Test
	public void testCancelOrderLessThan1Hour() 
	{
		// lessThan1Hour
		stabDB.setCustomer(customerStub);
		stabDB.setOrder(lessThan1Hour);
		// refund the loaded order
		ProtoTypeServer.CancelInfo result = server.refundOrder(new RemoveOrderRequest(lessThan1Hour.getID()));
		// assert refund amount
		assertTrue("Assert refund amount", result.getRefundAmount() == 0);
		// assert that customer wasn't updated
		assertTrue("Assert that customer wasn't updated",stabDB.isCustomerUpdated() == false);
		// assert the updated order data
		assertTrue("Assert updated order ID", stabDB.getUpdatedOrderID() == lessThan1Hour.getID());
		assertTrue("Assert updated order Refund", stabDB.getUpdatedOrderRefund() == lessThan1Hour.getRefund());
		assertTrue("Assert updated order status", stabDB.getUpdatedOrderStatus() == Order.Status.CANCELED);
		
	}

}
