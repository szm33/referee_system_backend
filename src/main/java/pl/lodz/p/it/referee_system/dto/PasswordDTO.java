package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class PasswordDTO {

    @NotEmpty
    @Length(min = 8)
    private String oldPassword;
    @NotEmpty
    @Length(min = 8)
    private String newPassword;
    @NotEmpty
    @Length(min = 8)
    private String confirmedPassword;
}
