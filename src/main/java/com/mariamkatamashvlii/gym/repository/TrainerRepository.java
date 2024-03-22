package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.Trainer;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Generated
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t INNER JOIN t.user u WHERE u.username = :username")
    Trainer findByUsername(@Param("username") String username);
}
