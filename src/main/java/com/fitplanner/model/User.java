package com.fitplanner.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private String id; // id is a String containing the MongoDB ObjectId, this is automatically
                       // generated
    private String username; // username is a String containing the user's username - Batuhan
    @Indexed(unique = true) // for not allowing duplicate emails - Batuhan
    private String email; // email is a String containing the user's email - Batuhan
    @JsonIgnore // for not returning it, super finding from the internet - Batuhan
    private String password; // password is a String containing the user's password - Batuhan
    private Gender gender;
    private int age; // age is an int containing the user's age - Batuhan
    private int height; // height is an int containing the user's height in cm - Batuhan
    private int weight; // weight is an int containing the user's weight in kg - Batuhan
    private int activityLevel; // activityLevel is an int containing the user's activity level, will be between
                               // 0 and 10. - Batuhan
    private int weightGoal; // weightGoal is an int containing the user's weight goal. - Batuhan
    private int calories; // calories is an int containing the user's daily calorie goal. - Batuhan

    public User(String username, String email, String password, Gender gender, int age, int height, int weight,
            int activityLevel, int weightGoal, int calories) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.activityLevel = activityLevel;
        this.weightGoal = weightGoal;
        this.calories = calories;
    }

    // All these are for the spring security, we don't need to worry about them. -
    // Batuhan

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // Below for UserDetails classes' requirements. No need to worry about them. -
    // Batuhan
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

/*
 * JSON file of Post User /register
 * {
 * "username": "Batuhan",
 * "email": "batuhan.isildak@malwation.com",
 * "password": "Batuhan25-",
 * "gender": "MALE",
 * "age": 20,
 * "height": 180,
 * "weight": 80,
 * "activityLevel": 5,
 * "weightGoal": 75,
 * "calories": 2000
 * }
 * 
 */