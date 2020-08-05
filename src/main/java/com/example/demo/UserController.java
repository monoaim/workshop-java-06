package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public UserResponse[] getAllUser() {
        UserResponse[] ur = new UserResponse[2];
        ur[0] = new UserResponse(1, "User 1");
        ur[1] = new UserResponse(2, "User 2");
        return ur;
    }
}
