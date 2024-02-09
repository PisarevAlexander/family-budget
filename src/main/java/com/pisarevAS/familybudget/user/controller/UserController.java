package com.pisarevAS.familybudget.user.controller;

import com.pisarevAS.familybudget.user.model.UserDto;
import com.pisarevAS.familybudget.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    @PostMapping("/users/patch")
    public String update(@RequestParam(required = false) String name,
                         @RequestParam(required = false) String password,
                         Model model) {

        if (name.isBlank() && password.isBlank()) {
            model.addAttribute("user", new UserDto());
            return "users";
        }

        userService.update(name, password);
        authentication.setAuthenticated(false);
        return "index";
    }

    @PostMapping("/user/delete")
    public String delete() {
        userService.delete();
        authentication.setAuthenticated(false);
        return "index";
    }
}