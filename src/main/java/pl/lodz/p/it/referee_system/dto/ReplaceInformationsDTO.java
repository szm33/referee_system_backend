package pl.lodz.p.it.referee_system.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ReplaceInformationsDTO {

    private Long id;
    private Long arrivalTime;
}
