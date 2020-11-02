package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.RefereeFunctionOnMatch;

@Data
public class RefereeOnMatchDTO {

    private String name;
    private String function;
    private Long id;


    public RefereeOnMatchDTO() {}

    public RefereeOnMatchDTO(RefereeFunctionOnMatch refereeFunctionOnMatch) {
        this.name = refereeFunctionOnMatch.getReferee().getName() + " " + refereeFunctionOnMatch.getReferee().getSurname();
        this.function = refereeFunctionOnMatch.getMatchFunction().getFunctionName();
        this.id = refereeFunctionOnMatch.getReferee().getId();
    }
}
