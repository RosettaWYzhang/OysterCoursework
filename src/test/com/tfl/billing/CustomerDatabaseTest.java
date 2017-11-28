package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import java.util.List;
import java.util.UUID;


public class CustomerDatabaseTest {
    CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
    List<Customer> customers = customerDatabase.getCustomers();

    @Test
    public void testGetCustomerList(){
        System.out.println(customers.size());
        assertTrue(customers.size() > 0);
    }

    @Test
    public void testGetCustomerByIndex(){
        assertTrue(customers.get(0)!=null);
    }

    @Test
    public void testCustomerDatabaseIsRegisteredID(){
        UUID fakeCardId = UUID.fromString("38411111-8cf0-11bd-b23e-10b96e4ef00d");
        UUID trueCardId = UUID.fromString("3b5a03cb-2be6-4ed3-b83e-94858b43e407");
        assertFalse(customerDatabase.isRegisteredId(fakeCardId));
        assertTrue(customerDatabase.isRegisteredId(trueCardId));
    }

}
