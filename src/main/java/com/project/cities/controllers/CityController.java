package com.project.cities.controllers;

import com.project.cities.exceptions.CityServiceException;
import com.project.cities.exceptions.UserServiceException;
import com.project.cities.models.request.UserRequest;
import com.project.cities.models.response.CityRequestResponse;
import com.project.cities.models.response.UserResponse;
import com.project.cities.service.CityService;
import com.project.cities.shared.ErrorMessages;
import com.project.cities.shared.dto.CityDto;
import com.project.cities.shared.dto.UserDto;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/cities")
public class CityController {

    @Autowired
    CityService cityService;

    @PostMapping(consumes = {
        MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
    produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public CityRequestResponse createCity(@RequestBody CityRequestResponse cityRequest) throws CityServiceException {

        CityRequestResponse returnValue = new CityRequestResponse();

        if(cityRequest.getName().isEmpty() || cityRequest.getPopulation() == 0
         || cityRequest.getDescription().isEmpty()) throw new CityServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

        CityDto cityRequestDto = new CityDto();
        BeanUtils.copyProperties(cityRequest, cityRequestDto);

        cityService.createCity(cityRequestDto);
        BeanUtils.copyProperties(cityRequestDto, returnValue);

        return returnValue;
    }

    @GetMapping(path = "/get-cities",consumes = {
            MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public List<CityDto> getCities(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "3") int limit)  {

        List<CityDto> returnValue = new ArrayList<>();
        returnValue = cityService.getUsers(page, limit);

        return returnValue;
    }

}
