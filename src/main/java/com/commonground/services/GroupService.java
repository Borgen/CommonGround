package com.commonground.services;

import com.commonground.authentication.AuthenticationFacade;
import com.commonground.entity.Group;
import com.commonground.entity.GroupMember;
import com.commonground.entity.User;
import com.commonground.logging.DbLogger;
import com.commonground.repositories.GroupMemberRepository;
import com.commonground.repositories.GroupRepository;
import com.commonground.util.RandomString;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    public void save(Group group){
        groupRepository.save(group);
    }
    public Group findByName(String name) throws Exception {
        Optional<Group> optGroup = groupRepository.findByName(name);
        if(optGroup.isPresent()){
            return optGroup.get();
        }
        else throw new Exception("Group not found!");
    }
    public List<Group> findByMember(User user) throws Exception{
        List<Group> groups = groupRepository.findByMember(user.getId());
        return groups;
    }
    public void addGroupMemberToGroup(Group group, User user, Boolean isOwner) throws Exception {
        Optional<Group> groupEntity = groupRepository.findByName(group.getName());
        GroupMember groupMemberEntity = new GroupMember();
        if(groupEntity.isPresent()){
            groupMemberEntity.setGroup(groupEntity.get());
            groupMemberEntity.setMember(user);
            groupMemberEntity.setOwner(isOwner);
            groupMemberRepository.save(groupMemberEntity);
        }else{
            DbLogger.logger.error("Couldn't add group member to group because can't find group");
            throw new Exception("Error while adding group member!");
        }
    }
    public List<GroupMember> listGroupMembersByGroupName(String name) throws Exception {
        Group group = findByName(name);
        return groupMemberRepository.findByGroup(group).get();
    }

    public  List<Group> searchByGroupName(String keyword){
        return groupRepository.searchName(keyword);
    }


    public String getJoinPhrase(UUID groupId) throws Exception {
        Group group = groupRepository.getById(groupId);
        if(group == null){
            throw new Exception("There is no group with provided ID");
        }

        String joinPhrase = group.getJoinPhrase();
        if(StringUtils.isBlank(joinPhrase)){
            joinPhrase = new RandomString(10).nextString();
            group.setJoinPhrase(joinPhrase);
            groupRepository.save(group);
        }

        return joinPhrase;
    }

    public void addUserToGroupWithJoinPhrase(String joinPhrase) throws Exception{
        Group group = groupRepository.findByJoinPhrase(joinPhrase);
        List<GroupMember> groupMembers = group.getMembers();
        User currUser = authenticationFacade.getUser();
        if(groupMembers.stream().anyMatch(gm -> gm.getMember() == currUser)){
            return;
        }
        GroupMember newGroupMember = new GroupMember();
        newGroupMember.setGroup(group);
        newGroupMember.setMember(currUser);
        newGroupMember.setOwner(false);
        groupMemberRepository.save(newGroupMember);

    }
}
