package com.yemekgetir.menuservice.controller;


import com.yemekgetir.menuservice.entity.Menu;
import com.yemekgetir.menuservice.entity.MenuItem;
import com.yemekgetir.menuservice.repository.MenuRepository;
import com.yemekgetir.menuservice.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {


    private final MenuService menuService;
    private final MenuRepository menuRepository;


    public MenuController(MenuService menuService,
                          MenuRepository menuRepository) {
        this.menuService = menuService;
        this.menuRepository = menuRepository;
    }

    @PostMapping
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu) {
        Menu newMenu = menuService.addMenu(menu);
        return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }

    @PostMapping("/{menuId}/items")
    public ResponseEntity<MenuItem> addItemToMenu (@PathVariable Long menuId,@RequestBody MenuItem menuItem)
    {
        MenuItem newItem = menuService.addItemToMenu(menuId, menuItem);
        return new ResponseEntity<>(newItem,HttpStatus.CREATED);
    }

    @GetMapping("byrestaurant/{restaurantId}")
    public ResponseEntity<List<Menu>> getAllMenusOfRestaurant(@PathVariable Long restaurantId)
    {
        List<Menu> menus = menuRepository.findAllByRestaurantId(restaurantId);
        if(!menus.isEmpty())
        {
            return ResponseEntity.ok(menus);
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long menuId)
    {

        Menu menu = menuService.getMenuById(menuId);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long menuId,@RequestBody Menu updatedmenu)
    {
        Menu menu = menuService.updateMenu(menuId,updatedmenu);
        return ResponseEntity.ok(updatedmenu);
    }

    @DeleteMapping("{menuId}/items/{itemId}")
    public ResponseEntity<Void> deleteItemFromMenu(@PathVariable Long menuId,@PathVariable Long itemId)
    {
        menuService.deleteItemFromMenu(menuId,itemId);
        return ResponseEntity.noContent().build();
    }

}

