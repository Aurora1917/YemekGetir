package com.yemekgetir.restaurantservice.repository;

import com.yemekgetir.restaurantservice.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional findByEmail(String email);
}
