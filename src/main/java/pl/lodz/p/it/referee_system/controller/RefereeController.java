package pl.lodz.p.it.referee_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.referee_system.dto.RefereeDTO;
import pl.lodz.p.it.referee_system.entity.Referee;
import pl.lodz.p.it.referee_system.repository.RefereeRepository;
import pl.lodz.p.it.referee_system.service.AccountServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("referee")
public class RefereeController {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private RefereeRepository refereeRepository;

    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping
    public List<Referee> getAllReferee() {
        return refereeRepository.findAll();
    }

    @GetMapping("{id}")
    public RefereeDTO getReferee(@PathVariable("id") Long id) {

        return new RefereeDTO(refereeRepository.findById(id).get());
    }

    @GetMapping("test")
    public UserDetails getReferee1() {
        return accountService.loadUserByUsername("sss");
    }


    @GetMapping("test1")
    public String getReferee11() {

        return "test1";
    }

    @PostMapping
    public String addReferee(@Valid @RequestBody RefereeDTO refereeDTO) {
//        refereeRepository.save(refereeDTO);
        return "OK";
    }
}
