package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.referee_system.dto.PasswordDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.exception.AccountException;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.repository.AccountRepository;
import pl.lodz.p.it.referee_system.repository.EntityManagerRepository;
import pl.lodz.p.it.referee_system.service.AccountService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;

import java.util.List;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

   @Autowired
   private AccountRepository accountRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private EntityManagerRepository entityManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws  UsernameNotFoundException {
        Account account = accountRepository.findAccountByUsername(username).get();
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
        return this.accountRepository.findAccountByUsername(ContextUtills.getUsername()).orElseThrow();
    }

    @Override
    public void editAccount(Account account) {
        try {
            Account accountEntity = accountRepository.findAccountByUsername(ContextUtills.getUsername()).orElseThrow();
            entityManager.detach(accountEntity);
            accountEntity.setEmail(account.getEmail());
            accountEntity.setVersion(account.getVersion());
            accountRepository.save(accountEntity);
        }
        catch(OptimisticLockingFailureException e) {
            throw ApplicationException.exceptionForOptimisticLock(e);
        }
    }

    @Override
    public void changePassword(PasswordDTO passwordDTO) {
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmedPassword())) {
            throw AccountException.exceptionForNotMatchingPasswords();
        }
        Account accountEntity = accountRepository.findAccountByUsername(ContextUtills.getUsername()).orElseThrow();
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(),accountEntity.getPassword())) {
            throw AccountException.exceptionForIncorectPassword();
        }
        accountEntity.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
        accountRepository.save(accountEntity);
    }

    @Override
    public void resetPassword(Account account) {
        //wysylanie maila i odbieranie po linku
    }
}
