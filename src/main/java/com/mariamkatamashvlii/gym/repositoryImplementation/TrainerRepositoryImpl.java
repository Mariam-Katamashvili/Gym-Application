package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.Trainer;
import com.mariamkatamashvlii.gym.entity.User;
import com.mariamkatamashvlii.gym.repository.TrainerRepository;
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
public class TrainerRepositoryImpl implements TrainerRepository {
    private final EntityManager entityManager;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public Trainer create(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainer);
        log.info("Created trainer - {}", trainer.getUser().getUsername());
        return trainer;
    }

    @Override
    @Transactional
    public Trainer update(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainer);
        log.info("Updated trainer - {}", trainer.getUser().getUsername());
        return trainer;
    }

    @Override
    @Transactional
    public Trainer select(long id) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Selecting trainer with id {}", id);
        return session.get(Trainer.class, id);
    }

    @Override
    @Transactional
    public Trainer select(String username) {
        User user = userRepo.select(username);
        log.info("Selecting trainer - {}", username);
        return user.getTrainer();
    }

    @Override
    @Transactional
    public List<Trainer> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainer> query = session.createQuery("from Trainer ", Trainer.class);
        log.info("Returning all trainers");
        return query.getResultList();
    }
}
