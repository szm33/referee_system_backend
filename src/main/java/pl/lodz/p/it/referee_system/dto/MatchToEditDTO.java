package pl.lodz.p.it.referee_system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.MatchFunction;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.RefereeFunctionOnMatch;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class MatchToEditDTO {

    private Long id;
    private List<String> functions;
    private List<FreeRefereeDTO> referees;
//    private Map<MatchFunction,FreeRefereeDTO> refereesFunction = new HashMap<>();
    private FreeTeamDTO homeTeam;
    private FreeTeamDTO awayTeam;
    private LocalDate dateOfMatch;
    private String description;
    private String version;

    public MatchToEditDTO() {
    }

    public MatchToEditDTO(Match match, List<MatchFunction> functions) {
        this.id = match.getId();
        this.description = match.getDescription();
        this.version = ContextUtills.encrypt(match.getVersion());
        this.dateOfMatch = match.getDateOfMatch();
        if (match.getTeams().get(0).isGuest()) {
            this.awayTeam = new FreeTeamDTO(match.getTeams().get(0).getTeam());
            this.homeTeam = new FreeTeamDTO(match.getTeams().get(1).getTeam());
        }
        else {
            this.awayTeam = new FreeTeamDTO(match.getTeams().get(1).getTeam());
            this.homeTeam = new FreeTeamDTO(match.getTeams().get(0).getTeam());
        }
        this.functions = functions.stream()
                .map(MatchFunction::getFunctionName)
                .collect(Collectors.toList());
        this.referees = match.getReferees().stream()
                .map(refereeFunctionOnMatch -> new FreeRefereeDTO(refereeFunctionOnMatch.getReferee(),
                        refereeFunctionOnMatch.getMatchFunction().getFunctionName()))
                .collect(Collectors.toList());
//        functions.forEach(function -> {
//            this.refereesFunction.put(function,match.getReferees().stream()
//                    .filter(referee -> referee.getMatchFunction().equals(function))
//                    .findFirst()
//                    .map(refereeFunction -> new FreeRefereeDTO(refereeFunction.getReferee()))
//                    .orElse(null));
//        });
    }
}
