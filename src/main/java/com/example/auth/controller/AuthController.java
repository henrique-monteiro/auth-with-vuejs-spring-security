package com.example.auth.controller;

import com.example.auth.service.AuthService;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //record is a way to implements DTO pattern that has been used in register endpoint
    record RegisterRequest(@JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, String email, String password, @JsonProperty("password_confirm") String passwordConfirm){}
    record RegisterResponse(Long id, @JsonProperty("first_name") String firstName, @JsonProperty("last_name") String lastName, String email){}

    @PostMapping(value = "/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest){
        var user = authService.register(
                registerRequest.firstName(), registerRequest.lastName(),
                registerRequest.email(), registerRequest.password(), registerRequest.passwordConfirm()
        );
        return new RegisterResponse(user.getId(), user.getFirstName(),user.getLastName(), user.getEmail());
    }
}
