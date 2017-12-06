//package com.tfl.billing;
//
//import com.oyster.OysterCard;
//import com.oyster.OysterCardReader;
//import com.tfl.external.Customer;
//import com.tfl.external.PaymentsSystem;
//import com.tfl.billing.PaymentsSystemAdapter;
//import com.tfl.underground.OysterReaderLocator;
//import com.tfl.underground.Station;
//import org.jmock.Expectations;
//import org.jmock.integration.junit4.JUnitRuleMockery;
//import org.junit.Rule;
//import org.junit.Test;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class PaymentsSystemAdapterTest {
//
//    @Rule
//    public JUnitRuleMockery context = new JUnitRuleMockery();
//
//    PaymentsSystem mockPaymentSystem = context.mock(PaymentsSystem.class);
//    List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
//
//
//    final OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
//
//    OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
//    OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
//    OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);
//
//
//    @Test
//    public void callsPaymentAdapterChargeMethodWithCorrectParameter(){
//        PaymentHandlerInterface paymentHandler = new PaymentHandler(mockPaymentStrategy, mockPaymentSystem);
//
//        Customer yihang= new Customer("Yihang Li", new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");
//
//        context.checking(new Expectations() {{
//            List<Journey> journeys = new ArrayList<Journey>();
//
//            BigDecimal customerTotal = new BigDecimal(0);
//            customerTotal= customerTotal.setScale(2, BigDecimal.ROUND_HALF_UP);
//
//            exactly(1).of(mockPaymentStrategy).totalJourneysFor(with(equal(zlatan_ibrahimovic)), with(aNonNull(ArrayList.class))); will(returnValue(customerTotal));
//            exactly(1).of(mockPaymentStrategy).getJourneysForCustomer(); will(returnValue(journeys));
//
//            exactly(1).of(mockPaymentSystem).charge(zlatan_ibrahimovic,journeys, customerTotal);
//
//
//        }});
//
//        paymentHandler.charge(yihang,eventLog);
//
//    }
//
//
//}