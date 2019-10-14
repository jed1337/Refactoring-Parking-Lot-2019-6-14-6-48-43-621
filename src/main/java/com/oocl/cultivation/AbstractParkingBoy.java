package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public abstract class AbstractParkingBoy {
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
            lastErrorMessage = "Not enough position";
            return null;
        }
    }

    protected abstract ParkingLot getBestParkingLot();

    public Car fetch(ParkingTicket ticket) {
        if (ticket == null) {
            lastErrorMessage = "Please provide your parking ticket";
            return null;
        }

        Car car = fetchCarFromParkingLotList(ticket);

        if (car == null) {
            lastErrorMessage = "Unrecognized parking ticket";
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
