package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.repository.LeagueRepository;
import pl.lodz.p.it.referee_system.repository.TeamRepository;
import pl.lodz.p.it.referee_system.service.TeamService;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LeagueRepository leagueRepository;

    @Override
    public void addTeam(Team team) {
        team.setLeague(leagueRepository.findByName(team.getLeague().getName()));
        teamRepository.save(team);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElseThrow();
    }

    @Override
    public void editTeam(Team team) {

    }

    @Override
    public void removeTeam(Long id) {

    }
}
