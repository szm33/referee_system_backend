package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.MatchCreateDTO;
import pl.lodz.p.it.referee_system.dto.MatchToEditDTO;
import pl.lodz.p.it.referee_system.entity.*;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchMapper {

    public static Match map(MatchCreateDTO matchDTO) {
        Match match = new Match();
        match.setDescription(matchDTO.getDescription());
        match.setDateOfMatch(matchDTO.getDateOfMatch());
        Team homeTeam = new Team();
        Team awayTeam = new Team();
        homeTeam.setId(matchDTO.getHomeTeamId());
        awayTeam.setId(matchDTO.getAwayTeamId());
        TeamOnMatch homeTeamOnMatch = new TeamOnMatch();
        TeamOnMatch awayTeamOnMatch = new TeamOnMatch();
        homeTeamOnMatch.setTeam(homeTeam);
        homeTeamOnMatch.setGuest(false);
        awayTeamOnMatch.setTeam(awayTeam);
        awayTeamOnMatch.setGuest(true);
        List<TeamOnMatch> teams = new ArrayList(List.of(homeTeamOnMatch, awayTeamOnMatch));
        match.setTeams(teams);
        match.setReferees(matchDTO.getReferees().stream().map(freeRefereeDTO -> {
            RefereeFunctionOnMatch refereeFunctionOnMatch = new RefereeFunctionOnMatch();
            MatchFunction function = new MatchFunction();
            function.setFunctionName(freeRefereeDTO.getFunction());
            refereeFunctionOnMatch.setMatchFunction(function);
            Referee referee = new Referee();
            referee.setId(freeRefereeDTO.getId());
            refereeFunctionOnMatch.setReferee(referee);
            return refereeFunctionOnMatch;
        }).collect(Collectors.toList()));
        return match;
    }

    public static Match map(MatchToEditDTO matchDTO) {
        Match match = new Match();
        match.setId(matchDTO.getId());
        match.setVersion(ContextUtills.decrypt(matchDTO.getVersion()));
        match.setDescription(matchDTO.getDescription());
        match.setDateOfMatch(matchDTO.getDateOfMatch());
        match.setReferees(matchDTO.getReferees().stream().map(freeRefereeDTO -> {
            RefereeFunctionOnMatch refereeFunctionOnMatch = new RefereeFunctionOnMatch();
            MatchFunction function = new MatchFunction();
            function.setFunctionName(freeRefereeDTO.getFunction());
            refereeFunctionOnMatch.setMatchFunction(function);
            Referee referee = new Referee();
            referee.setId(freeRefereeDTO.getId());
            refereeFunctionOnMatch.setReferee(referee);
            return refereeFunctionOnMatch;
        }).collect(Collectors.toList()));
        return match;
    }
}
