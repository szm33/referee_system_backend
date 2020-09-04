package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.lodz.p.it.referee_system.entity.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Long> {
}
