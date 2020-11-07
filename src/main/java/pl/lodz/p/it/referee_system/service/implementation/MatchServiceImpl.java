package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.*;
import pl.lodz.p.it.referee_system.repository.MatchFunctionRepository;
import pl.lodz.p.it.referee_system.repository.MatchRepository;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.repository.TeamRepository;
import pl.lodz.p.it.referee_system.service.MatchService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private RefereeRepository refereeRepository;
    @Autowired
    private MatchFunctionRepository matchFunctionRepository;

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
        teams = teams.stream()
                .filter(team -> team.getMatches().stream()
                        .noneMatch(teamOnMatch -> teamOnMatch.getMatch()
                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
        .collect(Collectors.toList());
        if (teams.size() != 2) {
            throw new NoSuchElementException("No value present");
        }

        matchEntity.setTeams(match.getTeams());
        for(int i = 0; i < teams.size(); i++){
            matchEntity.getTeams().get(i).setTeam(teams.get(i));
            matchEntity.getTeams().get(i).setMatch(matchEntity);
        }

        List<Referee> referees = refereeRepository.findRefereesByIds(match.getReferees().stream()
                .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList()));
        referees = referees.stream()
                .filter(referee -> referee.getMatches().stream()
                .noneMatch(refereeOnMatch -> refereeOnMatch.getMatch()
                        .getDateOfMatch().isEqual(match.getDateOfMatch())))
        .collect(Collectors.toList());
        if (referees.size() != match.getReferees().size()) {
            throw new NoSuchElementException("No value present");
        }
        List<MatchFunction> matchFunctions = matchFunctionRepository.findAll();
//        match.getReferees().get(0).setMatchFunction(matchFunctions.stream().filter(matchFunction -> matchFunction.getFunctionName().equals(match)).findFirst());
        matchEntity.setReferees(match.getReferees());
        for(int i = 0; i < referees.size(); i++){
            matchEntity.getReferees().get(i).setMatchFunction(
                    matchFunctionRepository.findByFunctionName(
                            matchEntity.getReferees().get(i).getMatchFunction().getFunctionName()).orElseThrow());
            matchEntity.getReferees().get(i).setReferee(referees.get(i));
            matchEntity.getReferees().get(i).setMatch(matchEntity);

        }
        matchEntity.setDateOfMatch(match.getDateOfMatch());
        matchEntity.setDescription(match.getDescription());
        matchRepository.save(matchEntity);
    }

    @Override
    public void editMatch(Match match) {

    }

    @Override
    public Pair<List<Team>, List<Referee>> getFreeTeamsAndReferees(LocalDate date) {
        return Pair.of(teamRepository.findAllFreeTeams(date), refereeRepository.findAllFreeReferees(date));
    }

    @Override
    public List<MatchFunction> getAllMatchFunctions() {
        return matchFunctionRepository.findAll();
    }
}
