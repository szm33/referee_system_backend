package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.dto.Dictionaries;
import pl.lodz.p.it.referee_system.repository.LeagueRepository;
import pl.lodz.p.it.referee_system.repository.LicenseRepository;
import pl.lodz.p.it.referee_system.repository.MatchFunctionRepository;
import pl.lodz.p.it.referee_system.service.DictionariesService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
@Retryable( value = Exception.class,
        maxAttemptsExpression = "${retry.maxAttempts}",
        backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
public class DictionariesServiceImpl implements DictionariesService {

    @Autowired
    private LeagueRepository leagueRepository;
    @Autowired
    private LicenseRepository licenseRepository;
    @Autowired
    private MatchFunctionRepository matchFunctionRepository;

    @Override
    public Dictionaries getAllDictionaries() {
        return Dictionaries.builder()
                .leagues(leagueRepository.findAll())
                .licenses(licenseRepository.findAll())
                .matchFunctions(matchFunctionRepository.findAll())
                .build();
    }
}
