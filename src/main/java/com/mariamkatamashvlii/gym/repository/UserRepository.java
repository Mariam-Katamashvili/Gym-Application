package com.mariamkatamashvlii.gym.repository;

import com.mariamkatamashvlii.gym.entity.User;

import java.util.List;

public interface UserRepository {
    User create(User user);

    User update(User user);

    void delete(User user);

    User select(long id);

    User select(String username);

    List<User> findAll();
}
