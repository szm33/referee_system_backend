package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Team;

@Data
public class TeamListDTO {

    private Long id;
    private String name;
    private String league;


    public TeamListDTO() {}

    public TeamListDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.league = team.getLeague().getName();
    }
}
