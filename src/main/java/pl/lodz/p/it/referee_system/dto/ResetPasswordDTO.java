package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordDTO {

        @Length(min = 8)
        private String password;
        @NotEmpty
        @Pattern(regexp = "^[0-9]+$")
        private String link;
        @Length(min = 8)
        private String confirmedPassword;
}
