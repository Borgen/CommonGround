package com.commonground.services;

import ch.qos.logback.classic.Logger;
import com.commonground.dto.GroupDto;
import com.commonground.entity.Group;
import com.commonground.entity.GroupMembers;
import com.commonground.entity.User;
import com.commonground.logging.DbLogger;
import com.commonground.repositories.GroupMembersRepository;
import com.commonground.repositories.GroupRepository;
import jdk.jshell.spi.ExecutionControl;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;

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
    public void addGroupMemberToGroup(Group group, User user, Boolean isOwner) throws Exception {
        Optional<Group> groupEntity = groupRepository.findByName(group.getName());
        GroupMembers groupMembersEntity = new GroupMembers();
        if(groupEntity.isPresent()){
            groupMembersEntity.setGroup(groupEntity.get());
            groupMembersEntity.setMember(user);
            groupMembersEntity.setOwner(isOwner);
            groupMembersRepository.save(groupMembersEntity);
        }else{
            DbLogger.logger.error("Couldn't add group member to group because can't find group");
            throw new Exception("Error while adding group member!");
        }
    }
    public List<GroupMembers> listGroupMembersByMember(User user){
        Optional<List<GroupMembers>> groupMembersList = groupMembersRepository.findGroupMembersByMember(user);
        if(groupMembersList.isEmpty()){
            DbLogger.logger.error("No group found by this member!");
        }
        return groupMembersList.get();
    }
    public List<GroupMembers> listGroupMembersByGroupName(String name) throws Exception {
        Group group = findByName(name);
        return groupMembersRepository.findByGroup(group).get();
    }

    public  List<Group> searchByGroupName(String keyword){
        return groupRepository.searchName(keyword);
    }
}
