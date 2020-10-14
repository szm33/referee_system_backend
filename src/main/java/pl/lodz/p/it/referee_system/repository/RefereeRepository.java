package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Referee;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
