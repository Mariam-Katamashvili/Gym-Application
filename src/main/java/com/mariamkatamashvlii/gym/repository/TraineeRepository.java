package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainee;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Generated
public interface TraineeRepository extends JpaRepository<Trainee, Long>{
    @Query("SELECT t FROM Trainee t INNER JOIN t.user u WHERE u.username = :username")
    Trainee findByUsername(@Param("username") String username);
}
