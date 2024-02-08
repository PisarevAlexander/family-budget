package com.pisarevAS.familybudget.user.service;

import com.pisarevAS.familybudget.user.model.User;
import com.pisarevAS.familybudget.user.model.UserDto;

public interface UserService {

    boolean create(UserDto userDto);

    void update(String name, String password);

    void delete();

    User getByLogin();
}