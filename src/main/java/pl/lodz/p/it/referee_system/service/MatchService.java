package pl.lodz.p.it.referee_system.service;

import pl.lodz.p.it.referee_system.entity.Match;

import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();

    List<Match> getRefereeMatches(Long id);

    List<Match> getMyMatches();

    Match getMatch(Long id);

    void createMatch(Match match);

    void editMatch(Match match);

}
