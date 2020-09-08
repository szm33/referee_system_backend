package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.service.RefereeService;

import java.util.List;

@Service
public class RefereeServiceImpl implements RefereeService {

    @Autowired
    private RefereeRepository refereeRepository;

    public Referee getReferee(Long id) {
        return refereeRepository.findById(id).get();
    }
    public List<Referee> getAllReferees() {
        return refereeRepository.findAll();
    }
}
