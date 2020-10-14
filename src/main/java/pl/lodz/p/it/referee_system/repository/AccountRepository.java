package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Account;

import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findAccountByUsername(String username);
}
