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
    private TeamOnMatchDTO homeTeam;
    private TeamOnMatchDTO awayTeam;
    private LocalDate dateOfMatch;
    private String description;
    private String timeOfMatch;

    public MatchDTO() {
    }

    public MatchDTO(Match match) {
        this.id = match.getId();
        this.dateOfMatch = match.getDateOfMatch();
        this.description = match.getDescription();
        this.referees = match.getReferees().stream().map(RefereeOnMatchDTO::new).collect(Collectors.toList());
        int i = match.getTeams().get(0).isGuest() ? 1 : 0;
        this.homeTeam = new TeamOnMatchDTO(match.getTeams().get(i));
        this.awayTeam = new TeamOnMatchDTO(match.getTeams().get((i + 1) % 2));
        this.timeOfMatch = match.getMatchTime().toString();
    }
}
