package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.List;

public class JourneyPrinter {

    CostCalculator calculator = new CostCalculator();
    public void chargeAccounts() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        List<Customer> customers = customerDatabase.getCustomers();
        for (Customer customer : customers) {
            calculator.totalJourneysFor(customer);
        }
    }

}
