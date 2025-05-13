package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Order;
import com.NBP.NBP.services.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

    @PreAuthorize("hasAuthority('NBP08_USER') or hasAuthority('NBP08_ADMIN')")
    @PostMapping
    public void createOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PutMapping("/{id}")
    public void updateOrder(@PathVariable int id, @RequestBody Order order) {
        order.setId(id);
        orderService.updateOrder(order);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }
}
