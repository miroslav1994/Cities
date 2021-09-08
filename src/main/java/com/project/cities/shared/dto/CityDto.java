package com.project.cities.shared.dto;

import java.util.Date;

public class CityDto {

    private String name;
    private String description;
    private Integer population;
    private Date createdAt;
    private Long numUsers;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getNumUsers() {
        return numUsers;
    }

    public void setNumUsers(Long numUsers) {
        this.numUsers = numUsers;
    }
}
