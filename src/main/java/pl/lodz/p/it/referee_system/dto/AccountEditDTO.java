package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class AccountEditDTO {

    private Long id;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String version;

}
