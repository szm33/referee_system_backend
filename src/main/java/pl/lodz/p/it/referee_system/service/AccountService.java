package pl.lodz.p.it.referee_system.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.entity.Token;

import java.util.List;

public interface AccountService extends UserDetailsService {

    @Secured("ROLE_ADMIN")
    Account getAccount(Long id);

    @PreAuthorize("permitAll()")
    List<Account> getAllAccounts();

    @PreAuthorize("isAuthenticated()")
    Account getMyAccount();

    @PreAuthorize("isAuthenticated()")
    void editAccount(Account account);

    @PreAuthorize("isAuthenticated()")
    void changePassword(PasswordDTO passwordDTO);

    @PreAuthorize("permitAll()")
    void sendResetLink(String username);

    @PreAuthorize("permitAll()")
    boolean validateResetLink(String link);

    @PreAuthorize("permitAll()")
    void resetPassword(Account account);

    @PreAuthorize("isAnonymous()")
    Token login(Account account);

    @PreAuthorize("isAuthenticated()")
    void logout(Token token);

    @PreAuthorize("isAnonymous()")
    Token refresh(Token token);
}
