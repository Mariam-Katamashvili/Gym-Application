package com.mariamkatamashvlii.gym.repoImpl;

import com.mariamkatamashvlii.gym.repo.TrainerRepo;
import com.mariamkatamashvlii.gym.repo.UserRepo;
import com.mariamkatamashvlii.gym.model.Trainer;
import com.mariamkatamashvlii.gym.model.User;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TrainerRepoImpl implements TrainerRepo {
    private static final Logger logger = LoggerFactory.getLogger(TrainerRepoImpl.class);
    private EntityManager entityManager;
    private UserRepo userRepo;

    @Autowired
    public TrainerRepoImpl(EntityManager entityManager, UserRepo userRepo) {
        this.entityManager = entityManager;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public void create(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.persist(trainer);
        logger.info("Created trainer with id {}", trainer.getTrainerId());
    }

    @Override
    @Transactional
    public void update(Trainer trainer) {
        Session session = entityManager.unwrap(Session.class);
        session.merge(trainer);
        logger.info("Updated trainer with id {}", trainer.getTrainerId());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Session session = entityManager.unwrap(Session.class);
        Trainer trainer = session.get(Trainer.class, id);
        session.remove(trainer);
    }

    @Override
    @Transactional
    public Trainer select(long id) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Trainer.class, id);
    }

    @Override
    @Transactional
    public Trainer select(String username) {
        User user = userRepo.select(username);
        return user.getTrainer();
    }

    @Override
    @Transactional
    public List<Trainer> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<Trainer> query = session.createQuery("from Trainer ", Trainer.class);
        List<Trainer> trainerList = query.getResultList();
        return trainerList;
    }
}