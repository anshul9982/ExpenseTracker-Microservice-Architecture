package com.example.userservice.controller;

import com.example.userservice.model.UserSignUpEventDto;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/v1/create-update")
    public ResponseEntity<UserSignUpEventDto> createUser(@RequestBody UserSignUpEventDto userSignUpEventDto){
        try {
            UserSignUpEventDto user = userService.createOrUpdateUser(userSignUpEventDto);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/user/v1/get-user")
    public ResponseEntity<UserSignUpEventDto> getUser(@RequestHeader("X-Claim-Userid") String userId){
        try {
            UserSignUpEventDto user = userService.getUser(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
