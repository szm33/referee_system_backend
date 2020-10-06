package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Referee;

@Data
public class RefereeListDTO {

    private Long id;
    private String name;
    private String surname;
    private boolean active;

    public RefereeListDTO(){}

    public RefereeListDTO(Referee referee) {
        this.id = referee.getId();
        this.name = referee.getName();
        this.surname = referee.getSurname();
        this.active = referee.getAccount().isActive();
    }
}
