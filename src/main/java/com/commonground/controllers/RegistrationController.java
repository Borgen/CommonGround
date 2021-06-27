package com.commonground.controllers;

import com.commonground.dto.*;
import com.commonground.entity.*;
import com.commonground.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/user/registration")
    public String submitRegistrationForm(@ModelAttribute UserDto userDto, Model model) {
        userService.add(new User(userDto.getFirstName(), userDto.getLastName(), userDto.getEmail()));
        return "success";
    }
}
