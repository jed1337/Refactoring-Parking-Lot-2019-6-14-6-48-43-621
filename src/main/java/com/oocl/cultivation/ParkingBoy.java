package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public class ParkingBoy extends AbstractParkingBoy {
    public ParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public ParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    protected ParkingLot getBestParkingLot() {
        return parkingLotList.stream()
                .filter(parkingLot -> !parkingLot.isFull())
                .findFirst()
                .get();
    }
}
