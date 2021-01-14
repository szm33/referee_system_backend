package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Match;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface MatchRepository extends JpaRepository<Match, Long> {

    @Query("SELECT r.match FROM RefereeFunctionOnMatch r where r.referee.id = :referee_id")
    List<Match> findByRefereeId(@Param("referee_id") Long id);

    @Query("SELECT r.match FROM RefereeFunctionOnMatch r where r.referee.account.username = :username")
    List<Match> findByUsername(@Param("username") String username);
}
