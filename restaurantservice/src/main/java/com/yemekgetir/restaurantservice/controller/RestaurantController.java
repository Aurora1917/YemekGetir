package com.yemekgetir.restaurantservice.controller;

import com.yemekgetir.restaurantservice.entity.Restaurant;
import com.yemekgetir.restaurantservice.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
@Tag(name = "Restaurant Yönetimi", description = "Restaurantların kaydedilmesi, silinmesi veya bilgilerinin güncellenmesiyle alakalı operasyonlar")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/register")
    @Operation(summary = "Yeni restaurant oluşturur", description = "Verilen restaurant bilgileriyle yeni bir restaurant sisteme kaydedilir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant başarıyla oluşturuldu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "409", description = "Bu restaurant zaten mevcut.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz bilgiler.",
                    content = @Content)
    })
    public ResponseEntity<Restaurant> registerRestaurant(@RequestBody Restaurant restaurant) {
        try {
            Restaurant savedRestaurant = restaurantService.registerRestaurant(restaurant);
            return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "Restaurantı ID'sine göre getirir", description = "Restaurant'a ait bilgileri ID değerine göre döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant başarıyla bulundu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "404", description = "Restaurant bulunamadı.",
                    content = @Content)
    })
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Restaurant bilgilerini günceller", description = "Verilen ID'ye sahip restaurant'ın bilgilerini günceller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Restaurant başarıyla güncellendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Restaurant.class))),
            @ApiResponse(responseCode = "404", description = "Güncellenecek restaurant bulunamadı.",
                    content = @Content)
    })
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id, restaurant);
        if (updatedRestaurant != null) {
            return ResponseEntity.ok(updatedRestaurant);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Restaurantı ID'sine göre siler", description = "Verilen ID'ye sahip restaurant'ı sistemden siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Restaurant başarıyla silindi (İçerik yok).", content = @Content),
            @ApiResponse(responseCode = "404", description = "Silinecek restaurant bulunamadı.", content = @Content)
    })
    public ResponseEntity<Void> deleteRestaurantById(@PathVariable Long id) {
        restaurantService.deleteRestaurantById(id);
        return ResponseEntity.noContent().build();
    }
}
