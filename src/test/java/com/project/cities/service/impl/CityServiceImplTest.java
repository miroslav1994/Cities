package com.project.cities.service.impl;

import com.project.cities.models.CityEntity;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.shared.dto.CityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceImplTest {

    @Mock
    CityRepository cityRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    CityServiceImpl cityService;

    CityDto cityDto = new CityDto();
    CityEntity cityEntity = new CityEntity();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        cityDto.setName("Podgorica");
        cityDto.setDescription("The capital city of Montenegro");
        cityDto.setPopulation(200000);
        cityDto.setCreatedAt(new Date());
        cityDto.setNumUsers(1L);

        cityEntity.setName("Podgorica");
        cityEntity.setDescription("The capital city of Montenegro");
        cityEntity.setPopulation(200000);
        cityEntity.setCreatedAt(new Date());
        cityEntity.setNumUsers(1L);
    }

    @Test
    void createCity() {

        when(cityRepository.findByName(cityDto.getName())).thenReturn(null);

        CityDto returnValue = cityService.createCity(cityDto);

        assertEquals(cityDto.getName(), returnValue.getName());
        assertEquals(cityDto.getDescription(), returnValue.getDescription());
        assertEquals(cityDto.getPopulation(), returnValue.getPopulation());

        assertNotNull(cityDto.getName());
        assertNotNull(cityDto.getDescription());
        assertNotNull(cityDto.getPopulation());

    }

    @Test
    void getUsers() {

        List<CityEntity> allCities = new ArrayList<>();
        allCities.add(cityEntity);
        Pageable pageableRequest = PageRequest.of(0,10);
        Page<CityEntity> result = new PageImpl<>(allCities);

        when(cityRepository.findAllSorted(pageableRequest)).thenReturn(result);

        List<CityDto> returnValue = cityService.getUsers(0,10);
        assertNotNull(returnValue);
        assertEquals(allCities.size(), returnValue.size());
    }
}