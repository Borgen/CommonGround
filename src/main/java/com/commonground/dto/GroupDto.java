package com.commonground.dto;

import com.commonground.entity.*;

import java.util.List;

public class GroupDto {

    private String name;
    private List<GroupMembers> members;
    private DateRange dateRange;
    private  Group group;

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
