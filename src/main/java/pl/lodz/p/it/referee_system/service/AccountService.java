package pl.lodz.p.it.referee_system.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.Token;

import java.util.List;

public interface AccountService extends UserDetailsService {

    Account getAccount(Long id);

    List<Account> getAllAccounts();

    Account getMyAccount();

    void editAccount(Account account);

    void changePassword(PasswordDTO passwordDTO);

    void sendResetLink(String username);

    boolean validateResetLink(String link);

    void resetPassword(Account account);

    Token login(Account account);

    void logout(Token token);

    Token refresh(Token token);
}
