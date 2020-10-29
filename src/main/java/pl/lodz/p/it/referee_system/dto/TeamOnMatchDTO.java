package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.TeamOnMatch;

@Data
public class TeamOnMatchDTO {

    private String teamName;
    private boolean isGuest;

    public TeamOnMatchDTO() {}

    public TeamOnMatchDTO(TeamOnMatch teamOnMatch) {
        this.teamName = teamOnMatch.getTeam().getName();
        this.isGuest = teamOnMatch.isGuest();
    }
}
