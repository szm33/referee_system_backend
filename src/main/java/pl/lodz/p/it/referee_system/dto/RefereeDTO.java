package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Referee;

@Data
public class RefereeDTO {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String license;
    private Long version;
    private Long accountVersion;

    public RefereeDTO(){}

    public RefereeDTO(Referee referee) {
        this.id = referee.getId();
        this.name = referee.getName();
        this.surname = referee.getSurname();
        this.email = referee.getAccount().getEmail();
        this.license = referee.getLicense().getType();
        this.version = referee.getVersion();
        this.accountVersion = referee.getAccount().getVersion();
    }
}
