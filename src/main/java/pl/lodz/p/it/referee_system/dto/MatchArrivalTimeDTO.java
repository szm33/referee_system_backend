package pl.lodz.p.it.referee_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MatchArrivalTimeDTO {

    public MatchArrivalTimeDTO() {}

    private Long id;
    private Long arrivalTime;
}
