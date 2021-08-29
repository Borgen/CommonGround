package com.commonground.repositories;

import com.commonground.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonGroundRepository extends JpaRepository<CommonGround, Long> {

    public  CommonGround findCommonGroundByGroup(Group group);
}