package com.commonground.repositories;

import com.commonground.entity.Group;
import com.commonground.entity.GroupMember;
import com.commonground.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {

    Optional<List<GroupMember>> findByMember(User member);
    Optional<GroupMember> findById(UUID id);
    Optional<List<GroupMember>> findByGroup(Group group);
}
