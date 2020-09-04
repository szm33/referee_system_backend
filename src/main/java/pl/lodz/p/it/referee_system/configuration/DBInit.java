package pl.lodz.p.it.referee_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.repository.AccountRepository;

@Service
public class DBInit implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;


    @Override
    public void run(String... args) throws Exception {
        accountRepository.save(new Account(5L,"sss",passwordEncoder.encode("123"),true,"ADMIN"));
        accountRepository.save(new Account(6L,"s",passwordEncoder.encode("1"),true,"USER"));
        accountRepository.save(new Account(7L,"ss",passwordEncoder.encode("1"),true,"USER"));
    }
}
