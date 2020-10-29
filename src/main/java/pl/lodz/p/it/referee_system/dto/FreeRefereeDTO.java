package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Referee;

@Data
public class FreeRefereeDTO {

    private Long id;
    private String name;
    private String function;

    public FreeRefereeDTO() {
    }

    public FreeRefereeDTO(Referee referee) {
        this.id = referee.getId();
        this.name = referee.getName();
        this.surname = referee.getSurname();
    }

    private String surname;
}
