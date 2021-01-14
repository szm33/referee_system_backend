package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.referee_system.dto.*;
import pl.lodz.p.it.referee_system.entity.Token;
import pl.lodz.p.it.referee_system.exception.ApplicationException;
import pl.lodz.p.it.referee_system.exception.ExceptionMessages;
import pl.lodz.p.it.referee_system.mapper.AccountMapper;
import pl.lodz.p.it.referee_system.service.AccountService;
import pl.lodz.p.it.referee_system.service.DictionariesService;
import pl.lodz.p.it.referee_system.utill.ContextUtills;
//import pl.lodz.p.it.referee_system.utill.EmailUtills;
import pl.lodz.p.it.referee_system.utill.TokenUtills;


import javax.mail.MessagingException;
import javax.validation.*;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@RestController
@Transactional(propagation = Propagation.NEVER)
public class AccountController {

    @Autowired
    private TokenUtills tokenUtills;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DictionariesService dictionariesService;

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<TokenDTO> createAuthenticationToken(
            @Valid @RequestBody AccountAuthenticationDTO authenticateAccount, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        TokenDTO tokenDTO = new TokenDTO();
        Token token = accountService.login(AccountMapper.map(authenticateAccount));
        tokenDTO.setJwt(tokenUtills.generateToken(token.getAccount(), token.getAccount().getReferee().getId()));
        tokenDTO.setRefreshToken(token.getRefreshToken());
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("refresh")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO tokenDTO) {
        Token token = accountService.refresh(Token.builder()
                .refreshToken(tokenDTO.getRefreshToken())
                .build());
        tokenDTO.setJwt(tokenUtills.generateToken(token.getAccount(), token.getAccount().getReferee().getId()));
        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity logout(@RequestBody TokenDTO token) {
        accountService.logout(Token.builder()
                .refreshToken(token.getRefreshToken())
                .build());
        return ResponseEntity.ok().build();
    }

    @GetMapping("account")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return ResponseEntity.ok((accountService.getAllAccounts().stream()
                .map(AccountDTO::new)
                .collect(Collectors.toList())));
    }


    @GetMapping("account/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new AccountDTO(accountService.getAccount(id)));
    }
//    @Autowired
//    private EmailUtills emailUtills;

    @GetMapping("locale")
    @PreAuthorize("permitAll()")
    public ResponseEntity<StringDTO> getLocale() throws MessagingException {
        return ResponseEntity.ok(new StringDTO(ContextUtills.getLanguage()));
    }

    @GetMapping("myaccount")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AccountDTO> getMyAccount() {
        return ResponseEntity.ok(new AccountDTO(accountService.getMyAccount()));
    }

    //edycja konta tylko dla wlasciciela edyja reszty danych imie,email,nazwisko ranga dla admina
    @PutMapping("account")
    @PreAuthorize("isAuthenticated()")
    @Validated
    public ResponseEntity editAccount(@RequestBody @Valid AccountEditDTO account, BindingResult result) {
        if(result.hasErrors()){
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        accountService.editAccount(AccountMapper.map(account));
        return ResponseEntity.ok().build();
    }

    @GetMapping("account/reset/{username}")
    @PreAuthorize("permitAll()")
    public ResponseEntity sendResetLink(@PathVariable String username) {
        if (username.isEmpty() || !username.matches("^[a-zA-Z0-9]+$")) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        accountService.sendResetLink(username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("validate/{link}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<StringDTO> validateResetLink(@PathVariable String link) {
        StringDTO stringDTO = new StringDTO("false");
        if(accountService.validateResetLink(link)){
            stringDTO.setValue("true");
        }
        return ResponseEntity.ok(stringDTO);
    }

    @PostMapping("account/reset")
    @PreAuthorize("permitAll()")
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordDTO resetPassword, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.VALIDATION_ERROR);
        }
        if(!resetPassword.getPassword().equals(resetPassword.getConfirmedPassword())){
            throw new ApplicationException(ExceptionMessages.PASSWORDS_NOT_THE_SAME);
        }
        accountService.resetPassword(AccountMapper.map(resetPassword));
        return ResponseEntity.ok().build();
    }

    //haslo zmoenia tylko posiadacz
    @PostMapping("account/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity changePassword(@Valid @RequestBody PasswordDTO password, BindingResult result) {
        if (result.hasErrors()) {
            throw new ApplicationException(ExceptionMessages.PASSWORD_VALIDATION_ERROR);
        }
        accountService.changePassword(password);
        return ResponseEntity.ok().build();
    }

    @GetMapping("dictionaries")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Dictionaries> getAllDictionaries() {
        return ResponseEntity.ok(dictionariesService.getAllDictionaries());
    }
}

