package com.tfl.billing;


import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class PaymentSystemAdapter implements PaymentSystemIF {
    private static PaymentsSystem paymentsSystem =PaymentsSystem.getInstance();
    private static PaymentSystemAdapter instance = new PaymentSystemAdapter();
    private PaymentSystemAdapter(){}

    @Override
    public PaymentSystemAdapter getInstance() {
        return instance;
    }

    @Override
    public void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill) {
        paymentsSystem.charge(customer, journeys, totalBill);
    }

    @Override
    public String stationWithReader(UUID originId) {
        return null;
    }
}
