package pl.lodz.p.it.referee_system.dto;

import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.ReplaceInformations;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

@Getter
@Setter
public class ReplaceInformationsDTO {

    public ReplaceInformationsDTO(ReplaceInformations replaceInformations) {
        this.id = replaceInformations.getId();
        this.match = new MatchDTO(replaceInformations.getRefereeFunctionOnMatch().getMatch());
        this.replaceFunction = replaceInformations.getRefereeFunctionOnMatch().getMatchFunction().getFunctionName();
        this.refereeCandidate = replaceInformations.getCandidates().stream()
                .anyMatch(candidate -> candidate.getRefereeForReplacement().getAccount().getUsername().equals(ContextUtills.getUsername()));
    }
    private Long id;
    private MatchDTO match;
    private boolean refereeCandidate;
    private String replaceFunction;
}
