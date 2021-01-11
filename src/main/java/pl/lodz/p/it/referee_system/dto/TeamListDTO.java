package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import pl.lodz.p.it.referee_system.entity.Team;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class TeamListDTO {

    @NotNull
    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String name;
    @NotEmpty
    private String league;
    @NotEmpty
    private String version;

    public TeamListDTO() {}

    public TeamListDTO(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.league = team.getLeague().getName();
        this.version = ContextUtills.encrypt(team.getVersion());
    }
}
