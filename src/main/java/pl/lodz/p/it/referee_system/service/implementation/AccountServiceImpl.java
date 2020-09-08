package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.repository.AccountRepository;
import pl.lodz.p.it.referee_system.service.AccountService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

   @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("nie ma uzytkownika");
        }
        return account;
    }

    @Override
    public void registerAccount(Account account) {
        accountRepository.save(account);
    }
}
