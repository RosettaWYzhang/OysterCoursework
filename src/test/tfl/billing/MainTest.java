package test.tfl.billing;




import com.tfl.billing.*;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;



public class MainTest {

//	+chargeAccounts(): void
//	-totalJourneysFor(customer):void
//	-roundToNearestPenny(BigDecimal): BigDecimal
//	-peak(Journey): boolean
//	-peak(Date):boolean
//	+connect(OysterCardReader... cardReaders): void
//	-cardScanned(cardId, readerID): void



    private JourneyStart start;
    private JourneyEnd end;

    final Journey journey = new Journey(start, end);

    final TravelTracker travelTracker = new TravelTracker();
    List<Journey> journeys = new ArrayList<Journey>();





        // A customer touch the sensor



        @Test
        public void checkCardScanned(){

        }

        @Test
        public void checkReadAndWriteCustData(){

        }

          @Test
          public void checkPeaktimeCostOld(){
              journeys.add(journey);
              BigDecimal price = new BigDecimal(2.40);
              assertTrue(travelTracker.journeysListCostOldVersion(journeys) == price);
          }

          @Test
          public void checkPeaktimeCostNew(){
              journeys.add(journey);
              BigDecimal price = new BigDecimal(2.90);
              assertTrue(travelTracker.journeysListCostNewVersion(journeys) == price);
          }

//
//
//        @Test
//        public void checkCorrectCalculateCap(){
//                  journeys.add(journey);
//
//
//        }

        @Test
        public void allJourneyIsRecord(){

        }

        @Test
        public void checkTopUpSuccess(){

        }




}


