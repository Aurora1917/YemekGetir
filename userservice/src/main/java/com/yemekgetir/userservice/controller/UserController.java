package com.yemekgetir.userservice.controller;

import com.yemekgetir.userservice.entity.User;
import com.yemekgetir.userservice.service.UserService;
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
@RequestMapping("/users")
@Tag(name = "Kullanıcı Yönetimi", description = "Kullanıcıların kaydedilmesi, getirilmesi ve silinmesiyle ilgili operasyonlar.")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Yeni kullanıcı kaydı oluşturur", description = "Verilen kullanıcı bilgileri ile sisteme yeni bir kullanıcı kaydeder.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Kullanıcı başarıyla oluşturuldu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "409", description = "Bu kullanıcı adı veya e-posta adresi zaten mevcut.",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Geçersiz giriş bilgileri.",
                    content = @Content)
    })
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Kullanıcıyı ID'sine göre getirir", description = "Verilen ID ile eşleşen kullanıcıyı bulur ve döndürür.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Kullanıcı başarıyla bulundu.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "Kullanıcı bulunamadı.",
                    content = @Content)
    })
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Kullanıcıyı ID'sine göre siler", description = "Verilen ID'ye sahip kullanıcıyı sistemden siler.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Kullanıcı başarıyla silindi (İçerik yok).",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Silinecek kullanıcı bulunamadı.",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}