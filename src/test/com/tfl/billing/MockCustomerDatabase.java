package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class MockCustomerDatabase implements CustomerDatabaseIF{

    private static MockCustomerDatabase instance = new MockCustomerDatabase();

    private MockCustomerDatabase() {
        add();
    }

    private List<Customer> customers = new ArrayList();

    private void add(){
        customers.add(new Customer("Rosetta Zhang", new OysterCard("38411111-8cf0-11bd-b23e-10b96e4ef00d")));
        customers.add(new Customer("Yihang Li", new OysterCard("3822222-f266-4426-ba1b-bcc506541866")));
        customers.add(new Customer("Roboto Lin", new OysterCard("3833333-87df-447f-bf5c-d9961ab9d01e")));
    }


    public static MockCustomerDatabase getInstance() {
        return instance;
    }

    @Override
    public boolean isRegisteredId(UUID cardId) {
        Iterator i$ = this.customers.iterator();

        Customer customer;
        do {
            if(!i$.hasNext()) {
                return false;
            }

            customer = (Customer)i$.next();
        } while(!customer.cardId().equals(cardId));

        return true;
    }

    @Override
    public List<Customer> getCustomers() {
        return customers;
    }
}
