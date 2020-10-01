package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AccountEditDTO {

    private Long id;
    @Email
    private String email;
    private Long version;

}
