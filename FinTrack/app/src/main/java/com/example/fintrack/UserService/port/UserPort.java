package com.example.fintrack.UserService.port;

import com.example.fintrack.UserService.data.entity.UserEntity;

public interface UserPort {

    UserEntity getUserById(String userId);

    UserEntity getUserByEmail(String email);

    UserEntity getUserByUsername(String username);

}