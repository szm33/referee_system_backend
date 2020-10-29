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

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface RefereeRepository extends JpaRepository<Referee, Long> {

    @Query(value = "SELECT Referee from Referee where id in :id")
    List<Referee> findRefereesByIds(@Param("id") Collection<Long> id);

    @Query(value = "SELECT Referee from Referee where id not in (SELECT referee.id from RefereeFunctionOnMatch where match.dateOfMatch = :date)")
    List<Referee> findAllFreeReferees(@Param("date") LocalDate date);
}
