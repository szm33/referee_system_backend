package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.RefereeCreateDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.entity.Referee;

public class RefereeCreateMapper {

    public static Referee map(RefereeCreateDTO refereeDTO) {
        Referee referee = new Referee();
        Account account = new Account();
        referee.setName(refereeDTO.getName());
        referee.setSurname(refereeDTO.getSurname());
        account.setEmail(refereeDTO.getEmail());
        account.setUsername(refereeDTO.getUserName());
        referee.setAccount(account);
        return referee;
    }
}
