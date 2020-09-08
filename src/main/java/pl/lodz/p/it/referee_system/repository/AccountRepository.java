package pl.lodz.p.it.referee_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Account;

@Repository
@Transactional(propagation = Propagation.MANDATORY)
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByUsername(String username);
}
