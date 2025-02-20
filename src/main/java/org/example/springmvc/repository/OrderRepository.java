package org.example.springmvc.repository;

import org.example.springmvc.model.Order;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();
    private final AtomicLong orderId = new AtomicLong(1);

    public OrderRepository() {
        orders.add(new Order(orderId.getAndIncrement(), LocalDate.now(), 1000, new ArrayList<>()));
        orders.add(new Order(orderId.getAndIncrement(), LocalDate.now(), 2000, new ArrayList<>()));
        orders.add(new Order(orderId.getAndIncrement(), LocalDate.now(), 3000, new ArrayList<>()));
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Optional<Order> getOrderById(Long id) {
        return orders.stream()
                .filter(order -> order.getId().equals(id))
                .findFirst();
    }

    public Order addOrder(Order order) {
        order.setId(orderId.getAndIncrement());
        order.setCreationDate(LocalDate.now());
        orders.add(order);
        return order;
    }
}
