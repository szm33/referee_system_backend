package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.*;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.repository.*;
import pl.lodz.p.it.referee_system.service.MatchService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;
import pl.lodz.p.it.referee_system.utill.ReplaceInformationsSender;
import pl.lodz.p.it.referee_system.utill.ResetLinkSender;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
@Retryable( value = Exception.class,
        maxAttemptsExpression = "${retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
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
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public Match createMatch(Match match) {
        if (LocalDate.now().isAfter(match.getDateOfMatch())) {
            throw new ApplicationException(ExceptionMessages.DATE_OF_MATCH_ERROR);
        }
        Match matchEntity = new Match();

        List<Team> teams = teamRepository.findTeamsByIds(match.getTeams().stream()
                .map(teamOnMatch -> teamOnMatch.getTeam().getId()).collect(Collectors.toList()));
        teams = teams.stream()
                .filter(team -> team.getMatches().stream()
                        .noneMatch(teamOnMatch -> teamOnMatch.getMatch()
                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
                .collect(Collectors.toList());
        if(teams.size() != 2){
            throw new ApplicationException(ExceptionMessages.MATCH_TEAMS_ERROR);
        }

        matchEntity.setTeams(match.getTeams());
        for(int i = 0; i < teams.size(); i++){
            matchEntity.getTeams().get(i).setTeam(teams.get(i));
            matchEntity.getTeams().get(i).setMatch(matchEntity);
        }

        List<Referee> referees = checkIfRefereesAreFree(match.getReferees().stream()
                        .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList()),
                match.getDateOfMatch());

//        List<Referee> referees = refereeRepository.findRefereesByIds(match.getReferees().stream()
//                .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList()));
//        referees = referees.stream()
//                .filter(referee -> referee.getMatches().stream()
//                        .noneMatch(refereeOnMatch -> refereeOnMatch.getMatch()
//                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
//                .collect(Collectors.toList());
//        if(referees.size() != match.getReferees().size()){
//            throw new ApplicationException(ExceptionMessages.MATCH_REFEREES_ERROR);
//        }

//        matchEntity.setReferees(match.getReferees());
        List<MatchFunction> matchFunctionList = matchFunctionRepository.findAll();


        referees.forEach(referee -> {
            String functionName = getRefereeFunction(referee.getId(), match.getReferees());
            MatchFunction matchFunction = matchFunctionList.stream().
                    filter(matchFun -> matchFun.getFunctionName().equals(functionName))
                    .findFirst().orElseThrow(() -> new ApplicationException(ExceptionMessages.MATCH_FUNCTIONS_ERROR));
            matchFunctionList.remove(matchFunction);
            RefereeFunctionOnMatch rfm = new RefereeFunctionOnMatch();
            rfm.setReferee(referee);
            rfm.setMatch(matchEntity);
            rfm.setMatchFunction(matchFunction);
            matchEntity.getReferees().add(rfm);
        });


//        for(int i = 0; i < referees.size(); i++){
//            String functionName = matchEntity.ge
//            MatchFunction matchFunction = matchFunctionList.stream().
//                    filter(matchFun -> matchFun.getFunctionName().equals(functionName))
//                    .findFirst().orElseThrow(() -> new ApplicationException(ExceptionMessages.MATCH_FUNCTIONS_ERROR));
//            matchFunctionList.remove(matchFunction);
//
//            matchEntity.getReferees().get(i).setMatchFunction(matchFunction);
////                    matchFunctionRepository.findByFunctionName(
////                            matchEntity.getReferees().get(i).getMatchFunction().getFunctionName()).orElseThrow());
//            matchEntity.getReferees().get(i).setReferee(referees.get(i));
//            matchEntity.getReferees().get(i).setMatch(matchEntity);
//        }
        matchEntity.setDateOfMatch(match.getDateOfMatch());
        matchEntity.setDescription(match.getDescription());
        matchEntity.setMatchTime(match.getMatchTime());
        return matchRepository.save(matchEntity);
    }

    private List<Referee> checkIfRefereesAreFree(List<Long> refereeIds, LocalDate date) {
        List<Referee> freeReferees = refereeRepository.findAllFreeReferees(date).stream()
                .filter(referee -> refereeIds.contains(referee.getId()))
                .collect(Collectors.toList());
        if(refereeIds.size() != freeReferees.size()){
            throw new ApplicationException(ExceptionMessages.MATCH_REFEREES_ERROR);
        }
        return freeReferees;
    }

    //odfiltrowac sedziow ktozy zglosili sie na zastapienie TODO
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void editMatch(Match match) {
        Match matchEntity = matchRepository.findById(match.getId()).orElseThrow();
        if (!match.getDateOfMatch().isEqual(matchEntity.getDateOfMatch()) && LocalDate.now().isAfter(match.getDateOfMatch())) {
            throw new ApplicationException(ExceptionMessages.DATE_OF_MATCH_ERROR);
        }
        if(match.getDateOfMatch() == null){
            throw new NoSuchElementException("No value present");
        }
        //jesli data zmieniona sprawdzenie czy druzyny sa wolne
        if(!match.getDateOfMatch().isEqual(matchEntity.getDateOfMatch()) && matchEntity.getTeams().stream()
                .filter(team -> team.getTeam().getMatches().stream()
                        .noneMatch(teamOnMatch -> teamOnMatch.getMatch()
                                .getDateOfMatch().isEqual(match.getDateOfMatch())))
                .count() != 2){
            throw new ApplicationException(ExceptionMessages.MATCH_TEAMS_ERROR);
        }
//        List<Long> newRefereesIds = match.getReferees().stream()
//                .map(refereeFunctionOnMatch -> refereeFunctionOnMatch.getReferee().getId()).collect(Collectors.toList());
//        List<Referee> referees = new ArrayList<>();
//        if(match.getDateOfMatch().isEqual(matchEntity.getDateOfMatch())){
//            List<Long> matchRefereesIds = matchEntity.getReferees().stream()
//                    .map(referee -> referee.getReferee().getId())
//                    .collect(Collectors.toList());
//            //dodanie sedziow bedacych juz wczesniej na meczu
//            final List<Long> allMatchRefereeIds = newRefereesIds;
//            matchEntity.getReferees().forEach(referee -> {
//                if (allMatchRefereeIds.contains(referee.getReferee().getId())) {
//                    referees.add(referee.getReferee());
//                }
//            });
//            newRefereesIds = newRefereesIds.stream()
//                    .filter(refereeId -> !matchRefereesIds.contains(refereeId))
//                    .collect(Collectors.toList());
//        }
//         referees.addAll(checkIfRefereesAreFree(newRefereesIds, match.getDateOfMatch()));



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
            throw new ApplicationException(ExceptionMessages.MATCH_REFEREES_ERROR);
        }
        //usuniecie startych sedziow i dodanie nowych
        matchEntity.getReferees().forEach(r -> r.getReferee().getMatches().remove(r));
        List<RefereeFunctionOnMatch> refereeFunctionOnMatchesToRemove = matchEntity.getReferees();
        matchEntity.setReferees(new ArrayList<>());
        //usuniecie automatycznych zastepstw w tym meczu
        refereeFunctionOnMatchRepository.deleteAll(refereeFunctionOnMatchesToRemove);

        List<MatchFunction> matchFunctionList = matchFunctionRepository.findAll();
//        for(int i = 0; i < referees.size(); i++){
//            final int it = i;
//            RefereeFunctionOnMatch rfm = new RefereeFunctionOnMatch();
//            rfm.setReferee(referees.get(i));
//            rfm.setMatch(matchEntity);
//            rfm.setMatchFunction(matchFunctionList.stream()
//                    .filter(ref -> ref.getFunctionName().equals(match.getReferees().get(it).getMatchFunction().getFunctionName()))
//                    .findFirst().orElse(null));
//            matchEntity.getReferees().add(rfm);
//        }

        referees.forEach(referee -> {
            String functionName = getRefereeFunction(referee.getId(), match.getReferees());
            MatchFunction matchFunction = matchFunctionList.stream().
                    filter(matchFun -> matchFun.getFunctionName().equals(functionName))
                    .findFirst().orElseThrow(() -> new ApplicationException(ExceptionMessages.MATCH_FUNCTIONS_ERROR));
            matchFunctionList.remove(matchFunction);
            RefereeFunctionOnMatch rfm = new RefereeFunctionOnMatch();
            rfm.setReferee(referee);
            rfm.setMatch(matchEntity);
            rfm.setMatchFunction(matchFunction);
            matchEntity.getReferees().add(rfm);
        });


        matchEntity.setDateOfMatch(match.getDateOfMatch());
        matchEntity.setDescription(match.getDescription());
        matchEntity.setMatchTime(match.getMatchTime());
        entityManager.detach(matchEntity);
        matchRepository.save(matchEntity);
    }

    private String getRefereeFunction(Long id, List<RefereeFunctionOnMatch> referees) {
        RefereeFunctionOnMatch referee = referees.stream().filter(ref -> ref.getReferee().getId().equals(id)).findFirst().orElseThrow();
        return referee.getMatchFunction().getFunctionName();
    }

    @Override
    public Pair<List<Team>, List<Referee>> getFreeTeamsAndReferees(LocalDate date) {
        List<Referee> freeReferees = refereeRepository.findAllFreeReferees(date);
        List<ReplaceInformations> replaceInformations = replaceInformationsRepository.findAll();
        return Pair.of(teamRepository.findAllFreeTeams(date), refereeRepository.findAllFreeReferees(date));
    }

    @Override
    public List<MatchFunction> getAllMatchFunctions() {
        return matchFunctionRepository.findAll();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void initReplacement(Long matchId) {
        Referee referee = refereeRepository.findByAccount_Username(ContextUtills.getUsername()).orElseThrow();
        //sprawdzenie czy nie wystepuje juz zastapienie na ten mecz tego sedziego
        RefereeFunctionOnMatch refereeFunction = referee.getMatches().stream()
                .filter(refereeFunctionOnMatch -> refereeFunctionOnMatch.getMatch().getId().equals(matchId))
                .findFirst().orElseThrow();
        replaceInformationsRepository.findByRefereeFunctionOnMatch(refereeFunction).ifPresent(r -> {
            throw new NoSuchElementException("No value present");
        });
        LocalDateTime executeTime = LocalDateTime.of(refereeFunction.getMatch().getDateOfMatch(), refereeFunction.getMatch().getMatchTime()).minusMinutes(15);
        if(LocalDateTime.now().isAfter(executeTime)){
            throw new NoSuchElementException("No value present");
        }
        ReplaceInformations replaceInformations = ReplaceInformations.builder()
                .refereeFunctionOnMatch(refereeFunction)
                .executeTime(executeTime)
                .build();
        ReplaceInformations savedReplaceInformations = replaceInformationsRepository.save(replaceInformations);
        String link = ContextUtills.createReplaceInformationsLink(savedReplaceInformations.getId());
        ReplaceInformationsSender replaceInformationsSender = new ReplaceInformationsSender(refereeRepository.findAll().stream()
                .map(refereeEntity -> refereeEntity.getAccount().getEmail())
                .collect(Collectors.toList()), link);
        replaceInformationsSender.start();
    }

    //zastanowic sie co z kwestja ponownego zapisu przez tego samego uzytkownika
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void registerArrivalTime(ReplacementCandidate replacementCandidate) {
        ReplaceInformations replaceInformationsEntity = replaceInformationsRepository.findById(replacementCandidate.getReplaceInformations().getId()).orElseThrow();
        long arrivalTimeInMinutes = replacementCandidate.getArrivalTime();
        Referee candidateReferee = refereeRepository.findByAccount_Username(ContextUtills.getUsername()).orElseThrow();
        //Sprawdzanie cyz nie zastepujesz samego siebie
        if (candidateReferee.equals(replaceInformationsEntity.getRefereeFunctionOnMatch().getReferee())) {
            throw new ApplicationException(ExceptionMessages.OWN_REPLACE_ERROR);
        }
        //sprawdzenie czy jest nie ma zandego meczu/zastapienia
        checkIfRefereesAreFree(List.of(candidateReferee.getId()),
                replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getDateOfMatch());

        List<ReplacementCandidate> candidates = replaceInformationsEntity.getCandidates().stream().
                sorted((Comparator.comparing(ReplacementCandidate::getArrivalTime))).collect(Collectors.toList());
        LocalDateTime nowDate = LocalDateTime.now();
        LocalDateTime arrivalDate = nowDate.plusMinutes(arrivalTimeInMinutes);
        LocalDateTime dateOfMatch = LocalDateTime.of(replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getDateOfMatch(),
                replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getMatchTime());

        if(candidates.size() < 1 || candidates.get(0).getArrivalTime() > replacementCandidate.getArrivalTime()){
            //sprawwdzenie czasu przy zastepowaniu

            if(arrivalDate.isBefore(dateOfMatch.minusMinutes(70))){
                replaceInformationsEntity.setExecuteTime(dateOfMatch.minusMinutes(70 + arrivalTimeInMinutes));
            } else if(arrivalDate.isAfter(dateOfMatch.minusMinutes(70)) && arrivalDate.isBefore(dateOfMatch)){
                replaceInformationsEntity.setExecuteTime(arrivalDate.plusMinutes(10));
            } else if(arrivalDate.isBefore(dateOfMatch.plusMinutes(20))){
                replaceInformationsEntity.setExecuteTime(arrivalDate.plusMinutes(5));
            }
        }

        replacementCandidate.setRefereeForReplacement(candidateReferee);
        replacementCandidate.setReplaceInformations(replaceInformationsEntity);
        replaceInformationsEntity.getCandidates().add(replacementCandidate);

        replaceInformationsRepository.save(replaceInformationsEntity);
    }

    //zastanowic sie nad kaskadami i nad detach czy potrzebne tu i tam wyzej
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void replaceReferee(ReplaceInformations replaceInformation) {
        ReplaceInformations replaceInformationsEntity = replaceInformationsRepository.findById(replaceInformation.getId()).orElseThrow();
        List<ReplacementCandidate> candidates = replaceInformationsEntity.getCandidates().stream().
                sorted((Comparator.comparing(ReplacementCandidate::getArrivalTime))).collect(Collectors.toList());

        if(candidates.size() != 0){
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime arrivalDate = nowDate.plusMinutes(candidates.get(0).getArrivalTime());
            LocalDateTime dateOfMatch = LocalDateTime.of(replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getDateOfMatch(),
                    replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch().getMatchTime());
            if(arrivalDate.isBefore(dateOfMatch.plusMinutes(30))){
                RefereeFunctionOnMatch refereeFunctionOnMatch = refereeFunctionOnMatchRepository
                        .findById(replaceInformationsEntity.getRefereeFunctionOnMatch().getId()).orElseThrow();
                refereeFunctionOnMatch.getReferee().getMatches().remove(refereeFunctionOnMatch);
                refereeFunctionOnMatch.setReferee(refereeRepository.findById(candidates.get(0).getRefereeForReplacement().getId()).orElseThrow());
                refereeFunctionOnMatchRepository.save(refereeFunctionOnMatch);
            }
        }
        replaceInformationsRepository.delete(replaceInformationsEntity);
    }

    @Override
    public List<ReplaceInformations> getAllReplaceInformations() {
        return replaceInformationsRepository.findAll();
    }

    @Override
    public List<ReplaceInformations> getMatchReplaceInformations(Long matchId) {
        List<ReplaceInformations> replaceInformations = replaceInformationsRepository.findAllByRefereeFunctionOnMatch_Match_Id(matchId);
        if(replaceInformations.isEmpty()){
            throw new NoSuchElementException("No value present");
        }
        return replaceInformations;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void replacementResign(Long replaceId) {
        ReplaceInformations replaceInformations = replaceInformationsRepository.findById(replaceId).orElseThrow();
        List<ReplacementCandidate> candidates = replaceInformations.getCandidates().stream().
                sorted((Comparator.comparing(ReplacementCandidate::getArrivalTime))).collect(Collectors.toList());
        ReplacementCandidate resignCandidate = candidates.stream()
                .filter(info -> info.getRefereeForReplacement().getAccount().getUsername()
                        .equals(ContextUtills.getUsername()))
                .findFirst().orElseThrow();
        if(candidates.get(0).equals(resignCandidate)){
            Match match = replaceInformations.getRefereeFunctionOnMatch().getMatch();
            LocalDateTime dateOfMatch = LocalDateTime.of(match.getDateOfMatch(), match.getMatchTime());
            if(candidates.size() == 1){
                replaceInformations.setExecuteTime(dateOfMatch.minusMinutes(15));
            } else {
                LocalDateTime nowDate = LocalDateTime.now();
                long arrivalTimeInMinutes = candidates.get(1).getArrivalTime();
                LocalDateTime arrivalDate = nowDate.plusMinutes(arrivalTimeInMinutes);
                if(arrivalDate.isBefore(dateOfMatch.minusMinutes(70))){
                    replaceInformations.setExecuteTime(dateOfMatch.minusMinutes(70 + arrivalTimeInMinutes));
                } else if(arrivalDate.isAfter(dateOfMatch.minusMinutes(70)) && arrivalDate.isBefore(dateOfMatch)){
                    replaceInformations.setExecuteTime(arrivalDate.plusMinutes(10));
                } else if(arrivalDate.isBefore(dateOfMatch.plusMinutes(20))){
                    replaceInformations.setExecuteTime(arrivalDate.plusMinutes(5));
                } else {
                    replaceInformations.setExecuteTime(LocalDateTime.of(replaceInformations.getRefereeFunctionOnMatch().getMatch().getDateOfMatch(),
                            replaceInformations.getRefereeFunctionOnMatch().getMatch().getMatchTime()));
                }
            }
        }
        replaceInformations.getCandidates().remove(resignCandidate);
        entityManager.remove(resignCandidate);
        replaceInformationsRepository.save(replaceInformations);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public void confirmReplacement(ReplaceInformations replaceInformations) {
        ReplaceInformations replaceInformationsEntity = replaceInformationsRepository.findById(replaceInformations.getId()).orElseThrow();
        replaceInformationsEntity.getRefereeFunctionOnMatch().getReferee().getMatches()
                .remove(replaceInformationsEntity.getRefereeFunctionOnMatch().getMatch());
        replaceInformationsEntity.getRefereeFunctionOnMatch().setReferee(refereeRepository.
                findById(replaceInformations.getCandidates().get(0).getId()).orElseThrow());
        refereeFunctionOnMatchRepository.save(replaceInformationsEntity.getRefereeFunctionOnMatch());
        replaceInformationsRepository.delete(replaceInformationsEntity);
    }
}
