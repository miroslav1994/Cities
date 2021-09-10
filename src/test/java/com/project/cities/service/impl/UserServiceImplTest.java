package com.project.cities.service.impl;

import com.project.cities.models.CityEntity;
import com.project.cities.models.UserEntity;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.shared.Utils;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    CityRepository cityRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    SecurityContextHolder securityContextHolder;

    UserDto userDto = new UserDto();
    UserEntity userEntity = new UserEntity();

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        userDto.setName("Miroslav Perovic");
        userDto.setEncryptedPassword("oigerogheroger");
        userDto.setEmail("miroslav.perovic@gmail.com");

        userEntity.setName("Miroslav Perovic");
        userEntity.setEncryptedPassword("oigerogheroger");
        userEntity.setEmail("miroslav.perovic@gmail.com");
    }

    @Test
    void createUser() {

        when(userRepository.findUserByEmail(userDto.getEmail())).thenReturn(null);
        when(bCryptPasswordEncoder.encode(userDto.getPassword())).thenReturn(userDto.getEncryptedPassword());

        UserDto returnValue = userService.createUser(userDto);

        assertNotNull(returnValue);
        assertEquals(userDto.getEmail(), returnValue.getEmail());
        verify(userRepository).save(any());
    }

    @Test
    void getUser() {

        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

        UserDto returnValue = userService.getUser(userDto.getEmail());
        assertEquals(returnValue.getEmail(), userDto.getEmail());
        assertNotEquals(userDto.getPassword(), userEntity.getEncryptedPassword());
    }

    @Test
    void addFavouriteCity() {

        List<CityEntity> favouriteCities = favouriteCities();

        userEntity.setCities(listCities());
        when(cityRepository.findAllById(getCityIds())).thenReturn(favouriteCities);
        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);

        userService = spy(userService);
        doReturn(userEntity.getEmail()).when(userService).getCurrentUserEmail();

        List<CityDto> returnValue = userService.addFavouriteCity(getCityIds());

        assertNotNull(returnValue);
        assertNotEquals(returnValue.size(), userEntity.getCities().size());
        verify(userRepository).save(any());
    }

    @Test
    void removeFavouriteCity() {

        when(cityRepository.findAllById(getCityIds())).thenReturn(favouriteCities());
        when(userRepository.findUserByEmail(userEntity.getEmail())).thenReturn(userEntity);
        userEntity.setCities(listCities());

        userService = spy(userService);
        doReturn(userEntity.getEmail()).when(userService).getCurrentUserEmail();

        userService.removeFavouriteCity(getCityIds());

        verify(userService).getCurrentUserEmail();
        verify(userRepository).save(any());
    }

    private List<CityEntity> favouriteCities() {

        CityEntity cityEntity1 = new CityEntity();
        cityEntity1.setName("Podgorica");
        cityEntity1.setDescription("The capital city of Montenegro");
        cityEntity1.setPopulation(120000);

        CityEntity cityEntity2 = new CityEntity();
        cityEntity2.setName("Beograd");
        cityEntity2.setDescription("The capital city of Serbia");
        cityEntity2.setPopulation(2500000);

        List<CityEntity> favouriteCities = new ArrayList<>();
        favouriteCities.add(cityEntity1);
        favouriteCities.add(cityEntity2);

        return favouriteCities;
    }

    private List<CityEntity> listCities() {

        CityEntity cityEntity1 = new CityEntity();
        cityEntity1.setName("Pljevlja");
        cityEntity1.setDescription("Pljevlja");
        cityEntity1.setPopulation(12000);

        CityEntity cityEntity2 = new CityEntity();
        cityEntity2.setName("Herceg novi");
        cityEntity2.setDescription("Herceg Novi");
        cityEntity2.setPopulation(2500);

        List<CityEntity> cities = new ArrayList<>();
        cities.add(cityEntity1);
        cities.add(cityEntity2);

        return cities;
    }

    private Set<Long> getCityIds() {
        Set<Long> cityIdSet = new HashSet<>();
        cityIdSet.add(1L);
        cityIdSet.add(2L);
        cityIdSet.add(3L);
        cityIdSet.add(4L);

        return cityIdSet;
    }
}