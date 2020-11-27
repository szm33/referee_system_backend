package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.AccountAuthenticationDTO;
import pl.lodz.p.it.referee_system.dto.AccountEditDTO;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.dto.ResetPasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

public class AccountMapper {

    static public Account map(AccountEditDTO accountDTO) {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setEmail(accountDTO.getEmail());
        account.setVersion(ContextUtills.decrypt(accountDTO.getVersion()));
        return account;
    }

    static public Account map(ResetPasswordDTO resetPassword) {
        Account account = new Account();
        account.setResetLink(resetPassword.getLink());
        account.setPassword(resetPassword.getPassword());
        return account;
    }

    static public Account map(AccountAuthenticationDTO accountDTO) {
        Account account = new Account();
        account.setPassword(accountDTO.getPassword());
        account.setUsername(accountDTO.getUsername());
        return account;
    }
}
