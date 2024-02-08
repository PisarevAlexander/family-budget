package com.pisarevAS.familybudget.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "name can't be blank")
    private String name;

    @NotBlank(message = "login can't be blank")
    @Size(min = 5, max = 50, message = "login can't be 5 - 50 symbols")
    private String login;

    @NotBlank(message = "password can't be blank")
    @Size(min = 5, max = 50, message = "password can't be 5 - 50 symbols")
    private String password;
}