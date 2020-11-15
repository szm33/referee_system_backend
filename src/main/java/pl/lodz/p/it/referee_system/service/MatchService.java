package pl.lodz.p.it.referee_system.service;

import org.springframework.data.util.Pair;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.MatchFunction;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.Team;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();

    List<Match> getRefereeMatches(Long id);

    List<Match> getMyMatches();

    Match getMatch(Long id);

    void createMatch(Match match);

    void editMatch(Match match);

    Pair<List<Team>, List<Referee>> getFreeTeamsAndReferees(LocalDate date);

    List<MatchFunction> getAllMatchFunctions();

    void initReplacement(Long machtId);

}
