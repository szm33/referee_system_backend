package pl.lodz.p.it.referee_system.dto;


import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.Referee;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class MatchToEditDTO {

   private MatchDTO match;
   private List<FreeRefereeDTO> freeReferees;

    public MatchToEditDTO() {
    }

    public MatchToEditDTO(Match match, List<Referee> referees) {
        this.match = new MatchDTO(match);
        this.freeReferees = referees.stream()
                .map(FreeRefereeDTO::new)
                .collect(Collectors.toList());
    }
}
