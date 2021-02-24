package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Team;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TeamRepository extends JpaRepository<Team, Long> {

 @Query("SELECT t from Team t where t.id in :id")
    List<Team> findTeamsByIds(@Param("id") Collection<Long> id);

    @Query("SELECT t from Team t where t.id not in(SELECT tm.team.id from TeamOnMatch tm where tm.match.dateOfMatch = :date)")
    List<Team> findAllFreeTeams(@Param("date") LocalDate date);

}
