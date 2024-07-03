package com.swayam.banking.controller;

import com.swayam.banking.dto.User;
import com.swayam.banking.dto.UserUpdateRequest;
import com.swayam.banking.serivce.KeycloakUserService;
import com.swayam.banking.serivce.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/bank-user")
public class UserController {
    private final KeycloakUserService keycloakUserService;
    private final UserService userService;

    @Autowired
    public UserController(KeycloakUserService keycloakUserService, UserService userService) {
        this.keycloakUserService = keycloakUserService;
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity createUser(@RequestBody User request) {
        log.info("Creating user with {}", request.toString());
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PatchMapping(value = "/update/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long userId, @RequestBody UserUpdateRequest userUpdateRequest) {
        log.info("Updating user with {}", userUpdateRequest.toString());
        return ResponseEntity.ok(userService.updateUser(userId, userUpdateRequest));
    }

    @GetMapping
    public ResponseEntity readUsers(Pageable pageable) {
        log.info("Reading all users from API");
        return ResponseEntity.ok(userService.readUsers(pageable));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity readUser(@PathVariable("id") Long id) {
        log.info("Reading user by id {}", id);
        return ResponseEntity.ok(userService.readUser(id));
    }
}
