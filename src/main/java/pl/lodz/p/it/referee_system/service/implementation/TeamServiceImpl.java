package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.exception.TeamException;
import pl.lodz.p.it.referee_system.repository.EntityManagerRepository;
import pl.lodz.p.it.referee_system.repository.LeagueRepository;
import pl.lodz.p.it.referee_system.repository.TeamRepository;
import pl.lodz.p.it.referee_system.service.TeamService;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private EntityManagerRepository entityManager;

    @Override
    public void addTeam(Team team) {
        try {
            team.setLeague(leagueRepository.findByName(team.getLeague().getName()).orElseThrow());
            teamRepository.save(team);
            teamRepository.flush();
        }
        //dodac unique na parrze liga nazwa
        catch (DataIntegrityViolationException e) {
            throw TeamException.exceptionForNotUniqueNameInLeague(e);
        }
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
        teamRepository.findById(team.getId()).ifPresentOrElse(teamEntity -> {
            entityManager.detach(teamEntity);
            teamEntity.setVersion(team.getVersion());
            teamEntity.setName(team.getName());
            teamEntity.setLeague(leagueRepository.findByName(team.getLeague().getName()).orElseThrow());
            teamRepository.save(teamEntity);
        }, () -> {
            throw new NoSuchElementException("No value present");
        });
    }

    @Override
    public void removeTeam(Long id) {

    }
}
