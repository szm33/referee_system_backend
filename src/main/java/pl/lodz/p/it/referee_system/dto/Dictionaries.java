package pl.lodz.p.it.referee_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.lodz.p.it.referee_system.entity.League;
import pl.lodz.p.it.referee_system.entity.License;
import pl.lodz.p.it.referee_system.entity.MatchFunction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Dictionaries {

    public Dictionaries(){}

    private List<League> leagues = new ArrayList<>();
    private List<License> licenses = new ArrayList<>();
    private List<MatchFunction> matchFunctions = new ArrayList<>();
}
