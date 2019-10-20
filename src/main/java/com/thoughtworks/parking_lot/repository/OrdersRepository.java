package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.core.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    Orders findByplateNumber(String plateNumber);
    List<Orders> findAllByParkingLotNameAndOrderStatus(String parkingLotName, String orderStatus);
}
