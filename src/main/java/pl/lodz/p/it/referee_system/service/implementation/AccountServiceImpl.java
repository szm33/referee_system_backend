package pl.lodz.p.it.referee_system.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import pl.lodz.p.it.referee_system.utill.ResetLinkSender;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
public class AccountServiceImpl implements AccountService {

   @Autowired
   private AccountRepository accountRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private EntityManagerRepository entityManager;

    @Value("${expiry.reset.link}")
    private String expiryResetLink;

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
    public void sendResetLink(String username) {
        accountRepository.findAccountByUsername(username).ifPresent(data -> {
            //tworzenie linku zapis do bazy
            String token = String.valueOf(new Date().getTime());
            String link = ContextUtills.createResetLink(token);
            data.setResetLink(token);
            accountRepository.save(data);
            ResetLinkSender resetLinkSender = new ResetLinkSender(data.getEmail(), link);
            resetLinkSender.start();
        });
    }

    @Override
    public boolean validateResetLink(String link) {
        Optional<Account> account = accountRepository.findAccountByResetLink(link);
        if(account.isPresent()){
            Date expiryDate = new Date(Long.valueOf(account.get().getResetLink()) + Long.valueOf(expiryResetLink));
            if(expiryDate.after(new Date())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetPassword(Account account) {
        accountRepository.findAccountByResetLink(account.getResetLink()).ifPresentOrElse(
                accountEntity -> {
                    accountEntity.setResetLink(null);
                    accountEntity.setPassword(passwordEncoder.encode(account.getPassword()));
                    accountRepository.save(accountEntity);
                },
                () -> {
                    throw new NoSuchElementException("No value present");
                });
    }
}
