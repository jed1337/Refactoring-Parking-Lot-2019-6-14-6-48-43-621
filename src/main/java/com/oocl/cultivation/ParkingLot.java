package com.oocl.cultivation;

import java.util.HashMap;
import java.util.Map;

public class ParkingLot {
    private final int capacity;
    private Map<ParkingTicket, Car> cars = new HashMap<>();

    public ParkingLot() {
        this(10);
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    public boolean isFull() {
        return cars.size() >= capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public ParkingTicket park(Car car) {
        if(cars.size()>=capacity){
            return null;
        }

        ParkingTicket parkingTicket = new ParkingTicket();
        cars.put(parkingTicket, car);
        return parkingTicket;
    }

    public Car fetchCar(ParkingTicket ticket) {
        return cars.remove(ticket);
    }

    public int getNumberOfEmptyParkingSpace(){
        return capacity - cars.size();
    }
}
