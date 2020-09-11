package pl.lodz.p.it.referee_system.service;

import pl.lodz.p.it.referee_system.entity.Referee;

import java.util.List;

public interface RefereeService {

    Referee getReferee(Long id);

    List<Referee> getAllReferees();

    void addReferee(Referee referee);

    void editReferee(Referee referee);
}
