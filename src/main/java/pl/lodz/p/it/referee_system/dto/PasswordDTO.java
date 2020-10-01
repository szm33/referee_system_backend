package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

@Data
public class PasswordDTO {

    private String oldPassword;
    private String newPassword;
    private String confirmedPassword;
}
