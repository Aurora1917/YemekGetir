package com.yemekgetir.menuservice.controller;

import com.yemekgetir.menuservice.entity.Menu;
import com.yemekgetir.menuservice.entity.MenuItem;
import com.yemekgetir.menuservice.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu) {
        Menu newMenu = menuService.addMenu(menu);
        return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }

    @PostMapping("/{menuId}/items")
    public ResponseEntity<MenuItem> addItemToMenu(@PathVariable Long menuId, @RequestBody MenuItem menuItem) {
        MenuItem newItem = menuService.addItemToMenu(menuId, menuItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<List<Menu>> getAllMenusOfRestaurant(@PathVariable Long restaurantId) {
        List<Menu> menus = menuService.getAllMenusOfRestaurant(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/{menuId}/items")
    public ResponseEntity<List<MenuItem>> getAllMenuItemsInMenu(@PathVariable Long menuId) {
        List<MenuItem> menuItems = menuService.getAllMenuItemsInMenu(menuId);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem menuItem = menuService.getMenuItemById(id);
        return ResponseEntity.ok(menuItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        Menu menu = menuService.updateMenu(id, updatedMenu);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        MenuItem menuItem = menuService.updateMenuItem(id, updatedItem);
        return ResponseEntity.ok(menuItem);
    }

    @DeleteMapping("/{menuId}/items/{itemId}")
    public ResponseEntity<Void> deleteItemFromMenu(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuService.deleteItemFromMenu(menuId, itemId);
        return ResponseEntity.noContent().build();
    }
}