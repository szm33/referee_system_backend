package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.referee_system.dto.AccountDTO;
import pl.lodz.p.it.referee_system.entity.Account;
import pl.lodz.p.it.referee_system.repository.AccountRepository;
import pl.lodz.p.it.referee_system.utill.TokenUtills;

import javax.validation.Valid;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtills tokenUtills;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("error")
    public String sad(){
        return "error";
    }

    @GetMapping("/")
    public String sasd(){
        return "start";
    }

    @PostMapping("/registration")
    public String registry(@RequestBody AccountDTO authenticateAccount) {
        Account account = new Account();
        account.setId(11L);
        account.setUsername(authenticateAccount.getUsername());
        account.setPassword(passwordEncoder.encode(authenticateAccount.getPassword()));
        account.setActive(true);
        accountRepository.save(account);
        return "sukces";
    }

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@Valid @RequestBody AccountDTO authenticateAccount) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateAccount.getUsername(), authenticateAccount.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
        final String jwt = tokenUtills.generateToken(authenticateAccount.getUsername());
        tokenUtills.extractExpiration(jwt);
        return new ResponseEntity<>(jwt, HttpStatus.OK);
    }
}

