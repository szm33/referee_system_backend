package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.RefereeFunctionOnMatch;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;

import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface ReplaceInformationsRepository extends JpaRepository<ReplaceInformations, Long> {

    Optional<ReplaceInformations> findByRefereeFunctionOnMatch(RefereeFunctionOnMatch refereeFunctionOnMatch);
}
