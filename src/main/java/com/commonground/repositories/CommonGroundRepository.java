package com.commonground.repositories;

import com.commonground.entity.CommonGround;
import com.commonground.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommonGroundRepository extends JpaRepository<CommonGround, Long> {

    @Override
    Optional<CommonGround> findById(Long aLong);

    List<CommonGround> findByUser(User user);

}
