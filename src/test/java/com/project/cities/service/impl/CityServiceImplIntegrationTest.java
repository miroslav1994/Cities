package com.project.cities.service.impl;

import com.project.cities.models.UserEntity;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.service.CityService;
import com.project.cities.service.UserService;
import com.project.cities.shared.Utils;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CityServiceImplIntegrationTest {

    @Autowired
    CityService cityService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CityRepository cityRepository;

    CityDto cityDto = new CityDto();

    @BeforeEach
    void setUp() {

        cityDto.setName("Gusinje");
        cityDto.setDescription("Gusinje");
        cityDto.setPopulation(2000);
    }

    @Test
    void createCity() {

       CityDto returnValue = cityService.createCity(cityDto);

       assertNotNull(returnValue);
    }
}