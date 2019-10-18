package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    public Iterable<ParkingLot> findAllWithPageRequest(int page, int size) {
        return parkingLotRepository.findAll(of(page, size));
    }

    public Iterable<ParkingLot> findAll() {
        return parkingLotRepository.findAll();
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }

    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ParkingLot update(String name, ParkingLot parkingLot) {
        ParkingLot isFound = parkingLotRepository.findByNameContaining(name);
        if(isNull(isFound)){
            isFound.setName(parkingLot.getName());
            isFound.setCapacity(parkingLot.getCapacity());
            parkingLotRepository.save(isFound);
        }
            return parkingLot;
    }

    public ParkingLot delete(String name) {
        ParkingLot findPadrkingLot = findByNameContaining(name);
        if(!isNull(findPadrkingLot)) {
            parkingLotRepository.delete(findPadrkingLot);
        }
        return findPadrkingLot;
    }
}
