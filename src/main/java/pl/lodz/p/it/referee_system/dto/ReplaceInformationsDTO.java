package pl.lodz.p.it.referee_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.Match;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;

import java.time.LocalDateTime;


@Getter
@Setter
public class ReplaceInformationsDTO {

    public ReplaceInformationsDTO(ReplaceInformations replaceInformations) {
        Match match = replaceInformations.getRefereeFunctionOnMatch().getMatch();
        this.description = match.getDescription();
        this.dateOfMatch = LocalDateTime.of(match.getDateOfMatch(), match.getMatchTime());
    }

    private LocalDateTime dateOfMatch;
    private String description;
}
