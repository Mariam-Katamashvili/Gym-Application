package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.User;
import lombok.Generated;
import org.springframework.data.jpa.repository.JpaRepository;

@Generated
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
