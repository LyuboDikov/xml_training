package com.example.xmlex.services.impl;

import com.example.xmlex.models.dtos.UserSeedDto;
import com.example.xmlex.models.entities.User;
import com.example.xmlex.repositories.UserRepository;
import com.example.xmlex.services.UserService;
import com.example.xmlex.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }
    @Override
    public long getUsersCount() {
      return userRepository.count();
    }

    @Override
    public void seedUsers(List<UserSeedDto> users) {
        users.stream()
                .filter(validationUtil::isValid)
                .map(userSeedDto -> modelMapper.map(userSeedDto, User.class))
                .forEach(userRepository::save);
    }

    @Override
    public User getRandomUser() {

        long randomId = ThreadLocalRandom.current()
                .nextLong(1, userRepository.count() + 1);

        return userRepository.findById(randomId).orElse(null);
    }
}
