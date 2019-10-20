package com.thoughtworks.parking_lot.service;


import com.thoughtworks.parking_lot.core.Orders;
import com.thoughtworks.parking_lot.core.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static org.springframework.data.domain.PageRequest.of;

@Service
public class OrdersService {

    @Autowired
    OrdersRepository ordersRepository;


    public Iterable<Orders> findAllWithPageRequest(int page, int pageSize) {
        return ordersRepository.findAll(of(page, pageSize));
    }

    public Iterable<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public Orders findByplateNumber(String plateNumber) {
        return ordersRepository.findByplateNumber(plateNumber);
    }

    public List<Orders> findAllByParkingLotNameAndOrderStatus(String parkingLotName, String orderStatus) {
        return ordersRepository.findAllByParkingLotNameAndOrderStatus(parkingLotName, orderStatus);
    }

    public Orders save(ParkingLot parkingLot, Orders orders) {
        Orders myOrder = new Orders();
        myOrder.setParkingLotName(parkingLot.getName());
        myOrder.setPlateNumber(orders.getPlateNumber());
        myOrder.setCreationTime(getDate());
        myOrder.setOrderStatus("Open");
        myOrder.setCloseTime(null);
        return ordersRepository.save(myOrder);
    }

    private String getDate() {
        LocalDateTime getDateTime = LocalDateTime.now();
        DateTimeFormatter getFormatDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return getDateTime.format(getFormatDateTime);
    }

    public Orders update(Orders orders) {
        if(!isNull(orders)) {
            orders.setOrderStatus("Close");
            orders.setCloseTime(getDate());
            ordersRepository.save(orders);
        }
        return orders;
    }
}
