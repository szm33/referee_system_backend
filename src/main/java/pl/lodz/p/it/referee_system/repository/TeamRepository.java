package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.Team;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface TeamRepository extends JpaRepository<Team, Long> {
//    @Query(value = "SELECT Team from Team where id in :id AND id not in (SELECT team.id from TeamOnMatch where match.dateOfMatch = :date)")
//    List<Team> findTeamsByIdIfFree(@Param("id") Collection<Long> id, @Param("date") Date date);
    @Query(value = "SELECT Team from Team where id in :id")
    List<Team> findTeamsByIds(@Param("id") Collection<Long> id);

    @Query(value = "SELECT Team from Team where id not in (SELECT team.id from TeamOnMatch where match.dateOfMatch = :date)")
    List<Team> findAllFreeTeams(@Param("date") LocalDate date);

}
