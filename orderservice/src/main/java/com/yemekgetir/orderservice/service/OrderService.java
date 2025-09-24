package com.yemekgetir.orderservice.service;

import com.yemekgetir.DTO.MenuItemDTO;
import com.yemekgetir.orderservice.entity.Order;
import com.yemekgetir.orderservice.entity.OrderItem;
import com.yemekgetir.orderservice.repository.OrderRepository;
import com.yemekgetir.orderservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    public Order addOrder(Order order) {
        order.setTotalAmount(BigDecimal.ZERO);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found. ID: " + orderId));
        orderRepository.deleteById(order.getId());
    }


    public Order addOrderItemToOrder(Long orderId, Long menuItemId, int quantity) {
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: ID: " + orderId));

        // The URL is corrected to match the MenuController's GET endpoint
        MenuItemDTO menuItemDetails = restTemplate.getForObject(
                "http://menuservice/menus/items/" + menuItemId, MenuItemDTO.class);

        if (menuItemDetails == null) {
            throw new ResourceNotFoundException("Menu Item not found: ID: " + menuItemId);
        }

        OrderItem newOrderItem = new OrderItem();
        newOrderItem.setMenuItemId(menuItemId);
        newOrderItem.setName(menuItemDetails.getName());
        newOrderItem.setPrice(menuItemDetails.getPrice());
        newOrderItem.setQuantity(quantity);
        newOrderItem.setOrder(existingOrder);

        BigDecimal itemTotal = newOrderItem.getPrice().multiply(new BigDecimal(quantity));
        existingOrder.setTotalAmount(existingOrder.getTotalAmount().add(itemTotal));
        existingOrder.getOrderItems().add(newOrderItem);

        return orderRepository.save(existingOrder);
    }

    public Order deleteOrderItemFromOrder(Long orderId, Long menuItemId) {
        // 1. Siparişi veritabanından bul
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: ID: " + orderId));


        Optional<OrderItem> itemToRemove = existingOrder.getOrderItems().stream()
                .filter(item -> item.getMenuItemId().equals(menuItemId))
                .findFirst();


        if (itemToRemove.isPresent()) {
            OrderItem item = itemToRemove.get();

            BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
            existingOrder.setTotalAmount(existingOrder.getTotalAmount().subtract(itemTotal));

            existingOrder.getOrderItems().remove(item);
        } else {
            // Eğer item bulunamazsa hata fırlat
            throw new ResourceNotFoundException("OrderItem not found in order: MenuItem ID: " + menuItemId);
        }

        return orderRepository.save(existingOrder);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: ID: " + orderId));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}