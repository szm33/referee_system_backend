package pl.lodz.p.it.referee_system.mapper;

import pl.lodz.p.it.referee_system.dto.RefereeCreateDTO;
import pl.lodz.p.it.referee_system.dto.RefereeDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.entity.License;
import pl.lodz.p.it.referee_system.entity.Referee;

public class RefereeMapper {

    public static Referee map(RefereeCreateDTO refereeDTO) {
        Referee referee = new Referee();
        Account account = new Account();
        referee.setName(refereeDTO.getName());
        referee.setSurname(refereeDTO.getSurname());
        account.setEmail(refereeDTO.getEmail());
        account.setUsername(refereeDTO.getUsername());
        referee.setAccount(account);
        return referee;
    }

    public static Referee map(RefereeDTO refereeDTO) {
        Referee referee = new Referee();
        Account account = new Account();
        License license = new License();
        license.setType(refereeDTO.getLicense());
        referee.setId(refereeDTO.getId());
        referee.setName(refereeDTO.getName());
        referee.setSurname(refereeDTO.getSurname());
        account.setEmail(refereeDTO.getEmail());
        account.setVersion(refereeDTO.getAccountVersion());
        referee.setLicense(license);
        referee.setAccount(account);
        referee.setVersion(refereeDTO.getVersion());
        return referee;
    }
}
