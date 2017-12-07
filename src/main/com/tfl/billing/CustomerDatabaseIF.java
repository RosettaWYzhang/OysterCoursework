package com.tfl.billing;

import com.tfl.external.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerDatabaseIF {
    boolean isRegisteredId(UUID cardId);
    List<Customer> getCustomers();
}
