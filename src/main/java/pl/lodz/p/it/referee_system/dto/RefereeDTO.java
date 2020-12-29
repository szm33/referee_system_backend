package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class RefereeDTO {

    private Long id;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String surname;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z_0-9]+$")
    private String license;
    @NotEmpty
    private String version;
    @NotEmpty
    private String accountVersion;
    private String username;

    public RefereeDTO(){}

    public RefereeDTO(Referee referee) {
        this.id = referee.getId();
        this.name = referee.getName();
        this.surname = referee.getSurname();
        this.email = referee.getAccount().getEmail();
        this.license = referee.getLicense().getType();
        this.version = ContextUtills.encrypt(referee.getVersion());
        this.accountVersion =  ContextUtills.encrypt(referee.getAccount().getVersion());
        this.username = referee.getAccount().getUsername();
    }
}
