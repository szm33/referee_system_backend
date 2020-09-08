package pl.lodz.p.it.referee_system.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.lodz.p.it.referee_system.entity.Account;

public interface AccountService extends UserDetailsService {
    void registerAccount(Account account);
}
