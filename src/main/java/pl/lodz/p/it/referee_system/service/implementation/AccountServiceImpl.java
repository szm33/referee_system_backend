package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.repository.AccountRepository;
import pl.lodz.p.it.referee_system.service.AccountService;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

   @Autowired
    AccountRepository accountRepository;

   @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username);
        if(account == null) {
            throw new UsernameNotFoundException("nie ma uzytkownika");
        }
        return account;
    }

    @Override
    public Account getAccount(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getMyAccount() {
        //wyciagamy z tokena user name i bierzemy konto po loginie
        return this.accountRepository
                .findAccountByUsername(((Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Override
    public void editAccount(Account account) {
        //wyciaganie po nazwie
        Account accountEntity = accountRepository.findAccountByUsername(((Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        accountEntity.setEmail(account.getEmail());
        accountEntity.setVersion(account.getVersion());
        accountRepository.save(accountEntity);
    }

    @Override
    public void changePassword(PasswordDTO passwordDTO) throws Exception {
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmedPassword())) {
            throw new Exception("dasd1");
        }
        Account accountEntity = accountRepository.findAccountByUsername(((Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(),accountEntity.getPassword())) {
            throw new Exception("dasd2");
        }
        accountEntity.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        accountRepository.save(accountEntity);
    }

    @Override
    public void resetPassword(Account account) {
        //wysylanie maila i odbieranie po linku
    }

    @Override
    public void changeActiveStatus(Long id) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.setActive(!account.isActive());
        accountRepository.save(account);
    }
}
