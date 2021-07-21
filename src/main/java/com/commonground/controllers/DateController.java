package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.DateInput;
import com.commonground.entity.CommonGround;
import com.commonground.entity.User;
import com.commonground.services.CommonGroundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DateController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CommonGroundService commonGroundService;

    @GetMapping("/date")
    public String showDateForm(Model model) {
        DateInput dateInput = new DateInput();
        model.addAttribute("dateInput", dateInput);

        List<DateInput> currentAvailableDates = new ArrayList<>();
        try {
            for(CommonGround currCommonGround : commonGroundService.getByUser(authenticationFacade.getUser())){
                currentAvailableDates.add(new DateInput(currCommonGround.getStartDateTime().toString(),
                                                        currCommonGround.getEndDateTime().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("currentAvailableDates", currentAvailableDates);

        return "date";
    }

    @PostMapping("/date")
    public String submitDateForm(@ModelAttribute DateInput dateInput) throws Exception {

        LocalDateTime startDateTime = LocalDateTime.parse(dateInput.getStartDate(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDateTime = LocalDateTime.parse(dateInput.getEndDate(), DateTimeFormatter.ISO_DATE_TIME);

        User currentUser = authenticationFacade.getUser();
        CommonGround commonGround = new CommonGround();
        commonGround.setStartDateTime(startDateTime);
        commonGround.setEndDateTime(endDateTime);
        commonGround.setUser(currentUser);
        commonGroundService.save(commonGround);

        return "success";
    }

}
