package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.service.OrdersService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/parkingLot")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    private ParkingLotController parkingLotController;


    @GetMapping(value = "/all/orders", produces = APPLICATION_JSON_VALUE)
    public Iterable<Orders> listOrders(@RequestParam(required = false, defaultValue = "") Integer page,
                                       @RequestParam(required = false, defaultValue = "") Integer pageSize) {
        if (!isNull(page) && !isNull(pageSize)) {
            return ordersService.findAllWithPageRequest(page, pageSize);
        }
        return ordersService.findAll();
    }

    @GetMapping(value = "/getOrder", produces = APPLICATION_JSON_VALUE)
    private Orders getOrderByPlateNumber(@RequestParam(required = false, defaultValue = "") String plateNumber) {
        return ordersService.findByplateNumber(plateNumber);
    }

    @GetMapping(value = "/getOrders", produces = APPLICATION_JSON_VALUE)
    private List<Orders> getAllOrdersByparkingLotNameAndorderStatus
            (@RequestParam(required = false, defaultValue = "") String parkingLotName,
             @RequestParam(required = false, defaultValue = "") String orderStatus) {
        return ordersService.findAllByParkingLotNameAndOrderStatus(parkingLotName, orderStatus);
    }


    @PostMapping(value = "/{name}/orders", produces = APPLICATION_JSON_VALUE)
    public Orders addOrder(@PathVariable String name, @RequestBody Orders orders) throws NotFoundException {

        ParkingLot parkingLot = parkingLotController.getParkingLotByName(name);
        List<Orders> checkParkingLotSpaces = getAllOrdersByparkingLotNameAndorderStatus(parkingLot.getName(), "Open");
        if (checkParkingLotSpaces.size() >= parkingLot.getCapacity()) {
            throw new NotFoundException("The parking lot is full");
        }
        return ordersService.save(parkingLot, orders);
    }

    @PatchMapping(value = "/{name}/orders", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable String name,
                                                    @RequestParam(required = false, defaultValue = "") String plateNumber)
            throws NotFoundException {
        Orders findOrder = ordersService.findByplateNumber(plateNumber);
        Orders isUpdated = ordersService.update(findOrder);
        if (!isNull(isUpdated)) {
            return new ResponseEntity<>(isUpdated, HttpStatus.OK);
        }
        throw new NotFoundException("Order not found! Cannot update");
    }
}