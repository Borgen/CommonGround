package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.DateInput;
import com.commonground.dto.UserDto;
import com.commonground.entity.CommonGround;
import com.commonground.entity.User;
import com.commonground.services.CommonGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Controller
public class DateController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CommonGroundService commonGroundService;

    @GetMapping("/date")
    public String showDateForm(WebRequest request, Model model) {
        DateInput dateInput = new DateInput();
        model.addAttribute("dateInput", dateInput);
        return "date";
    }

    @PostMapping("/date")
    public String submitDateForm(@ModelAttribute DateInput dateInput, Model model) throws Exception {

        LocalDateTime startDateTime = LocalDateTime.parse(dateInput.getStartDate(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDateTime = LocalDateTime.parse(dateInput.getEndDate(), DateTimeFormatter.ISO_DATE_TIME);

        Optional<User> currentUser = authenticationFacade.getUser();
        if(currentUser.isPresent()){
            CommonGround commonGround = new CommonGround();
            commonGround.setStartDateTime(startDateTime);
            commonGround.setEndDateTime(endDateTime);
            commonGround.setUser(currentUser.get());
            commonGroundService.save(commonGround);
        }
        else throw new Exception("User not found!");

        return "success";
    }

}
