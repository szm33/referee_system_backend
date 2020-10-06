package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.lodz.p.it.referee_system.entity.League;

public interface LeagueRepository extends JpaRepository<League, Long> {

    League findByName(String name);
}
