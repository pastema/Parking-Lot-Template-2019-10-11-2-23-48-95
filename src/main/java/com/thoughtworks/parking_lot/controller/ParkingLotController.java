package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/parkingLot")
public class ParkingLotController {

    @Autowired
    private ParkingLotService parkingLotService;

    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public Iterable<ParkingLot> list(@RequestParam(required = false, defaultValue = "") Integer page,
                                     @RequestParam(required = false, defaultValue = "") Integer pageSize) {
        if (!isNull(page) && !isNull(pageSize)) {
            return parkingLotService.findAllWithPageRequest(page, pageSize);
        }
        return parkingLotService.findAll();
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ParkingLot getParkingLotByName(@RequestParam(required = false, defaultValue = "") String name) {
        return parkingLotService.findByName(name);
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity addparkingLot(@RequestBody ParkingLot parkingLot) {
        ParkingLot isSaved = parkingLotService.save(parkingLot);
        if (isNull(isSaved)) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(isSaved, HttpStatus.CREATED);
    }

    @PatchMapping(produces = APPLICATION_JSON_VALUE)
    public HttpEntity updateParkingLot(@RequestBody ParkingLot parkingLot,
                                       @RequestParam(required = false, defaultValue = "") String name)
            throws NotFoundException {
        ParkingLot isUpdated = parkingLotService.update(name, parkingLot);
        if (!isNull(isUpdated)) {
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        }
        throw new NotFoundException("Company not found! Cannot update");
    }

    @DeleteMapping(value = "/{name}", produces = APPLICATION_JSON_VALUE)
    public ParkingLot deleteParkingLot(@PathVariable String name) {
        return parkingLotService.delete(name);
    }

}