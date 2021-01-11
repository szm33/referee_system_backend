package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
public class AccountAuthenticationDTO {

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;
    @Min(8)
    private String password;
}
