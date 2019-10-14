package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public class SuperSmartParkingBoy extends AbstractParkingBoy {
    public SuperSmartParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public SuperSmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    protected ParkingLot getBestParkingLot() {
        return parkingLotList.stream()
                .sorted((p1, p2) -> Double.compare(getSpaceRate(p2), getSpaceRate(p1)))
                .findFirst()
                .get();
    }

    private double getSpaceRate(ParkingLot parkingLot) {
        double numberOfEmptyParkingSpace = parkingLot.getNumberOfEmptyParkingSpace();
        double totalCapacity = parkingLot.getCapacity();

        return numberOfEmptyParkingSpace/totalCapacity;
    }
}
