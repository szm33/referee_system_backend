package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Referee;

import javax.validation.constraints.Min;
import java.io.Serializable;

@Data
public class RefereeDTO implements Serializable {

    public RefereeDTO(Referee referee) {
        this.id = referee.getId();
        this.name = referee.getName();
        this.surname = referee.getSurname();
    }

    @Min(2)
    private Long id;

    private String name;

    private String surname;

    private String permissionClass;
}
