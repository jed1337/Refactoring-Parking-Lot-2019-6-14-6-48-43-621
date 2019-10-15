package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public abstract class AbstractParkingBoy {
    private static final String NOT_ENOUGH_POSITION = "Not enough position";
    private static final String NO_PARKING_TICKET = "Please provide your parking ticket";
    private static final String UNRECOGNIZED_PARKING_TICKET = "Unrecognized parking ticket";

    protected final List<ParkingLot> parkingLotList;
    protected String lastErrorMessage;

    public AbstractParkingBoy(ParkingLot parkingLot) {
        this.parkingLotList = Collections.singletonList(parkingLot);
    }

    public AbstractParkingBoy(List<ParkingLot> parkingLotList) {
        this.parkingLotList = parkingLotList;
    }

    public ParkingTicket park(Car car){
        boolean allParkingLotsAreFull = parkingLotList.stream()
                .allMatch(ParkingLot::isFull);

        if(!allParkingLotsAreFull){
            return getBestParkingLot().park(car);
        }
        else{
            lastErrorMessage = NOT_ENOUGH_POSITION;
            return null;
        }
    }

    protected abstract ParkingLot getBestParkingLot();

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            lastErrorMessage = NO_PARKING_TICKET;
            return null;
        }

        Car car = fetchCarFromParkingLotList(ticket);

        if (car == null) {
            lastErrorMessage = UNRECOGNIZED_PARKING_TICKET;
            return null;
        }
        return car;
    }

    private Car fetchCarFromParkingLotList(ParkingTicket ticket) {
        for (ParkingLot parkingLot : parkingLotList) {
            Car fetchedCar = parkingLot.fetchCar(ticket);
            if(fetchedCar!=null){
                return fetchedCar;
            }
        }
        return null;
    }

    public String getLastErrorMessage() {
        return lastErrorMessage;
    }
}
