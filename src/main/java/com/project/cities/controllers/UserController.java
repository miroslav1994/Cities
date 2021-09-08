package com.project.cities.controllers;


import com.project.cities.exceptions.UserServiceException;
import com.project.cities.models.CityEntity;
import com.project.cities.models.request.UserRequest;
import com.project.cities.models.response.OperationResponse;
import com.project.cities.models.response.ResponseAction;
import com.project.cities.models.response.ResponseOperationStatus;
import com.project.cities.models.response.UserResponse;
import com.project.cities.service.UserService;
import com.project.cities.shared.ErrorMessages;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cities")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = {"/register"}, consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
                produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public UserResponse createUser(@RequestBody UserRequest userRequest) {

        UserResponse returnValue = new UserResponse();

        if(userRequest.getEmail().isEmpty() ||
                userRequest.getPassword().isEmpty() ||
                userRequest.getName().isEmpty()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        UserDto userRequestDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userRequestDto);

        userService.createUser(userRequestDto);
        BeanUtils.copyProperties(userRequestDto, returnValue);

        return returnValue;
    }

    @PostMapping(path = "add-favourite-city", consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<CityDto> addFavouriteCIty(@RequestBody Set<Long> cityIds) {

        List<CityDto> returnValue = userService.addFavouriteCity(cityIds);

        return returnValue;
    }

    @DeleteMapping(path = "remove-favourite-city", consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public OperationResponse removeFavouriteCity(@RequestBody Set<Long> cityIds)
    {
        OperationResponse returnValue = new OperationResponse();
        returnValue.setAction(ResponseAction.REMOVE_FAVOURITE_CITIES.name());

        userService.removeFavouriteCity(cityIds);
        returnValue.setStatus(ResponseOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @GetMapping("/logout")
    public void getLogoutPage(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            new SecurityContextLogoutHandler().logout(request, response, authentication);

    }
}
