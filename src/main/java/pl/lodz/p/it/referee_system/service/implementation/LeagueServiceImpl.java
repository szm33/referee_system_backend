package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.League;
import pl.lodz.p.it.referee_system.repository.LeagueRepository;
import pl.lodz.p.it.referee_system.service.LeagueService;

import java.util.List;

@Service
public class LeagueServiceImpl implements LeagueService {

    @Autowired
    private LeagueRepository leagueRepository;


    @Override
    public List<League> getAllLeagues() {
        return leagueRepository.findAll();
    }
}
