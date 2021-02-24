package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Referee;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RefereeRepository extends JpaRepository<Referee, Long> {

    @Query("SELECT r from Referee r where r.id in :id")
    List<Referee> findRefereesByIds(@Param("id") Collection<Long> id);

    @Query("SELECT r from Referee r where r.id not in" +
            " (SELECT rf.referee.id from RefereeFunctionOnMatch rf where rf.match.dateOfMatch = :date) " +
            "AND r.id not in (SELECT candidates.refereeForReplacement.id FROM ReplacementCandidate candidates" +
            " where candidates.replacementInformation.refereeFunctionOnMatch.match.dateOfMatch = :date) ")
    List<Referee> findAllFreeReferees(@Param("date") LocalDate date);

    Optional<Referee> findByAccount_Username(String username);
}
