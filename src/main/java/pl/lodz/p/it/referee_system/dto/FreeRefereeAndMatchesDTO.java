package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.springframework.data.util.Pair;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.Team;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class FreeRefereeAndMatchesDTO {
    public FreeRefereeAndMatchesDTO(Pair<List<Team>, List<Referee>> refereesAndTeams) {
        this.referees = refereesAndTeams.getSecond().stream().map(FreeRefereeDTO::new).collect(Collectors.toList());
        this.teams = refereesAndTeams.getFirst().stream().map(FreeTeamDTO::new).collect(Collectors.toList());
    }

    List<FreeRefereeDTO> referees;
    List<FreeTeamDTO> teams;

}
