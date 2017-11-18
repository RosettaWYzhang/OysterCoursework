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


        //mock function
//	@Rule
//	  public JUnitRuleMockery context = new JUnitRuleMockery();
//
//	  MemoryCard memoryCard = context.mock(MemoryCard.class);
//	  Sensor sensor = context.mock(Sensor.class);
//	//  WriteListener writeListener = context.mock(WriteListener.class);
//	  Camera camera = new Camera(sensor, memoryCard);
//
//
//
//
//	  @Test
    //mock example
//	  public void switchingTheCameraOnPowersUpTheSensor() {
//
//	    // write your test here
//		  context.checking(new Expectations(){{
//			  exactly(1).of(sensor).powerUp();
//
//		  }
//		});
//	 camera.powerOn();
//	  }


        //steps:
        //mock A person who scan the oyster card  , return exactly(1).
        //
        //
        //mock A person who start from time a  to time b
        //calcualte the total fee whether correct or not

//
//        //example test to check database is empty
//        @Test
//        public void isInitialisedEmpty(){
//            UserID list = new UserID();
//            assertTrue(list.isEmpty());
//            assertThat(list.size().is(0));
//        }

  /*  private JourneyStart start;
    private JourneyEnd end;

    final Journey journey = new Journey(start, end);

    final TravelTracker travelTracker = new TravelTracker();
    List<Journey> journeys = new ArrayList<Journey>();


*/


        /*
           A customer touch the sensor

         */

        @Test
        public void checkCardScanned(){

        }

        @Test
        public void checkReadAndWriteCustData(){

        }

      /*  @Test
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
        */
        @Test
        public void checkCorrectCalculateCap(){

        }

        @Test
        public void allJourneyIsRecord(){

        }

        @Test
        public void checkTopUpSuccess(){

        }

//    public void cardScanned(UUID cardId, UUID readerId) {
//        if (currentlyTravelling.contains(cardId)) {
//            eventLog.add(new JourneyEnd(cardId, readerId));
//            currentlyTravelling.remove(cardId);
//        } else {
//            if (CustomerDatabase.getInstance().isRegisteredId(cardId)) {
//                currentlyTravelling.add(cardId);
//                eventLog.add(new JourneyStart(cardId, readerId));
//            } else {
//                throw new UnknownOysterCardException(cardId);
//            }
//        }
//    }




    }


