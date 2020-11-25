package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Match;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MatchDTO {

    private Long id;
    private List<RefereeOnMatchDTO> referees = new ArrayList<>();
    private List<TeamOnMatchDTO> teams = new ArrayList<>();
    private LocalDate dateOfMatch;
    private Integer homeScore;
    private Integer awayScore;
    private String description;
    private LocalTime timeOfMatch;

    public MatchDTO() {}

    public MatchDTO(Match match) {
        this.id = match.getId();
        this.dateOfMatch = match.getDateOfMatch();
        this.awayScore = match.getAwayScore();
        this.homeScore = match.getHomeScore();
        this.description = match.getDescription();
        this.referees = match.getReferees().stream().map(RefereeOnMatchDTO::new).collect(Collectors.toList());
        this.teams = match.getTeams().stream().map(TeamOnMatchDTO::new).collect(Collectors.toList());
        this.timeOfMatch = match.getMatchTime();
    }
}
