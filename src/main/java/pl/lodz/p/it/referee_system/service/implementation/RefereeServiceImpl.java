package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.repository.LicenseRepository;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.repository.RoleRepository;
import pl.lodz.p.it.referee_system.repository.EntityManagerRepository;
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
    @Autowired
    private EntityManagerRepository entityManager;

    public Referee getReferee(Long id) {
        return refereeRepository.findById(id).orElseThrow();
    }

    public List<Referee> getAllReferees() {
        return refereeRepository.findAll();
    }

    @Override
    public void addReferee(Referee referee) {
        try {
        referee.getAccount().setPassword(passwordEncoder.encode(referee.getSurname()));
        referee.setRole(roleRepository.findByName("REFEREE"));
        referee.setLicense(licenseRepository.findByType("CANDIDATE"));
        referee.getAccount().setActive(true);
        refereeRepository.save(referee);
        refereeRepository.flush();
        }
        catch (DataIntegrityViolationException e) {
            throw new ApplicationException(ExceptionMessages.USERNAME_NOT_UNIQUE);
        }
    }

    @Override
    public void editReferee(Referee referee) {
        try {
            Referee refereeEntity = refereeRepository.findById(referee.getId()).orElseThrow();
            entityManager.detach(refereeEntity);
            refereeEntity.setName(referee.getName());
            refereeEntity.setSurname(referee.getSurname());
            refereeEntity.setLicense(licenseRepository.findByType(referee.getLicense().getType()));
            refereeEntity.getAccount().setEmail(referee.getAccount().getEmail());
            refereeEntity.getAccount().setVersion(referee.getAccount().getVersion());
            refereeEntity.setVersion(referee.getVersion());
            refereeRepository.save(refereeEntity);
        }
        catch (OptimisticLockingFailureException e) {
            throw new ApplicationException(ExceptionMessages.OPTIMISTIC_LOCK_PROBLEM);
        }
    }

    @Override
    public void changeActiveStatus(Long id) {
        Referee referee = refereeRepository.findById(id).orElseThrow();
        referee.getAccount().setActive(!referee.getAccount().isActive());
        refereeRepository.save(referee);
    }
}
