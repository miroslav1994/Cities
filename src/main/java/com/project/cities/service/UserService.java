package com.project.cities.service;

import com.project.cities.models.CityEntity;
import com.project.cities.models.response.ResponseOperationStatus;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);
    UserDto getUser(String email);
    List<CityDto> addFavouriteCity(Set<Long> cityIds);
    void removeFavouriteCity(Set<Long> cityIds);
}
