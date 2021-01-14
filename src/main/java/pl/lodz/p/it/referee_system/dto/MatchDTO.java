package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MatchDTO {

    private Long id;
    @NotNull
    private List<RefereeOnMatchDTO> referees = new ArrayList<>();
    @NotNull
    private TeamOnMatchDTO homeTeam;
    @NotNull
    private TeamOnMatchDTO awayTeam;
    @NotNull
    private LocalDate dateOfMatch;
    @Pattern(regexp = "^[a-zA-Z_0-9:,?/@() ]+$")
    private String description;
    @NotEmpty
    @Pattern(regexp = "^[0-9]{2}:[0-9]{2}+$")
    private String timeOfMatch;
    private String version;

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
        this.version = ContextUtills.encrypt(match.getVersion());
    }
}
