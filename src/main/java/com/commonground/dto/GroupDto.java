package com.commonground.dto;

import com.commonground.entity.GroupMembers;

import java.util.List;

public class GroupDto {

    private String name;
    private List<GroupMembers> members;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<GroupMembers> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMembers> members) {
        this.members = members;
    }
}
