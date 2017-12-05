package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface PaymentsSystemIF {
    void charge(Customer customer, List<Journey> journeys, BigDecimal totalBill);
}
