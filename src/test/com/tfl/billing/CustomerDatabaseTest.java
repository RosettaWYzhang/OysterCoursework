package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import java.util.List;



public class CustomerDatabaseTest {
    List<Customer> customers = CustomerDatabase.getInstance().getCustomers();

    @Test
    public void testGetCustomerList(){
        System.out.println(customers.size());
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testGetCustomerByIndex(){
        assertTrue(customers.get(0)!=null);
    }


}
