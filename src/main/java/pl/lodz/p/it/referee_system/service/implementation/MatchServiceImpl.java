package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.*;
import pl.lodz.p.it.referee_system.repository.*;
import pl.lodz.p.it.referee_system.service.MatchService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RefereeFunctionOnMatchRepository refereeFunctionOnMatchRepository;
    @Autowired
    private ReplaceInformationsRepository replaceInformationsRepository;

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
        if(teams.size() != 2){
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
        if(referees.size() != match.getReferees().size()){
            throw new NoSuchElementException("No value present");
        }
//        List<MatchFunction> matchFunctions = matchFunctionRepository.findAll();
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
        Match matchEntity = matchRepository.findById(match.getId()).orElseThrow();
        if(match.getDateOfMatch() == null){
            throw new NoSuchElementException("No value present");
        }
        //jesli data zmieniona sprawdzenie czy druzyny sa wolne
        if(!match.getDateOfMatch().isEqual(matchEntity.getDateOfMatch()) && matchEntity.getTeams().stream()
                .filter(team -> team.getTeam().getMatches().stream()
                        .noneMatch(teamOnMatch -> teamOnMatch.getMatch()
                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
                .count() != 2){
            throw new NoSuchElementException("No value present");
        }
        //sprawdzenie czy wybrani sedziowie sa wolni
        List<Referee> referees = refereeRepository.findRefereesByIds(match.getReferees().stream()
                .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList()));
        referees = referees.stream()
                .filter(referee -> referee.getMatches().stream()
                        .noneMatch(refereeOnMatch -> refereeOnMatch.getMatch()
                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
                .collect(Collectors.toList());
        //dodanie sedziow bedacych na edytowanym meczu jesli data jest ta sama
        if(match.getDateOfMatch().isEqual(matchEntity.getDateOfMatch())){
            List<Long> matchRefereesId = new ArrayList<>();
            matchRefereesId.addAll(matchEntity.getReferees().stream()
                    .map(RefereeFunctionOnMatch::getReferee)
                    .filter(referee -> match.getReferees().stream()
                            .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId())
                            .collect(Collectors.toList())
                            .contains(referee.getId()))
                    .map(Referee::getId)
                    .collect(Collectors.toList()));
            referees.addAll(refereeRepository.findRefereesByIds(matchRefereesId));
        }
        if(referees.size() != match.getReferees().size()){
            throw new NoSuchElementException("No value present");
        }
        //usuniecie startych sedziow i dodanie nowych
        matchEntity.getReferees().forEach(r -> r.getReferee().getMatches().remove(r));
        List<RefereeFunctionOnMatch> refereeFunctionOnMatchesToRemove = matchEntity.getReferees();
        matchEntity.setReferees(new ArrayList<>());
        refereeFunctionOnMatchRepository.deleteAll(refereeFunctionOnMatchesToRemove);

        List<MatchFunction> matchFunctionList = matchFunctionRepository.findAll();
        for(int i = 0; i < referees.size(); i++){
            final int it = i;
            RefereeFunctionOnMatch rfm = new RefereeFunctionOnMatch();
            rfm.setReferee(referees.get(i));
            rfm.setMatch(matchEntity);
            rfm.setMatchFunction(matchFunctionList.stream()
                    .filter(ref -> ref.getFunctionName().equals(match.getReferees().get(it).getMatchFunction().getFunctionName()))
                    .findFirst().orElse(null));
            matchEntity.getReferees().add(rfm);
        }
        matchEntity.setDateOfMatch(match.getDateOfMatch());
        matchEntity.setDescription(match.getDescription());
        entityManager.detach(matchEntity);
        matchRepository.save(matchEntity);
    }

    @Override
    public Pair<List<Team>, List<Referee>> getFreeTeamsAndReferees(LocalDate date) {
        return Pair.of(teamRepository.findAllFreeTeams(date), refereeRepository.findAllFreeReferees(date));
    }

    @Override
    public List<MatchFunction> getAllMatchFunctions() {
        return matchFunctionRepository.findAll();
    }

    @Override
    public void initReplacement(Long machtId) {
        Referee referee = refereeRepository.findByAccount_Username(ContextUtills.getUsername());
        RefereeFunctionOnMatch refereeFunction = referee.getMatches().stream()
                .filter(refereeFunctionOnMatch -> refereeFunctionOnMatch.getMatch().getId().equals(machtId))
                .findFirst().orElseThrow();
        replaceInformationsRepository.findByRefereeFunctionOnMatchOr(refereeFunction).ifPresent(r -> {
            throw new NoSuchElementException("No value present");
        });
        LocalDateTime executeTime = LocalDateTime.of(refereeFunction.getMatch().getDateOfMatch(), refereeFunction.getMatch().getMatchTime());
        ReplaceInformations replaceInformations = ReplaceInformations.builder()
                .refereeFunctionOnMatch(refereeFunction)
                .executeTime(executeTime)
                .build();
        replaceInformationsRepository.save(replaceInformations);
    }

    @Override
    public void registerArrivalTime(ReplaceInformations replaceInformations) {
        ReplaceInformations replaceInformationsEntity = replaceInformationsRepository.findById(replaceInformations.getId()).orElseThrow();
        long arrivalTimeInMinutes = replaceInformations.getArrivalTime();
        LocalDateTime nowDate = LocalDateTime.now();
        LocalDateTime arrivalDate = nowDate.plusMinutes(arrivalTimeInMinutes);
//                ChronoUnit.MINUTES.between(LocalDateTime.now(), replaceInformations.getArrivalTime());
        LocalDateTime dateOfMatch = LocalDateTime.of(replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getDateOfMatch(),
                replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getMatchTime());
        if (replaceInformationsEntity.getRefereeForReplacement() == null || replaceInformationsEntity.getArrivalTime() > arrivalTimeInMinutes){
            if (arrivalDate.isBefore(dateOfMatch.minusMinutes(70))){
                replaceInformationsEntity.setArrivalTime(arrivalTimeInMinutes);
                replaceInformationsEntity.setRefereeForReplacement(refereeRepository.findByAccount_Username(ContextUtills.getUsername()));
                replaceInformationsEntity.setExecuteTime(dateOfMatch.minusMinutes(70 + arrivalTimeInMinutes));
            } else if (arrivalDate.isAfter(dateOfMatch.minusMinutes(70)) && arrivalDate.isBefore(dateOfMatch)){
                replaceInformationsEntity.setArrivalTime(arrivalTimeInMinutes);
                replaceInformationsEntity.setRefereeForReplacement(refereeRepository.findByAccount_Username(ContextUtills.getUsername()));
                replaceInformationsEntity.setExecuteTime(arrivalDate.plusMinutes(10));
            } else if (arrivalDate.isBefore(dateOfMatch.plusMinutes(20))){
                replaceInformationsEntity.setArrivalTime(arrivalTimeInMinutes);
                replaceInformationsEntity.setRefereeForReplacement(refereeRepository.findByAccount_Username(ContextUtills.getUsername()));
                replaceInformationsEntity.setExecuteTime(arrivalDate.plusMinutes(5));
            }
        }
        replaceInformationsRepository.save(replaceInformationsEntity);
    }
//zastanowic sie nad kaskadami i nad detach czy potrzebne tu i tam wyzej
    @Override
    public void replaceReferee(ReplaceInformations replaceInformation) {
        ReplaceInformations replaceInformationsEntity = replaceInformationsRepository.findById(replaceInformation.getId()).orElseThrow();
        if (replaceInformationsEntity.getRefereeForReplacement() != null) {
            RefereeFunctionOnMatch refereeFunctionOnMatch = refereeFunctionOnMatchRepository
                    .findById(replaceInformationsEntity.getRefereeFunctionOnMatch().getId()).orElseThrow();
            refereeFunctionOnMatch.getReferee().getMatches().remove(refereeFunctionOnMatch);
            refereeFunctionOnMatch.setReferee(refereeRepository.findById(replaceInformationsEntity.getRefereeForReplacement().getId()).orElseThrow());
            refereeFunctionOnMatchRepository.save(refereeFunctionOnMatch);
        }
        replaceInformationsRepository.delete(replaceInformationsEntity);
    }

    @Override
    public List<ReplaceInformations> getAllReplaceInformations() {
        return replaceInformationsRepository.findAll();
    }
}
