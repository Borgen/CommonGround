package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.*;
import com.commonground.entity.*;
import com.commonground.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/group")
public class GroupController {

    private final Logger logger = LoggerFactory.getLogger(GroupController.class);

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
        List<Group> groups = groupService.findByMember(authenticationFacade.getUser());
        List<GroupDto> groupDtoList = new ArrayList<GroupDto>();
        for(Group group :  groups){
            try{
                GroupDto groupDto = new GroupDto();
                groupDto.setGroup(groupService.findByName(group.getName()));

                CommonGround commonGround = commonGroundService.getCommonGroundOfGroup(group);
                if(commonGround != null){
                    DateRange dateRange = new DateRange(commonGround.getStartDateTime().toString(), commonGround.getEndDateTime().toString());
                    groupDto.setDateRange(dateRange);
                }

                groupDtoList.add(groupDto);
            }
            catch(Exception ex){
                logger.error("Exception while populating user's group list. {}", ex);
            }
        }

        model.addAttribute("groups", groupDtoList);
        return "listgroups";
    }

    @GetMapping("/details")
    public String showDetailsOfUserGroups(WebRequest request, @RequestParam String groupName, Model model) throws Exception {
        model.addAttribute("groupMembers", groupService.listGroupMembersByGroupName(groupName));
        return "groupdetails";
    }

    @GetMapping("/join")
    public String joinToGroup(WebRequest request, @RequestParam String phrase) throws Exception {
        groupService.addUserToGroupWithJoinPhrase(phrase);
        return "success";
    }

    @GetMapping("/joinlink")
    @ResponseBody
    public String generateJoinLink(HttpServletRequest request, @RequestParam String groupId) throws Exception {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        String joinPhrase = groupService.getJoinPhrase(UUID.fromString(groupId));
        return baseUrl + "/group/join?phrase=" + joinPhrase;
    }

    @RequestMapping(value="/searchname", method=RequestMethod.GET)
    @ResponseBody
    public List<Group> searchName(WebRequest request, @RequestParam String term) {
        return groupService.searchByGroupName(term);
    }

    @GetMapping("/search")
    public String search() {
        return "searchgroups";
    }

}
