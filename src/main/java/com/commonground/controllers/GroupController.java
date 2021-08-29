package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.*;
import com.commonground.entity.*;
import com.commonground.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Autowired
    private CommonGroundService commonGroundService;

    @Autowired
    private GroupService groupService;

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
        List<GroupDto> groupDtoList = new ArrayList<GroupDto>();
        for(Group group :  groups){
            GroupDto groupDto = new GroupDto();
            CommonGround commonGround = commonGroundService.getCommonGroundOfGroup(group);
            DateRange dateRange = new DateRange(commonGround.getStartDateTime().toString(), commonGround.getEndDateTime().toString());
            groupDto.setGroup(groupService.findByName(group.getName()));
            groupDto.setDateRange(dateRange);
            groupDtoList.add(groupDto);
        }

        model.addAttribute("groups", groupDtoList);
        return "listgroups";
    }

    @GetMapping("/details")
    public String showDetailsOfUserGroups(WebRequest request, @RequestParam String groupName, Model model) throws Exception {
        model.addAttribute("groupMembers", groupService.listGroupMembersByGroupName(groupName));
        return "groupdetails";
    }

    @RequestMapping(value="/searchname", method=RequestMethod.GET)
    @ResponseBody
    public List<String> searchName(WebRequest request, @RequestParam String term) {
        return groupService.searchByGroupName(term);
    }

    @GetMapping("/search")
    public String search() {
        return "searchgroups";
    }

}
