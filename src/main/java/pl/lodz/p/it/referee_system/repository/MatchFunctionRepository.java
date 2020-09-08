package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.MatchFunction;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MatchFunctionRepository extends JpaRepository<MatchFunction, Long> {
}
