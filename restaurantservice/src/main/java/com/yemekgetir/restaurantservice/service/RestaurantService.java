package com.yemekgetir.restaurantservice.service;

import com.yemekgetir.restaurantservice.entity.Restaurant;
import com.yemekgetir.restaurantservice.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public Restaurant registerRestaurant(Restaurant restaurant) {
        if (restaurantRepository.findByEmail(restaurant.getEmail()).isPresent()) {
            throw new IllegalStateException("This email address is already in use by another restaurant.");
        }
        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(Long restaurantId) {
        return restaurantRepository.findById(restaurantId);
    }

    public void deleteRestaurantById(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    public Restaurant updateRestaurant(Long id, Restaurant updatedRestaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant.isPresent()) {
            Restaurant restaurant = existingRestaurant.get();
            restaurant.setRestaurantName(updatedRestaurant.getRestaurantName());
            restaurant.setDescription(updatedRestaurant.getDescription());
            restaurant.setPhoneNumber(updatedRestaurant.getPhoneNumber());
            restaurant.setEmail(updatedRestaurant.getEmail());
            return restaurantRepository.save(restaurant);
        }

        return null;
    }
}
