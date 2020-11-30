package pl.lodz.p.it.referee_system.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import pl.lodz.p.it.referee_system.entity.Referee;

import java.util.List;

public interface RefereeService {

    @PreAuthorize("permitAll()")
    Referee getReferee(Long id);

    @PreAuthorize("permitAll()")
    List<Referee> getAllReferees();

    @Secured("ROLE_ADMIN")
    void addReferee(Referee referee);

    @Secured("ROLE_ADMIN")
    void editReferee(Referee referee);

    @Secured("ROLE_ADMIN")
    void changeActiveStatus(Long id);
}
