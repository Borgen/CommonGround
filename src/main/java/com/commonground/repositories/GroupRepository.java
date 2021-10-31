package com.commonground.repositories;


import com.commonground.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    public Optional<Group> findByName(String name);
    public Optional<Group> findById(UUID id);
    public Group findByJoinPhrase(String joinPhrase);

    @Query(value = "SELECT * FROM Groups where name LIKE %:keyword%", nativeQuery = true)
    public List<Group> searchName(@Param("keyword") String keyword);

    @Query(value = "SELECT g.* FROM groups g JOIN group_member gm on g.id = gm.group_id where gm.member_id = :userId", nativeQuery = true)
    public List<Group> findByMember(@Param("userId") UUID userId);
}
