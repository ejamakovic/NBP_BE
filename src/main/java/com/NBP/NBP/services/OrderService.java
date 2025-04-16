package com.NBP.NBP.services;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.Order;
import com.NBP.NBP.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id);
    }

    public int saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public int updateOrder(Order order) {
        return orderRepository.update(order);
    }

    public int deleteOrder(int id) {
        return orderRepository.delete(id);
    }

    public List<Order> findByEquipment(Equipment equipment) {
        return orderRepository.findByEquipment(equipment);
    }

}
