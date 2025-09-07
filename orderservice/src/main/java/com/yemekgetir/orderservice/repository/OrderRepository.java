package com.yemekgetir.orderservice.repository;


import com.yemekgetir.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {

}
