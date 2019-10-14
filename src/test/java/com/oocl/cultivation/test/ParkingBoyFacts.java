package com.oocl.cultivation.test;

import com.oocl.cultivation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParkingBoyFacts {
    private ParkingBoy defaultParkingBoy;

    @BeforeEach
    void setUp() {
        defaultParkingBoy = new ParkingBoy(new ParkingLot());
    }

    @Test
    public void should_park_car_by_parking_boy_and_return_ticket(){
        Car car = new Car();

        ParkingTicket ticket = defaultParkingBoy.park(car);

        assertNotNull(ticket);
    }

    @Test
    public void should_get_car_if_parking_boy_is_given_ticket(){
        Car car = new Car();
        ParkingTicket ticket = defaultParkingBoy.park(car);
        Car fetchedCar = defaultParkingBoy.fetch(ticket);

        assertNotNull(fetchedCar);
    }

    @Test
    public void should_get_correct_car_by_parking_boy() {
        Car firstCar = new Car();
        Car otherCar = new Car();

        defaultParkingBoy.park(firstCar);
        defaultParkingBoy.park(otherCar);
        ParkingTicket ticket = defaultParkingBoy.park(firstCar);
        Car fetchedCar = defaultParkingBoy.fetch(ticket);

        assertSame(fetchedCar, firstCar);
        assertNotSame(fetchedCar, otherCar);
    }

    @Test
    public void should_return_no_car_if_no_ticket_is_given_to_parking_boy() {
        Car fetchedCar = defaultParkingBoy.fetch(null);

        assertNull(fetchedCar);
    }

    @Test
    public void should_return_no_car_if_wrong_ticket_is_given_to_parking_boy() {
        ParkingTicket wrongTicket = new ParkingTicket();

        Car fetchedCar = defaultParkingBoy.fetch(wrongTicket);

        assertNull(fetchedCar);
    }

    @Test
    public void should_return_no_car_if_parking_boy_is_given_a_used_ticket() {
        Car myCar = new Car();

        ParkingTicket ticket = defaultParkingBoy.park(myCar);
        defaultParkingBoy.fetch(ticket);

        Car carFetchedByUsedTicket = defaultParkingBoy.fetch(ticket);
        assertNull(carFetchedByUsedTicket);
    }

    @Test
    public void should_not_be_able_to_park_when_parking_lot_is_full() {
        fillParkingLot(defaultParkingBoy);

        ParkingTicket ticketFromFullParking = defaultParkingBoy.park(new Car());
        assertNull(ticketFromFullParking);
    }
    
    @Test
    public void should_be_able_to_park_when_a_car_is_fetched_from_a_full_parking_lot() {
        ParkingTicket firstCarTicket = defaultParkingBoy.park(new Car());
        fillParkingLot(defaultParkingBoy);

        defaultParkingBoy.fetch(firstCarTicket);

        ParkingTicket ticket = defaultParkingBoy.park(new Car());
        assertNotNull(ticket);
    }

    @Test
    public void should_create_error_message_when_parking_boy_is_given_no_ticket() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());

        parkingboy.fetch(null);

        assertEquals(parkingboy.getLastErrorMessage(), "Please provide your parking ticket");
    }

    @Test
    public void should_create_error_message_when_parking_boy_is_given_wrong_ticket() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());
        Car myCar = new Car();
        ParkingTicket wrongTicket = new ParkingTicket();

        ParkingTicket correctTicket = parkingboy.park(myCar);
        parkingboy.fetch(wrongTicket);

        assertEquals(parkingboy.getLastErrorMessage(), "Unrecognized parking ticket");
    }

    @Test
    public void should_create_error_message_when_attempt_to_park_in_full_lot() {
        ParkingBoy parkingboy = new ParkingBoy(new ParkingLot());
        Car myCar = new Car();

        fillParkingLot(parkingboy);

        parkingboy.park(myCar);

        assertEquals(parkingboy.getLastErrorMessage(), "Not enough position");
    }

    @Test
    public void should_be_able_to_park_in_multiple_parking_lots() {
        List<ParkingLot> parkingLotList = new ArrayList<>();
        parkingLotList.add(new ParkingLot());
        parkingLotList.add(new ParkingLot());
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);

        ParkingTicket zerothTicket = parkingBoy.park(new Car());

        for (int i = 1; i <= 14; i++) {
            parkingBoy.park(new Car());
        }

        ParkingTicket fifteenthTicket = parkingBoy.park(new Car());

        Car fetchedZerothCar = parkingBoy.fetch(zerothTicket);
        Car fetchedTwentiethCar = parkingBoy.fetch(fifteenthTicket);

        assertNotNull(fetchedZerothCar);
        assertNotNull(fetchedTwentiethCar);
    }

    @Test
    public void should_park_in_the_first_parking_lot_with_space() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstParkingLotTicket = parkingBoy.park(firstCar);
        ParkingTicket secondParkingLotTicket = parkingBoy.park(secondCar);

        assertEquals(firstParkingLot.fetchCar(firstParkingLotTicket), firstCar);
        assertEquals(secondParkingLot.fetchCar(secondParkingLotTicket), secondCar);
    }

    @Test
    public void should_park_in_the_first_parking_lot_with_space_when_a_car_is_removed_from_a_previously_full_lot() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
        ParkingBoy parkingBoy = new ParkingBoy(parkingLotList);
        Car firstCar = new Car();

        ParkingTicket firstParkingLotTicket = parkingBoy.park(firstCar);
        parkingBoy.park(new Car());

        assertEquals(firstParkingLot.fetchCar(firstParkingLotTicket), firstCar);
        assertFalse(firstParkingLot.isFull());
        assertTrue(secondParkingLot.isFull());

        Car thirdCar = new Car();
        ParkingTicket thirdParkingTicket = parkingBoy.park(thirdCar);
        assertEquals(firstParkingLot.fetchCar(thirdParkingTicket), thirdCar);
    }

    @Test
    public void should_park_in_the_first_parking_lot_available_given_multiple_parking_lots_with_the_same_space_by_smart_parking_boy() {
        ParkingLot firstParkingLot = new ParkingLot(5);
        ParkingLot secondParkingLot = new ParkingLot(5);
        List<ParkingLot> parkingLotList = Arrays.asList(firstParkingLot, secondParkingLot);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLotList);
        Car firstCar = new Car();
        Car secondCar = new Car();

        ParkingTicket firstParkingLotTicket = smartParkingBoy.park(firstCar);
        ParkingTicket secondParkingLotTicket = smartParkingBoy.park(secondCar);

        assertEquals(firstParkingLot.fetchCar(firstParkingLotTicket), firstCar);
        assertEquals(secondParkingLot.fetchCar(secondParkingLotTicket), secondCar);
    }

    @Test
    public void should_park_in_parking_lot_with_more_space_by_smart_parking_boy_given_parking_lot_with_same_size() {
        ParkingLot lessSpaceParkingLot = new ParkingLot(5);
        lessSpaceParkingLot.park(new Car());
        lessSpaceParkingLot.park(new Car());
        lessSpaceParkingLot.park(new Car());

        ParkingLot moreSpaceParkingLot = new ParkingLot(5);

        List<ParkingLot> parkingLotList = Arrays.asList(lessSpaceParkingLot, moreSpaceParkingLot);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLotList);
        Car myCar = new Car();

        assertEquals(lessSpaceParkingLot.getNumberOfEmptyParkingSpace(), 2);
        assertEquals(moreSpaceParkingLot.getNumberOfEmptyParkingSpace(), 5);

        ParkingTicket ticket = smartParkingBoy.park(myCar);
        assertEquals(moreSpaceParkingLot.fetchCar(ticket), myCar);
    }

    @Test
    public void should_park_in_parking_lot_with_more_space_by_smart_parking_boy_given_parking_lot_with_different_size() {
        ParkingLot lessSpaceParkingLot = new ParkingLot(5);
        ParkingLot moreSpaceParkingLot = new ParkingLot(10);

        List<ParkingLot> parkingLotList = Arrays.asList(lessSpaceParkingLot, moreSpaceParkingLot);
        SmartParkingBoy smartParkingBoy = new SmartParkingBoy(parkingLotList);
        Car myCar = new Car();

        assertEquals(lessSpaceParkingLot.getNumberOfEmptyParkingSpace(), 5);
        assertEquals(moreSpaceParkingLot.getNumberOfEmptyParkingSpace(), 10);

        ParkingTicket ticket = smartParkingBoy.park(myCar);
        assertEquals(moreSpaceParkingLot.fetchCar(ticket), myCar);
    }

    @Test
    public void should_park_in_parking_lot_with_larger_available_position_rate_with_super_smart_parking_boy_given_parking_lot_with_different_space_rate() {
        ParkingLot lessSpaceRateParkingLot = new ParkingLot(10);
        lessSpaceRateParkingLot.park(new Car());
        lessSpaceRateParkingLot.park(new Car());

        ParkingLot moreSpaceRateParkingLot = new ParkingLot(5);

        List<ParkingLot> parkingLotList = Arrays.asList(lessSpaceRateParkingLot, moreSpaceRateParkingLot);
        SuperSmartParkingBoy superSmartParkingBoy = new SuperSmartParkingBoy(parkingLotList);
        Car myCar = new Car();

        ParkingTicket ticket = superSmartParkingBoy.park(myCar);
        assertEquals(moreSpaceRateParkingLot.fetchCar(ticket), myCar);
    }

    private void fillParkingLot(ParkingBoy parkingboy) {
        while (true) {
            if(parkingboy.park(new Car()) == null){
                break;
            }
        }
    }
}
