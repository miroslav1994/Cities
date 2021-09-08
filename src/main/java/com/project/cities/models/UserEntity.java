package com.project.cities.models;

import org.hibernate.cfg.annotations.Nullability;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String email;
    @Column(nullable = false, length = 255)
    private String encryptedPassword;
    @Column(nullable = false, length = 50)
    private String name;

    @ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(name="users_cities",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="city_id", referencedColumnName = "id"))
    private Collection<CityEntity> cities;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<CityEntity> getCities() {
        return cities;
    }

    public void setCities(Collection<CityEntity> cities) {
        this.cities = cities;
    }
}
