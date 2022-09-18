package com.example.auth;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private final UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/register")
    public User register(@RequestBody User user){
        return userRepo.save(user);
    }
}
