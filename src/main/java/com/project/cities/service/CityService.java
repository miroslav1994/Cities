package com.project.cities.service;

import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;

import java.util.List;
import java.util.Set;

public interface CityService {

    CityDto createCity(CityDto cityDto);
    List<CityDto> getUsers(int page, int limit);
}
