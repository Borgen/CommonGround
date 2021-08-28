package com.commonground.repositories;

import com.commonground.entity.Group;
import com.commonground.entity.GroupMembers;
import com.commonground.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {

    Optional<GroupMembers> findByMember(User member);
    Optional<List<GroupMembers>> findGroupMembersByMember(User member);
    Optional<GroupMembers> findById(Long id);
    Optional<List<GroupMembers>> findByGroup(Group group);
}
