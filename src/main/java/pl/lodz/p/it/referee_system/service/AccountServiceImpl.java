package pl.lodz.p.it.referee_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.repository.AccountRepository;

@Service
public class AccountServiceImpl implements UserDetailsService {

   @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("nie ma uzytkownika");
        }
        return account;
    }
}
