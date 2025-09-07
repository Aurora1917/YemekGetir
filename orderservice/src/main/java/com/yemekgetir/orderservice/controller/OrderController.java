package com.yemekgetir.orderservice.controller;
import com.yemekgetir.DTO.OrderItemRequestDTO;
import com.yemekgetir.orderservice.entity.Order;
import com.yemekgetir.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order) {
        Order newOrder = orderService.addOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<Order> addNewOrderItemToOrder(@PathVariable Long orderId, @RequestBody OrderItemRequestDTO requestDTO) {
        Order updatedOrder = orderService.addOrderItemToOrder(orderId, requestDTO.getMenuItemId(), requestDTO.getQuantity());
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/items/{menuItemId}")
    public ResponseEntity<Order> deleteOrderItemFromOrder(@PathVariable Long orderId, @PathVariable Long menuItemId) {
        Order updatedOrder = orderService.deleteOrderItemFromOrder(orderId, menuItemId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}