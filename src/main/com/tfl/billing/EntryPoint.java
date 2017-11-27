package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.ArrayList;
import java.util.List;

public class EntryPoint {
    public static void main(String[] args){
        JourneyPrinter printer = new JourneyPrinter();
        printer.chargeAccounts();
        //List<Customer> customerList = CustomerDatabase.getInstance().getCustomers();
        //CustomerSummary summary = new CustomerSummary(customerList.get(0));
        //summary.printCustomerSummary();

    }
}

// TODO: must check test
// consider using mock //impt
// test printer //impt
// Bigdecimal round up //impt
// connect //impt

// card scanner not complete
// check date format, see if we want to use other time format?
// test journey start, journey end individually
// add more journey events for testing
// TODO: could check
// external jar: oyster card format, scan listener, touch, read id, customer format, check get instance,
// charge(mock object), check diff lines, locator, lookup,
// stationlist, station format
// System test

// TODO: design pattern
// Factory, builder, Singleton, Facade, Adapter, Decorator, Simplicator, Proxy, Caching
// Strategy, template

// caching: store station list in local ram?
// builder: for journey, but may not be better than the inheritance structure?
// factory: extract convertEventLogToJourneys to have a class to determine whether it's start and end
// facade: wrapping to reduce complexity, probably preferable than factory for convertEventLogToJourneys?
// adapter: adapter for external system
// decorator: