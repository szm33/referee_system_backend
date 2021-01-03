package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmReplacemnetDTO {

    private Long replaceInformationsId;
    private Long confirmedCandidateId;
    private String version;
}
