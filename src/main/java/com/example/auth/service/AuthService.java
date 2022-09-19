package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final String accessTokenSecret;
    private final String refreshTokenSecret;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder,
                       @Value("${application.security.access-token-secret}") String accessTokenSecret,
                       @Value("${application.security.refresh-token-secret}") String refreshTokenSecret) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.accessTokenSecret = accessTokenSecret;
        this.refreshTokenSecret = refreshTokenSecret;
    }

    public User register(String firstName, String lastName, String email, String password, String passwordConfirm){
        if(!Objects.equals(password, passwordConfirm)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password do not match");
        }
        //factory method User.of
        return userRepo.save(User.of(firstName, lastName, email, passwordEncoder.encode(password)));
    }

    public Login login(String email, String password) {
        var user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid credentials");
        }

        return Login.of(user.getId(), accessTokenSecret, refreshTokenSecret);
    }
}
