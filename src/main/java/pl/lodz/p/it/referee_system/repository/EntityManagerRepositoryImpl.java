package pl.lodz.p.it.referee_system.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public class EntityManagerRepositoryImpl implements EntityManagerRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void detach(Object o) {
        entityManager.detach(o);
        entityManager.merge(o);
    }


}
