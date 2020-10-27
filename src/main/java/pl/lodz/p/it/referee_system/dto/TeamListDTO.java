package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

@Data
public class TeamListDTO {

    private Long id;
    private String name;
    private String league;
    private String version;

    public TeamListDTO() {}

    public TeamListDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.league = team.getLeague().getName();
        this.version = ContextUtills.encrypt(team.getVersion());
    }
}
