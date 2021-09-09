package com.project.cities.service.impl;

import com.project.cities.exceptions.CityServiceException;
import com.project.cities.models.CityEntity;
import com.project.cities.models.UserEntity;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.service.CityService;
import com.project.cities.shared.ErrorMessages;
import com.project.cities.shared.dto.CityDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CityDto createCity(CityDto cityDto) {

        CityDto returnValue = new CityDto();

        CityEntity cityEntity = new CityEntity();
        BeanUtils.copyProperties(cityDto, cityEntity);

        if(cityRepository.findByName(cityDto.getName()) != null)
            throw new CityServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        if(cityEntity.getPopulation() == 0 || cityEntity.getName().isEmpty() || cityEntity.getDescription().isEmpty())
            throw new CityServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        cityEntity.setCreatedAt(new Date());
        cityEntity.setNumUsers(0L);
        cityRepository.save(cityEntity);

        BeanUtils.copyProperties(cityEntity, returnValue);

        return returnValue;
    }

    @Override
    public List<CityDto> getUsers(int page, int limit) {

        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<CityEntity> sortedCities = cityRepository.findAllSorted(pageableRequest);
        List<CityEntity> allCities = sortedCities.getContent();
        List<CityDto> returnValue = new ArrayList<>();

        for(CityEntity city : allCities) {
            CityDto cityDto = new CityDto();
            BeanUtils.copyProperties(city, cityDto);
            returnValue.add(cityDto);
        }

        return returnValue;
    }
}
