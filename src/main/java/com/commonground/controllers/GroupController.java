package com.commonground.controllers;

import com.commonground.authentication.IAuthenticationFacade;
import com.commonground.dto.*;
import com.commonground.entity.*;
import com.commonground.exceptions.CommonGroundDateExpiredException;
import com.commonground.exceptions.CommonGroundNotFoundException;
import com.commonground.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Controller
@ControllerAdvice
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

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT);
        List<Group> groups = groupService.findByMember(authenticationFacade.getUser());
        List<GroupDto> groupDtoList = new ArrayList<GroupDto>();
        for(Group group :  groups){
            try{
                GroupDto groupDto = new GroupDto();
                groupDto.setName(group.getName());
                groupDto.setCreateDate(group.getCreateDate());
                groupDto.setMembers(group.getMembers());
                groupDto.setId(group.getId().toString());


                CommonGround commonGround = null;
                try {
                    commonGround = commonGroundService.getCommonGroundOfGroup(group);
                }
                catch (CommonGroundNotFoundException commonGroundNotFoundException){
                    groupDto.setCommonGroundErrorDescription(commonGroundNotFoundException.getMessage());
                    groupDto.setCommonGroundAvailable(false);
                }
                catch (CommonGroundDateExpiredException commonGroundDateExpiredException){
                    groupDto.setCommonGroundErrorDescription(commonGroundDateExpiredException.getMessage());
                    groupDto.setCommonGroundAvailable(false);
                }
                catch (Exception e) {
                    groupDto.setCommonGroundAvailable(false);
                }
                if(commonGround != null){
                    DateRange dateRange = new DateRange(commonGround.getStartDateTime().format(dateTimeFormatter), commonGround.getEndDateTime().format(dateTimeFormatter));
                    groupDto.setDateRange(dateRange);
                    groupDto.setCommonGroundAvailable(true);
                    
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
    public String showDetailsOfUserGroups(WebRequest request, @RequestParam String groupId, Model model) throws Exception {
        List<GroupMemberDto> groupMemberDtos = groupService.getGroupMemberDtos(UUID.fromString(groupId));
        Group group = groupService.findById(UUID.fromString(groupId));
        GroupDto groupDto = new GroupDto();
        groupDto.setName(group.getName());
        groupDto.setCreateDate(group.getCreateDate());

        model.addAttribute("groupMembers", groupMemberDtos);
        model.addAttribute("group", groupDto);

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

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        try {
            model.addAttribute("loggedInUserInfo", authenticationFacade.getUser());
        } catch (Exception e) {
            logger.error("User objesi modele eklenirken hata: ", e);
        }
    }
}
