package com.yemekgetir.menuservice.controller;

import com.yemekgetir.menuservice.entity.Menu;
import com.yemekgetir.menuservice.entity.MenuItem;
import com.yemekgetir.menuservice.service.MenuService;
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
@RequestMapping("/menus")
@Tag(name = "Menü Yönetimi", description = "Restoran menüleri ve menü öğeleriyle ilgili operasyonlar.")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @Operation(summary = "Yeni bir menü ekler", description = "Verilen menü bilgileriyle yeni bir menü kaydı oluşturur.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menü başarıyla oluşturuldu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Menu.class))),
            @ApiResponse(responseCode = "400", description = "Geçersiz menü bilgileri.",
                    content = @Content)
    })
    public ResponseEntity<Menu> addMenu(@RequestBody Menu menu) {
        Menu newMenu = menuService.addMenu(menu);
        return new ResponseEntity<>(newMenu, HttpStatus.CREATED);
    }

    @PostMapping("/{menuId}/items")
    @Operation(summary = "Menüye yeni bir öğe ekler", description = "Belirtilen menüye yeni bir menü öğesi ekler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Menü öğesi başarıyla eklendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItem.class))),
            @ApiResponse(responseCode = "404", description = "Menü bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz öğe bilgileri.", content = @Content)
    })
    public ResponseEntity<MenuItem> addItemToMenu(@PathVariable Long menuId, @RequestBody MenuItem menuItem) {
        MenuItem newItem = menuService.addItemToMenu(menuId, menuItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    @GetMapping("/restaurants/{restaurantId}")
    @Operation(summary = "Bir restorana ait tüm menüleri getirir", description = "Belirtilen restoran ID'sine ait tüm menüleri listeler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menüler başarıyla listelendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Restoran bulunamadı veya menüsü yok.", content = @Content)
    })
    public ResponseEntity<List<Menu>> getAllMenusOfRestaurant(@PathVariable Long restaurantId) {
        List<Menu> menus = menuService.getAllMenusOfRestaurant(restaurantId);
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Menüyü ID'sine göre getirir", description = "Belirtilen ID'ye sahip menüyü döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menü başarıyla bulundu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Menu.class))),
            @ApiResponse(responseCode = "404", description = "Menü bulunamadı.", content = @Content)
    })
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Menu menu = menuService.getMenuById(id);
        return ResponseEntity.ok(menu);
    }

    @GetMapping("/{menuId}/items")
    @Operation(summary = "Bir menüdeki tüm öğeleri listeler", description = "Belirtilen menü ID'sine ait tüm menü öğelerini döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menü öğeleri başarıyla listelendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Menü bulunamadı.", content = @Content)
    })
    public ResponseEntity<List<MenuItem>> getAllMenuItemsInMenu(@PathVariable Long menuId) {
        List<MenuItem> menuItems = menuService.getAllMenuItemsInMenu(menuId);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/items/{id}")
    @Operation(summary = "Bir menü öğesini ID'sine göre getirir", description = "Belirtilen ID'ye sahip menü öğesini döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menü öğesi başarıyla bulundu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItem.class))),
            @ApiResponse(responseCode = "404", description = "Menü öğesi bulunamadı.", content = @Content)
    })
    public ResponseEntity<MenuItem> getMenuItemById(@PathVariable Long id) {
        MenuItem menuItem = menuService.getMenuItemById(id);
        return ResponseEntity.ok(menuItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Bir menüyü günceller", description = "Belirtilen ID'ye sahip menüyü günceller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menü başarıyla güncellendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Menu.class))),
            @ApiResponse(responseCode = "404", description = "Güncellenecek menü bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz güncelleme bilgileri.", content = @Content)
    })
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu updatedMenu) {
        Menu menu = menuService.updateMenu(id, updatedMenu);
        return ResponseEntity.ok(menu);
    }

    @PutMapping("/items/{id}")
    @Operation(summary = "Bir menü öğesini günceller", description = "Belirtilen ID'ye sahip menü öğesini günceller.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Menü öğesi başarıyla güncellendi.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MenuItem.class))),
            @ApiResponse(responseCode = "404", description = "Güncellenecek menü öğesi bulunamadı.", content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz güncelleme bilgileri.", content = @Content)
    })
    public ResponseEntity<MenuItem> updateMenuItem(@PathVariable Long id, @RequestBody MenuItem updatedItem) {
        MenuItem menuItem = menuService.updateMenuItem(id, updatedItem);
        return ResponseEntity.ok(menuItem);
    }

    @DeleteMapping("/{menuId}/items/{itemId}")
    @Operation(summary = "Menüden bir öğeyi siler", description = "Belirtilen menüden, belirtilen menü öğesini siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Menü öğesi başarıyla silindi (İçerik yok).", content = @Content),
            @ApiResponse(responseCode = "404", description = "Menü veya öğe bulunamadı.", content = @Content)
    })
    public ResponseEntity<Void> deleteItemFromMenu(@PathVariable Long menuId, @PathVariable Long itemId) {
        menuService.deleteItemFromMenu(menuId, itemId);
        return ResponseEntity.noContent().build();
    }
}
