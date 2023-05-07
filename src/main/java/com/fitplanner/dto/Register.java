package com.fitplanner.dto;

import com.fitplanner.model.Gender;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Register {
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;
    @NotBlank
    @Size(max = 60)
    @Email
    private String email;
    @NotBlank
    @Size(min = 6, max = 60)
    private String password;
    private Gender gender;
    // @NotBlank
    // @Size(min = 0, max = 120)
    private int age;
    // @NotBlank
    // @Size(min = 0, max = 300)
    private int height;
    // @NotBlank
    // @Size(min = 0, max = 500)
    private int weight;
    // @NotBlank
    // @Size(min = 0, max = 10)
    private int activityLevel;
    // @NotBlank
    // @Size(min = 0, max = 500)
    private int weightGoal;
    // @NotBlank
    // @Size(min = 0, max = 10000)
    private int calories;
}