package com.commonground.dto;

import com.commonground.entity.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class GroupDto {

    private static int PREVIEW_MEMBER_TEXT_SIZE = 3;

    private String name;
    private String Id;
    private List<GroupMember> members;
    private DateRange dateRange;
    private Date createDate;
    private Boolean isCommonGroundAvailable;
    private String commonGroundErrorDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GroupMember> getMembers() {
        return members;
    }

    public void setMembers(List<GroupMember> members) {
        this.members = members;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public Boolean getCommonGroundAvailable() {
        return isCommonGroundAvailable;
    }

    public void setCommonGroundAvailable(Boolean commonGroundAvailable) {
        isCommonGroundAvailable = commonGroundAvailable;
    }

    public String getCommonGroundErrorDescription() {
        return commonGroundErrorDescription;
    }

    public void setCommonGroundErrorDescription(String commonGroundErrorDescription) {
        this.commonGroundErrorDescription = commonGroundErrorDescription;
    }


    public String getCreateDate() {
        Locale locale = new Locale("tr", "TR");
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
        return dateFormat.format(createDate);
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMemberPreviewText(){

        if(members.size() == 0){
            return "";
        }
        if(members.size() == 1){
            return members.get(0).getMember().getFirstName();
        }

        if(PREVIEW_MEMBER_TEXT_SIZE > members.size()){
            PREVIEW_MEMBER_TEXT_SIZE = members.size();
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < PREVIEW_MEMBER_TEXT_SIZE; i++){
            sb.append(members.get(i).getMember().getFirstName());
            if(i != PREVIEW_MEMBER_TEXT_SIZE - 1){
                sb.append(", ");
            }
            else{
                sb.append("...");
            }
        }
        return sb.toString();
    }
}
