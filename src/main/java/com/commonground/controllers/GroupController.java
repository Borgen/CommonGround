package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.GroupDto;
import com.commonground.entity.Group;
import com.commonground.entity.GroupMembers;
import com.commonground.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/list")
    public String showListOfUserGroups(WebRequest request, Model model) throws Exception {
        List<GroupMembers> groupMembers = groupService.listGroupMembersByMember(authenticationFacade.getUser());
        List<Group> groups = groupMembers.stream().map(GroupMembers :: getGroup).collect(Collectors.toList());
        model.addAttribute("groups", groups);
        return "listgroups";
    }

    @GetMapping("/details")
    public String showDetailsOfUserGroups(WebRequest request, @RequestParam String groupName, Model model) throws Exception {
        model.addAttribute("groupMembers", groupService.listGroupMembersByGroupName(groupName));
        return "groupdetails";
    }

}
