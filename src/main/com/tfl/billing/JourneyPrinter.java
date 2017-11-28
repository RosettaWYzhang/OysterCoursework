package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.List;

public class JourneyPrinter {


    public void chargeAccounts() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            CustomerSummary summary = new CustomerSummary(customer);
            summary.printCustomerSummary();
        }
    }

}
