package com.yemekgetir.menuservice.service;

import com.yemekgetir.menuservice.entity.Menu;
import com.yemekgetir.menuservice.entity.MenuItem;
import com.yemekgetir.menuservice.repository.MenuItemRepository;
import com.yemekgetir.menuservice.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    private final MenuItemRepository menuItemRepository;


    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public Menu addMenu(Menu menu)
    {
        return menuRepository.save(menu);
    }

    public MenuItem addItemToMenu(Long menuId, MenuItem item)
    {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalStateException("This menu cannot be found"));
        item.setMenu(existingMenu);

        return menuItemRepository.save(item);

    }

    public List<Menu> getAllMenusOfRestaurant(Long restaurantId)
    {
       return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public void deleteItemFromMenu(Long menuId, Long itemId)
    {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalStateException("This menu cannot be found"));
        MenuItem menuItem = menuItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalStateException("This item cannot be found!"));

        if (!menuItem.getMenu().getId().equals(menuId)) {
            throw new IllegalStateException("Bu menü öğesi belirtilen menüye ait değil.");
        }

        menuItemRepository.deleteById(itemId);
    }

    // MenuService.java
    public Menu getMenuById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Menü bulunamadı."));
    }

    public MenuItem getMenuItemById(Long id) {
        return menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Menü öğesi bulunamadı."));
    }

    public List<MenuItem> getAllMenuItemsInMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalStateException("Menü bulunamadı."));

        return menu.getMenuItems();
    }

    public Menu updateMenu(Long id, Menu updatedMenu) {
        Menu existingMenu = menuRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Güncellenecek menü bulunamadı. ID: " + id));

        if (updatedMenu.getName() != null && !updatedMenu.getName().trim().isEmpty()) {
            existingMenu.setName(updatedMenu.getName());
        }


        if (updatedMenu.getRestaurantId() != null) {
            existingMenu.setRestaurantId(updatedMenu.getRestaurantId());
        }

        return menuRepository.save(existingMenu);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        MenuItem existingItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Menü öğesi bulunamadı."));

        existingItem.setName(updatedItem.getName());
        existingItem.setDescription(updatedItem.getDescription());
        existingItem.setPrice(updatedItem.getPrice());

        return menuItemRepository.save(existingItem);
    }
}
