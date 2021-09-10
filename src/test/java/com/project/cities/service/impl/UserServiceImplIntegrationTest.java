package com.project.cities.service.impl;

import com.project.cities.models.CityEntity;
import com.project.cities.models.UserEntity;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.service.UserService;
import com.project.cities.shared.Utils;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceImplIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CityRepository cityRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    UserDto userDto = new UserDto();
    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void setUp() {

        userDto.setEmail("miroslav.perovic@gmail.com");
        userDto.setPassword("123456789");
        userDto.setName("Miroslav Perovic");
    }

    @Test
    void createUser() {

        UserDto returnValue = new UserDto();

        returnValue = userService.createUser(userDto);

        assertNotNull(returnValue);
        assertEquals(returnValue.getEmail(), userDto.getEmail());
    }
}