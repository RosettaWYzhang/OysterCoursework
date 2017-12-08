package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.List;

public class PaymentsSystemAdapter implements PaymentsSystemIF {
    private static PaymentsSystem paymentsSystem = PaymentsSystem.getInstance();
    private static PaymentsSystemAdapter instance = new PaymentsSystemAdapter();

    private PaymentsSystemAdapter(){}

    public static PaymentsSystemAdapter getInstance(){
        return instance;
    }

    @Override
    public void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill) {
        paymentsSystem.charge(customer, journeys, totalBill);
    }

    public void chargeAllAccounts(List<Customer> customers){
        for(Customer customer: customers){
            CustomerSummary summary = new CustomerSummary(customer);
            summary.printCustomerBill(EventLogger.getInstance());
        }
    }

}
