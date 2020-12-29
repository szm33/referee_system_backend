package pl.lodz.p.it.referee_system.dto;

import lombok.Data;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class RefereeCreateDTO implements Serializable {

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
    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String username;
}
