package com.commonground.controllers;

import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class FragmentsController {
    @GetMapping("/fragments")
    public String getHome() {
        return "fragments/basic";
    }
}
