package com.NBP.NBP.models;

import com.NBP.NBP.models.enums.OrderStatus;

public class Order {
    private int id;
    private int customUserId;
    private int equipmentId;
    private int price;
    private int supplierId;
    private String invoiceNumber;
    private OrderStatus orderStatus;

    public Order(int id, int customUserId, int equipmentId, int price, int supplierId,
            String invoiceNumber, OrderStatus orderStatus) {
        this.id = id;
        this.customUserId = customUserId;
        this.equipmentId = equipmentId;
        this.price = price;
        this.supplierId = supplierId;
        this.invoiceNumber = invoiceNumber;
        this.orderStatus = orderStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomUserId() {
        return customUserId;
    }

    public void setCustomUserId(int customUserId) {
        this.customUserId = customUserId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
