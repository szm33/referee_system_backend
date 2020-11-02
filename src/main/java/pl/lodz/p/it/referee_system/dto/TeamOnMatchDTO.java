package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.TeamOnMatch;

@Data
public class TeamOnMatchDTO {

    private String name;
    private boolean isGuest;
    private Long id;

    public TeamOnMatchDTO() {}

    public TeamOnMatchDTO(TeamOnMatch teamOnMatch) {
        this.name = teamOnMatch.getTeam().getName();
        this.isGuest = teamOnMatch.isGuest();
        this.id = teamOnMatch.getTeam().getId();
    }
}
