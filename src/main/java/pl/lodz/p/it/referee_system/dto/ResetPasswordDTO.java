package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ResetPasswordDTO {

        @Min(8)
        private String password;
        @NotEmpty
        @Pattern(regexp = "^[0-9]+$")
        private String link;
        @Min(8)
        private String confirmedPassword;
}
