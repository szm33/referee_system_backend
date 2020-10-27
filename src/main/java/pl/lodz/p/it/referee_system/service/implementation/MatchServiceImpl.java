package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.entity.TeamOnMatch;
import pl.lodz.p.it.referee_system.repository.MatchRepository;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.repository.TeamRepository;
import pl.lodz.p.it.referee_system.service.MatchService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RefereeRepository refereeRepository;

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public List<Match> getRefereeMatches(Long id) {
        return matchRepository.findByRefereeId(id);
    }

    @Override
    public List<Match> getMyMatches() {
        return matchRepository.findByUsername(ContextUtills.getUsername());
    }

    @Override
    public Match getMatch(Long id) {
        return matchRepository.findById(id).orElseThrow();
    }

    //2 druzyny data opis sedziowie z funkcjami
//sprawdzamy czy nie graja juz tego samego dnia+
    // moze czy data jest w przod
    //sprawdzamy czy sedziowie sa wolni+
    @Override
    public void createMatch(Match match) {
        Match matchEntity = new Match();
//        List<Team> teams = teamRepository.findTeamsByIdIfFree(match.getTeams().stream()
//                        .map(teamOnMatch -> teamOnMatch.getTeam().getId()).collect(Collectors.toList()),
//                            match.getDateOfMatch());
        List<Team> teams = teamRepository.findTeamsByIds(match.getTeams().stream()
                        .map(teamOnMatch -> teamOnMatch.getTeam().getId()).collect(Collectors.toList()));
        teams.stream()
                .filter(team -> team.getMatches().stream()
                        .noneMatch(teamOnMatch -> teamOnMatch.getMatch()
                                .getDateOfMatch().compareTo(match.getDateOfMatch()) == 0));
        if (teams.size() != 2) {
            throw new NoSuchElementException("No value present");
        }

        matchEntity.setTeams(match.getTeams());
        for(int i = 0; i < teams.size(); i++){
            matchEntity.getTeams().get(i).setTeam(teams.get(i));
            //matchEntity.getTeams().get(i).setMatch(matchEntity);
        }

        List<Referee> referees = refereeRepository.findRefereesByIds(match.getReferees().stream()
                .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList()));
        referees.stream()
                .filter(referee -> referee.getMatches().stream()
                .noneMatch(refereeOnMatch -> refereeOnMatch.getMatch()
                        .getDateOfMatch().compareTo(match.getDateOfMatch()) == 0));
        if (referees.size() != match.getReferees().size()) {
            throw new NoSuchElementException("No value present");
        }

        matchEntity.setReferees(match.getReferees());
        for(int i = 0; i < referees.size(); i++){
            matchEntity.getReferees().get(i).setReferee(referees.get(i));
//            matchEntity.getReferees().get(i).setMatch(matchEntity);

        }
        matchEntity.setDateOfMatch(match.getDateOfMatch());
        matchEntity.setDescription(match.getDescription());
        matchRepository.save(matchEntity);
    }

    @Override
    public void editMatch(Match match) {

    }
}
