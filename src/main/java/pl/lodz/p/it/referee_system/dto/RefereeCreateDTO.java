package pl.lodz.p.it.referee_system.dto;

import lombok.Data;


import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class RefereeCreateDTO implements Serializable {

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String email;
    @NotEmpty
    private String userName;
}
