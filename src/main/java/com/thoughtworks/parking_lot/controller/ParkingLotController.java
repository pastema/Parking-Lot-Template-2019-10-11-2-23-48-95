package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.PageRequest.of;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/parkingLot")
public class ParkingLotController {

    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public Iterable<ParkingLot> list(@RequestParam(required = false, defaultValue = "") Integer page,
                                     @RequestParam(required = false, defaultValue = "") Integer pageSize){
        if(!isNull(page) && !isNull(pageSize)){
            return parkingLotRepository.findAll(of(page, pageSize));
        }
        return parkingLotRepository.findAll();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    private ParkingLot getParkingLotByName(@RequestParam(required = false, defaultValue = "") String name) {
        return parkingLotRepository.findByNameContaining(name);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public ParkingLot addparkingLot(@RequestBody ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public ParkingLot updateParkingLot(@RequestBody ParkingLot parkingLot,
                                       @RequestParam(required = false, defaultValue = "") String name) {
        ParkingLot findParkingLot = getParkingLotByName(name);
        if(!isNull(findParkingLot)){
            findParkingLot.setName(parkingLot.getName());
            findParkingLot.setCapacity(parkingLot.getCapacity());
            parkingLotRepository.save(findParkingLot);
        }
        return findParkingLot;
    }

    @DeleteMapping(value = "/{name}", produces = APPLICATION_JSON_VALUE)
    public void deleteParkingLot(@PathVariable String name) {
        ParkingLot findParkingLot = getParkingLotByName(name);
        parkingLotRepository.delete(findParkingLot);
    }
}