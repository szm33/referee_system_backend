package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.ReplacementInformation;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ReplacementInformationDetailsDTO {

    public ReplacementInformationDetailsDTO(ReplacementInformation replacementInformation) {
        this.id = replacementInformation.getId();
        this.refereeName = replacementInformation.getRefereeFunctionOnMatch().getReferee().getName() + " " +
                replacementInformation.getRefereeFunctionOnMatch().getReferee().getSurname();
        this.refereesForReplacement = replacementInformation.getCandidates().stream()
                .map(ReplaceRefereeDTO::new).collect(Collectors.toList());
        this.version = ContextUtills.encrypt(replacementInformation.getVersion());
    }

    private Long id;
    private String refereeName;
    private List<ReplaceRefereeDTO> refereesForReplacement;
    private String version;
}
