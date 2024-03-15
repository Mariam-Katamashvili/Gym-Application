package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.Trainee;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TraineeRepository;
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
public class TraineeRepositoryImpl implements TraineeRepository {
    private final EntityManager entityManager;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public Trainee create(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainee);
        log.info("Created trainee - {}", trainee.getUser().getUsername());
        return trainee;
    }

    @Override
    @Transactional
    public Trainee update(Trainee trainee) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainee);
        log.info("Updated trainee - {}", trainee.getUser().getUsername());
        return trainee;
    }

    @Override
    @Transactional
    public void delete(String username) {
        Session session = entityManager.unwrap(Session.class);
        Trainee trainee = session.get(Trainee.class, username);
        session.remove(trainee);
        log.info("Removed trainee - {}", username);
    }

    @Override
    @Transactional
    public Trainee select(long id) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Selecting trainee with id {}", id);
        return session.get(Trainee.class, id);
    }

    @Override
    @Transactional
    public Trainee select(String username) {
        User user = userRepo.select(username);
        log.info("Selecting trainee - {}", username);
        return user.getTrainee();
    }

    @Override
    @Transactional
    public List<Trainee> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainee> query = session.createQuery("from Trainee", Trainee.class);
        log.info("Returning all trainees");
        return query.getResultList();
    }
}