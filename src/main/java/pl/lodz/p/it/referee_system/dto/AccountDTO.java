package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

@Data
public class AccountDTO {

    private String username;
    private String email;
    private String  version;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.version = ContextUtills.encrypt(account.getVersion());
    }

}
