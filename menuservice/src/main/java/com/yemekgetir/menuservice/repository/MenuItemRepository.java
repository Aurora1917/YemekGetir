package com.yemekgetir.menuservice.repository;

import com.yemekgetir.menuservice.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
