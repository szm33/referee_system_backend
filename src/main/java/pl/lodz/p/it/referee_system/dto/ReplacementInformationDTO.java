package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.ReplacementInformation;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

@Getter
@Setter
public class ReplacementInformationDTO {

    public ReplacementInformationDTO(ReplacementInformation replacementInformation) {
        this.id = replacementInformation.getId();
        this.match = new MatchDTO(replacementInformation.getRefereeFunctionOnMatch().getMatch());
        this.replaceFunction = replacementInformation.getRefereeFunctionOnMatch().getMatchFunction().getFunctionName();
        this.refereeCandidate = replacementInformation.getCandidates().stream()
                .anyMatch(candidate -> candidate.getRefereeForReplacement().getAccount().getUsername().equals(ContextUtills.getUsername()));
    }
    private Long id;
    private MatchDTO match;
    private boolean refereeCandidate;
    private String replaceFunction;
}
