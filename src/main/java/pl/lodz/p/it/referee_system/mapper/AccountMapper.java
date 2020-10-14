package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.AccountEditDTO;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;

public class AccountMapper {

    static public Account map(AccountEditDTO accountDTO) {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setEmail(accountDTO.getEmail());
        account.setVersion(accountDTO.getVersion());
        return account;
    }
}
