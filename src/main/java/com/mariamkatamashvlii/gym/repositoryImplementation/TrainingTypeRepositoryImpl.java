package com.mariamkatamashvlii.gym.repositoryImplementation;

import com.mariamkatamashvlii.gym.entity.TrainingType;
import com.mariamkatamashvlii.gym.repository.TrainingTypeRepository;
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
public class TrainingTypeRepositoryImpl implements TrainingTypeRepository {
    private final EntityManager entityManager;

    @Override
    @Transactional
    public TrainingType select(long id) {
        Session session = entityManager.unwrap(Session.class);
        log.info("Selecting training type with id {}", id);
        return session.get(TrainingType.class, id);

    }

    @Override
    @Transactional
    public List<TrainingType> findAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<TrainingType> query = session.createQuery("from TrainingType ", TrainingType.class);
        List<TrainingType> trainingTypes = query.getResultList();
        log.info("Returning all trainingTypes");
        return trainingTypes;
    }
}
