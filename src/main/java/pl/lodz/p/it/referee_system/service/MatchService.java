package pl.lodz.p.it.referee_system.service;

import org.springframework.data.util.Pair;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.referee_system.entity.*;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    @PreAuthorize("permitAll()")
    List<Match> getAllMatches();

    @PreAuthorize("permitAll()")
    List<Match> getRefereeMatches(Long id);

    @PreAuthorize("isAuthenticated()")
    List<Match> getMyMatches();

    @Secured("ROLE_ADMIN")
    Match getMatch(Long id);

    @Secured("ROLE_ADMIN")
    Match createMatch(Match match);

    @Secured("ROLE_ADMIN")
    void editMatch(Match match);

    @Secured("ROLE_ADMIN")
    Pair<List<Team>, List<Referee>> getFreeTeamsAndReferees(LocalDate date);

    @Secured("ROLE_ADMIN")
    List<MatchFunction> getAllMatchFunctions();

    @PreAuthorize("isAuthenticated()")
    void initReplacement(Long matchId);

    @PreAuthorize("isAuthenticated()")
    void registerArrivalTime(ReplacementCandidate replacementCandidate);

    void replaceReferee(ReplaceInformations replaceInformations);

    @PreAuthorize("isAuthenticated()")
    List<ReplaceInformations> getMatchReplaceInformations(Long matchId);

    @PreAuthorize("isAuthenticated()")
    List<ReplaceInformations> getAllReplaceInformations();

    List<ReplaceInformations> getAllReplaceInformationsForScheduler();

    @PreAuthorize("isAuthenticated()")
    void replacementResign(Long replaceId);

    @Secured("ROLE_ADMIN")
    void confirmReplacement(ReplaceInformations replaceInformations);

}
