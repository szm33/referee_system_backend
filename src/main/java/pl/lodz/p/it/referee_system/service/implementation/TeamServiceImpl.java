package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Override
    public void addTeam(Team team) {

    }

    @Override
    public List<Team> getAllTeams() {
        return null;
    }

    @Override
    public Team getTeam(Long id) {
        return null;
    }

    @Override
    public void editTeam(Team team) {

    }

    @Override
    public void removeTeam(Long id) {

    }
}
