package com.commonground.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/group")
public class GroupController {


    @GetMapping("/create")
    public  String showGroupCreateForm(WebRequest request, Model model){

        return "creategroup";
    }

}
