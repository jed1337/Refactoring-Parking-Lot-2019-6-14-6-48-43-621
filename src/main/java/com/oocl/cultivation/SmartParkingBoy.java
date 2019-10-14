package com.oocl.cultivation;

import java.util.Collections;
import java.util.List;

public class SmartParkingBoy extends AbstractParkingBoy {
    public SmartParkingBoy(ParkingLot parkingLot) {
        super(Collections.singletonList(parkingLot));
    }

    public SmartParkingBoy(List<ParkingLot> parkingLotList) {
        super(parkingLotList);
    }

    @Override
    protected ParkingLot getBestParkingLot() {
        return parkingLotList.stream()
            .sorted((p1, p2) -> Integer.compare(p2.getNumberOfEmptyParkingSpace(), p1.getNumberOfEmptyParkingSpace()))
            .findFirst()
            .get();
    }
}
