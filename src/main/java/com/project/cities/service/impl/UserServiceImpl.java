package com.project.cities.service.impl;

import com.project.cities.exceptions.UserServiceException;
import com.project.cities.models.CityEntity;
import com.project.cities.models.UserEntity;
import com.project.cities.models.response.ResponseOperationStatus;
import com.project.cities.repositories.CityRepository;
import com.project.cities.repositories.UserRepository;
import com.project.cities.security.UserPrincipal;
import com.project.cities.service.UserService;
import com.project.cities.shared.ErrorMessages;
import com.project.cities.shared.Utils;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CityRepository cItyRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDto createUser(UserDto userDto) throws UserServiceException {

        UserDto returnValue = new UserDto();

        if(userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
        }

        if(userDto == null) throw new UserServiceException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);

        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity user = userRepository.findUserByEmail(email);
        if(user == null) throw new UsernameNotFoundException(email);

        return new UserPrincipal(user);
    }

    @Override
    public UserDto getUser(String email) {

        UserDto returnValue = new UserDto();

        UserEntity userEntity = userRepository.findUserByEmail(email);
        BeanUtils.copyProperties(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public List<CityDto> addFavouriteCity(Set<Long> cityIds) {

        String email = getCurrentUserEmail();

        List<CityEntity> listCities = new ArrayList<>();
        List<CityDto> returnValue = new ArrayList<>();
        Iterable<CityEntity> favouriteCities = cItyRepository.findAllById(cityIds);

        for(CityEntity city : favouriteCities) {
            listCities.add(city);
            CityDto cityDto = new CityDto();
            BeanUtils.copyProperties(city, cityDto);
            returnValue.add(cityDto);
        }

        UserEntity userEntity = userRepository.findUserByEmail(email);

        userEntity.setCities(listCities);
        userRepository.save(userEntity);

        return returnValue;
    }

    @Override
    public void removeFavouriteCity(Set<Long> cityIds) {
        String email = getCurrentUserEmail();

        Iterable<CityEntity> favouriteCities = cItyRepository.findAllById(cityIds);
        UserEntity userEntity = userRepository.findUserByEmail(email);

        for(CityEntity city : favouriteCities) {
            userEntity.getCities().remove(city);
        }

        userRepository.save(userEntity);
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        return email;
    }
}
