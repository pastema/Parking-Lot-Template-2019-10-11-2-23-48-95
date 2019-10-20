package com.thoughtworks.parking_lot.core;

import javax.persistence.*;

@Entity
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderNumber;
    private String parkingLotName;

    @Column(unique = true)
    private String plateNumber;

    private String creationTime;
    private String closeTime;
    private  String orderStatus;

    public Orders() {
    }

    public Orders(String parkingLotName, String plateNumber, String creationTime, String closeTime, String orderStatus) {
        this.parkingLotName = parkingLotName;
        this.plateNumber = plateNumber;
        this.creationTime = creationTime;
        this.closeTime = closeTime;
        this.orderStatus = orderStatus;
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getParkingLotName() {
        return parkingLotName;
    }

    public void setParkingLotName(String parkingLotName) {
        this.parkingLotName = parkingLotName;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }


    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
