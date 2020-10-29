package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Team;

@Data
public class FreeTeamDTO {

    private Long id;
    private String name;

    public FreeTeamDTO() {
    }

    public FreeTeamDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
