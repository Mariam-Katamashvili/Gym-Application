package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public User create(User user) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Created user - {}", user.getUsername());
        session.persist(user);
        return user;
    }

    @Override
    @Transactional
    public User update(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(user);
        log.info("Updated user - {}", user.getUsername());
        return user;
    }

    @Override
    public void delete(User user) {
        Session session = entityManager.unwrap(Session.class);
        session.remove(user);
        log.info("Deleted user - {}", user.getUsername());
    }

    @Override
    @Transactional
    public User select(long id) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Selecting user with id {}", id);
        return session.get(User.class, id);
    }

    @Override
    @Transactional
    public User select(String username) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User where username = :username ", User.class);
        query.setParameter("username", username);
        log.info("Selecting user - {}", username);
        return query.uniqueResult();
    }

    @Override
    @Transactional
    public List<User> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("from User ", User.class);
        List<User> users = query.getResultList();
        log.info("Returning all users");
        return users;
    }
}
