package com.yemekgetir.menuservice.repository;


import com.yemekgetir.menuservice.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu,Long> {

    List<Menu> findAllByRestaurantId(Long restaurantId);

}
