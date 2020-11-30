package pl.lodz.p.it.referee_system.service;

import org.springframework.security.access.annotation.Secured;
import pl.lodz.p.it.referee_system.entity.League;

import java.util.List;

public interface LeagueService {

    @Secured("ROLE_ADMIN")
    List<League> getAllLeagues();
}
