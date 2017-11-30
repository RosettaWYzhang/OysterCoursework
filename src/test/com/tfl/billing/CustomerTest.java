package com.tfl.billing;

import com.oyster.OysterCard;

import com.tfl.external.Customer;

import org.junit.Test;


import static org.junit.Assert.assertTrue;

public class CustomerTest {
    OysterCard oysterCard = new OysterCard();
    Customer customer = new Customer("Test", oysterCard);



    @Test
    public void testCustomerCardId(){
        assertTrue(oysterCard.id() == customer.cardId());
    }
    @Test
    public void testCustomerFullName(){
        assertTrue("Test".equals(customer.fullName()));

    }


}
