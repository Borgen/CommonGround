package com.commonground.repositories;


import com.commonground.entity.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByName(String name);
    Optional<Group> findById(Long id);

    @Query(value = "SELECT name FROM Groups where name LIKE %:keyword%", nativeQuery = true)
    public List<String> searchName(@Param("keyword") String keyword);

}
