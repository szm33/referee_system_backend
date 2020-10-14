package pl.lodz.p.it.referee_system.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.entity.Referee;

import java.util.List;

public interface AccountService extends UserDetailsService {

    Account getAccount(Long id);

    List<Account> getAllAccounts();

    Account getMyAccount();

    void editAccount(Account account);

    void changePassword(PasswordDTO passwordDTO);

    void resetPassword(Account account);
}
