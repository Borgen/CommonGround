package com.commonground.services;

import com.commonground.entity.*;
import com.commonground.repositories.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.time.*;
import java.util.*;
import java.util.stream.*;

@Service
public class CommonGroundService {

    @Autowired
    private CommonGroundRepository commonGroundRepository;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserAvailabilityService userAvailabilityService;

    public CommonGround getCommonGroundOfGroup(Group group) throws Exception {

        List<GroupMember> groupMemberList = group.getMembers();

        if(groupMemberList.size() == 0){
            throw new Exception("No member in group!");
        }

        List<User> userList = groupMemberList.stream().map(GroupMember::getMember).collect(Collectors.toList());

        LocalDateTime latestStartDate = null;
        LocalDateTime earliestEndDate = null;

        UserAvailability currUserAvailability;
        LocalDateTime currUserStartDate;
        LocalDateTime currUserEndDate;


        for(User user : userList){
            currUserAvailability = userAvailabilityService.getLatestAvailability();
            if(currUserAvailability == null){
                continue;
            }
            currUserStartDate = currUserAvailability.getStartDateTime();
            currUserEndDate = currUserAvailability.getEndDateTime();

            if(latestStartDate == null || currUserStartDate.isAfter(latestStartDate)){
                latestStartDate = currUserStartDate;
            }
            if(earliestEndDate == null || currUserEndDate.isBefore(earliestEndDate)){
                earliestEndDate = currUserEndDate;
            }
        }

        if(latestStartDate == null || earliestEndDate == null){
            return null;
        }

        CommonGround commonGround = new CommonGround();
        commonGround.setGroup(group);
        commonGround.setStartDateTime(latestStartDate);
        commonGround.setEndDateTime(earliestEndDate);

        return commonGround;
    }
}
