package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Account;

@Data
public class AccountDTO {

    private String username;
    private String email;
    private Long version;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.username = account.getUsername();
        this.email = account.getEmail();
        this.version = account.getVersion();
    }

}
