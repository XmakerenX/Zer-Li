package unittests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

//*************************************************************************************************
/**
* Test Suite which run 2 tests 
* 1. TestGetFieldValue that verifies the GetFieldValue function inside DBMock 
* 2. TestCancelOrder that verifies the refundOrder in ProtoTypeServer
*/
//*************************************************************************************************
@RunWith(Suite.class)
@SuiteClasses({ TestGetFieldValue.class , TestCancelOrder.class })
public class CancelOrderSuite {

}
