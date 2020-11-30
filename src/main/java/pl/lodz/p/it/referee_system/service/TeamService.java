package pl.lodz.p.it.referee_system.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.referee_system.entity.Team;

import java.util.List;

public interface TeamService {

    @Secured("ROLE_ADMIN")
    void addTeam(Team team);

    @PreAuthorize("permitAll()")
    List<Team> getAllTeams();

    @Secured("ROLE_ADMIN")
    Team getTeam(Long id);

    @Secured("ROLE_ADMIN")
    void editTeam(Team team);

    void removeTeam(Long id);
}
