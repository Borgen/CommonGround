package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.GroupDto;
import com.commonground.entity.Group;
import com.commonground.entity.GroupMembers;
import com.commonground.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    GroupService groupService;

    @GetMapping("/create")
    public  String showGroupCreateForm(WebRequest request, Model model){
        GroupDto group = new GroupDto();
        model.addAttribute("group", group);
        return "creategroup";
    }

    @PostMapping("/create")
    public String submitGroupCreateForm(@ModelAttribute GroupDto group) throws Exception {
        Group groupEntity = new Group();

        groupEntity.setName(group.getName());

        groupService.save(groupEntity);
        groupService.addGroupMemberToGroup(groupEntity, authenticationFacade.getUser(), true);

        return "success";
    }

}
