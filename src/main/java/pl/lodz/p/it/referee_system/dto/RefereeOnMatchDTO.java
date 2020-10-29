package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.RefereeFunctionOnMatch;

@Data
public class RefereeOnMatchDTO {

    private String refereeName;
    private String function;

    public RefereeOnMatchDTO() {}

    public RefereeOnMatchDTO(RefereeFunctionOnMatch refereeFunctionOnMatch) {
        this.refereeName = refereeFunctionOnMatch.getReferee().getName() + " " + refereeFunctionOnMatch.getReferee().getSurname();
        this.function = refereeFunctionOnMatch.getMatchFunction().getFunctionName();
    }
}
