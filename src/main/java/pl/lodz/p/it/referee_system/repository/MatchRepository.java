package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Match;

import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query(value = "SELECT mf.match FROM RefereeFunctionOnMatch as mf WHERE mf.referee.id = :refere_id")
    List<Match> findByRefereeId(@Param("referee_id") Long id);

    @Query(value = "SELECT mf.match FROM RefereeFunctionOnMatch as mf WHERE mf.referee.account.username = :username")
    List<Match> findByUsername(@Param("username") String username);
}
