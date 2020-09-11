package pl.lodz.p.it.referee_system.dto;

import lombok.Data;
import pl.lodz.p.it.referee_system.entity.Account;

@Data
public class AccountDTO {

    private String username;
    private String email;

    public AccountDTO() {
    }

    public AccountDTO(Account account) {
        this.username = account.getUsername();
        this.email = account.getEmail();
    }

}
