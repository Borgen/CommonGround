package com.commonground.services;

import ch.qos.logback.classic.Logger;
import com.commonground.entity.Group;
import com.commonground.repositories.GroupRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {

    final static Logger logger = (Logger) LoggerFactory.getLogger(GroupService.class);

    @Autowired
    private GroupRepository groupRepository;

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
}
