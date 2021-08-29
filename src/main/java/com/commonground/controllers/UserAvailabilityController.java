package com.commonground.controllers;

import com.commonground.dto.DateRange;
import com.commonground.entity.UserAvailability;
import com.commonground.services.UserAvailabilityService;
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
public class UserAvailabilityController {

    @Autowired
    private UserAvailabilityService userAvailabilityService;

    @GetMapping("/date")
    public String showDateForm(Model model) {
        DateRange dateRange = new DateRange();
        model.addAttribute("dateRange", dateRange);

        List<DateRange> currentAvailableDates = new ArrayList<>();
        try {
            for(UserAvailability currUserAvailability : userAvailabilityService.getByUser()){
                currentAvailableDates.add(new DateRange(currUserAvailability.getStartDateTime().toString(),
                                                        currUserAvailability.getEndDateTime().toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.addAttribute("currentAvailableDates", currentAvailableDates);

        return "date";
    }

    @PostMapping("/date")
    public String submitDateForm(@ModelAttribute DateRange dateRange) throws Exception {

        LocalDateTime startDateTime = LocalDateTime.parse(dateRange.getStartDate(), DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime endDateTime = LocalDateTime.parse(dateRange.getEndDate(), DateTimeFormatter.ISO_DATE_TIME);

        UserAvailability userAvailability = new UserAvailability();
        userAvailability.setStartDateTime(startDateTime);
        userAvailability.setEndDateTime(endDateTime);
        userAvailabilityService.save(userAvailability);

        return "success";
    }

}
