package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.ReplacementCandidate;

@Setter
@Getter
public class ReplaceRefereeDTO {

    public ReplaceRefereeDTO(ReplacementCandidate referee) {
        this.name = referee.getRefereeForReplacement().getName() + " " + referee.getRefereeForReplacement().getSurname();
        this.refereeId = referee.getRefereeForReplacement().getId();
        this.arrivalTime = referee.getArrivalTime();
        this.id = referee.getRefereeForReplacement().getId();
    }

    private Long id;
    private String name;
    private Long refereeId;
    private Long arrivalTime;
}
