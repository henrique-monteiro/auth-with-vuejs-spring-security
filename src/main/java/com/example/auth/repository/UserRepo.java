package com.example.auth.repository;

import com.example.auth.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {

    //frameword does: select * from user u where u.email = :email
    Optional <User> findByEmail(String email);
}
