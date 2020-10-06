package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.referee_system.dto.*;
import pl.lodz.p.it.referee_system.mapper.AccountMapper;
import pl.lodz.p.it.referee_system.service.AccountService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;
import pl.lodz.p.it.referee_system.utill.TokenUtills;


import javax.validation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RestController
@Transactional(propagation = Propagation.NEVER)
public class AccountController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenUtills tokenUtills;
    @Autowired
    private AccountService accountService;
    @GetMapping("error")
    public String sad(){
        return "error";
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> createAuthenticationToken(@Valid @RequestBody AccountAuthenticationDTO authenticateAccount) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticateAccount.getUsername(), authenticateAccount.getPassword()));
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
        final String jwt = tokenUtills.generateToken(authenticateAccount.getUsername());
        tokenUtills.extractExpiration(jwt);
        TokenDTO token = new TokenDTO();
        token.setToken(jwt);
        token.setUsername(authenticateAccount.getUsername());
        return ResponseEntity.ok(token);
    }

    @GetMapping("account")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok((accountService.getAllAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList())));
    }


    @GetMapping("account/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new AccountDTO(accountService.getAccount(id)));
    }

    @GetMapping("locale")
    public ResponseEntity<StringDTO> getLocale() {
        return ResponseEntity.ok(new StringDTO(ContextUtills.getLanguage()));
    }

    @GetMapping("myaccount")
    public ResponseEntity<AccountDTO> getMyAccount() {
        return ResponseEntity.ok(new AccountDTO(accountService.getMyAccount()));
    }
    //edycja konta tylko dla wlasciciela edyja reszty danych imie,email,nazwisko ranga dla admina
    @PutMapping("account")
    public ResponseEntity<String> editAccount(@Valid @RequestBody AccountEditDTO account) {
        accountService.editAccount(AccountMapper.map(account));
        return ResponseEntity.ok("Successfully edited account ");
    }

    //haslo zmoenia tylko posiadacz
    @PostMapping("account/password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordDTO password) {
        try {
            accountService.changePassword(password);
            return ResponseEntity.ok("Password changed");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password do not match");
        }

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NoSuchElementException.class)
    public String noAccountFound() {
        return "Account not found";
    }
}

