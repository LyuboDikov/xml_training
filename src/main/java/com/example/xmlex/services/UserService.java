package com.example.xmlex.services;

import com.example.xmlex.models.dtos.UserSeedDto;
import com.example.xmlex.models.dtos.UserViewRootDto;
import com.example.xmlex.models.entities.User;

import java.util.List;

public interface UserService {
    long getUsersCount();
    void seedUsers(List<UserSeedDto> users);
    User getRandomUser();

    UserViewRootDto findUserWithMoreThanOneSoldProduct();
}
