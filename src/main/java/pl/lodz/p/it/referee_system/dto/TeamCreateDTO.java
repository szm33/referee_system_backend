package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
public class TeamCreateDTO {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
    private String name;
    @NotEmpty
    private String league;
}
