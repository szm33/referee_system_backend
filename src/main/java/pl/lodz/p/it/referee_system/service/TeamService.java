package pl.lodz.p.it.referee_system.service;

import pl.lodz.p.it.referee_system.entity.Team;

import java.util.List;

public interface TeamService {

    void addTeam(Team team);

    List<Team> getAllTeams();

    Team getTeam(Long id);

    void editTeam(Team team);

    void removeTeam(Long id);
}
