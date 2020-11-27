package pl.lodz.p.it.referee_system.dto;

import lombok.Data;

@Data
public class TokenDTO {

    private String jwt;
    private String refreshToken;
}
