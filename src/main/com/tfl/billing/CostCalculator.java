package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;

public class CostCalculator {

    static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);

    public void totalCostFor(Customer customer){
        CustomerSummary customerSummary = new CustomerSummary(customer);
        List<Journey> journeys = customerSummary.getCustomerJourney();

        BigDecimal customerTotal = new BigDecimal(0);
        for(Journey journey: journeys){
            customerTotal = customerTotal.add(determinePrice(journey));
        }
        //important charge, belong to PaymentSystem class
        //System.out.println(customerTotal);

        PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));
    }

    private BigDecimal determinePrice(Journey journey){
        TimeChecker timeChecker = new TimeChecker();
        BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
        if (timeChecker.peak(journey)) {
            journeyPrice = PEAK_JOURNEY_PRICE;
        }
        return journeyPrice;
    }


    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }





}
