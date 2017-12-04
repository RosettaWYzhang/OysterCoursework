package com.tfl.billing;

import com.tfl.external.Customer;

import java.util.List;

public class ChargeAllCustomers {

    private final CustomerDatabaseIF customerDatabase;
    public ChargeAllCustomers(CustomerDatabaseIF customerDatabase){
        this.customerDatabase = customerDatabase;
    }

    public void chargeAccounts(CustomerDatabaseIF database) {
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            totalJourneysFor(customer);
        }
    }

    private void totalJourneysFor(Customer customer) {
        CustomerSummary summary = new CustomerSummary(customer);
        summary.chargeCustomer(EventLogger.getInstance());
    }


}
