package com.commonground.repositories;

import com.commonground.entity.UserAvailability;
import com.commonground.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAvailabilityRepository extends JpaRepository<UserAvailability, Long> {

    @Override
    Optional<UserAvailability> findById(Long aLong);

    List<UserAvailability> findByUser(User user);

    UserAvailability findTopByUserOrderByCreateDateDesc(User user);

}
