package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReplaceInformationsDetailsDTO {

    public ReplaceInformationsDetailsDTO(ReplaceInformations replaceInformations) {
        this.id = replaceInformations.getId();
        this.refereeName = replaceInformations.getRefereeFunctionOnMatch().getReferee().getName() + " " +
                replaceInformations.getRefereeFunctionOnMatch().getReferee().getSurname();
        this.refereesForReplacement = replaceInformations.getCandidates().stream()
                .map(ReplaceRefereeDTO::new).collect(Collectors.toList());
        this.version = ContextUtills.encrypt(replaceInformations.getVersion());
    }

    private Long id;
    private String refereeName;
    private List<ReplaceRefereeDTO> refereesForReplacement;
    private String version;
}
