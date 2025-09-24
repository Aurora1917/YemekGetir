package com.yemekgetir.orderservice.controller;

import com.yemekgetir.DTO.OrderItemRequestDTO;
import com.yemekgetir.orderservice.entity.Order;
import com.yemekgetir.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "Sipariş Yönetimi", description = "Siparişlerin oluşturulması, güncellenmesi ve silinmesiyle ilgili operasyonlar.")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Yeni bir sipariş oluşturur", description = "Sipariş bilgileriyle birlikte yeni bir sipariş kaydı oluşturur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sipariş başarıyla oluşturuldu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "400", description = "Geçersiz sipariş bilgileri.",
                    content = @Content)
    })
    public ResponseEntity<Order> addNewOrder(@RequestBody Order order) {
        Order newOrder = orderService.addOrder(order);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    @Operation(summary = "Mevcut siparişe yeni ürün ekler", description = "Belirtilen siparişe yeni bir menü öğesi ve miktarını ekler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ürün başarıyla siparişe eklendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz ürün bilgileri.", content = @Content)
    })
    public ResponseEntity<Order> addNewOrderItemToOrder(@PathVariable Long orderId, @RequestBody OrderItemRequestDTO requestDTO) {
        Order updatedOrder = orderService.addOrderItemToOrder(orderId, requestDTO.getMenuItemId(), requestDTO.getQuantity());
        return new ResponseEntity<>(updatedOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/items/{menuItemId}")
    @Operation(summary = "Siparişten bir ürünü siler", description = "Belirtilen siparişten, belirtilen menü öğesini siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ürün siparişten başarıyla silindi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Sipariş veya ürün bulunamadı.", content = @Content)
    })
    public ResponseEntity<Order> deleteOrderItemFromOrder(@PathVariable Long orderId, @PathVariable Long menuItemId) {
        Order updatedOrder = orderService.deleteOrderItemFromOrder(orderId, menuItemId);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Bir siparişi siler", description = "Belirtilen ID'ye sahip siparişi siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Sipariş başarıyla silindi (İçerik yok).", content = @Content),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Tüm siparişleri listeler", description = "Sistemdeki tüm siparişlerin listesini döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Siparişler başarıyla listelendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class)))
    })
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Siparişi ID'sine göre getirir", description = "Belirtilen ID'ye sahip siparişi döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sipariş başarıyla bulundu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Order.class))),
            @ApiResponse(responseCode = "404", description = "Sipariş bulunamadı.", content = @Content)
    })
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
