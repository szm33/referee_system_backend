package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

@Data
public class ResetPasswordDTO {

        private String password;
        private String link;
        private String confirmedPassword;
}
