package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.repository.LicenseRepository;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.repository.RoleRepository;
import pl.lodz.p.it.referee_system.service.RefereeService;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class RefereeServiceImpl implements RefereeService {

    @Autowired
    private RefereeRepository refereeRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LicenseRepository licenseRepository;

    public Referee getReferee(Long id) {
        return refereeRepository.findById(id).get();
    }
    public List<Referee> getAllReferees() {
        return refereeRepository.findAll();
    }

    @Override
    public void addReferee(Referee referee) {
        referee.getAccount().setPassword(passwordEncoder.encode(referee.getSurname()));
        referee.setRole(roleRepository.findByName("REFEREE"));
        referee.setLicense(licenseRepository.findByType("CANDIDATE"));
        refereeRepository.save(referee);
    }
}
